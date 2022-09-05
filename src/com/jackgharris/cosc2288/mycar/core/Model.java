//**** PACKAGE****\\
//Declares the specific package this class belongs too
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//This section imports are required packages, in this case
//we are only importing the Java HashMap method.
import java.util.HashMap;

//**** ABSTRACT CLASS ****\\
//The model class is an abstract class this means that it is
//the parent class for a number of children, in this program
//the children are the "Car" and the "Reservation" models.
public abstract class Model {

    //**** PROTECTED VARS ****\\
    //Here we are our single protected variable called data, this
    //is settable and assemble by the children and is used to store
    //the model data for this object, a hash map has been chosen
    //here for its speed.
    protected HashMap<String, String> data;

    //**** CLASS CONSTRUCTOR ****\\
    //The class constructor takes in the HashMap of data and sets it to
    //the value of the protected data variable.
    public Model(HashMap<String, String> data){
        this.data = data;
    }

    //**** PUBLIC GET DATA VALUE METHOD ****\\
    //This method returns the data value for a specific key, this is used
    //by any Collection or controllers that want to retrieve data regardless
    //of casting the object or if the call is programmatically and thus a child
    //getter cannot be used. Example (Searching in the CarCollection)
    public String getDataValue(String key){
        return data.get(key);
    }

}
