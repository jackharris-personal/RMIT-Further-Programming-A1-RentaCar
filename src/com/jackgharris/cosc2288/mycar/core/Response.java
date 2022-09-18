package com.jackgharris.cosc2288.mycar.core;

import java.util.HashMap;

public class Response {

    private String error;
    private String notification;
    private final HashMap<String, Object> data;

    private String redirect;

    public Response(){
        this.error = "";
        this.notification = "";
        this.data = new HashMap<String, Object>();
    }

    public Response(Request request){
        this.error = "";
        this.notification = "";
        this.data = request.getData();
    }

    public void add(String key, Object object){
        this.data.put(key, object);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    public boolean isset(String key){
        return this.data.containsKey(key);
    }

    public void setError(String error){
        this.error = error;
    }

    public void setNotification(String notification, String color){
        this.notification = color+notification+Ascii.black;
    }

    public String getNotification(){
        return this.notification+"\n";
    }

    public boolean containsNotification(){
        return !this.notification.equals("");
    }

    public boolean containsError(){
        return !this.error.equals("");
    }

    public String getError(){
        return Ascii.red+this.error+Ascii.black;
    }

    public void setViewRedirect(String redirect){
        this.redirect = redirect;
    }


    public String getViewRedirect(){
        return this.redirect;
    }


    public boolean shouldRedirect(){

        return this.redirect != null;
    }

    public Request cloneDataToRequest(Request request){
        this.data.forEach(request::add);

        return request;
    }

}
