package pl.kostrowski.doka.jzwroty.service.persist;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertSalesmanExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertSalesman;
import pl.kostrowski.doka.jzwroty.dao.SalesmanDao;
import pl.kostrowski.doka.jzwroty.model.db.SalesmanDb;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;

import java.io.File;
import java.util.List;

import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.*;

@Service
public class PersistSalesman {

    private final static Logger LOG = LoggerFactory.getLogger(PersistSalesman.class);
    private ConvertSalesmanExcel convertSalesmanExcel;
    private ConvertSalesman convertSalesman;
    private SalesmanDao salesmanDao;

    @Autowired
    public PersistSalesman(ConvertSalesmanExcel convertSalesmanExcel, ConvertSalesman convertSalesman, SalesmanDao salesmanDao) {
        this.convertSalesmanExcel = convertSalesmanExcel;
        this.convertSalesman = convertSalesman;
        this.salesmanDao = salesmanDao;
    }

    public void persist() {
        try {
            String path = "." + File.separator + getFileSaveFolder() + File.separator + getCommonFileFolder() + File.separator + getSalesmanFileName();
            File inputFile = new File(path);
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook salesmanWorkbook = new XSSFWorkbook(inputFile);
            List<SalesmanExcel> salesmanExcel = convertSalesmanExcel.convert(salesmanWorkbook, getSalesmanSheetName());
            List<SalesmanDb> salesmanDb = convertSalesman.convert(salesmanExcel);
            LOG.info("Znaleziono " + salesmanDb.size() + " unikalnych handlowców");
            salesmanDao.saveAll(salesmanDb);
            salesmanDao.flush();
            salesmanWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Todo implement if workbooks cant be opened
        }
    }


}
