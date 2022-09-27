//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//These imports are class files that we are referencing or using
//inside this class, and thus they are imported.
import com.jackgharris.cosc2288.mycar.controllers.MenuController;
import com.jackgharris.cosc2288.mycar.controllers.ReservationController;
import com.jackgharris.cosc2288.mycar.controllers.SearchController;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;

import java.util.HashMap;

//**** CLASS ****\\
public class Application {

    //**** PRIVATE VARIABLES ****\\

    //controllers hashmap, this hashmap stores all our controllers
    //that we will use in the application, these have a key that will
    //match up with the active controller string to determine what controller
    //is currently active and required to be called.
    private final HashMap<String, Controller> controllers;

    //Active controller string, this must match a key in the controllers
    //hashmap and will be used to target the controller that is currently
    //active in the program.
    private String activeController;

    //**** CONSTRUCTOR ****\\
    //The main application constructor, this performs a number of functions
    //relating to getting the application ready to start the core run loop.
    public Application(){

        //set our working directory
        String directory = System.getProperty("user.dir");

        //bind Car model data to the car models that are contained inside the fleet.csv file
        Car.bindModelData(FileService.loadCarModels(directory+"/fleet.csv"));

        //instantiate our core controllers hashmap
        this.controllers = new HashMap<>();

        //add our controllers and pass them a reference to this application via 'this'
        this.controllers.put("MenuController", new MenuController(this));
        this.controllers.put("SearchController", new SearchController(this));
        this.controllers.put("ReservationController", new ReservationController(this));

        //set the default active controller
        this.activeController = "MenuController";

        //start the core loop by updating the view and provide a new blank response object.
        this.updateView(new Response());
    }

    //**** SET ACTIVE CONTROLLER METHOD ****\\
    //This method accepts a string and then sets the private active controller method to that
    //string, this is used by the handle request and update view method calls.
    public void setActiveController(String controller){
        this.activeController = controller;
    }

    //**** HANDLE REQUEST ****\\
    //this method accepts a request object and then calls the handle request method on our current
    //active controller passing it the request.
    public void handleRequest(Request request){
        this.controllers.get(this.activeController).handleRequest(request);
    }

    //**** UPDATE VIEW ****\\
    //This method accepts a response object and then calls the update view method on the active
    //controller.
    public void updateView(Response response){

        //next we check if the response from the application contains a redirect call
        //if so then we set the current view to the redirect and recall the main application
        //handle request method. Lastly you'll note that we clone our response data to the
        //request this is to ensure any data passed does not get lost in the redirect.
        if(response.shouldRedirect()){
            this.controllers.get(this.activeController).setCurrentView(response.getViewRedirect());
            this.handleRequest(new Request(response));
        }

        this.controllers.get(this.activeController).updateView(response);
    }

    //**** EXIT METHOD ****\\
    //This method is called when the program needs to close, this can be called from any
    //controller via thia.app.exit()
    public void exit(){
        System.out.println("Thank-you for using MyCar Console");
        System.out.println("MyCar is now saving & closing");
        System.exit(0);
    }

}
