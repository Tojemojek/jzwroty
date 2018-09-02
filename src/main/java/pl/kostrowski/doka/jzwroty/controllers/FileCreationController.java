package pl.kostrowski.doka.jzwroty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.kostrowski.doka.jzwroty.service.excel.ToDiscosCreator;
import pl.kostrowski.doka.jzwroty.service.excel.WynikiCreator;
import pl.kostrowski.doka.jzwroty.service.persist.SaveAllExcelDataToDb;

import java.util.LinkedList;
import java.util.List;


@Controller
public class FileCreationController {

    private SaveAllExcelDataToDb saveAllExcelDataToDb;
    private WynikiCreator wynikiCreator;
    private ToDiscosCreator toDiscosCreator;

    @Autowired
    public FileCreationController(WynikiCreator wynikiCreator, ToDiscosCreator toDiscosCreator, SaveAllExcelDataToDb saveAllExcelDataToDb) {
        this.wynikiCreator = wynikiCreator;
        this.toDiscosCreator = toDiscosCreator;
        this.saveAllExcelDataToDb = saveAllExcelDataToDb;
    }

    @RequestMapping(value = "/create/salesman", method = RequestMethod.POST)
    public String createSalesmanFiles(Model model, @RequestParam(value = "folderName") String folderName,
                                      RedirectAttributes redirectAttributes) {
        saveAllExcelDataToDb.convertExcelToDb(folderName);
        wynikiCreator.createResultFile(folderName);

        List<String> notatki = new LinkedList<>();
        notatki.add("Zrobione Pliki Dla Handlowców z folderu" + folderName);
        redirectAttributes.addFlashAttribute("notatki", notatki);

        return "redirect:/";
    }

    @RequestMapping(value = "/create/discos", method = RequestMethod.POST)
    public String createDiscosFile(Model model, @RequestParam(value = "folderName") String folderName, RedirectAttributes redirectAttributes) {
        toDiscosCreator.createResultFile(folderName);

        List<String> notatki = new LinkedList<>();
        notatki.add("Zrobiony Plik do Discos z folderu" + folderName);
        redirectAttributes.addFlashAttribute("notatki", notatki);

        return "redirect:/";
    }
}
