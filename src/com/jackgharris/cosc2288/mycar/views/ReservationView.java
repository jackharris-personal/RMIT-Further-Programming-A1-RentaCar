//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.views;

//**** PACKAGE IMPORTS ****\\
//Classes from other packages that are required by this class
import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.Reservation;

//**** START CLASS ****\\
//This class extends the parent view class
public class ReservationView extends View {

    //**** SELECT DATES METHOD ****\\
    //This method returns a request with user input and accepts a response
    public Request selectDates(Response response){

        //create the request and parse it the response data to extreact
        Request request = new Request(response);

        //display the heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> "+response.get("heading"));
        System.out.println("------------------------------------------------------------------");

        //check if the response contains a notification
        if(response.containsNotification()){
            //if so then display it
            System.out.println(response.getNotification());
        }

        //show the menu / user input request
        System.out.println("Please provide a "+response.get("type")+" date");

        //check if the response contains an error
        if(response.containsError()){
            //if so then display the error
            System.out.println(response.getError());
        }

        //return the request with the user input
        return this.getUserInput(request);
    }

    //**** SHOW VEHICLE DETAILS METHOD ****\\
    //This method view shows all the vehicle details to the user regarding their booking
    public Request showVehicleDetails(Response response){
        //create our new request object and give it the response
        Request request = new Request(response);

        //display the heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> show vehicle details");
        System.out.println("------------------------------------------------------------------");

        //check if the response contains a notification
        if(response.containsNotification()){
            //display the notification is required
            System.out.println(response.getNotification());
        }

        //print a new line
        System.out.println("\n");

        //get the car from the response
        Car car = (Car)response.get("car");
        //get the reservation from the response
        Reservation reservation = (Reservation)response.get("reservation");

        //output the car and reservation details
        System.out.println("Vehicle:              "+car.getID());
        System.out.println("Brand:                "+car.getBrand());
        System.out.println("Model:                "+car.getModel());
        System.out.println("Type of vehicle:      "+car.getType());
        System.out.println("year of manufacture:  "+car.getYearOfManufacture());
        System.out.println("No. Of Seats:         "+car.getSeatCount());
        System.out.println("Color:                "+car.getColor());
        System.out.println("Rental:               $"+reservation.getRentalCost()+"     "+reservation.getStandardCalculation());

        //check if the reservation has a discount, if so output it, if not output "discount not applicable"
        if(reservation.hasDiscount()){
            System.out.println("Discount price:       $"+reservation.getDiscountPrice()+"     "+reservation.getDiscountCalculation());
        }else{
            System.out.println("Discount price:       $"+reservation.getDiscountPrice()+"      (Discount is not applicable)");
        }

        //continue to output the details
        System.out.println("Insurance:            $"+reservation.getInsuranceCost()+"     "+reservation.getInsuranceCalculation());
        System.out.println("Service Fee:          $"+reservation.getServiceFee());
        System.out.println("Total:                $"+reservation.getTotalCost()+"\n");

        //ask the user if they would like to confirm or not "y" or "n"
        System.out.println("Would you like to reserve the vehicle (y/n)?");

        //check if the response contains an error
        if(response.containsError()){
            //if so print the error
            System.out.println(response.getError());
        }

        //return the request with the user input
        return this.getUserInput(request);
    }

    //**** COLLECT USER DETAILS METHOD ****\\
    //This method view collects all the user details from the user
    public Request collectUserDetails(Response response){

        //create our new request from the response
        Request request = new Request(response);

        //output the heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Provide personal information");
        System.out.println("------------------------------------------------------------------");

        //check if we need to show a notification
        if(response.containsNotification()){
            //output the notification
            System.out.println(response.getNotification());
        }

        //print a new line
        System.out.println("\n");

        //track our details provided count
        int  detailsProvided = 0;

        //if the user has provided the given name then output what the name is an increment the details provided.
        if(response.containsKey("given name")) {
            System.out.println("Given Name: " + response.get("given name"));
            detailsProvided++;
        }

        //if the user has provided the surname then output what the name is an increment the details provided.
        if(response.containsKey("surname")){
            System.out.println("Surname: "+response.get("surname"));
            detailsProvided++;
        }

        //if the user has provided the email then output what the name is an increment the details provided.
        if(response.containsKey("email")){
            System.out.println("Email: "+response.get("email"));
            detailsProvided++;
        }

        //if the user has provided the passengers then output what the name is an increment the details provided.
        if(response.containsKey("passengers")){
            System.out.println("Passengers: "+response.get("passengers")+"\n");
            detailsProvided++;
        }

        //finally check if all 4 details have been provided, if no then show the current heading
        if(detailsProvided < 4){
            System.out.println(response.get("heading"));
        }else{
            //else ask the user to confirm "y" or "n" to create the booking
            System.out.println("Please enter 'y' or 'n' to confirm and pay or cancel and return to the main menu");
        }

        //output a new line
        System.out.println("\n");

        //check if the response contains an error
        if(response.containsError()){
            //if so then display it
            System.out.println(response.getError());
        }

        //finally return this request to the system with the user input.
        return this.getUserInput(request);
    }

    //**** CONFIRMATION METHOD ****\\
    //This method view shows the final booking confirmation to the user after a successful booking has been made.
    public Request confirmation(Response response){

        //create our request and provide it the response
        Request request = new Request(response);

        //get our reservation object from the response
        Reservation reservation = (Reservation) response.get("reservation");

        //show our heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Congratulations! Your vehicle is booked.");
        System.out.println("------------------------------------------------------------------");

        //check if the response contains a notification, if so then show it
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }

        //print a blank line
        System.out.println("");

        //show the confirmation
        System.out.println("Name:                 "+reservation.getUserGivenName()+" "+reservation.getUserSurname());
        System.out.println("Email:                "+reservation.getUserEmail());
        System.out.println("Your Vehicle:         "+reservation.getCar().getBrand()+" "+reservation.getCar().getModel()+" "+reservation.getCar().getType()+" with "+reservation.getCar().getSeatCount()+" seats");
        System.out.println("Number of passengers: "+reservation.getPassengerCount());
        System.out.println("Pick-up date:         "+reservation.getPickUpDate());
        System.out.println("Drop-off date:        "+reservation.getDropOffDate());
        System.out.println("Total payment:        $"+reservation.getTotalCost());

        //print a blank line
        System.out.println("");

        //show the options to the user, either return to main menu or exit
        System.out.println("1) Return to main menu");
        System.out.println("2) Exit program");

        //check if the response contains any errors and display them if so
        if(response.containsError()){
            System.out.println(response.getError());
        }

        //finally return the request via the get user input protected method.
        return this.getUserInput(request);
    }

}
