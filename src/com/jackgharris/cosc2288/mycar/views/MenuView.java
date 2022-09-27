//**** SET PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.views;

//**** PACKAGE IMPORTS ****\\
import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;

//**** START CLASS ****\\
//This class extends the parent view class
public class MenuView extends View {

    //**** MENU METHOD ****\\
    //The menu method accepts a response and will return the request to the application's current controller
    public Request menu(Response response){

        //show the menu heading
        System.out.println("\n");
        System.out.println("Welcome to MyCar!");
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from main menu");
        System.out.println("------------------------------------------------------------------");

        //check if a notification is set.
        if(response.containsNotification()){
            //if so then output the notification
            System.out.println(response.getNotification()+"\n");
        }

        //show the menu options
        System.out.println("    1) Search by brand");
        System.out.println("    2) Browse by vehicle type");
        System.out.println("    3) Filter by number of passengers");
        System.out.println("    4) Exit");
        System.out.println("\n");

        //check if the response contains the error
        if(response.containsError()){
            //if so then output the error
            System.out.println(response.getError());
        }

        //finally then we return the new request via the getUserInput protected method.
        return this.getUserInput(new Request());
    }

}
