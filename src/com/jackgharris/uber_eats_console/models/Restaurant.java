package com.jackgharris.uber_eats_console.models;

import com.jackgharris.uber_eats_console.core.Database;
import com.jackgharris.uber_eats_console.core.Model;

import java.util.HashMap;
import java.util.List;

public class Restaurant extends Model {

    public Restaurant(HashMap<String, String> data) {
        super(data);
    }

    public int getId(){
        return Integer.parseInt(this.data.get("id"));
    }

    public String getName(){
        return this.data.get("name");
    }

    public String getDescription(){
        return this.data.get("description");
    }

    public String getAddress(){
        return this.data.get("address");
    }

    public static Restaurant[] all(){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM restaurants");

        Restaurant[] restaurants = new Restaurant[result.size()];


        int i = 0;
        for (HashMap<String, String> entry: result) {
            restaurants[i] = new Restaurant(entry);
            i++;
        }


        return restaurants;
    }


}