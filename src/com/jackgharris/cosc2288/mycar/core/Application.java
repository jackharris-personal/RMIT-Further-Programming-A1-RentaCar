package com.jackgharris.cosc2288.mycar.core;

import com.jackgharris.cosc2288.mycar.controllers.MenuController;
import com.jackgharris.cosc2288.mycar.controllers.ReservationController;
import com.jackgharris.cosc2288.mycar.controllers.SearchController;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.services.FileService;

import java.util.HashMap;


public class Application {

    private final HashMap<String, Controller> controllers;
    private String activeController;

    public Application(){

        String directory = System.getProperty("user.dir");
        Car.bindModelData(FileService.loadCarModels(directory+"/fleet.csv"));

        this.controllers = new HashMap<>();

        this.controllers.put("Menu", new MenuController(this));
        this.controllers.put("Search", new SearchController(this));
        this.controllers.put("Reservation", new ReservationController(this));

        this.activeController = "Menu";

        this.updateView(new Response());
    }

    public void setActiveController(String controller){
        this.activeController = controller;
    }

    public void handleRequest(Request request){
        this.controllers.get(this.activeController).handleRequest(request);
    }

    public void updateView(Response response){
        this.controllers.get(this.activeController).updateView(response);
    }

    public void exit(){
        System.out.println("Thankyou for using MyCar Console");
        System.out.println("MyCar is now saving & closing");
        System.exit(0);
    }

}
