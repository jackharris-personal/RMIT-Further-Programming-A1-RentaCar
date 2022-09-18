package com.jackgharris.cosc2288.mycar.core;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Request {

    private final HashMap<String, Object> data;

    public Request() {
        this.data = new HashMap<>();
    }

    public void setUserInput(String input) {
        this.data.put("input", input);
    }

    public void resetUserInput(){
        this.data.remove("input");
    }

    public String getUserInput() {
        return (String) this.data.get("input");
    }

    public void add(String key, Object object){
        this.data.put(key, object);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    public boolean containsData(String key){
        return this.data.containsKey(key);
    }

    public void setError(String error) {
        this.data.put("error",error);
    }

    public String getError() {
        return (String)this.data.get("error");
    }

    public int getUserInputAsInteger(){
        return Integer.parseInt((String) this.data.get("input"));
    }

    public boolean containsUserInput(){
        return this.data.containsKey("input");
    }

    public boolean isInteger() {

        boolean outcome = true;

        try {
            int test = Integer.parseInt((String) this.data.get("input"));
        }catch (NumberFormatException e){
            outcome = false;
        }

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
        boolean validDate = true;

        //START REFERENCED CODE BLOCK
        DateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        simpleDateFormat.setLenient(false);

        try{
            simpleDateFormat.parse(this.getUserInput());
        } catch (ParseException e) {
            validDate = false;
        }

        //END REFERENCED CODE BLOCK

        return validDate;
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }
}
