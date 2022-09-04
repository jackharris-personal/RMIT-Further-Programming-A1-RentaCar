package com.jackgharris.rent_a_car.core;

import java.util.HashMap;

public abstract class Model {

    protected HashMap<String, String> data;

    public Model(HashMap<String, String> data){
        this.data = data;
    }

    public String getDataValue(String key){
        return data.get(key);
    }

}
