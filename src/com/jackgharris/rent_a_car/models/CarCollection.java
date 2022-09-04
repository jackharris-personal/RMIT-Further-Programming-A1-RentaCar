//**** PACKAGE IMPORTS ****\\
//This is the Java package that the class is associated with.
package com.jackgharris.rent_a_car.models;

//**** PACKAGE IMPORTS ****\\
//This section shows us the list of Java Packages used in this package.
import java.util.ArrayList;

public class CarCollection {

    //**** CLASS CONSTRUCTOR ****\\
    //Our only private variables is the cars array of models, this is provided
    //as part of the class constructor and is refined during our chainable query's.
    private Car[] cars;

    //**** CLASS CONSTRUCTOR ****\\
    //This constructor accepts a starting array of cars, in this example we load them
    //from a .csv file however this could be provided from a Database too.
    public CarCollection(Car[] cars){
        this.cars = cars;
    }

    //**** SELECT WHERE METHOD ****\\
    //This method allows the user to select all the models where a key match's a value
    //for example Car.where("Make","Ford").get() returns an array of models where the
    //make is Ford, this method can be chained together to build refined searches.
    //
    //Example:
    //Car ford = Car.where("Brand","Ford").where("Model","Puma").getFirst();
    //
    public CarCollection where(String key, String value){

        //Firstly we create a new arraylist for our matched cars that match the query.
        ArrayList<Car> matchedCars = new ArrayList<>();

        //we then iterate over the cars to check if their data value for that key matches the
        //data value we are looking for, if so then add that to the matched cars array.
        for(Car car : this.cars){
            if(car.getDataValue(key).equals(value)){
                matchedCars.add(car);
            }
        }

        //Now our search is complete we build our results array, this is a standard array
        //to make iterations in the controllers and views simpler. We initialize it to a new
        //array using the length of the array list, then insert all our data into it.
        Car[] results = new Car[matchedCars.toArray().length];
        int i = 0;
        while(i < matchedCars.toArray().length){
            results[i] = (Car) matchedCars.toArray()[i];
            i++;
        }


        //Finally we set cars array to the class wide array for further searching
        this.cars = results;

        //we then return this object to be chained further
        return this;
    }

    //**** ORDER BY METHOD ****\\
    //This method will order the results by a specific car data's value
    //TODO implement this later
    public CarCollection orderBy(String key){
        return this;
    }

    //**** GET ALL METHOD ****\\
    //This method returns the full array of cars for this CarCollection object, this can
    //be unrefined or a chained behind many where statements.
    public Car[] getAll(){
        return this.cars;
    }

    //**** GET FIRST METHOD ****\\
    //This method returns the first car value of an array, this is used when searching the cars array,
    //this method will return the first single result as a car object.
    public Car getFirst(){
        return this.cars[0];
    }


}


