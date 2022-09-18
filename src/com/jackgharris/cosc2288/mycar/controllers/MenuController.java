//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.controllers;

//**** PACKAGE IMPORTS ****\\
//Other packages that have been imported into this class file.
import com.jackgharris.cosc2288.mycar.core.*;
import com.jackgharris.cosc2288.mycar.views.MenuView;

//**** START MENU CONTROLLER CLASS ****\\
//This class file extends the core controller class
public class MenuController extends Controller {

    //**** CONSTRUCTOR ****\\
    //This is the class constructor, it takes in an instance of our app and
    //sets the view object to a new instance of the view, followed by the
    //current view string, Note: in this application View Objects contain a
    //number of subviews relating to that view, thus the current view must
    //be specified.
    public MenuController(Application app) {
        super(app);
        this.view = new MenuView();
        this.currentView = "menu";
    }

    //**** HANDLE REQUEST METHOD ****\\
    //This method is the logical request processing of the controller, it will
    //take in a request object and handle any input, validation or data loading
    //before calling the this.app.updateView(Response) method that will
    //return the response that has been built by the controller.
    @Override
    public void handleRequest(Request request) {

        //Declare our new response object that this request will return.
        Response response = new Response();

        //Firstly we check if the input is not an integer, in this menu only
        //integers are valid input, so we can error here if It's anything else.
        if(!request.isInteger()){
            response.setError("Invalid input provided '"+request.getUserInput()+"', please provide a valid number");

        //else then the input is an integer then we can proceed
        }else {

            //next we perform a second check to see if user input has been provided or
            //if this is a blank request, this is important as we don't want to process
            //this code for blank requests.
            if (request.containsUserInput()) {

                //This switch statement represents our menu with each case being an option
                switch (request.getUserInputAsInteger()) {

                    //case 1 is filter by brand, if selected we set the view redirect to the brandMenu view
                    //and then set the active controller to the search controller
                    case 1 -> {
                        response.setViewRedirect("brandMenu");
                        this.app.setActiveController("SearchController");
                    }

                    //case 2 is filter by car type, if selected we set the view redirect and then set the
                    //active controller to Search Controller
                    case 2 -> {
                        response.setViewRedirect("typeMenu");
                        this.app.setActiveController("SearchController");
                    }

                    //case 3 is filter by passengers, if selected we set the view redirect to passengers then
                    //set the active controller to your SearchController.
                    case 3 -> {
                        response.setViewRedirect("passengers");
                        this.app.setActiveController("SearchController");
                    }

                    //case 4 is our close application call, this calls our main application exit method.
                    case 4 -> this.app.exit();

                    //our default is the option when case 1-4 fail, in this case we want to provide the user with
                    //an error passage stating this was an invalid selection.
                    default -> response.setError("Invalid selection provided '" + request.getUserInputAsInteger() + "', please select a valid input from 1-4");
                }
            }
        }


        //Finally at the end of the input processing we recall our app update view and pass
        //it the response variable created by this method.
        this.app.updateView(response);
    }

    //**** UPDATE VIEW METHOD ****\\
    //This method accepts a response from the application and calls the main application
    //handle request method with the request provided back from the view that was last called.
    @Override
    public void updateView(Response response) {

        //firstly we declare our request local variable
        Request request;

        //next we check if the response from the application contains a redirect call
        //if so then we set the current view to the redirect and recall the main application
        //handle request method. Lastly you'll note that we clone our response data to the
        //request this is to ensure any data passed does not get lost in the redirect.
        if(response.shouldRedirect()){
            this.currentView = response.getViewRedirect();
            this.app.handleRequest(new Request(response));
        }

        //Next we process our view, if the current view is the "menu" view then we set the request to the
        //menu view and pass it the response, else we state that an invalid view was called. As this controller
        //only has one view then we do not need a switch statement and this simply if you will suffice.
        if ("menu".equals(this.currentView)) {
            request = ((MenuView) this.view).menu(response);
        } else {
            System.out.println(Ascii.red+"Error: invalid view called"+Ascii.black);
            request = new Request();
        }

        //finally we tell the main application to the handle the request we just got from the view.
        this.app.handleRequest(request);
    }
}
