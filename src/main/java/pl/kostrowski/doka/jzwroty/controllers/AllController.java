package pl.kostrowski.doka.jzwroty.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class  AllController {

    @RequestMapping(value = "/")
    public String displayMenu() {

        return "index";
    }
}
