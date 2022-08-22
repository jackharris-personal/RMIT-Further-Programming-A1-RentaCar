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

        return buildRestaurantObjects(result);
    }

    public static Restaurant[] all(int limit){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM restaurants LIMIT "+limit);

        return buildRestaurantObjects(result);
    }

    public static Restaurant[] allWhere(String column, String value){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM restaurants WHERE "+column+" = "+"'"+value+"'");
        return buildRestaurantObjects(result);
    }

    public static Restaurant getWhere(String column, String value){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM restaurants WHERE "+column+" = "+"'"+value+"' LIMIT 1");
        return buildRestaurantObjects(result)[0];
    }

    public static Restaurant[] buildRestaurantObjects(List<HashMap<String, String>> result) {
        Restaurant[] restaurants = new Restaurant[result.size()];


        int i = 0;
        for (HashMap<String, String> entry: result) {
            restaurants[i] = new Restaurant(entry);
            i++;
        }


        return restaurants;
    }


}
