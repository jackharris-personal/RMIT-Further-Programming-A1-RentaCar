package com.jackgharris.cosc2288.mycar.controllers;

import com.jackgharris.cosc2288.mycar.core.*;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.Reservation;
import com.jackgharris.cosc2288.mycar.views.ReservationView;

public class ReservationController extends Controller {

    public ReservationController(Application app) {
        super(app);
        this.view = new ReservationView();
        this.currentView = "selectDates";
    }

    @Override
    public void handleRequest(Request request) {
        //Create our response object
        Response response = new Response();

        //Depending on the state of the current view we call the helper methods to process logic, this controller
        //has its logic split into methods rather than in handle request like the MenuController due to the size
        //of the logic, the method would become too big and hard to read if the code was not segmented like this.
        //All the helper view methods return a response.
        switch(this.currentView){

            case "selectDates" -> response = this.selectDates(request);
            case "showVehicleDetails" -> response = this.showVehicleDetails(request);
            case "collectUserDetails" -> response = this.CollectUserDetails(request);
            case "confirmation" -> response = this.confirmation(request);

        }

        //finally once our processing is completed we update the view and pass it the response.
        this.app.updateView(response);
    }

    @Override
    public void updateView(Response response) {
        //Declare our new request object
        Request request;

        //All views return a request and accept a response as an input and will return the request object.
        switch (this.currentView) {

            //Set pickup and return dates view
            case "selectDates" -> request = ((ReservationView) this.view).selectDates(response);
            //Show all vehicle details and confirm request to book view
            case "showVehicleDetails" -> request = ((ReservationView) this.view).showVehicleDetails(response);
            //Collects all the details from the user
            case "collectUserDetails" -> request = ((ReservationView) this.view).collectUserDetails(response);
            //Show Cars view that is called by the above three options to display a dynamic list of cars.
            case "confirmation" -> request = ((ReservationView) this.view).confirmation(response);

            //Else we throw an error that an invalid view was called.
            default -> {
                System.out.println(Ascii.red+"Error: invalid view called"+Ascii.black);
                request = new Request();
            }
        }

        //Finally once we have our input and built our request we recall the this.app.handleRequest(request) method
        //to have it processed.
        this.app.handleRequest(request);
    }

    private Response selectDates(Request request){
        Response response = new Response(request);

        if(request.containsUserInput()){

            if(!request.isDate()){
                response.setError("Invalid date provided, dates must be formatted as 'day/month/year'\nExample: '16/09/2022'");
                return response;
            }

            response.getData().remove("error");


            if(!request.containsData("reservation")){

                Reservation reservation = new Reservation();
                response.setNotification("Pick-up date set for '"+request.getUserInput()+"'");
                reservation.setPickUpDate(request.getUserInput());
                reservation.setCar((Car)request.get("car"));
                response.add("reservation",reservation);
                response.add("type","drop-off");

            }else{

                ((Reservation)request.get("reservation")).setDropOffDate(request.getUserInput());
                response.add("reservation",request.get("reservation"));

                if(!((Reservation)response.get("reservation")).validateDateRange()){
                    response.setError(((Reservation)response.get("reservation")).getDateRangeError()+", please select a date greater than '"+((Reservation)response.get("reservation")).getPickUpDate()+"'");
                }else{
                    response.setViewRedirect("showVehicleDetails");
                }
            }

        }

        return response;
    }

    private Response showVehicleDetails(Request request){
        Response response = new Response(request);

        //show notification that dates were set correctly
        response.setNotification("Pick-up & Drop-off dates set for '"+((Reservation) request.get("reservation")).getPickUpDate()+" - "+((Reservation) request.get("reservation")).getDropOffDate()+"'");

        if(!request.containsUserInput()){
            return response;
        }

        if(request.getUserInput().matches("n")){
            response.setNotification("Booking Cancelled -> Returned user to main menu");
            response.getData().remove("error");
            this.app.setActiveController("MenuController");
            return response;

        }

        if(request.getUserInput().matches("y")) {
            response.setViewRedirect("collectUserDetails");
            response.setNotification("Car Selected, please enter your details.");
            return response;
        }

        response.setError("Invalid selection, please enter 'y' to confirm or 'n' to cancel");

        return response;
    }

    private Response CollectUserDetails(Request request){
        Response response = new Response(request);

        Reservation reservation = (Reservation) request.get("reservation");

        if(!request.containsData("given name")){
            response.add("heading","Please enter your given name");

            if(request.containsUserInput()) {

                if(!request.isString() || request.getUserInput().isBlank()){
                    response.setError("Given name must be a string only that is not blank");

                }else{
                    response.add("given name", request.getUserInput());
                    response.add("heading","Please enter your surname name");
                    response.setNotification("Given name set to '" + request.getUserInput() + "'");
                    response.getData().remove("error");
                    reservation.setUserGivenName(request.getUserInput());

                }

            }

            return response;
        }

        if(!request.containsData("surname")){
            response.add("heading","Please enter your surname name");

            if(request.containsUserInput()) {

                if(!request.isString() || request.getUserInput().isBlank()){
                    response.setError("Given name must be a string only that is not blank");
                }else{
                    response.add("surname", request.getUserInput());
                    response.add("heading","Please enter your email address");
                    response.setNotification("Surname set to '" + request.getUserInput() + "'");
                    response.getData().remove("error");
                    reservation.setUserSurname(request.getUserInput());
                }


            }
            return response;
        }

        if(!request.containsData("email")){
            response.add("heading","Please enter your email address");

            if(request.containsUserInput()) {

                if (!request.isEmail()) {
                    response.setError("Error, email address must be in a valid format,\nFor example: john.smith@email.com");
                    return response;
                }

                response.add("email", request.getUserInput());
                response.add("heading","Please enter your number of passengers");
                response.setNotification("Surname email to '" + request.getUserInput() + "'");
                response.getData().remove("error");
                reservation.setUserEmail(request.getUserInput());

            }

            return response;

        }

        if(!request.containsData("passengers")){
            response.add("heading","Please the number of passengers traveling with you");

            if(request.containsUserInput()) {

                if (!request.isInteger()) {
                    response.setError("Error, number of passengers must be a whole number only");
                    return response;
                }

                response.add("passengers", request.getUserInput());
                response.add("heading","Please enter your number of passengers");
                response.setNotification("Passengers set to '" + request.getUserInput() + "'");
                response.getData().remove("error");
                reservation.setPassengerCount(request.getUserInputAsInteger());
            }

            return response;

        }

        if(request.containsUserInput()){
            if(request.getUserInput().equals("n")){
                response.setNotification("Booking Cancelled -> Returned user to main menu");
                this.app.setActiveController("MenuController");
                return response;
            }

            if(request.getUserInput().equals("y")){
                response.setNotification("A receipt has been sent to your email.\nWe will soon be in touch before your  pick-up date.");
                response.setViewRedirect("confirmation");
                return response;
            }

            response.setError("unknown input, please enter 'y' to confirm or 'n' to cancel the booking");
        }


        return response;
    }

    private Response confirmation(Request request){
        Response response = new Response(request);

        if(request.containsUserInput()){
            if(request.isInteger()){
                if(request.getUserInputAsInteger() == 2){
                    this.app.exit();
                }else{
                    response.setError("Invalid input, please enter 2 to exit the program or 1 to return to the main menu");
                }
                if(request.getUserInputAsInteger() == 1){
                    response.setViewRedirect("menu");
                    this.app.setActiveController("MenuController");
                }
            }else{
                response.setError("Invalid input, please enter 2 to exit the program or 1 to return to the main menu");
            }
        }

        return response;
    }
}
