package com.jackgharris.cosc2288.mycar.core;

import java.util.HashMap;

public class Response {

    private final HashMap<String, Object> data;

    public Response(){
        this.data = new HashMap<>();
    }

    public Response(Request request){
        this.data = request.getData();
    }

    public void add(String key, Object object){
        this.data.put(key, object);
    }

    public Object get(String key){
        return this.data.get(key);
    }

    public boolean containsKey(String key){
        return this.data.containsKey(key);
    }

    public void setError(String error){
        this.data.put("error", error);
    }

    public boolean containsError(){
        return this.data.containsKey("error");
    }

    public String getError(){
        return Ascii.red+this.data.get("error")+Ascii.black;
    }

    public void setNotification(String notification){
        this.data.put("notification",notification);
    }

    public String getNotification(){
        return Ascii.green+this.data.get("notification")+Ascii.black;
    }

    public boolean containsNotification(){
        return this.data.containsKey("notification");
    }



    public void setViewRedirect(String redirect){
        this.data.put("redirect", redirect);
    }


    public String getViewRedirect(){
        return (String) this.data.get("redirect");
    }


    public boolean shouldRedirect(){
        return this.data.containsKey("redirect");
    }

    public HashMap<String, Object> getData(){
        return this.data;
    }

}
