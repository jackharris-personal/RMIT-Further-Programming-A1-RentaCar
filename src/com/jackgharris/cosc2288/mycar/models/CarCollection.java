//**** PACKAGE****\\
package com.jackgharris.cosc2288.mycar.models;

//**** PACKAGE IMPORTS ****\\
//This section shows us the list of Java Packages used in this package.
import com.jackgharris.cosc2288.mycar.enums.Sort;
import java.util.ArrayList;

public class CarCollection {

    //**** PRIVATE VARS ****\\
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
    //This method will order the results by a specific car data's value, the method for performing
    //this was created by ritikasharma23 in a website post on GeeksForGeeks, all credit to him for
    //creating this sorting method, referenced code indicated by START and END block.
    //this method has been heavily edited by me to fit the required use case of this project.
    //
    //-------------------------  Code Bibliography Reference -------------------------
    //
    //Java Program to Sort Names in an Alphabetical Order - GeeksforGeeks. ritikasharma23.
    //Retrieved 4 September 2022, from https://www.geeksforgeeks.org/java-program-to-sort-names-in-an-alphabetical-order/.
    //
    //--------------------------------------------------------------------------------
    public CarCollection orderBy(String key, Sort orderType){

        //START - Referenced Code Block

        //create our temp car variable, this is used to store the object we are moving
        Car temp;

        //next we create two for loops to check the current car and the one ahead, our second loop j is equal
        //to the value of i+1
        for (int i = 0; i < this.cars.length; i++) {
            for (int j = i + 1; j < this.cars.length; j++) {

                //next we perform a switch statement on the sort type to see if we are
                //sorting this by accenting or descending value.
                switch (orderType){
                    case ASCENDING:

                        //next we compare the current car to the next car and if it's less than zero then we move
                        // its position on the list
                        if (this.cars[i].getDataValue(key).compareTo(this.cars[j].getDataValue(key)) > 0) {
                            //set the current temp value to this car, next set the new car value and then
                            //place the temp car back into the array
                            temp = this.cars[i];
                            this.cars[i] = this.cars[j];
                            this.cars[j] = temp;
                        }
                        break;

                    //Sort by descending option
                    case DESCENDING:
                        //next we compare the current car to the next car and if it's less than zero then we move
                        // its position on the list
                        if (this.cars[i].getDataValue(key).compareTo(this.cars[j].getDataValue(key)) < 0) {
                            //set the current temp value to this car, next set the new car value and then
                            //place the temp car back into the array
                            temp = this.cars[i];
                            this.cars[i] = this.cars[j];
                            this.cars[j] = temp;
                        }
                        break;
                }

            }
        }
        //END - Referenced Code Block

        //finally we return this CarCollection object back to the caller.
        return this;
    }

    //**** GET ALL METHOD ****\\
    //This method returns the full array of cars for this CarCollection object, this can
    //be unrefined or a chained behind many where statements.
    public Car[] get(){
        return this.cars;
    }

    //**** GET FIRST METHOD ****\\
    //This method returns the first car value of an array, this is used when searching the cars array,
    //this method will return the first single result as a car object.
    public Car getFirst(){
        return this.cars[0];
    }


    //**** GET UNIQUE VALUES WHERE KEY ****\\
    //This method accepts a key and returns all the unique values were the key matches the one provided.
    //These returned values will always be String and can be later converted into other types by the caller
    //Example: Integer.parseint()

    public String[] getUniqueValuesWhereKey(String key){

        //create our array list of new unique values
        ArrayList<String> values = new ArrayList<>();

        //for each car inside our collection of cars we perform this check
        for (Car car : this.cars) {

            //if the value is currently not inside the list of values then we add it
            if(!values.contains(car.getDataValue(key))){
                //else we skip it
                values.add(car.getDataValue(key));
            }
        }

        //return our list of values as a fixed size array of strings.
        return values.toArray(String[]::new);
    }


}


