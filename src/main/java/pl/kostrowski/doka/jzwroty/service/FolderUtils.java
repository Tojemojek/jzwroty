package pl.kostrowski.doka.jzwroty.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pl.kostrowski.doka.jzwroty.koconfig.ReadExternalProperties;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class FolderUtils {

    private final static Logger LOG = LoggerFactory.getLogger(FolderUtils.class);
    private final String RESULT_PREFIX = "wynik_";
    private final String XLSX_SUFFIX = ".xlsx";


    public List<File> listFilesInSubfolders(String subfolder) {
        String path = "." + File.separator + ReadExternalProperties.getFileSaveFolder() + File.separator + subfolder + File.separator;
        File folder = new File(path);
        List<File> filesInSubfolder;
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            filesInSubfolder = Arrays.stream(files)
                    .filter(File::isFile)
                    .collect(Collectors.toList());

            return filesInSubfolder;
        } else {
            LOG.info("Nie znaleziono żadnych plików w folderze " + path);
            return null;
        }
    }

    public List<File> listOfSubFolders() {
        String path = "." + File.separator + ReadExternalProperties.getFileSaveFolder() + File.separator;
        File folder = new File(path);
        List<File> subFolders;
        File[] files = folder.listFiles();

        if (files != null && files.length > 0) {
            subFolders = Arrays.stream(files)
                    .filter(f -> f.isDirectory() && !f.getName().equals(ReadExternalProperties.getCommonFileFolder()) && !f.getName().equals("config"))
                    .collect(Collectors.toList());
            return subFolders;
        } else {
            LOG.info("Nie znaleziono żadnych użytecznych podfolderów w folderze " + path);
            return null;
        }
    }

    public List<File> listOfResutFiles(String subfolder) {

        List<File> filesInSubfolders = listFilesInSubfolders(subfolder);

        if (filesInSubfolders == null || filesInSubfolders.size() == 0) {
            return null;
        }

        List<File> resultFiles;
        Pattern pattern = Pattern.compile(RESULT_PREFIX + ".*" + XLSX_SUFFIX);

        resultFiles = filesInSubfolders.stream()
                .filter(f -> pattern.matcher(f.getName()).matches())
                .collect(Collectors.toList());

        if (resultFiles.size() == 0) {
            LOG.info("Nie znaleziono żadnych plików wynikowych w podfolderze " + subfolder);
            return null;
        }

        return resultFiles;
    }
}
