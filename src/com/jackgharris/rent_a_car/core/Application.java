package com.jackgharris.rent_a_car.core;

import com.jackgharris.rent_a_car.enums.Sort;
import com.jackgharris.rent_a_car.models.Car;
import com.jackgharris.rent_a_car.services.FileService;


public class Application {

    public Application(){

        String directory = System.getProperty("user.dir");
        Car.bindModelData(FileService.loadCarModels(directory+"/fleet.csv"));




        Car[] cars = Car.orderBy("Year of Manufacture", Sort.DESCENDING).get();

        for (Car car: cars) {
            System.out.println("CarAttributes: "+car.getBrand()+" "+car.getModel()+" Year of Manufacture: "+car.getYearOfManufacture());
        }

    }

}
