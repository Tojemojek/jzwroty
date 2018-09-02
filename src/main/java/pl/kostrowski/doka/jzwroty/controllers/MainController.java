package pl.kostrowski.doka.jzwroty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kostrowski.doka.jzwroty.service.CloseApp;
import pl.kostrowski.doka.jzwroty.service.FolderUtils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;


@Controller
public class MainController {


    private FolderUtils folderUtils;
    private CloseApp closeApp;

    @Autowired
    public MainController(FolderUtils folderUtils, CloseApp closeApp) {
        this.folderUtils = folderUtils;
        this.closeApp = closeApp;
    }

    @RequestMapping(value = "/")
    public String displayMenu(Model model) {
        List<File> subFolders = folderUtils.listOfSubFolders();
        model.addAttribute("subFolders", subFolders);

        if (!model.containsAttribute("notatki")) {
            List<String> notatki = new LinkedList<>();
            notatki.add("Wybierz co chcesz zrobić!");
            model.addAttribute("notatki", notatki);
        }
        return "index";
    }

    @RequestMapping(value = "/koniec")
    public void finishIt() {
        closeApp.closeApp();
    }
}
