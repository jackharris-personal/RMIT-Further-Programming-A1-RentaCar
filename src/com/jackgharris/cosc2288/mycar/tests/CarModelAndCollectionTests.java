package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.enums.Sort;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarModelAndCollectionTests {

    public CarModelAndCollectionTests(){
        //Bind all our model data that will be required for testing
        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
    }

    @Test
    public void carGet(){

        assertEquals(Car.get().length, 9);
    }

    @Test
    public void carIsValidBrand(){
        assertTrue(Car.isValidBrand("Ford"));
    }

    @Test
    public void carGetSeatsWhereGreaterThan(){
        assertEquals(Car.getWhereSeats(5, Sort.GREATER_THAN).get().length, 2);
    }

    @Test
    public void carGetSeatsWhereLessThan(){
        assertEquals(Car.getWhereSeats(5, Sort.LESS_THAN).get().length, 3);
    }

    @Test
    public void carWhereKeyEqualsValue(){
        assertEquals(Car.where("Brand","BMW").get().length, 2);
    }

    @Test
    public void carGetUniqueValuesWhereKey(){
        assertEquals(Car.getUniqueValuesWhereKey("Type").length,3);
    }

    @Test
    public void carOrderByAscending(){
        assertEquals(Car.orderBy("Brand", Sort.ASCENDING).getFirst().getBrand(),"Audi");
    }

    @Test
    public void carOrderByDescending(){
        assertEquals(Car.orderBy("Brand", Sort.DESCENDING).getFirst().getBrand(),"Toyota");
    }

    @Test
    public void carGetIDUsingWhereBrandAndWhereModelAndGetFirst(){
        assertEquals(Car.where("Brand","Toyota").where("Model","Corolla").getFirst().getID(),"C002");
    }

    @Test
    public void carGetRentalPricePerDay(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getRentalPricePerDay(), 70);
    }

    @Test
    public void carGetInsurancePerDay(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getInsurancePerDay(), 10);
    }

    @Test
    public void carGetServiceFee(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getServiceFee(), 10);
    }

    @Test
    public void carGetDiscount(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getDiscount(), 10);
    }

    @Test
    public void carGetNumberOfSeats(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getSeatCount(), 4);
    }

    @Test
    public void carGetColor(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getColor(), "Green");
    }

    @Test
    public void carGetType(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getType(), "Hatch");
    }

    @Test
    public void carGetModel(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getModel(), "Cruze");
    }

    @Test
    public void carGetYearOfManufacture(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getYearOfManufacture(), 2020);
    }

    @Test
    public void carGetVehicleId(){
        Car car = Car.where("Vehicle ID","C005").getFirst();

        assertEquals(car.getID(), "C005");

    }


}
