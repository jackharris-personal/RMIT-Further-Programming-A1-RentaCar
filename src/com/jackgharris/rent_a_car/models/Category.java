package com.jackgharris.rent_a_car.models;

import com.jackgharris.rent_a_car.core.Database;
import com.jackgharris.rent_a_car.core.Model;

import java.util.HashMap;
import java.util.List;

public class Category extends Model {
    public Category(HashMap<String, String> data) {
        super(data);
    }

    public static Restaurant[] getRestaurantsWhere(String value){
        List<HashMap<String, String>> result = Database.query("SELECT * FROM categories WHERE Category='"+value+"'");

        Restaurant restaurants[] = new Restaurant[result.size()];

        int i = 0;

        for (HashMap<String, String> entry: result) {
            String name = entry.get("restaurant");
            restaurants[i] = Restaurant.getWhere("name",name);
            i++;
        }

        return restaurants;
    }

    public static Restaurant[] getRestaurantsWhere(String[] values){



        return null;

    }



}
