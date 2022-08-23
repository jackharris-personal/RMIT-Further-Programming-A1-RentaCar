package com.jackgharris.uber_eats_console.core;

import com.jackgharris.uber_eats_console.services.FileService;

import java.util.HashMap;

public class Application {

    private String directory;
    private HashMap<String, String> config;

    public Application(){

        this.directory = System.getProperty("user.dir");
        this.config = FileService.loadConfig(this.directory+"/config.txt");

        Database.boot(this.config.get("DB_DRIVER"), this.config);

    }

}
