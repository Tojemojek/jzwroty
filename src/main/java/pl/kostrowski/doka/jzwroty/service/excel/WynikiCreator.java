package pl.kostrowski.doka.jzwroty.service.excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.dao.CustomQueries;
import pl.kostrowski.doka.jzwroty.dao.SalesmanDao;
import pl.kostrowski.doka.jzwroty.dto.ProjectLinesDto;
import pl.kostrowski.doka.jzwroty.model.db.SalesmanDb;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.getFileSaveFolder;

@Service
public class WynikiCreator {

    private final static Logger LOG = LoggerFactory.getLogger(WynikiCreator.class);
    private final int FIRST_ROW_WITH_DATA = 10;
    private final int FIRST_COLUMN = 0;
    private final int LAST_COLUMN = 12;
    private final int SHEET_NAME_MAX_LENGTH = 16;
    private final String RESULT_PREFIX = "wynik_";


    private CustomQueries customQueries;
    private SalesmanDao salesmanDao;

    @Autowired
    public WynikiCreator(CustomQueries customQueries, SalesmanDao salesmanDao) {
        this.customQueries = customQueries;
        this.salesmanDao = salesmanDao;
    }

    public void createResultFile(String folderName) {

        String path = "." + File.separator + getFileSaveFolder() + File.separator + folderName + File.separator;

        List<SalesmanDb> allSalesman = salesmanDao.findAll();

        Map<String, List<SalesmanDb>> salesmanByBranch = new TreeMap<>();

        for (SalesmanDb salesmanDb : allSalesman) {
            if (salesmanByBranch.containsKey(StringUtils.chomp(salesmanDb.getBranchName()))) {
                salesmanByBranch.get(salesmanDb.getBranchName()).add(salesmanDb);
            } else {
                List<SalesmanDb> tmpSDB = new LinkedList<>();
                tmpSDB.add(salesmanDb);
                salesmanByBranch.put(StringUtils.chomp(salesmanDb.getBranchName()), tmpSDB);
            }
        }

        for (Map.Entry<String, List<SalesmanDb>> entry : salesmanByBranch.entrySet()) {

            String fileName = RESULT_PREFIX + entry.getKey() + ".xlsx";
            LOG.info("Tworzę plik wynikowy " + fileName);
            Workbook wb = new XSSFWorkbook();
            List<SalesmanDb> salesmanFromBranch = entry.getValue();

            for (SalesmanDb salesmanDb : salesmanFromBranch) {

                List<ProjectLinesDto> linesForSalesman = customQueries.getLinesForSalesman(salesmanDb.getSalesmanCode());

                if (linesForSalesman.size() == 0) {
                    continue;
                }

                String chomp = StringUtils.chomp(salesmanDb.getSalesmanCode() + " " + salesmanDb.getSalesmanName());
                int substringLength = chomp.length() > SHEET_NAME_MAX_LENGTH ? SHEET_NAME_MAX_LENGTH : chomp.length();
                LOG.debug("Tworzę zakładkę " + chomp.substring(0, substringLength));
                String sheetName = chomp.substring(0, substringLength);

                Sheet sheet = wb.createSheet(sheetName);

                Row titleRow = sheet.createRow(0);
                titleRow.createCell(0).setCellValue(salesmanDb.getSalesmanCode());
                titleRow.createCell(1).setCellValue(salesmanDb.getSalesmanName());

                Row titleRow2 = sheet.createRow(FIRST_ROW_WITH_DATA - 1);

                int colNum = 0;

                titleRow2.createCell(colNum++).setCellValue("Numer Klienta");
                titleRow2.createCell(colNum++).setCellValue("Nazwa Klienta");
                titleRow2.createCell(colNum++).setCellValue("Numer Projektu");
                titleRow2.createCell(colNum++).setCellValue("Nazwa Projektu");
                titleRow2.createCell(colNum++).setCellValue("Nr Budowy");
                titleRow2.createCell(colNum++).setCellValue("Nazwa Budowy");
                titleRow2.createCell(colNum++).setCellValue("Udział w marży");
                titleRow2.createCell(colNum++).setCellValue("Udział w obrocie");
                titleRow2.createCell(colNum++).setCellValue("Kod grupy");
                titleRow2.createCell(colNum++).setCellValue("Nazwa grupy");
                titleRow2.createCell(colNum++).setCellValue("Wartość");
                titleRow2.createCell(colNum++).setCellValue("Masa");
                titleRow2.createCell(colNum++).setCellValue("Planowana data zwrotu");

                int rownNum = FIRST_ROW_WITH_DATA;

                String projNumber = null;
                String siteNumber = null;

                for (ProjectLinesDto projectLinesDto : linesForSalesman) {

                    if (projNumber == null) {
                        projNumber = projectLinesDto.getProjectNumber();
                    }
                    if (siteNumber == null) {
                        siteNumber = projectLinesDto.getSiteNumber();
                    }

                    //Pusty wiersz między budowami
                    if (!projectLinesDto.getProjectNumber().equals(projNumber)) {
                        projNumber = projectLinesDto.getProjectNumber();
                        rownNum++;
                    }

                    //Pusty wiersz między projektami
                    if (!projectLinesDto.getSiteNumber().equals(siteNumber)) {
                        siteNumber = projectLinesDto.getSiteNumber();
                        rownNum++;
                    }

                    Row row = sheet.createRow(rownNum++);

                    colNum = 0;

                    row.createCell(colNum++).setCellValue(projectLinesDto.getCustomerNumber());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getCustomerName());

                    row.createCell(colNum++).setCellValue(projectLinesDto.getProjectNumber());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getProjectName());

                    row.createCell(colNum++).setCellValue(projectLinesDto.getSiteNumber());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getSiteName());

                    row.createCell(colNum++).setCellValue(projectLinesDto.getCommissionPercentage());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getTurnoverPercentage());

                    row.createCell(colNum++).setCellValue(projectLinesDto.getMainProductGroup());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getMainProductGroupName());

                    row.createCell(colNum++).setCellValue(projectLinesDto.getMaterialValue());
                    row.createCell(colNum++).setCellValue(projectLinesDto.getTotalWeight());
                }

                CellRangeAddress cellRangeAddress = new CellRangeAddress(FIRST_ROW_WITH_DATA - 1, rownNum, FIRST_COLUMN, LAST_COLUMN);
                sheet.setAutoFilter(cellRangeAddress);

                for (int j = FIRST_COLUMN; j <= LAST_COLUMN; j++) {
                    sheet.autoSizeColumn(j);
                }

                try (OutputStream fileOut = new FileOutputStream(path + fileName)) {
                    wb.write(fileOut);

                } catch (Exception e) {
                    LOG.error("Nie udało się zapisać pliku wynikowego");
                }
            }
        }

    }
}
