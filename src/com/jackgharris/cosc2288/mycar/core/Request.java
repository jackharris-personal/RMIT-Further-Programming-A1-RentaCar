//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//List of all packages imported into this class.
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

//**** START CLASS FILE ****\\
public class Request {

    //**** PRIVATE VARIABLES ****\\
    //Private class wide variables that will be used by methods in the class
    //to store and access data.

    //core data hashmap, this hashmap accepts a string as a key followed by any java
    //object as its data this includes custom classes as well as booleans, strings, ints ect.
    //All getters and setters map specific to this single hashmap. In that respect we can
    //consider this request class a wrapper for the hashmap.
    private final HashMap<String, Object> data;

    //**** CONSTRUCTOR ****\\
    //Request constructor, this accepts nothing and initializes our core data hashmap.
    public Request() {
        //As the key and data generics were declared above we do not need to redeclare them here.
        this.data = new HashMap<>();
    }

    public Request(Response response) {
        //As the key and data generics were declared above we do not need to redeclare them here.
        this.data = response.getData();
        //Remove the redirect key if it is present
        this.data.remove("redirect");
        //Remove the user input key if present
        this.data.remove("input");
    }

    //-------------------------  USER INPUT METHODS -------------------------\\
    //
    //All the following methods contain functions used to get, check and reset
    //the user input that has been added from the view by the user.

    //**** SET USER INPUT METHOD ****\\
    //This method accepts our string from the view input and sets it to the input
    //in our core data hashmap, it will return void.
    public void setUserInput(String input) {
        this.data.put("input", input);
    }

    //**** GET USER INPUT METHOD ****\\
    //This method returns the user input as a string from our hashmap
    public String getUserInput() {
        return (String) this.data.get("input");
    }

    //**** RESET USER INPUT METHOD ****\\
    //This clears the input placed into a request by the user and returns its
    //value to null.
    public void resetUserInput(){
        this.data.remove("input");
    }

    //**** GET USER INPUT AS INTEGER ****\\
    //Returns the user input as a java integer.
    public int getUserInputAsInteger(){
        return Integer.parseInt((String) this.data.get("input"));
    }

    //**** CONTAINS USER INPUT ****\\
    //Returns true or false depending on if the hashmap contains
    //any user input or not.
    public boolean containsUserInput(){
        return this.data.containsKey("input");
    }

    //**** IS INTEGER ****\\
    //Returns true or false depending on if the user input is a valid
    //integer or not.
    public boolean isInteger() {

        //Declare our outcome variable
        boolean outcome = true;

        try {
            //attempt to set the value of the parsed integer to a temp variable.
            int test = Integer.parseInt((String) this.data.get("input"));
        }catch (NumberFormatException e){

            //if we catch an error set the outcome to false.
            outcome = false;
        }

        //return the outcome of the test.
        return outcome;
    }

    //**** IS DATE METHOD ****\\
    //This method will return true or false to let the caller know if the user input
    //was a valid date or not, credit to C Parakash for providing his article on working
    //out how to test for a valid date input.
    //
    //-------------------------  Code Bibliography Reference -------------------------
    //
    // Prakash, C. (2021). Check If a String Is a Valid Date in Java. baeldung.com.
    // Retrieved 16 September 2022, from https://www.baeldung.com/java-string-valid-date.
    //
    //--------------------------------------------------------------------------------

    public boolean isDate(){

        //declare our outcome / valid date variable
        boolean validDate = true;

        //START REFERENCED CODE BLOCK
        //create our new date format and passé it the date formatting we want as a string.
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        //set the setLenient to false.
        simpleDateFormat.setLenient(false);

        try{
            //try to parse the date provided by the user into this format.
            simpleDateFormat.parse(this.getUserInput());
        } catch (ParseException e) {

            //if we catch an exception then set our outcome to false
            validDate = false;
        }

        //END REFERENCED CODE BLOCK

        //return the outcome.
        return validDate;
    }

    //-------------------------  REQUEST ERROR METHODS -------------------------\\
    //
    //These two methods are only used by the view to set a user input error if the
    //attempt to get the users input from the stream fails.

    //**** SET ERROR ****\\
    //This method accepts a string and sets the error in our hash map to the value
    //of the error provided.
    public void setError(String error) {
        this.data.put("error",error);
    }

    //**** GET ERROR ****\\
    //Returns the value of this error from the hashmap as a string, will return null
    //if no error is set.
    public String getError() {
        return (String)this.data.get("error");
    }

    //-------------------------  EXTRA DATA METHODS -------------------------\\
    //
    //These methods are used to add and return extra data to the request object,
    //these can be used to parse extra variables and information between the controller
    //and the view.

    //**** ADD METHOD ****\\
    //This method allows you to add a java object to the data hashmap, it accepts
    //a key as a string followed by any object.
    public void add(String key, Object object){
        this.data.put(key, object);
    }

    //**** GET METHOD ****\\
    //This method returns the object requested by the key provided by the caller
    //when an invalid key is provided it will simply return null.
    public Object get(String key){
        return this.data.get(key);
    }

    //**** CONTAINS DATA METHOD ****\\
    //Returns true or false if the provided key matches any data stored in the data
    //hashmap.
    public boolean containsData(String key){
        return this.data.containsKey(key);
    }

    //**** GET DATA METHOD ****\\
    //Returns the entire hashmap to the caller, this method is used when cloning data
    //between the view and the controller.
    public HashMap<String, Object> getData(){
        return this.data;
    }
}
