package com.jackgharris.cosc2288.mycar.views;

import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.Reservation;

public class ReservationView extends View {

    public Request selectDates(Response response){

        Request request = new Request(response);

        System.out.println("------------------------------------------------------------------");
        System.out.println("> "+response.get("heading"));
        System.out.println("------------------------------------------------------------------");
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }
        System.out.println("Please provide a "+response.get("type")+" date");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }

    public Request showVehicleDetails(Response response){
        Request request = new Request(response);

        System.out.println("------------------------------------------------------------------");
        System.out.println("> show vehicle details");
        System.out.println("------------------------------------------------------------------");
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }
        Car car = (Car)response.get("car");
        Reservation reservation = (Reservation)response.get("reservation");

        System.out.println("Vehicle:              "+car.getID());
        System.out.println("Brand:                "+ car.getBrand());
        System.out.println("Model:                "+ car.getModel());
        System.out.println("Type of vehicle:      "+car.getType());
        System.out.println("year of manufacture:  "+car.getYearOfManufacture());
        System.out.println("No. Of Seats:         "+car.getSeatCount());
        System.out.println("Color:                "+car.getColor());
        System.out.println("Rental:               $"+reservation.getRentalCost());
        System.out.println("Discount price:       $"+reservation.getDiscountPrice());
        System.out.println("Insurance:            $"+reservation.getInsuranceCost());
        System.out.println("Service Fee:          $"+reservation.getServiceFee());
        System.out.println("Total:                $"+reservation.getTotalCost()+"\n");

        System.out.println("Would you like to reserve the vehicle (Y/N)?");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }

    public Request collectUserDetails(Response response){

        return this.getUserInput(new Request());
    }

    public Request confirmAndPay(Response response){

        return this.getUserInput(new Request());
    }

}
