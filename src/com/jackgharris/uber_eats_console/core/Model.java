package com.jackgharris.uber_eats_console.core;

import java.util.HashMap;

public abstract class Model {

    protected HashMap<String, String> data;

    public Model(HashMap<String, String> data){
        this.data = data;
    }

}
