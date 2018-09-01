package pl.kostrowski.doka.jzwroty.koconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ReadExternalProperties {

    private final static Logger LOG = LoggerFactory.getLogger(ReadExternalProperties.class);
    private final static String PATH_TO_PROPERTIES_FILE = "./pliki/config/";
    private final static String PROPERTIES_FILE_NAME = "application.properties";

    private final static Properties externalProperties = new Properties();

    private final static String FILE_SAVE_FOLDER = "save.file.folder";
    private final static String COMMON_FILE_FOLDER = "common.file.folder";

    private final static String MPG_FILE_NAME = "mainproductgroup.file.name";
    private final static String MPG_SHEET_NAME = "mainproductgroup.sheet.name";
    private final static String MPG_FOLDER_NAME = "mainproductgroup.folder.name";

    private final static String SALESMAN_FILE_NAME = "salesman.file.name";
    private final static String SALESMAN_SHEET_NAME = "salesman.sheet.name";

    private final static String DISCOS_DATA_FILE_NAME = "discosdata.file.name";
    private final static String DISCOS_DATA_SHEET_NAME = "discosdata.sheet.name";

    private final static String PROJECT_FILE_NAME = "projects.file.name";
    private final static String PROJECT_SHEET_NAME = "projects.sheet.name";

    private final static String RESULT_SHEET_NAME = "result.sheet.name";

    static {
        try (FileInputStream file = new FileInputStream(PATH_TO_PROPERTIES_FILE + PROPERTIES_FILE_NAME)) {
            externalProperties.load(file);
        } catch (FileNotFoundException e) {
            LOG.error("Nie udało się znaleźć pliku z konfiguracją");
        } catch (IOException e) {
            LOG.error("Nie udało się otworzyć pliku z konfiguracją");
        }
    }

    public static String getMpgFileName() {
        return externalProperties.getProperty(MPG_FILE_NAME);
    }

    public static String getMpgSheetName() {
        return externalProperties.getProperty(MPG_SHEET_NAME);
    }

    public static String getMpgFolderName() {
        return externalProperties.getProperty(MPG_FOLDER_NAME);
    }

    public static String getSalesmanFileName() {
        return externalProperties.getProperty(SALESMAN_FILE_NAME);
    }

    public static String getSalesmanSheetName() {
        return externalProperties.getProperty(SALESMAN_SHEET_NAME);
    }

    public static String getDiscosDataSheetName() {
        return externalProperties.getProperty(DISCOS_DATA_SHEET_NAME);
    }

    public static String getProjectSheetName() {
        return externalProperties.getProperty(PROJECT_SHEET_NAME);
    }

    public static String getFileSaveFolder() {
        return externalProperties.getProperty(FILE_SAVE_FOLDER);
    }

    public static String getCommonFileFolder() {
        return externalProperties.getProperty(COMMON_FILE_FOLDER);
    }

    public static String getDiscosDataFileName() {
        return externalProperties.getProperty(DISCOS_DATA_FILE_NAME);
    }

    public static String getProjectFileName() {
        return externalProperties.getProperty(PROJECT_FILE_NAME);
    }

    public static String getResultSheetName() {
        return externalProperties.getProperty(RESULT_SHEET_NAME);
    }
}
