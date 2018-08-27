package pl.kostrowski.doka.jzwroty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kostrowski.doka.jzwroty.service.excel.SaveAllExcelDataToDb;


@Controller
public class  AllController {

    @Autowired
    SaveAllExcelDataToDb saveAllExcelDataToDb;

    @RequestMapping(value = "/")
    public String displayMenu() {
        return "index";
    }

    @RequestMapping(value = "/convert")
    public String startConverting() {
        saveAllExcelDataToDb.convertExcelToDb();
        return "index";
    }

}
