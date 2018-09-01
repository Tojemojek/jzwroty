package pl.kostrowski.doka.jzwroty.service.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.dto.DiscosResultLinesDto;
import pl.kostrowski.doka.jzwroty.exceptions.DataProblemException;
import pl.kostrowski.doka.jzwroty.mappings.MyMappings;
import pl.kostrowski.doka.jzwroty.service.FolderUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.getFileSaveFolder;
import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.getResultSheetName;
import static pl.kostrowski.doka.jzwroty.service.excel.ParseFromExcelHelper.*;

@Service
public class ToDiscosCreator {

    private final static Logger LOG = LoggerFactory.getLogger(ToDiscosCreator.class);
    private final int FIRST_ROW_WITH_DATA = 10;
    private final int FIRST_COLUMN = 0;
    private final int LAST_COLUMN = 12;
    private final int SHEET_NAME_MAX_LENGTH = 16;
    private final String FILE_NAME_STRING = "fileName";
    private final String FOLDER_NAME_STRING = "folderName";
    private final String SHEET_NAME_STRING = "sheetName";
    private final String RESULT_FILE_NAME = "input_for_discos";


    private final Map<String, Integer> columnMappings = MyMappings.getInstance().getResultColumnMapping();

    FolderUtils folderUtils;

    @Autowired
    public ToDiscosCreator(FolderUtils folderUtils) {
        this.folderUtils = folderUtils;
    }


    public void createResultFile(String folderName) {
        String path = "." + File.separator + getFileSaveFolder() + File.separator + folderName + File.separator;
        Set<DiscosResultLinesDto> uniqueInputToDiscos = createUniqueInputToDiscos(folderName);

        String fileName = RESULT_FILE_NAME + ".xlsx";
        LOG.info("Tworzę plik wynikowy " + fileName);
        Workbook wb = new XSSFWorkbook();

        Sheet sheet = wb.createSheet(getResultSheetName());

        Row menuRow = sheet.createRow(0);

        int cellNum = 0;
        int rowNum = 0;

        menuRow.createCell(cellNum++).setCellValue("project");
        menuRow.createCell(cellNum++).setCellValue("jobsite");
        menuRow.createCell(cellNum++).setCellValue("MPG");
        menuRow.createCell(cellNum++).setCellValue("return date");
        menuRow.createCell(cellNum++).setCellValue("comment");
        menuRow.createCell(cellNum++).setCellValue("days (from today)");

        rowNum++;

        for (DiscosResultLinesDto ud : uniqueInputToDiscos) {
            cellNum = 0;
            Row row = sheet.createRow(rowNum++);

            row.createCell(cellNum++).setCellValue(ud.getProjectNumber());
            row.createCell(cellNum++).setCellValue(ud.getJobsite());
            row.createCell(cellNum++).setCellValue(ud.getMpgNumber());
            row.createCell(cellNum++).setCellValue(Date.valueOf(ud.getReturnDate()));
            row.createCell(cellNum++).setCellValue(ud.getComments());
            row.createCell(cellNum++).setCellFormula("D2-TODAY()");
        }

        for (int j = FIRST_COLUMN; j <= LAST_COLUMN; j++) {
            sheet.autoSizeColumn(j);
        }

        try (OutputStream fileOut = new FileOutputStream(path + fileName)) {
            wb.write(fileOut);

        } catch (Exception e) {
            LOG.error("Nie udało się zapisać pliku wynikowego");
        }

    }

    public Set<DiscosResultLinesDto> createUniqueInputToDiscos(String folderName) {

        Map<String, DiscosResultLinesDto> result = new TreeMap<>();

        List<DiscosResultLinesDto> inputToDiscos = createInputToDiscos(folderName);

        for (DiscosResultLinesDto inp : inputToDiscos) {
            String tmpKey = inp.getProjectNumber() + inp.getJobsite() + inp.getMpgNumber();
            if (result.containsKey(tmpKey)) {
                DiscosResultLinesDto fromTree = result.get(tmpKey);
                if (inp.getSalesmanCommissionPercentage() > fromTree.getSalesmanCommissionPercentage()) {
                    result.replace(tmpKey, inp);
                } else if (inp.getSalesmanCommissionPercentage().equals(fromTree.getSalesmanCommissionPercentage())
                        && inp.getSalesmanTurnoverPercentage() > fromTree.getSalesmanTurnoverPercentage()) {
                    result.replace(tmpKey, inp);
                }
            } else {
                result.put(tmpKey, inp);
            }
        }

        Set<DiscosResultLinesDto> finalResult = new TreeSet<>(result.values());

        return finalResult;

    }

    protected List<DiscosResultLinesDto> createInputToDiscos(String folderName) {

        Map<String, String> localContext = new HashMap<>();
        List<DiscosResultLinesDto> fileResultLinesDtos = new LinkedList<>();

        List<File> files = folderUtils.listOfResutFiles(folderName);

        localContext.put(FOLDER_NAME_STRING, folderName);

        for (File file : files) {
            localContext.put(FILE_NAME_STRING, file.getName());
            List<DiscosResultLinesDto> discosResultLinesDtos = parseSingleFile(file, localContext);
            fileResultLinesDtos.addAll(discosResultLinesDtos);
        }
        Collections.sort(fileResultLinesDtos);
        return fileResultLinesDtos;
    }

    private List<DiscosResultLinesDto> parseSingleFile(File inputFile, Map<String, String> localContext) {

        Workbook projectWorkbook;
        try {
            projectWorkbook = new XSSFWorkbook(inputFile);
        } catch (Exception e) {
            e.printStackTrace();
            throw new DataProblemException("Nie umiem odczytać pliku " + inputFile.getName());
        }

        Iterator<Sheet> sheetIterator = projectWorkbook.sheetIterator();

        List<DiscosResultLinesDto> resultLinesDtos = new LinkedList<>();

        while (sheetIterator.hasNext()) {
            Sheet next = sheetIterator.next();
            if (next.getSheetName().equals("menu")) {
                continue;
            }
            localContext.put(SHEET_NAME_STRING, next.getSheetName());
            List<DiscosResultLinesDto> discosResultLinesDtos = parseSingleSheet(next, localContext);
            resultLinesDtos.addAll(discosResultLinesDtos);
        }

        return resultLinesDtos;
    }


    private List<DiscosResultLinesDto> parseSingleSheet(Sheet sheet, Map<String, String> localContext) {

        Iterator<Row> rowIterator = sheet.rowIterator();
        String salesmanCodeTmp = parseStringFromCell(sheet.getRow(0).getCell(0));
        String sheetName = sheet.getSheetName();

        List<DiscosResultLinesDto> discosResultLinesDtos = new LinkedList<>();

        String today = LocalDate.now().toString();

        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (row.getRowNum() < FIRST_ROW_WITH_DATA || row.getLastCellNum() < LAST_COLUMN + 1) {
                continue;
            }

            DiscosResultLinesDto discosResultLinesDto = new DiscosResultLinesDto();


            String projectNumberTmp = parseStringFromCell(row.getCell(columnMappings.get("projectNumber")));
            String jobsiteTmp = parseStringFromCell(row.getCell(columnMappings.get("jobsite")));
            String mpgNumberTmp = parseStringFromCell(row.getCell(columnMappings.get("mpgNumber")));
            LocalDate returnDateTmp = parseLocalDateFromCell(row.getCell(columnMappings.get("returnDate")));
            Double salesmanTurnoverPercentageTmp = parseDoubleFromCell(row.getCell(columnMappings.get("salesmanTurnoverPercentage")));
            Double salesmanCommissionPercentageTmp = parseDoubleFromCell(row.getCell(columnMappings.get("salesmanCommissionPercentage")));

            if (projectNumberTmp == null
                    || jobsiteTmp == null
                    || mpgNumberTmp == null
                    || returnDateTmp == null
                    || StringUtils.isEmpty(projectNumberTmp)
                    || StringUtils.isEmpty(jobsiteTmp)
                    || StringUtils.isEmpty(mpgNumberTmp)) {
                continue;
            }

            discosResultLinesDto.setSalesmanCode(salesmanCodeTmp);
            discosResultLinesDto.setFileName(localContext.get(FILE_NAME_STRING));
            discosResultLinesDto.setTabName(sheetName);

            discosResultLinesDto.setProjectNumber(projectNumberTmp);
            discosResultLinesDto.setJobsite(jobsiteTmp);
            discosResultLinesDto.setMpgNumber(mpgNumberTmp);
            discosResultLinesDto.setReturnDate(returnDateTmp);
            discosResultLinesDto.setSalesmanTurnoverPercentage(salesmanTurnoverPercentageTmp);
            discosResultLinesDto.setSalesmanCommissionPercentage(salesmanCommissionPercentageTmp);

            StringBuilder sb = new StringBuilder();
            sb.append("Plik ").append(localContext.get(FILE_NAME_STRING)).append("; ");
            sb.append("Zakładka ").append(sheetName).append("; ");
            sb.append("Data przetwarzania ").append(today).append("; ");

            discosResultLinesDto.setComments(sb.toString());

            discosResultLinesDtos.add(discosResultLinesDto);
        }

        return discosResultLinesDtos;
    }
}
