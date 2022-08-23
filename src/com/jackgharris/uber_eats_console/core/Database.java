package com.jackgharris.uber_eats_console.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private static Database instance;
    private final String driver;
    private final String host;
    private final int port;
    private final String name;
    private final String user;
    private final String password;
    private final String file;


    public Database(String driver, HashMap<String, String> config){

        this.driver = driver;

        this.host = config.get("SQL_HOST");
        this.port = Integer.parseInt(config.get("SQL_PORT"));
        this.name = config.get("SQL_NAME");
        this.user = config.get("SQL_USER");
        this.password = config.get("SQL_PASSWORD");
        this.file = config.get("SQLITE_FILE");

    }

    public static List<HashMap<String, String>> query(String query){

        List<HashMap<String, String>> data =  new ArrayList<>();

        //check for SQL connection error
        try{

            String url = "";
            Connection con = null;

            switch (Database.instance.driver) {
                case "mysql" -> {
                    url = Database.instance.host+":"+Database.instance.port+"/"+Database.instance.name;
                    con = DriverManager.getConnection("jdbc:"+Database.instance.driver+"://"+url,Database.instance.user,Database.instance.password);
                }
                case "sqlite" -> {
                    url = Database.instance.file;
                    con = DriverManager.getConnection("jdbc:"+Database.instance.driver+"://"+Database.instance.file);
                }
            }

            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);


            int columns  = result.getMetaData().getColumnCount();


            while(result.next()){
                HashMap<String, String> entry = new HashMap<String, String>();

                int i = 1;
                while(i <= columns){
                    entry.put(result.getMetaData().getColumnLabel(i),result.getString(i));
                    i++;
                }

                data.add(entry);
            }

        }catch (SQLException exception){
            exception.printStackTrace();
        }

        return data;

    }

    public static void boot(String driver, HashMap<String, String> config){
        Database.instance = new Database(driver, config);
    }

    public static Database instance(){
        return Database.instance;
    }

}
