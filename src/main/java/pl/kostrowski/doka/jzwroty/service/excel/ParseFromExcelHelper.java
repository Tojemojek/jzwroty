package pl.kostrowski.doka.jzwroty.service.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParseFromExcelHelper {

    private final static Logger LOG = LoggerFactory.getLogger(ParseFromExcelHelper.class);
    private final static DataFormatter dataFormatter = new DataFormatter();

    public static Map<String, Integer> findColumnNumbers(Map<String, String> invertedMappings, Row titleRow) {

        Map<String, Integer> columnNumbers = new HashMap<>();
        DataFormatter dataFormatter = new DataFormatter();

        for (Cell cell : titleRow) {
            String cellValue = dataFormatter.formatCellValue(cell);
            if (invertedMappings.containsKey(cellValue)) {
                columnNumbers.put(invertedMappings.get(cellValue), cell.getColumnIndex());
            }
        }
        return columnNumbers;
    }

    public static String parseStringFromCell(Cell cell) {
        try {
            return dataFormatter.formatCellValue(cell);
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseStringFromCell row " + cell.getRow()+ " kol " + cell.getColumnIndex());
            return null;
        } catch (Exception e) {
            LOG.debug(e.toString() + " parseStringFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        }
    }

    public static Double parseDoubleFromCell(Cell cell) {

        try {
            return cell.getNumericCellValue();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseDoubleFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseDoubleFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        }
    }

    public static Integer parseIntegerFromCell(Cell cell) {
        try {
            CellType cellTypeEnum = cell.getCellTypeEnum();
            if (cellTypeEnum.equals(CellType.STRING)) {
                return Integer.parseInt(cell.getStringCellValue());
            } else if (cellTypeEnum.equals(CellType.NUMERIC)) {
                return ((Double) cell.getNumericCellValue()).intValue();
            }
            return null;
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseIntegerFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseIntegerFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        }
    }

    public static LocalDate parseLocalDateFromCell(Cell cell) {
        try {
            Date dateCellValue = cell.getDateCellValue();
            return dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseLocalDateFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseLocalDateFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        }
    }

    public static LocalDateTime parseLocalDateTimeFromCell(Cell cell) {
        try {
            Date dateCellValue = cell.getDateCellValue();
            return dateCellValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        } catch (NullPointerException e) {
            LOG.trace("komórka pusta" + " parseLocalDateTimeFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        } catch (Exception e) {
            LOG.info(e.toString() + " parseLocalDateFromCell row " + cell.getRow() + " kol " + cell.getColumnIndex());
            return null;
        }
    }
}
