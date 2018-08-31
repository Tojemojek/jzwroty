package pl.kostrowski.doka.jzwroty.converters.exceldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.dao.MainProductGroupDao;
import pl.kostrowski.doka.jzwroty.dao.ProjectDao;
import pl.kostrowski.doka.jzwroty.model.db.DiscosData;
import pl.kostrowski.doka.jzwroty.model.excel.DiscosExcel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertDiscos {

    @Autowired
    ProjectDao projectDao;

    @Autowired
    MainProductGroupDao mainProductGroupDao;

    private final static Logger LOG = LoggerFactory.getLogger(ConvertDiscos.class);


    public List<DiscosData> convert(List<DiscosExcel> dirtyInput) {

        List<DiscosData> result = new LinkedList<>();

        List<DiscosExcel> input = cleanInputList(dirtyInput);

        for (DiscosExcel discosExcel : input) {

            DiscosData discosData = new DiscosData();

            discosData.setProjectDb(projectDao.getOne(discosExcel.getRentalProjectNumber()));
            discosData.setJobSiteNumber(discosExcel.getJobSiteNumber());
            discosData.setJobSiteName(discosExcel.getJobSiteName());
            discosData.setCustomerNumber(discosExcel.getCustomerNumber());
            discosData.setCustomerName(discosExcel.getCustomerName());

            discosData.setMainProductGroupDb(mainProductGroupDao.getOne(discosExcel.getMainProductGroupCode()));

            discosData.setItemNumber(discosExcel.getItemNumber());
            discosData.setItemName(discosExcel.getItemName());
            discosData.setBusinessType(discosExcel.getBusinessType());
            discosData.setQuantity(discosExcel.getQuantity());
            discosData.setMaterialValuePerUnit(discosExcel.getMaterialValuePerUnit());
            discosData.setTotalWeight(discosExcel.getTotalWeight());
            discosData.setPlannedReturnDate(discosExcel.getPlannedReturnDate());
            discosData.setCurrentPlannedReturnDate(discosExcel.getCurrentPlannedReturnDate());

            result.add(discosData);

            LOG.trace(discosData.toString());
        }
        return result;
    }

    private List<DiscosExcel> cleanInputList(List<DiscosExcel> input) {

        List<DiscosExcel> collect = input.stream()
                .filter(s -> s.getRentalProjectNumber() != null
                        && !s.getRentalProjectNumber().equals("")
                        && !s.getRentalProjectNumber().equals("Total"))
                .collect(Collectors.toList());

        return collect;
    }
}
