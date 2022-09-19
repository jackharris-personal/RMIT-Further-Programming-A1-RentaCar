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

        System.out.println("\n");

        Car car = (Car)response.get("car");
        Reservation reservation = (Reservation)response.get("reservation");

        System.out.println("Vehicle:              "+car.getID());
        System.out.println("Brand:                "+car.getBrand());
        System.out.println("Model:                "+car.getModel());
        System.out.println("Type of vehicle:      "+car.getType());
        System.out.println("year of manufacture:  "+car.getYearOfManufacture());
        System.out.println("No. Of Seats:         "+car.getSeatCount());
        System.out.println("Color:                "+car.getColor());
        System.out.println("Rental:               $"+reservation.getRentalCost()+"     "+reservation.getStandardCalculation());

        if(reservation.hasDiscount()){
            System.out.println("Discount price:       $"+reservation.getDiscountPrice()+"     "+reservation.getDiscountCalculation());
        }else{
            System.out.println("Discount price:       $"+reservation.getDiscountPrice()+"      (Discount is not applicable)");
        }

        System.out.println("Insurance:            $"+reservation.getInsuranceCost()+"     "+reservation.getInsuranceCalculation());
        System.out.println("Service Fee:          $"+reservation.getServiceFee());
        System.out.println("Total:                $"+reservation.getTotalCost()+"\n");

        System.out.println("Would you like to reserve the vehicle (y/n)?");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }

    public Request collectUserDetails(Response response){

        Request request = new Request(response);

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Provide personal information");
        System.out.println("------------------------------------------------------------------");
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }
        System.out.println("\n");

        int  detailsProvided = 0;

        if(response.containsKey("given name")){
            System.out.println("Given Name: "+response.get("given name"));
            detailsProvided++;
        }
        if(response.containsKey("surname")){
            System.out.println("Surname: "+response.get("surname"));
            detailsProvided++;
        }
        if(response.containsKey("email")){
            System.out.println("Email: "+response.get("email"));
            detailsProvided++;
        }

        if(response.containsKey("passengers")){
            System.out.println("Passengers: "+response.get("passengers")+"\n");
            detailsProvided++;
        }

        if(detailsProvided < 4){
            System.out.println(response.get("heading"));
        }else{
            System.out.println("Please enter 'y' or 'n' to confirm and pay or cancel and return to the main menu");
        }
        System.out.println("\n");


        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }

    public Request confirmation(Response response){

        Request request = new Request(response);
        Reservation reservation = (Reservation) response.get("reservation");

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Congratulations! Your vehicle is booked.");
        System.out.println("------------------------------------------------------------------");

        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }

        System.out.println("");

        System.out.println("Name:                 "+reservation.getUserGivenName()+" "+reservation.getUserSurname());
        System.out.println("Email:                "+reservation.getUserEmail());
        System.out.println("Your Vehicle:         "+reservation.getCar().getBrand()+" "+reservation.getCar().getModel()+" "+reservation.getCar().getType()+" with "+reservation.getCar().getSeatCount()+" seats");
        System.out.println("Number of passengers: "+reservation.getPassengerCount());
        System.out.println("Pick-up date:         "+reservation.getPickUpDate());
        System.out.println("Drop-off date:        "+reservation.getDropOffDate());
        System.out.println("Total payment:        $"+reservation.getTotalCost());

        System.out.println("");

        System.out.println("1) Return to main menu");
        System.out.println("2) Exit program");


        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }

}
