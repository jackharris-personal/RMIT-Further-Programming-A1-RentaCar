//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.controllers;

//**** PACKAGE IMPORTS ****\\
//Imports for all the packages used by this class
import com.jackgharris.cosc2288.mycar.core.*;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.Reservation;
import com.jackgharris.cosc2288.mycar.views.ReservationView;

//**** START CLASS ****\\
public class ReservationController extends Controller {

    //**** CONSTRUCTOR ****\\
    //The constructor accepts the instance of the Application object and initializes the view.
    public ReservationController(Application app) {
        super(app);
        this.view = new ReservationView();
        this.currentView = "selectDates";
    }

    //**** HANDLE REQUEST ****\\
    //This method is called by the this.app.handleRequest method and accepts a request from
    //a view and will process it before returning a response.
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

    //**** SELECT DATA METHOD ****\\
    //This method processes the logic for the select date view, it will ensure that the dates provided are valid and then
    //allow the user to process to the next step.
    private Response selectDates(Request request){
        //create our new response to return.
        Response response = new Response(request);

        //check if the view contains user input, if so then process the logic, if not then skip.
        if(request.containsUserInput()){

            //check if the input provided is a valid date format, if not then return an error.
            if(!request.isDate()){
                response.setError("Invalid date provided, dates must be formatted as 'day/month/year'\nExample: '16/09/2022'");
                //return the response to prevent further processing
                return response;
            }

            //if we reach this step then remove any stray errors that may remain in the request.
            response.getData().remove("error");

            //check if the request has a reservation object in it, if not then we can assume this is the pickup date,
            //else we assume it's the drop-off date.
            if(!request.containsData("reservation")){

                //create our new reservation object.
                Reservation reservation = new Reservation();
                //set the notification to say the pickup date is set.
                response.setNotification("Pick-up date set for '"+request.getUserInput()+"'");
                //set the pickup date in the reservation to the valid date provided.
                reservation.setPickUpDate(request.getUserInput());
                //set the car to the current selected var, get this from the request
                reservation.setCar((Car)request.get("car"));
                //place the reservation into the request
                response.add("reservation",reservation);
                //set the type to drop off.
                response.add("type","drop-off");

                //else we assume step 1 is complete a process the second step (drop off)
            }else{

                //get the reservation from the request and set the drop-off date.
                ((Reservation)request.get("reservation")).setDropOffDate(request.getUserInput());
                //add the reservation back to the response.
                response.add("reservation",request.get("reservation"));

                //check if the reservation has a valid date range (positive between dates)
                if(!((Reservation)response.get("reservation")).validateDateRange()){
                    //if not then set the error to return to the user
                    response.setError(((Reservation)response.get("reservation")).getDateRangeError()+", please select a date greater than '"+((Reservation)response.get("reservation")).getPickUpDate()+"'");
                }else{
                    //else set the view to show vehicle details
                    response.setViewRedirect("showVehicleDetails");
                }
            }

        }

        //return the response now all processing is completed.
        return response;
    }

    //**** SHOW VEHICLE DETAILS METHOD ****\\
    //This method will show and confirm the vehicle details to the user
    private Response showVehicleDetails(Request request){
        //create our response and give it the request to clone data
        Response response = new Response(request);

        //show notification that dates were set correctly
        response.setNotification("Pick-up & Drop-off dates set for '"+((Reservation) request.get("reservation")).getPickUpDate()+" - "+((Reservation) request.get("reservation")).getDropOffDate()+"'");

        //check if the request does not contain any user input, if not then return now and do not process further
        if(!request.containsUserInput()){
            return response;
        }

        //if we reach here first check if the user input is 'n' if so then cancel the booting
        if(request.getUserInput().matches("n")){
            //set a cancelled booking notification
            response.setNotification("Booking Cancelled -> Returned user to main menu");
            //remove any stray errors
            response.getData().remove("error");
            //set the controller back to the main menu
            this.app.setActiveController("MenuController");
            //return the response to stop further method processing
            return response;
        }

        //if 'y' then we set the view to collect user details and set the notification to state 'car selected'
        if(request.getUserInput().matches("y")) {
            //set the view to collect user details
            response.setViewRedirect("collectUserDetails");
            //set the notification
            response.setNotification("Car Selected, please enter your details.");
            //return the repose to stop further processing
            return response;
        }

        //else if we reach here then either 'n' or 'y' where entered, and we should show an error for invalid input.
        response.setError("Invalid selection, please enter 'y' to confirm or 'n' to cancel");

        //return the response
        return response;
    }

    //**** COLLECT USER DETAILS METHOD ****\\
    private Response CollectUserDetails(Request request){
        //create our response and clone the request data
        Response response = new Response(request);

        //pull our reservation object out of the request
        Reservation reservation = (Reservation) request.get("reservation");

        //check if the request contains the given name, if not then set the heading to request the given name
        if(!request.containsData("given name")){
            response.add("heading","Please enter your given name");

            //if we contain input then validate that the name is a string and not blank
            if(request.containsUserInput()) {

                //if the name is black or is not a string then set the error
                if(!request.isString() || request.getUserInput().isBlank()){
                    response.setError("Given name must be a string only that is not blank");

                    //else then we add the given name to the response and request the surname
                }else{
                    //add the given name to response
                    response.add("given name", request.getUserInput());
                    //set the heading to request the surname
                    response.add("heading","Please enter your surname name");
                    //set a notification that the given name is set
                    response.setNotification("Given name set to '" + request.getUserInput() + "'");
                    //remove any stray errors
                    response.getData().remove("error");
                    //set the given name in the reservation object.
                    reservation.setUserGivenName(request.getUserInput());
                }

            }
            //return the response to stop further processing
            return response;
        }

        //check if the method contains the surname, if not then process
        if(!request.containsData("surname")){
            //set the heading to request the surname
            response.add("heading","Please enter your surname name");

            //check if the request contains user input, if so process
            if(request.containsUserInput()) {

                //check if the input is a string and is not blank, if not set the error
                if(!request.isString() || request.getUserInput().isBlank()){
                    response.setError("Given name must be a string only that is not blank");

                    //else set the valid surname
                }else{
                    //add the surname to the response
                    response.add("surname", request.getUserInput());
                    //set the heading to request the users email
                    response.add("heading","Please enter your email address");
                    //set the notification to state the surname is set and valid
                    response.setNotification("Surname set to '" + request.getUserInput() + "'");
                    //remove any stray errors
                    response.getData().remove("error");
                    //set the reservation surname to this input
                    reservation.setUserSurname(request.getUserInput());
                }


            }
            //return the response to stop further processing.
            return response;
        }

        //if the request does not contain email then process this logic, else skip
        if(!request.containsData("email")){
            //set the heading to request the email
            response.add("heading","Please enter your email address");

            //check if the request contains user input
            if(request.containsUserInput()) {

                //if the request input is not an email throw an error
                if (!request.isEmail()) {
                    //set the response error to provide feedback to the user
                    response.setError("Error, email address must be in a valid format,\nFor example: john.smith@email.com");
                    //return the response to stop further processing
                    return response;
                }

                //else then the input is a valid email  set the email in the response to the user input
                response.add("email", request.getUserInput());
                //set the heading to request the number of passengers
                response.add("heading","Please enter your number of passengers");
                //set the notification to state email was set
                response.setNotification("Email set to '" + request.getUserInput() + "'");
                //clear stray errors
                response.getData().remove("error");
                //set the email in the reservation to the input
                reservation.setUserEmail(request.getUserInput());

            }

            //return the response to stop further processing
            return response;

        }

        //if the request does not contain passengers process the logic for collecting the passengers input
        if(!request.containsData("passengers")){
            //set the heading to request the passengers
            response.add("heading","Please the number of passengers traveling with you");

            //check if the request contains user input
            if(request.containsUserInput()) {

                //check if the request is an integer, if not then set the error
                if (!request.isInteger()) {
                    //if not an integer ask for an integer only
                    response.setError("Error, number of passengers must be a whole number only");
                    //return the response to prevent further processing.
                    return response;
                }

                //else this is a valid number input, set the passengers in the response
                response.add("passengers", request.getUserInput());
                //set the heading to request the number of passengers
                response.add("heading","Please enter your number of passengers");
                //set the notification to state the passengers have been set correctly.
                response.setNotification("Passengers set to '" + request.getUserInput() + "'");
                //remove any stray errors
                response.getData().remove("error");
                //set the reservation passenger count
                reservation.setPassengerCount(request.getUserInputAsInteger());
            }

            //return the response to prevent any further method processing
            return response;
        }

        //if we reach this state and no response has been returned then all the data is collected, and we can confirm or reject the booking.
        if(request.containsUserInput()){

            //if the user input is "n", then cancel the booking and return the user to the main menu.
            if(request.getUserInput().equals("n")){
                //set the notification to booking cancelled
                response.setNotification("Booking Cancelled -> Returned user to main menu");
                //set the active controller to the menu controller
                this.app.setActiveController("MenuController");
                //return the response to prevent further processing.
                return response;
            }

            //if the input is "y", then set the notification that a receipt has been emailed and we will be in contact
            if(request.getUserInput().equals("y")){
                //set the notification
                response.setNotification("A receipt has been sent to your email.\nWe will soon be in touch before your  pick-up date.");
                //set the view redirect to the confirmation page
                response.setViewRedirect("confirmation");
                //return the response to prevent further processing.
                return response;
            }

            //else then a invalid input has been provided, and we will request the user only enter "y" or "n"
            response.setError("unknown input, please enter 'y' to confirm or 'n' to cancel the booking");
        }

        //return the response
        return response;
    }

    //**** CONFIRMATION METHOD ****\\
    //The confirmation method displays the final confirmation to the user and gives them the option
    //to exit for to return to the main menu.
    private Response confirmation(Request request){
        //create our response and parse it the request to get the data
        Response response = new Response(request);

        //check if the response contains user input if not do nothing.
        if(request.containsUserInput()){

            //check if the request input is an integer, if so then process the logic else set the
            //error to invalid input.
            if(request.isInteger()){

                //if the integer is 2 then exit the program
                if(request.getUserInputAsInteger() == 2){
                    this.app.exit();
                }else{
                    //else set the response error to invalid input
                    response.setError("Invalid input, please enter 2 to exit the program or 1 to return to the main menu");
                }

                //if the input is 1 then set the redirect to the menu and set the active controller to the menu controller
                if(request.getUserInputAsInteger() == 1){
                    response.setViewRedirect("menu");
                    this.app.setActiveController("MenuController");
                }
            }else{
                //else then we have an invalid input provided by the user and set the error to tell them
                response.setError("Invalid input, please enter 2 to exit the program or 1 to return to the main menu");
            }
        }

        //finally we return the response back to the view.
        return response;
    }
}
