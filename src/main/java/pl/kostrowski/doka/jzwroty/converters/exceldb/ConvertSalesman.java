package pl.kostrowski.doka.jzwroty.converters.exceldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.db.SalesmanDb;
import pl.kostrowski.doka.jzwroty.model.excel.SalesmanExcel;

import java.util.LinkedList;
import java.util.List;

@Service
public class ConvertSalesman {

    private final static Logger LOG = LoggerFactory.getLogger(ConvertSalesman.class);

    public List<SalesmanDb> convert(List<SalesmanExcel> input) {


        List<SalesmanDb> result = new LinkedList<>();

        for (SalesmanExcel salesmanExcel : input) {
            SalesmanDb salesmanDb = new SalesmanDb();
            salesmanDb.setSalesmanCode(salesmanExcel.getSalesmanCode());
            salesmanDb.setSalesmanName(salesmanExcel.getSalesmanName());

            LOG.trace(salesmanDb.toString());

            result.add(salesmanDb);
        }
        return result;

    }
}
