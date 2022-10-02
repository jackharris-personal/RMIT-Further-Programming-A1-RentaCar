//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//List of all packages imported into this class.
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.regex.Pattern;

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

    //**** CONSTRUCTOR WITH RESPONSE****\\
    //This constructor accepts a response as a value and then copies all the data from the response
    //to the data in this request.
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
    //was a valid date or not, credit to Sweeper for providing his article on working
    //out how to test for a valid date input.
    //
    //-------------------------  Code Bibliography Reference -------------------------
    //
    //format, i., & Sattar, S. (2022). issue in the dd-MM-yyyy format. Stack Overflow.
    //Retrieved 2 October 2022, from https://stackoverflow.com/questions/44868266/issue-in-the-dd-mm-yyyy-format.
    //
    //--------------------------------------------------------------------------------

    public boolean isDate(){

        //declare our outcome / valid date variable
        boolean validDate = true;

        //START REFERENCED CODE BLOCK

        Pattern p =  Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
        if (!p.matcher(this.getUserInput()).matches()) {
            validDate = false;
        }

        //END REFERENCED CODE BLOCK

        //return the outcome.
        return validDate;
    }

    //**** DATE IS UPCOMING ONLY METHOD ****\\
    //This helper method will check to ensure the date is upcoming and not in the past
    public boolean dateIsUpcomingOnly(){

        //return false if the input is not a date
        if(!this.isDate()){
            return false;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        //try to parse the pickup and drop off date
        LocalDate date = LocalDate.parse(this.getUserInput(),formatter);
        LocalDate today = LocalDate.now();

        return today.isBefore(date);
    }

    //**** IS EMAIL METHOD ****\\
    //This method will return true or false depending on if the input provided by the user
    //matches that regex pattern required by a valid email. Credit to Baeldurg.com for the
    //regex pattern.
    //
    //-------------------------  Code Bibliography Reference -------------------------
    //
    //Email Validation in Java. baeldung. (2022). Retrieved 19 September 2022,
    // from https://www.baeldung.com/java-email-validation-regex.
    //
    //--------------------------------------------------------------------------------

    public boolean isEmail(){

        //START REFERENCED CODE BLOCK
        return this.getUserInput().matches("^(.+)@(\\S+)$");
        //END REFERENCED CODE BLOCK
    }

    //**** IS STRING METHOD ****\\
    //This method will return true or false depending on if the input is a string only, this
    //means that it cannot be only an integer or a date and must be a greater length than 2.
    public boolean isString(){
        return !this.isEmail() & !this.isDate() & !this.isInteger();
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
