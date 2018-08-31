package pl.kostrowski.doka.jzwroty.converters.exceldb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.model.db.ProjectDb;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConvertProject {

    private final static Logger LOG = LoggerFactory.getLogger(ConvertProject.class);

    public List<ProjectDb> convert(List<ProjectExcel> dirtyInput) {

        List<ProjectDb> result = new LinkedList<>();

        List<ProjectExcel> input = cleanInputList(dirtyInput);

        for (ProjectExcel projectExcel : input) {

            ProjectDb projectDb = new ProjectDb();
            projectDb.setRentalProjectNumber(projectExcel.getRentalProjectNumber());
            projectDb.setRentalProjectName(projectExcel.getRentalProjectName());
            projectDb.setCustomerNumber(projectExcel.getCustomerNumber());
            projectDb.setCustomerName(projectExcel.getCustomerName());
            projectDb.setProjectLeadingBranch(projectExcel.getProjectLeadingBranch());

            result.add(projectDb);

            LOG.trace(projectDb.toString());
        }
        return result;
    }

    private List<ProjectExcel> cleanInputList(List<ProjectExcel> input) {

        List<ProjectExcel> collect = input.stream()
                .filter(s -> s.getRentalProjectNumber() != null
                        && !s.getRentalProjectNumber().equals("")
                        && !s.getRentalProjectNumber().equals("Total"))
                .collect(Collectors.toList());

        return collect;
    }

}
