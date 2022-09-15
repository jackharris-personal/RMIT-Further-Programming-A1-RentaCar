package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CarModelTest{

    @Test
    public void evaluatesExpression() {

        int sum = 6;
        assertEquals(6, sum);
    }

    @Test
    public void evaluatesExpression2() {

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));

        assertEquals(Car.get().length, 9);
    }
}
