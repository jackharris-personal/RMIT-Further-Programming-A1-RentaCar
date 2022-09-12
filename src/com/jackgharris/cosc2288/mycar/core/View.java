//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** PACKAGE IMPORTS ****\\
//Class imports for classes used by this class
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//**** CLASS ****\\
public class View {

    //**** PRIVATE VARIABLES ****\\

    //Buffered Ready br is a final private variable that is used
    //to take input from the user in the view.
    private final BufferedReader br;

    //**** CONSTRUCTOR ****\\
    //This is the super constructor for the view parent and applies to all
    //children of this view. Here we can see it initializes it to a new instance
    //of the buffered reader using the InputSteamReader and system in.
    public View(){
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }


    //**** PROTECTED GET USER INPUT ****\\
    //This method gets the user input and takes in the request from the view,
    //it then sets the input from the user to the request user input before
    //returning it.
    protected Request getUserInput(Request request){

        try{
            request.setUserInput(this.br.readLine());
        } catch (IOException e) {

            //In the event an error is caught then it will set the error to
            //the request error message.
            request.setError(e.getMessage());
        }

        //return the request object
        return request;
    }

}
