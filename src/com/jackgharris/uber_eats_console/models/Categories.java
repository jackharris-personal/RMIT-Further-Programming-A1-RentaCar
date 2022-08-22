package com.jackgharris.uber_eats_console.models;

import com.jackgharris.uber_eats_console.core.Database;
import com.jackgharris.uber_eats_console.core.Model;

import java.util.HashMap;
import java.util.List;

public class Categories extends Model {
    public Categories(HashMap<String, String> data) {
        super(data);
    }

    public static Restaurant[] getRestaurantsWhereValue(String value){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM categories WHERE category='"+value+"'");

        return Restaurant.buildRestaurantObjects(result);
    }


}
