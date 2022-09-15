package com.jackgharris.cosc2288.mycar.views;

import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;

public class MenuView extends View {

    public Request menu(Response response){

        System.out.println("\n");
        System.out.println("Welcome to MyCar!");
        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from main menu");
        System.out.println("------------------------------------------------------------------");
        System.out.println("    1) Search by brand");
        System.out.println("    2) Browse by vehicle type");
        System.out.println("    3) Filter by number of passengers");
        System.out.println("    4) Exit");
        System.out.println("\n");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(new Request());
    }

}
