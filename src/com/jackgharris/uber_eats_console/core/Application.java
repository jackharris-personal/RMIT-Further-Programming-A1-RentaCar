package com.jackgharris.uber_eats_console.core;

import com.jackgharris.uber_eats_console.services.FileService;

import javax.xml.crypto.Data;
import java.util.HashMap;

public class Application {

    private static Application instance;

    private String directory;
    private HashMap<String, String> config;
    private String logo;

    public Application(){

        this.directory = System.getProperty("user.dir");
        this.config = FileService.loadConfig(this.directory+"/config.txt");

        Database.instance();

    }

    public static Application instance() {

        return instance;
    }

    public static void boot(){
        instance = new Application();
    }
}
