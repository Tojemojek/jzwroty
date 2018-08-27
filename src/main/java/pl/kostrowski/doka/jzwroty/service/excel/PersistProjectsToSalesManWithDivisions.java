package pl.kostrowski.doka.jzwroty.service.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertProjectExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertProjectToSalesManWithDivision;
import pl.kostrowski.doka.jzwroty.dao.ProjectToSalesManWithDivisionDao;
import pl.kostrowski.doka.jzwroty.model.db.ProjectToSalesManWithDivision;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import javax.transaction.Transactional;
import java.io.File;
import java.util.List;

@Service
@Transactional
public class PersistProjectsToSalesManWithDivisions {

    private final static Logger LOG = LoggerFactory.getLogger(PersistProjectsToSalesManWithDivisions.class);

    private ConvertProjectExcel convertProjectExcel;
    private ConvertProjectToSalesManWithDivision convertProjectToSalesManWithDivision;
    private ProjectToSalesManWithDivisionDao projectToSalesManWithDivisionDao;

    private final String WORKSHEET_WITH_DATA_NAME = "Dane";


    @Autowired
    public PersistProjectsToSalesManWithDivisions(ConvertProjectExcel convertProjectExcel, ConvertProjectToSalesManWithDivision convertProjectToSalesManWithDivision, ProjectToSalesManWithDivisionDao projectToSalesManWithDivisionDao) {
        this.convertProjectExcel = convertProjectExcel;
        this.convertProjectToSalesManWithDivision = convertProjectToSalesManWithDivision;
        this.projectToSalesManWithDivisionDao = projectToSalesManWithDivisionDao;
    }


    public void persist() {
        try {
            Workbook projectWorkbook = new XSSFWorkbook(new File("./pliki/03_input_projects.xlsx"));
            List<ProjectExcel> projectExcels = convertProjectExcel.convert(projectWorkbook, WORKSHEET_WITH_DATA_NAME);
            List<ProjectToSalesManWithDivision> convert = convertProjectToSalesManWithDivision.convert(projectExcels);
            projectToSalesManWithDivisionDao.saveAll(convert);
            projectToSalesManWithDivisionDao.flush();
            projectWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Todo implement if workbooks cant be opened
        }
    }


}
