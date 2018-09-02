package pl.kostrowski.doka.jzwroty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class CloseApp {

    @Autowired
    private ApplicationContext appContext;

    public void closeApp() {
        SpringApplication.exit(appContext, () -> 0);
    }
}
