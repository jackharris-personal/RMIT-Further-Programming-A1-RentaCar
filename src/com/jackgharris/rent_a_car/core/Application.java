package com.jackgharris.rent_a_car.core;

import com.jackgharris.rent_a_car.models.Category;
import com.jackgharris.rent_a_car.models.Restaurant;
import com.jackgharris.rent_a_car.services.FileService;

import java.util.HashMap;

public class Application {

    private String directory;
    private HashMap<String, String> config;

    public Application(){

        this.directory = System.getProperty("user.dir");
        this.config = FileService.loadConfig(this.directory+"/config.txt");

        Database.boot(this.config.get("DB_DRIVER"), this.config);

        //category load testing
        Restaurant[] restaurants = Category.getRestaurantsWhere("Fast Food");

        for (Restaurant restaurant : restaurants) {
            System.out.println(restaurant.getName());
        }

    }

}
