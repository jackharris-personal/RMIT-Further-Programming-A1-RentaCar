package com.jackgharris.uber_eats_console.core;

import com.jackgharris.uber_eats_console.services.FileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Application {

    private static Application instance;

    private String directory;
    private HashMap<String, String> config;
    private String logo;

    public Application(){

        this.directory = System.getProperty("user.dir");
        this.config = FileService.loadConfig(this.directory+"/config.txt");

        List<String> details = new ArrayList<>();

        switch (this.config.get("DB_DRIVER")) {
            case "mysql" -> {

                details.add(this.config.get("SQL_HOST"));
                details.add(this.config.get("SQL_PORT"));
                details.add(this.config.get("SQL_NAME"));
                details.add(this.config.get("SQL_USER"));
                details.add(this.config.get("SQL_PASS"));

                Database.boot("mysql", details);

            } case "sqlite" -> {

                details.add(this.config.get("SQLITE_FILE"));
                Database.boot("sqlite", details);

            }
        }

        Database.instance().test();



    }


    public static Application instance() {
        return instance;
    }

    public static void boot(){
        instance = new Application();
    }

    public String getConfig(String key){
        return this.config.get(key);
    }
}
