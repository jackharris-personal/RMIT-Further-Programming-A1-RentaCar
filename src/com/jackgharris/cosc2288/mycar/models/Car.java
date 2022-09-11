//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.models;

//**** PACKAGE IMPORTS ****\\
//This section shows us the list of Java Packages used in this package.
import com.jackgharris.cosc2288.mycar.core.Model;
import com.jackgharris.cosc2288.mycar.enums.Sort;
import java.util.ArrayList;
import java.util.HashMap;

public class Car extends Model {

    //**** PRIVATE VARS ****\\
    //our first and only private variable in this model child class
    //is the static Array of cars, this will be used by the facade calls
    //if this program was using a database this would not be required and
    //all requests would be sent from the facades for data to the Database
    //static object.
    private static Car[] cars;


    //**** CLASS CONSTRUCTOR ****\\
    //This method is our model class constructor it takes in a HashMap of data
    //that is sent to the parent using super to be bound to the protected
    //variable "this.data" that is then used by the accessor methods.
    //The provided HashMap is used as a key value pair lookup.
    public Car(HashMap<String, String> data) {
        //parse data to parent constructor via super
        super(data);
    }

    //-------------------------  MODEL DATA GETTERS -------------------------\\

    //**** GET ID METHOD ****\\
    //Returns the vehicle id to the caller as a string
    public String getID(){
        return this.data.get("Vehicle ID");
    }

    //**** GET BRAND METHOD ****\\
    //Returns the brand of the vehicle to the caller as a string
    public String getBrand(){
        return this.data.get("Brand");
    }

    //**** GET MODEL METHOD ****\\
    //Returns the vehicle model number to the caller as a string
    public String getModel(){
        return this.data.get("Model");
    }

    //**** GET TYPE METHOD ****\\
    //Returns the vehicle type number to the caller as a string
    public String getType(){
        return this.data.get("Type");
    }

    //**** GET COLOR METHOD ****\\
    //Returns the vehicle color number to the caller as a string
    public String getColor(){
        return this.data.get("Color");
    }

    //**** GET YEAR OF MANUFACTURE METHOD ****\\
    //Returns the vehicle Year of Manufacture number to the caller as an integer.
    public int getYearOfManufacture(){
        return Integer.parseInt(this.data.get("Year of Manufacture"));
    }

    //**** GET RENTAL PRICE PER DAY METHOD ****\\
    //Returns the rental price per day to the caller as an integer in AUD.
    public int getRentalPricePerDay(){
        return Integer.parseInt(this.data.get("Rental per day (AUD)"));
    }

    //**** GET SERVICE FEE METHOD ****\\
    //Returns the vehicle service fee to the caller as an integer.
    public int getServiceFee(){
        return Integer.parseInt(this.data.get("Service fee (AUD)"));
    }

    //**** GET DISCOUNT METHOD ****\\
    //Returns the vehicle discount percentage to the caller as an integer.
    public int getDiscount(){
        return Integer.parseInt(this.data.get("Discount"));
    }

    //**** GET SEAT COUNT METHOD ****\\
    //Returns the vehicle seat count to the caller as an integer.
    public int getSeatCount(){
        return Integer.parseInt(this.data.get("No. of seats"));
    }


    //-------------------------  CAR COLLECTION FACADES -------------------------\\
    //                             (STATIC GETTERS)

    //The following static methods are a facade to create and return a CarCollection class
    //any one of these methods can be called, and they will then themselves build a CarCollection
    //object before returning it to the user, this is important as it streamlines our object
    //calls as we only ever call "Car." instead of "CarCollection.", thus simplifying and
    //streamlining the code.

    //**** BIND MODEl DATA METHOD ****\\
    //The where() method accepts a key and value and will parse this request onto a new CarCollection
    //object that it will then return.
    public static CarCollection where(String key, String value){
        return new CarCollection(Car.cars).where(key,value);
    }

    //**** GET METHOD ****\\
    //This method returns a get call on the CarCollection object, this means it will return a standard
    //array of all the current car models loaded by the system.
    public static Car[] get(){
        return new CarCollection(Car.cars).get();
    }

    //**** ORDER BY METHOD ****\\
    //This method like the GET method simply returns a CarCollection object and calls the orderBy
    //method on it, this accepts a key and a order type (enum ASCENDING/DESCENDING)
    public static CarCollection orderBy(String key, Sort orderType){
        return new CarCollection(Car.cars).orderBy(key, orderType);
    }

    //**** GET UNIQUE VALUES WHERE KEY ****\\
    //This static facade returns the carCollection result of this method as an array of strings.
    public static String[] getUniqueValuesWhereKey(String key){return new CarCollection(Car.cars).getUniqueValuesWhereKey(key);}

    //-------------------------  STATIC METHODS -------------------------\\

    //**** BIND MODEl DATA METHOD ****\\
    //This static method is called once and is used to bind the model data loaded from the FileService into the program
    //this method accepts a ArrayList of HashMap's relating to each model, this data can come from a database, csv or
    //any source so long as it yields the expected data.
    public static void bindModelData(ArrayList<HashMap<String, String>> models){

        //set private static variable cars to the result of the buildCarModels call parsing it the models provided.
        Car.cars = Car.buildCarModels(models);
    }

    //**** BUILD CAR MODELS METHOD ****\\
    //This method takes in our Array list of hashmaps and builds an array of models based on it, the resulting array
    //of objects will be returned to the caller.
    private static Car[] buildCarModels(ArrayList<HashMap<String, String>> result) {

        //create our array of models and set the size to the size of the incoming ArrayList
        Car[] cars = new Car[result.size()];

        //create a counter variable 1
        int i = 0;
        //next create a for loop to loop over all the results, as we do this we create a new
        //Car object and set it to the value of "i" in the new cars array.
        for (HashMap<String, String> entry: result) {
            cars[i] = new Car(entry);
            i++;
        }

        //finally we return this array
        return cars;
    }

}
