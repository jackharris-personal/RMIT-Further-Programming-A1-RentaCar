package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileServiceTests {

    @Test
    public void loadCarModelsTestWithInValidPath(){
        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleetASDASDSAD.csv"));
        assertEquals(Car.get().length, 0);
    }

    @Test
    public void loadCarModelsTestWithValidPath(){
        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        assertEquals(Car.get().length, 9);
    }
}
