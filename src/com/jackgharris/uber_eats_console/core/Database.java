package com.jackgharris.uber_eats_console.core;

public class Database {

    private static Database instance;

    public Database(){

    }

    public static Database instance(){
        if(Database.instance == null){
            Database.instance = new Database();
        }
        return Database.instance;
    }

}
