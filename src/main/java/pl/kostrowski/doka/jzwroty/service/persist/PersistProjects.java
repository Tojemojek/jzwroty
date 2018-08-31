package pl.kostrowski.doka.jzwroty.service.persist;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.converters.excel.ConvertProjectExcel;
import pl.kostrowski.doka.jzwroty.converters.exceldb.ConvertProject;
import pl.kostrowski.doka.jzwroty.dao.ProjectDao;
import pl.kostrowski.doka.jzwroty.model.db.ProjectDb;
import pl.kostrowski.doka.jzwroty.model.excel.ProjectExcel;

import java.io.File;
import java.util.List;

@Service
public class PersistProjects {

    private final static Logger LOG = LoggerFactory.getLogger(PersistProjects.class);

    private ConvertProjectExcel convertProjectExcel;
    private ConvertProject convertProject;
    private ProjectDao projectDao;

    private final String WORKSHEET_WITH_DATA_NAME = "Dane";

    @Autowired
    public PersistProjects(ConvertProjectExcel convertProjectExcel, ConvertProject convertProjec, ProjectDao projectDao) {
        this.convertProjectExcel = convertProjectExcel;
        this.convertProject = convertProjec;
        this.projectDao = projectDao;
    }

    public void persist() {
        try {
            File inputFile = new File("./pliki/03_input_projects.xlsx");
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook projectWorkbook = new XSSFWorkbook(inputFile);
            List<ProjectExcel> projectExcels = convertProjectExcel.convert(projectWorkbook, WORKSHEET_WITH_DATA_NAME);
            List<ProjectDb> projectDb = convertProject.convert(projectExcels);
            LOG.info("Znaleziono " + projectDb.size() + " unikalnych projektów");
            projectDao.saveAll(projectDb);
            projectDao.flush();
            projectWorkbook.close();
        } catch (Exception e) {
            e.printStackTrace();
            //Todo implement if workbooks cant be opened
        }
    }


}
