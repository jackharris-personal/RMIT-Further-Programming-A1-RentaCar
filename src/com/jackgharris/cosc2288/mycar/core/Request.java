package com.jackgharris.cosc2288.mycar.core;

import java.util.HashMap;

public class Request {

    private String input;
    private String error;
    private final HashMap<String, Object> data;

    private boolean containsUserInput;



    public Request() {
        this.input = "";
        this.data = new HashMap<String, Object>();
        this.containsUserInput = false;
    }

    public void setUserInput(String input) {
        this.input = input;
        this.containsUserInput = true;
    }

    public void resetUserInput(){
        this.containsUserInput = false;
        this.input = "";
    }

    public String getUserInput() {
        return this.input;
    }

    public void add(String key, Object object){
        this.data.put(key, object);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return this.error;
    }

    public int getUserInputAsInteger(){
        return Integer.parseInt(this.input);
    }

    public boolean containsUserInput(){
        return this.containsUserInput;
    }

    public boolean isInteger() {

        boolean outcome = true;

        try {
            int test = Integer.parseInt(this.input);
        }catch (NumberFormatException e){
            outcome = false;
        }

        return outcome;
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }
}
