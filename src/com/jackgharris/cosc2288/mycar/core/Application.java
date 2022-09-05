package com.jackgharris.cosc2288.mycar.core;

import com.jackgharris.cosc2288.mycar.enums.Sort;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;


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
