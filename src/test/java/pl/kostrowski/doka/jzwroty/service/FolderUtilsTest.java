package pl.kostrowski.doka.jzwroty.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FolderUtilsTest {

    @Autowired
    FolderUtils folderUtils;

    @Test(expected = Test.None.class)
    public void listFilesInSubfolders() {
        for (File listFilesInSubfolder : folderUtils.listFilesInSubfolders("20180831")) {
            System.out.println(listFilesInSubfolder.getName());
        }
    }

    @Test(expected = Test.None.class)
    public void listOfSubFolders() {
        for (File listOfSubFolder : folderUtils.listOfSubFolders()) {
            System.out.println(listOfSubFolder.getName());
        }
    }

    @Test(expected = Test.None.class)
    public void listOfResultFiles() {
        for (File listOrResultFiles : folderUtils.listOfResutFiles("20180831")) {
            System.out.println(listOrResultFiles.getName());
        }
    }
}