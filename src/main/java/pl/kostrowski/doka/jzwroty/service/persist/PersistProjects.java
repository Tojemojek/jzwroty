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

import static pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties.*;

@Service
public class PersistProjects {

    private final static Logger LOG = LoggerFactory.getLogger(PersistProjects.class);

    private ConvertProjectExcel convertProjectExcel;
    private ConvertProject convertProject;
    private ProjectDao projectDao;



    @Autowired
    public PersistProjects(ConvertProjectExcel convertProjectExcel, ConvertProject convertProjec, ProjectDao projectDao) {
        this.convertProjectExcel = convertProjectExcel;
        this.convertProject = convertProjec;
        this.projectDao = projectDao;
    }

    public void persist(String folderName) {
        try {
            String path = "." + File.separator + getFileSaveFolder() + File.separator + folderName + File.separator + getProjectFileName();
            File inputFile = new File(path);
            LOG.info("Przetwarzam plik " + inputFile.getName());
            Workbook projectWorkbook = new XSSFWorkbook(inputFile);
            List<ProjectExcel> projectExcels = convertProjectExcel.convert(projectWorkbook, getProjectSheetName());
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
