package com.jackgharris.rent_a_car.models;

import com.jackgharris.rent_a_car.core.Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Car extends Model {

    private static Car[] models;

    public Car(HashMap<String, String> data) {
        super(data);
    }

    public String getID(){
        return this.data.get("Vehicle ID");
    }

    public String getBrand(){
        return this.data.get("Brand");
    }

    public String getModel(){
        return this.data.get("Model");
    }

    public String getType(){
        return this.data.get("Type");
    }

    public String getColor(){
        return this.data.get("Color");
    }

    public int getYearOfManufacture(){
        return Integer.parseInt(this.data.get("Year of Manufacture"));
    }

    public int getRentalPricePerDay(){
        return Integer.parseInt(this.data.get("Rental per day (AUD)"));
    }

    public int getServiceFee(){
        return Integer.parseInt(this.data.get("Service fee (AUD)"));
    }

    public int getDiscount(){
        return Integer.parseInt(this.data.get("Discount"));
    }


    public static CarCollection where(String key, String value){
        return new CarCollection(Car.models).where(key,value);
    }

    public static Car[] getAll(){
        return new CarCollection(Car.models).getAll();
    }


    public static void bindModelData(ArrayList<HashMap<String, String>> models){
        Car.models = Car.buildCarModels(models);
    }

    private static Car[] buildCarModels(ArrayList<HashMap<String, String>> result) {
        Car[] cars = new Car[result.size()];


        int i = 0;
        for (HashMap<String, String> entry: result) {
            cars[i] = new Car(entry);
            i++;
        }


        return cars;
    }


}
