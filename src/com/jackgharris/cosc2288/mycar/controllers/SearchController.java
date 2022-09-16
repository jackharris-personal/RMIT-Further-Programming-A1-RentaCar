//**** PACKAGE ****\\
//Sets the specific package this class is apart of
package com.jackgharris.cosc2288.mycar.controllers;

// **** PACKAGE IMPORTS ****\\
//Imports all the classes that this class file requires, all but array list are custom classes
import com.jackgharris.cosc2288.mycar.core.*;
import com.jackgharris.cosc2288.mycar.enums.Sort;
import com.jackgharris.cosc2288.mycar.models.Car;
import com.jackgharris.cosc2288.mycar.views.SearchView;

//**** SEARCH CONTROLLER CLASS ****\\
//Extends the core controller class and provides logic functionality to the view
public class SearchController extends Controller {

    //**** CONSTRUCTOR ****\\
    //This controller accepts an instance of the Application that is passes to its parent via super
    //it then sets the view to an instance of our search view and then sets a default current view.
    public SearchController(Application app) {
        super(app);
        this.view = new SearchView();
        this.currentView = "brandMenu";
    }

    //**** HANDLE REQUEST METHOD ****\\
    //This method is the logical request processing of the controller, it will
    //take in a request object and handle any input, validation or data loading
    //before calling the this.app.updateView(Response) method that will
    //return the response that has been built by the controller.
    @Override
    public void handleRequest(Request request) {

        //Create our response object
        Response response = new Response();

        //Depending on the state of the current view we call the helper methods to process logic, this controller
        //has its logic split into methods rather than in handle request like the MenuController due to the size
        //of the logic, the method would become too big and hard to read if the code was not segmented like this.
        //All the helper view methods return a response.
        switch(this.currentView){

            //Brand selection menu
            case "brandMenu" -> response = this.brandMenu(request);
            //Car Type selection menu
            case "typeMenu" -> response = this.typeMenu(request);
            //Passenger minimum selection menu
            case "passengers" -> response = this.passengerSelectionMenu(request);
            //Show Cars, this is called once a car type or filter of cars is selected
            //from one of the above methods and all 3 will redirect to it.
            case "showCars" -> response = this.showCars(request);

        }

        //finally once our processing is completed we update the view and pass it the response.
        this.app.updateView(response);
    }

    //**** UPDATE VIEW METHOD ****\\
    //This method accepts a response from the application and calls the main application
    //handle request method with the request provided back from the view that was last called.
    @Override
    public void updateView(Response response) {

        //Declare our new request object
        Request request;

        //Check if the response is calling for a redirect, if so set the current view to the redirect view
        //then recall the this.app.handleRequest method and ensure all the response data to cloned into a new
        //request.
        if(response.shouldRedirect()){
            this.currentView = response.getViewRedirect();
            this.app.handleRequest(response.cloneDataToRequest(new Request()));
        }

        //Else if we do not need to redirect we can load our view. All views return a request and accept a
        //response as an input.
        switch (this.currentView) {

            //Brand Selection Menu view
            case "brandMenu" -> request = ((SearchView) this.view).brandMenu(response);
            //Car Type Selection Menu View
            case "typeMenu" -> request = ((SearchView) this.view).typeMenu(response);
            //Get Passengers by Minimum Seats Menu View
            case "passengers" -> request = ((SearchView) this.view).Passengers(response);
            //Show Cars view that is called by the above three options to display a dynamic list of cars.
            case "showCars" -> request = ((SearchView) this.view).showCars(response);

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

    //-------------------------  LOGIC PROCESSING METHODS -------------------------\\
    //
    //These methods are called by our handle request method and contain the logic for
    //building a response.
    //

    //**** BRAND MENU METHOD ****\\
    //This method contains all the logic for the brand menu and will either forward the
    //user onto the show cars method based on a valid selected brand or hold them on
    //this menu until they make a valid selection. All Logic Methods accept a request and
    //return a response.
    private Response brandMenu(Request request){

        //Create our new response.
        Response response = new Response();

        //Add all our car brands to our response using the static helper method
        //Car.getUniqueValuesWhereKey
        response.add("carBrands", Car.getUniqueValuesWhereKey("Brand"));

        //Declare our selected brand to be an empty string.
        String selectedBrand = "";

        //Declare the outcome of the search to be null
        boolean searchOutcome = false;

        //When selecting a brand you can type the brand or enter the specific number
        //than shown in the view, first lets process the number selection this is one
        //via our request.isInteger boolean method to determine if the input was an integer
        //or a string.
        if(request.isInteger()){

            //Declare our options variable, this is the length of the car values +1 to include
            //the back to main menu option.
            int options = Car.getUniqueValuesWhereKey("Brand").length + 1;

            //Check if the user has pressed the back to main menu option, if so then we set
            //the active controller and return the response early to ensure no further processing
            //takes place.
            if(request.getUserInputAsInteger() == options){
                this.app.setActiveController("MenuController");
                return response;
            }

            //Next if check is the entered value was an integer that is more than 0 and less than our
            //final option 'back to main menu', if so we set the brand to that brands value remembering
            //that we added one so that is removed here, followed by setting the searchOutcome boolean
            //to true.
            if(request.getUserInputAsInteger() < options && request.getUserInputAsInteger() > 0){
                selectedBrand = Car.getUniqueValuesWhereKey("Brand")[request.getUserInputAsInteger()-1];
                searchOutcome = true;
            }


        //Else then we have entered a string not number, if so we attempt to load the value based on the input
        }else{

            //Check if the input is a valid brand using the Car.isValidBrand static model helper method, if false do
            //nothing, if true set the searchOutcome boolean to true and set the selected brand.
            if(Car.isValidBrand(request.getUserInput())){
                searchOutcome = true;
                selectedBrand = request.getUserInput();
            }
        }

        //if our search result fails we will add the error to response, but if the request does not contain
        //user input we will assume this request is from a redirect, and thus we will not show an error message.
        //Here we return early again to ensure no further processing occurs in the freak event that the search outcome
        //is true but the request does not contain user input.
        if(!searchOutcome && request.containsUserInput()){
            response.setError("Unknown brand '"+request.getUserInput()+"', please select a valid brand\nfrom the list above");
            return response;
        }

        //If the program has reached this step then we can say that all validation has passed and no early returns have occurred,
        //thus we perform one more validation to ensure our search outcome is true, and if so we package all the information into
        //the response for use by the view, you can see we also redirect the user to the "showCars" view now to display the list
        //of cars.
        if(searchOutcome){
            response.setViewRedirect("showCars");
            response.add("brand", selectedBrand);
            response.add("cars",Car.where("Brand",selectedBrand).get());
            response.add("options",Car.where("Brand",selectedBrand).get().length+1);
            response.add("heading", "Showing cars by '"+response.get("brand")+"'");
        }

        //Lastly return what ever response has made it to this stage.
        return response;
    }

    //**** CAR TYPE SELECTION METHOD ****\\
    //This method processes the logic for the car type selection view.
    private Response typeMenu(Request request){

        //Firstly create our new response to return.
        Response response = new Response();

        //Next we get a list of our car types using our static helper method Car.getUniqueValuesWhereKey method
        String[] carTypes = Car.getUniqueValuesWhereKey("Type");

        //Next we add that top the response
        response.add("carTypes", carTypes);

        //Next we create our list of valid options, remember that it's the size of the array plus "1" for the
        //return to main menu options.
        int options = carTypes.length+1;

        //We now check if this request contains user input, this is required as redirect requests will not contain
        //user input and thus should not be processed for logic.
        if(request.containsUserInput()){

            //Next we determine if the request is a string, if not then we set the response error and then return early.
            if(!request.isInteger()) {
                response.setError("Invalid input provided '" + request.getUserInput() + "', please provide a valid number");
                return response;
            }

            //Next we determine if the request is for the final option "return to main menu", if so then we set the active
            //controller and then return the response early to kill the method.
            if(request.getUserInputAsInteger() == options){
                this.app.setActiveController("MenuController");
                return response;
            }

            //Now if we have reached this stage we can process our user input, firstly we check to ensure the input is greater
            //than 0 and less than our final option, if so we proceed, else we throw an error.
            if(request.getUserInputAsInteger() > 0 && request.getUserInputAsInteger() < options){

                //Get our selected type for the list of car types
                String type = carTypes[request.getUserInputAsInteger()-1];

                //Get our list of cars where the Type matches this selected type
                Car[] cars =  Car.where("Type",type).get();

                //Redirect the user to showCars and add all our response variables
                response.setViewRedirect("showCars");
                response.add("heading", "Showing cars by '"+type+"'");
                response.add("cars",cars);
                response.add("options",cars.length+1);

            }else{
                //Else we have made an invalid selection and set the response error.
                response.setError("Invalid car type selected, please select a valid number");
            }

        }

        //Finally we return our response.
        return response;
    }

    //**** MINIMUM PASSENGER SELECTION METHOD ****\\
    //This method takes in an integer and then yields the user all the cars where the seats are
    //greater than the number provided, it also constricts this search from 1-8 seats max.
    public Response passengerSelectionMenu(Request request){

        //Create our new response and pass it the request, this clones our data across.
        Response response = new Response(request);

        //Firstly we check if the user has entered input, if so proceed else skip
        if(request.containsUserInput()) {

            //next check if this input is an integer, if not then we set the error and return the
            //response early to prevent any further processing.
            if (!request.isInteger()) {
                response.setError("Invalid input provided '" + request.getUserInput() + "', please provide a valid number");
                return response;
            }

            //Next we check to see that the input is not less than 1 or greater than 8, if so
            //then this is considered out of range, and we set the error to please provide a valid
            //seat count from between 1-8, this will then return early to prevent further processing.
            //
            //NOTE:
            //8 Was selected as the maximum as it is one of the most common seat limits for minivans
            //and personal vehicles.
            //
            if(request.getUserInputAsInteger() < 1 || request.getUserInputAsInteger() > 8){
                response.setError("Please provide a valid seat number from 1-8");
                return response;
            }


            //If we made it here then the input is valid, and we can retrieve our model data to be returned
            //to the view, we set our cars array that will be returned to our built query for car selection
            //in this case we first getWhereSeats are greater than our input, then we order the array by seats.
            Car[] cars = Car.getWhereSeats(request.getUserInputAsInteger(),Sort.GREATER_THAN).orderBy("No. of seats", Sort.ASCENDING).get();

            //Insert all the data into the response and set the redirect to "showCars"
            response.add("cars",cars);
            response.add("options",cars.length+1);
            response.add("heading","Showing cars with more than '"+request.getUserInputAsInteger()+"' seats");
            response.setViewRedirect("showCars");
        }

        //Finally we have finished this processing and can return the response.
        return response;
    }

    //**** SHOW CARS METHOD ****\\
    //This method is called as a redirect from the prior three searching methods and will display
    //the list of cars provided by those methods to the user, from here the user can use this logic
    //to select a car and start the booking process.
    private Response showCars(Request request){

        //Create our response and pass it the request to clone data across
        Response response = new Response(request);

        //Check if the request contains input, if not then do nothing
        if(request.containsUserInput()){

            //next check to ensure that the provided input is an integer only.
            //return the response and set an error if not to prevent any further
            //logic processing.
            if(!request.isInteger()){
                response.setError("Please enter a integer only");
                return response;
            }

            //Next we check to see if the user has selected to go back to the main menu
            //checking our options variable, if they have then we set the active controller
            //to the MenuController and then return the response to stop processing any more logic
            //in this method.
            if(request.getUserInputAsInteger() == (int)request.get("options")){
                this.app.setActiveController("MenuController");
                return response;
            }

            //Next we can check if the input is less than our options limit but greater than 0, if we set the selected car
            //and start the booking process
            if(request.getUserInputAsInteger() > 0 && request.getUserInputAsInteger() < (int)request.get("options")){

                Car car = ((Car[])request.get("cars"))[request.getUserInputAsInteger()-1];
                //Add all our response data including the view redirect.
                response.setViewRedirect("selectDates");
                response.add("heading","Pickup & Drop off dates for "+car.getBrand()+" "+car.getModel()+" ("+car.getID()+")");
                response.add("type","pick-up");
                response.add("car",car);

                //set the active controller
                this.app.setActiveController("ReservationController");

            }else{
                //Else we set a response for an invalid selection.
                response.setError("invalid menu selection '"+request.getUserInputAsInteger()+"', please enter a valid selection\nFrom 1-"+request.get("options"));
            }

        }

        //Finally our processing is done, and we return the response.
        return response;
    }
}
