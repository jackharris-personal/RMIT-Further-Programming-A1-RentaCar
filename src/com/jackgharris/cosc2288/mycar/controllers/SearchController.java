package com.jackgharris.cosc2288.mycar.controllers;

import com.jackgharris.cosc2288.mycar.core.*;
import com.jackgharris.cosc2288.mycar.enums.Sort;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.models.CarCollection;
import com.jackgharris.cosc2288.mycar.views.SearchView;

import java.util.ArrayList;


public class SearchController extends Controller {

    public SearchController(Application app) {
        super(app);
        this.view = new SearchView();
        this.currentView = "brandMenu";
    }

    @Override
    public void handleRequest(Request request) {

        Response response = new Response();

        switch(this.currentView){

            case "brandMenu" -> response = this.brandMenu(request);
            case "typeMenu" -> response = this.typeMenu(request);
            case "passengers" -> response = this.passengerSelectionMenu(request);
            case "showCars" -> response = this.showCars(request);

        }

        this.app.updateView(response);
    }

    @Override
    public void updateView(Response response) {
        Request request;

        if(response.shouldRedirect()){
            this.currentView = response.getViewRedirect();
            this.app.handleRequest(response.cloneDataToRequest(new Request()));
        }

        switch (this.currentView) {

            case "brandMenu" -> request = ((SearchView) this.view).brandMenu(response);
            case "typeMenu" -> request = ((SearchView) this.view).typeMenu(response);
            case "showCars" -> request = ((SearchView) this.view).showCars(response);
            case "passengers" -> request = ((SearchView) this.view).Passengers(response);

            default -> {
                System.out.println(Ascii.red+"Error: invalid view called"+Ascii.black);
                request = new Request();
            }
        }

        this.app.handleRequest(request);

    }

    private Response brandMenu(Request request){
        Response response = new Response();

        response.add("carBrands", Car.getUniqueValuesWhereKey("Brand"));

        String selectedBrand = "";

        boolean searchOutcome = false;

        if(request.isInteger()){

            int options = Car.getUniqueValuesWhereKey("Brand").length + 2;

            if(request.getUserInputAsInteger() < options && request.getUserInputAsInteger() > 0){

                if(request.getUserInputAsInteger() == 6){
                    this.app.setActiveController("MenuController");
                }else{

                    selectedBrand = Car.getUniqueValuesWhereKey("Brand")[request.getUserInputAsInteger()-1];

                    searchOutcome = true;
                }

            }
        }else{

            for (String brand: Car.getUniqueValuesWhereKey("Brand")) {
                if(brand.matches(request.getUserInput())){
                    selectedBrand = brand;

                    searchOutcome = true;
                    break;
                }
            }
        }

        //if our search result fails we will add the error to response, but if the request does not contain
        //user input we will assume this request is from a redirect, and thus we will not show an error message.
        if(!searchOutcome && request.containsUserInput()){
            response.setError("Unknown brand '"+request.getUserInput()+"', please select a valid brand\nfrom the list above");
            return response;
        }

        if(searchOutcome){
            response.setViewRedirect("showCars");
            response.add("brand", selectedBrand);
            response.add("cars",Car.where("Brand",selectedBrand).get());
            response.add("options",Car.where("Brand",selectedBrand).get().length+1);
            response.add("heading", "Showing cars by '"+response.get("brand")+"'");
        }

        return response;
    }

    private Response typeMenu(Request request){
        Response response = new Response();

        String[] carTypes = Car.getUniqueValuesWhereKey("Type");
        response.add("carTypes", carTypes);
        int options = carTypes.length+1;

        if(request.containsUserInput()){

            if(!request.isInteger()) {
                response.setError("Invalid input provided '" + request.getUserInput() + "', please provide a valid number");
                return response;
            }

            if(request.getUserInputAsInteger() == options){
                this.app.setActiveController("MenuController");
            }

            if(request.getUserInputAsInteger() > 0 && request.getUserInputAsInteger() < options){

                String type = carTypes[request.getUserInputAsInteger()-1];

                Car[] cars =  Car.where("Type",type).get();

                response.setViewRedirect("showCars");
                response.add("heading", "Showing cars by '"+type+"'");
                response.add("cars",cars);
                response.add("options",cars.length+1);

            }else{
                System.out.println(request.getUserInputAsInteger());
                response.setError("Invalid car type selected, please select a valid number");
            }

        }

        return response;
    }

    public Response passengerSelectionMenu(Request request){
        Response response = new Response(request);

        if(request.containsUserInput()) {

            if (!request.isInteger()) {
                response.setError("Invalid input provided '" + request.getUserInput() + "', please provide a valid number");
                return response;
            }

            if(request.getUserInputAsInteger() < 1 || request.getUserInputAsInteger() > 8){
                response.setError("Please provide a valid seat number from 1-8");
                return response;
            }

            ArrayList<Car> selectedCars = new ArrayList<>();

            for (Car car: Car.get()) {
                if(car.getSeatCount() > request.getUserInputAsInteger()){
                    selectedCars.add(car);
                }
            }

            Car[] cars = new Car[selectedCars.size()];
            cars = new CarCollection(selectedCars.toArray(cars)).orderBy("No. of seats", Sort.ASCENDING).get();

            response.add("cars",cars);
            response.add("options",cars.length+1);
            response.add("heading","Showing cars with more than '"+request.getUserInputAsInteger()+"' seats");
            response.setViewRedirect("showCars");

        }


        return response;
    }

    private Response showCars(Request request){

        Response response = new Response(request);

        if(request.containsUserInput()){

            if(!request.isInteger()){
                response.setError("Please enter a integer only");
                return response;
            }

            if(request.getUserInputAsInteger() == (int)request.get("options")){
                this.app.setActiveController("MenuController");
                return response;
            }

            if(request.getUserInputAsInteger() > 0 && request.getUserInputAsInteger() < (int)request.get("options")){
                response.setNotification("Valid Car Selected! - Pending Booking Implementation", Ascii.green);
                //TODO ADD CAR BOOKING LINK
            }else{
                response.setError("invalid menu selection '"+request.getUserInputAsInteger()+"', please enter a valid selection\nFrom 1-"+request.get("options"));
            }

        }

        return response;
    }
}
