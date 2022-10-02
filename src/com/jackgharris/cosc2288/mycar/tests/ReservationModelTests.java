package com.jackgharris.cosc2288.mycar.tests;

import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.Reservation;
import com.jackgharris.cosc2288.mycar.services.FileService;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ReservationModelTests {

    @Test
    public void setAndGetGivenName(){
        Reservation reservation = new Reservation();
        reservation.setUserGivenName("Jack");
        assertEquals(reservation.getUserGivenName(), "Jack");
    }

    @Test
    public void setAndGetSurname(){
        Reservation reservation = new Reservation();
        reservation.setUserSurname("Harris");
        assertEquals(reservation.getUserSurname(), "Harris");
    }

    @Test
    public void setAndGetEmailAddress(){
        Reservation reservation = new Reservation();
        reservation.setUserEmail("test@example.com");
        assertEquals(reservation.getUserEmail(), "test@example.com");
    }


    @Test
    public void setAndGetPassengerCount(){
        Reservation reservation = new Reservation();
        reservation.setPassengerCount(25);
        assertEquals(reservation.getPassengerCount(), 25);
    }

    @Test
    public void setAndGetPickUpDate(){
        Reservation reservation = new Reservation();
        reservation.setPickUpDate("10/12/2022");
        assertEquals(reservation.getPickUpDate(), "10/12/2022");
    }

    @Test
    public void SetAndGetDropOffDate(){
        Reservation reservation = new Reservation();
        reservation.setDropOffDate("25/12/2022");
        assertEquals(reservation.getDropOffDate(), "25/12/2022");
    }

    @Test
    public void SetAndGetCar(){
        Reservation reservation = new Reservation();
        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();
        reservation.setCar(yaris);
        assertEquals(reservation.getCar().getID(), yaris.getID());
    }

    @Test
    public void reservationValidateDateRangeWithDropOffBeforePickup(){
        Reservation reservation = new Reservation();
        reservation.setPickUpDate("10-12-2022");
        reservation.setDropOffDate("05-12-2022");
        assertFalse(reservation.validateDateRange());
    }

    @Test
    public void reservationValidateDateRangeWithPickUpAndDropOffOnSameDay(){
        Reservation reservation = new Reservation();
        reservation.setPickUpDate("23-12-2023");
        reservation.setDropOffDate("23-12-2023");

        assertTrue(reservation.validateDateRange());
    }

    @Test
    public void reservationHasDiscountWithBookingGreaterThanSevenDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("10-12-2022");
        reservation.setDropOffDate("25-12-2022");
        reservation.validateDateRange();

        assertTrue(reservation.hasDiscount());
    }

    @Test
    public void reservationGetDurationForSixteenDays(){
        Reservation reservation = new Reservation();

        reservation.setPickUpDate("10-12-2023");
        reservation.setDropOffDate("25-12-2023");

        reservation.validateDateRange();
        assertEquals(reservation.getDuration(),16);
    }

    @Test
    public void reservationGetDurationForTwoDays(){
        Reservation reservation = new Reservation();

        reservation.setPickUpDate("02-10-2022");
        reservation.setDropOffDate("03-10-2022");

        reservation.validateDateRange();
        assertEquals(2,reservation.getDuration());
    }

    @Test
    public void reservationGetRentalCostForEightDays(){
        Reservation reservation = new Reservation();

        reservation.setPickUpDate("10-12-2023");
        reservation.setDropOffDate("17-12-2023");

        reservation.validateDateRange();
        assertEquals(8,reservation.getDuration());
    }

    @Test
    public void reservationGetDiscountRentalCostForYarisModelForSevenDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("07-12-2023");
        reservation.validateDateRange();

        assertEquals(315,reservation.getDiscountPrice());
    }

    @Test
    public void reservationGetRentalCostForYarisModelForSixDaysNoDiscount(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("06-12-2023");
        reservation.validateDateRange();

        assertEquals(300,reservation.getRentalCost());
    }

    @Test
    public void reservationGetInsuranceCostForYarisModelForSixDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("06-12-2023");
        reservation.validateDateRange();

        assertEquals(90,reservation.getInsuranceCost());
    }

    @Test
    public void reservationGetServiceFeeForYarisModelForSixDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("06-12-2023");
        reservation.validateDateRange();

        assertEquals(10,reservation.getServiceFee());
    }

    @Test
    public void reservationGetTotalCostForYarisModelForSevenDaysAssumingDiscount(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("07-12-2023");
        reservation.validateDateRange();
        reservation.getDiscountPrice();

        assertEquals(430,reservation.getTotalCost());
    }

    @Test
    public void reservationGetTotalCostForYarisModelForDixDaysAssumingNoDiscount(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("06-12-2023");
        reservation.validateDateRange();

        assertEquals(300+90+10,reservation.getTotalCost());
    }

    @Test
    public void reservationGetDiscountCalculationForYarisModelForSevenDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("07-12-2023");
        reservation.validateDateRange();
        reservation.getDiscountPrice();

        assertEquals("($45.0 * 7 days )",reservation.getDiscountCalculation());
    }


    @Test
    public void reservationGetInsuranceCalculationForYarisModelForSevenDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("07-12-2023");
        reservation.validateDateRange();
        reservation.getDiscountPrice();

        assertEquals("($15 * 7 days )",reservation.getInsuranceCalculation());
    }

    @Test
    public void reservationGetStandardCalculationForYarisModelForTwoDays(){
        Reservation reservation = new Reservation();

        Car.bindModelData(FileService.loadCarModels(System.getProperty("user.dir")+"/fleet.csv"));
        Car yaris = Car.where("Vehicle ID","C001").getFirst();

        reservation.setCar(yaris);
        reservation.setPickUpDate("01-12-2023");
        reservation.setDropOffDate("02-12-2023");
        reservation.validateDateRange();

        assertEquals("($50 * 2 days )",reservation.getStandardCalculation());
    }



}
