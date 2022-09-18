//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//Imported custom and java packages required by this class
import java.util.HashMap;

//**** START CLASS ****\\
public class Response {

    //**** PRIVATE VARIABLES ****\\
    //These variables are class wide and used to store information about
    //the response object.

    //This data hashmap is the core of the class and like our response the
    //functions and methods wrap around this class for data storage.
    private final HashMap<String, Object> data;

    //**** RESPONSE CONSTRUCTOR ****\\
    //This constructor accepts nothing and sets this.data to a new instance
    //of the java hashmap
    public Response(){
        this.data = new HashMap<>();
    }

    //**** CREATE RESPONSE CONSTRUCTOR WITH REQUEST ****\\
    //This constructor will accept a request as part of the constructor it will
    //then set the data hash map to be the value of the data hashmap inside the
    //request that was provided.
    public Response(Request request){
        this.data = request.getData();
    }

    //-------------------------  ERROR METHODS -------------------------\\
    //
    //These methods cover adding, getting and setting the error message that is
    //contained in this response.

    //**** SET ERROR METHOD ****\\
    //This method accepts a string conveying the error and will then set that
    //string to the value of error inside our hashmap.
    public void setError(String error){
        this.data.put("error", error);
    }

    //**** CONTAINS ERROR METHOD ****\\
    //This method will return true or false to the caller depending on if an error
    //has been set in this response.
    public boolean containsError(){
        return this.data.containsKey("error");
    }

    //**** GET ERROR METHOD ****\\
    //This method returns the error, it also appends colors styles to the error using
    //the static variables set in the 'Ascii.java' class file, here we append red,
    //then append back to reset the color.
    public String getError(){
        return Ascii.red+this.data.get("error")+Ascii.black;
    }

    //-------------------------  NOTIFICATION METHODS -------------------------\\
    //
    //These methods work similar to the error methods where they accept a notification
    //from the controller and will return that in green text with the use of a contains
    //notification boolean helper method.

    //**** SET NOTIFICATION METHOD ****\\
    //This method sets our notification in our core data hashmap to the notification string
    //that has been provided.
    public void setNotification(String notification){
        this.data.put("notification",notification);
    }

    //**** GET NOTIFICATION METHOD ****\\
    //This method returns the notification string that is stored in the hashmap and will
    //append on the green text at the start and the black reset text color at the end.
    public String getNotification(){
        return Ascii.green+this.data.get("notification")+Ascii.black;
    }

    //**** CONTAINS NOTIFICATION METHOD ***\\
    //This method returns boolean true or false depending on if the response contains a
    //notification, this will be used by the views to determine if they must show a notification.
    public boolean containsNotification(){
        return this.data.containsKey("notification");
    }

    //-------------------------  VIEW REDIRECT METHODS -------------------------\\
    //
    //These methods will be used to set,get and redirect the current view that is
    //being shown to the user.

    //**** SET VIEW REDIRECT METHOD ****\\
    //This method sets the view string that we will be redirecting to the key of
    //redirect.
    public void setViewRedirect(String redirect){
        this.data.put("redirect", redirect);
    }

    //**** GET VIEW REDIRECT METHOD ****\\
    //This method returns the string to the user that is stored in the redirect
    //hash map location for that key, this string must match a valid view in one
    //of our view files.
    public String getViewRedirect(){
        return (String) this.data.get("redirect");
    }

    //**** SHOULD REDIRECT METHOD ****\\
    //This method returns true or false depending on if a redirect is saved in the
    //hash map.
    public boolean shouldRedirect(){
        return this.data.containsKey("redirect");
    }

    //-------------------------  HASH MAP DATA METHOD -------------------------\\
    //
    //The following methods add, return and check the contents of the full data
    //hash map, this will be used in the create of a request from a response or
    //to override and add or remove key, value pairs.

    //**** ADD METHOD ****\\
    //This method accepts a key as a string and a java object and adds it to the
    //hash map.
    public void add(String key, Object object){
        this.data.put(key, object);
    }

    //**** GET METHOD ****\\
    //returns the object that matches the key provided, this will return null
    //in the event no key matches the key provided.
    public Object get(String key){
        return this.data.get(key);
    }

    //**** CONTAINS KEY METHOD ****\\
    //returns true or false for if a key exists inside the response.
    public boolean containsKey(String key){
        return this.data.containsKey(key);
    }

    //**** GET DATA METHOD ****\\
    //Returns the entire this.data hashmap array, used when building a request
    //from this response.
    public HashMap<String, Object> getData(){
        return this.data;
    }

}
