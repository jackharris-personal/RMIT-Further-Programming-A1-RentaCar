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
            case "confirmAndPay" -> response = this.ConfirmAndPay(request);

        }

        //finally once our processing is completed we update the view and pass it the response.
        this.app.updateView(response);
    }

    @Override
    public void updateView(Response response) {
        //Declare our new request object
        Request request;

        //Check if the response is calling for a redirect, if so set the current view to the redirect view
        //then recall the this.app.handleRequest method and ensure all the response data to cloned into a new
        //request.
        if(response.shouldRedirect()){
            this.currentView = response.getViewRedirect();
            this.app.handleRequest(new Request(response));
        }

        //Else if we do not need to redirect we can load our view. All views return a request and accept a
        //response as an input.
        switch (this.currentView) {

            //Set pickup and return dates view
            case "selectDates" -> request = ((ReservationView) this.view).selectDates(response);
            //Show all vehicle details and confirm request to book view
            case "showVehicleDetails" -> request = ((ReservationView) this.view).showVehicleDetails(response);
            //Collects all the details from the user
            case "collectUserDetails" -> request = ((ReservationView) this.view).collectUserDetails(response);
            //Show Cars view that is called by the above three options to display a dynamic list of cars.
            case "confirmAndPay" -> request = ((ReservationView) this.view).confirmAndPay(response);

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

        return response;
    }

    private Response CollectUserDetails(Request request){
        Response response = new Response(request);

        return response;
    }

    private Response ConfirmAndPay(Request request){
        Response response = new Response(request);

        return response;
    }
}
