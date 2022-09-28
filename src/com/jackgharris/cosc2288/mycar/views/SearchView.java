//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.views;

//**** PACKAGE IMPORTS ****\\
//classes and packages required to be imported by this class
import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;
import com.jackgharris.cosc2288.mycar.models.Car;

//**** START CLASS ****\\
//This protected view class extends the main view parent
public class SearchView extends View {

    //**** BRAND MENU METHOD ****\\
    //This view method allows the user to filter a car by a specific brand, it will display
    //the brands that a user can select.
    public Request brandMenu(Response response) {

        //show the header
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | Filtering by Brand");
        System.out.println("------------------------------------------------------------------");

        //create our menu counter variable
        int counter = 1;

        //check if the carBrands are set by the response if so then display them
        if (response.containsKey("carBrands")) {
            //for each brand in the array of brands display them and increment the menu counter
            for (String brand : (String[]) response.get("carBrands")) {
                System.out.println("    "+counter+") "+brand);
                counter ++;
            }
        }

        //add the back to main menu option
        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");

        //request the user select a brand either by name or by menu item number.
        System.out.println("Please select the Brand number or enter the brand name as shown\nExample: 'Toyota' or '1'");
        System.out.println("\n");

        //check if the response contains an error
        if(response.containsError()){
            //if so print the error.
            System.out.println(response.getError());
        }

        //finally get the user input and return the request
        return this.getUserInput(new Request());
    }

    //**** CAR TYPE MENU METHOD ****\\
    //Show and request the user select a car type form the list of valid car types
    public Request typeMenu(Response response){

        //show the heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | Showing Vehicle Types");
        System.out.println("------------------------------------------------------------------");

        //declare our menu option counter variable
        int counter = 1;

        //check if the carTypes are set, if so then print them all and incriment the counter
        if (response.containsKey("carTypes")) {
            for (String type : (String[])response.get("carTypes")) {
                System.out.println("    "+counter+") "+type);
                counter ++;
            }
        }

        //add our main menu option
        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");

        //check if the response contains an error
        if(response.containsError()){
            //if so print the error
            System.out.println(response.getError());
        }

        //finally return the user input via a new request.
        return this.getUserInput(new Request());
    }

    //**** SHOW CARS METHOD ****\\
    //This method will show a list of cars, this list will be selected via
    //one of the other methods.
    public Request showCars(Response response){

        //create our new request and build it from the response
        Request request = new Request(response);

        //show our heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | "+response.get("heading"));
        System.out.println("------------------------------------------------------------------");

        //check if we have a notification if so then show it
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }

        //create our options counter
        int counter = 1;

        //for each of the cars inside our car response then display them and increase our
        if (response.containsKey("cars")) {
            for (Car car : (Car[])response.get("cars")) {
                System.out.println("    "+counter+") "+car.getID()+" "+car.getBrand()+" "+car.getModel()+" with "+car.getSeatCount()+" seats");
                counter ++;
            }
        }

        //show our back to main menu option
        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");

        //check if the response contains an error, if so then display the error
        if(response.containsError()){
            System.out.println(response.getError());
        }

        //finally return the user input with the request object
        return this.getUserInput(request);
    }

    //**** SEARCH BY PASSENGERS METHOD ****\\
    //This method allows the user to search the cars by the passenger count
    public Request Passengers(Response response){

        //create our new request object
        Request request = new Request();

        //show the heading
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Search for vehicle by seats");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Please enter the minimum number of seats required (1-8 limit)");

        //check if a notification is set, if so then show it
        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }

        //check if an error is set then display it, else do nothing
        if(response.containsError()){
            System.out.println(response.getError());
        }

        //finally return the user input via the get user input protected method.
        return this.getUserInput(request);
    }
}
