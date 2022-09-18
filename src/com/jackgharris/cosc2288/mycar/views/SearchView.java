package com.jackgharris.cosc2288.mycar.views;

import com.jackgharris.cosc2288.mycar.core.Request;
import com.jackgharris.cosc2288.mycar.core.Response;
import com.jackgharris.cosc2288.mycar.core.View;
import com.jackgharris.cosc2288.mycar.models.Car;

public class SearchView extends View {

    public Request brandMenu(Response response) {

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | Filtering by Brand");
        System.out.println("------------------------------------------------------------------");

        int counter = 1;

        if (response.containsKey("carBrands")) {
            for (String brand : (String[]) response.get("carBrands")) {
                System.out.println("    "+counter+") "+brand);
                counter ++;
            }
        }

        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");
        System.out.println("Please select the Brand number or enter the brand name as shown\nExample: 'Toyota' or '1'");
        System.out.println("\n");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(new Request());
    }

    public Request typeMenu(Response response){

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | Showing Vehicle Types");
        System.out.println("------------------------------------------------------------------");

        int counter = 1;

        if (response.containsKey("carTypes")) {
            for (String type : (String[])response.get("carTypes")) {
                System.out.println("    "+counter+") "+type);
                counter ++;
            }
        }

        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");

        if(response.containsError()){
            System.out.println(response.getError());
        }


        return this.getUserInput(new Request());
    }

    public Request showCars(Response response){
        Request request = new Request(response);

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | "+response.get("heading"));
        System.out.println("------------------------------------------------------------------");

        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }
        int counter = 1;

        if (response.containsKey("cars")) {
            for (Car car : (Car[])response.get("cars")) {
                System.out.println("    "+counter+") "+car.getID()+" "+car.getBrand()+" "+car.getModel()+" with "+car.getSeatCount()+" seats");
                counter ++;
            }
        }

        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");

        if(response.containsError()){
            System.out.println(response.getError());
        }


        return this.getUserInput(request);
    }

    public Request Passengers(Response response){

        Request request = new Request();

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Search for vehicle by seats");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Please enter the minimum number of seats required (1-8 limit)");

        if(response.containsNotification()){
            System.out.println(response.getNotification());
        }

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(request);
    }
}
