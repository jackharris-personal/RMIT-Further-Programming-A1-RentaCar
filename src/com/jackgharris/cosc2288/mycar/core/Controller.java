//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.core;

//**** CLASS ****\\
public abstract class Controller {

    //**** PROTECTED VARIABLES ****\\

    //Application app variable, this is used to store and call back
    //to the main application instance.
    protected Application app;

    //View, this is the specific view object that the controller is referencing
    //this is initialized by the controller constructor.
    protected View view;

    //CurrentView this is the core subview that we are targeting, a view object
    //can have many view methods and this String matches the view method we are
    //going to target.
    protected String currentView;

    //**** CONSTRUCTOR ****\\
    //The constructor accepts the app instance and sets the class wide variable
    //to it. This is set via the super call in the children controllers.
    public Controller(Application app){
        this.app = app;
    }

    //**** ABSTRACT UPDATE VIEW ****\\
    //This abstract update view method is implemented by each child controller
    //and is called by the Application when an update view is required. It accepts
    //a response object.
    public abstract void updateView(Response response);

    //**** ABSTRACT HANDLE REQUEST ****\\
    //This method accepts a request that is returned by a view and then processes
    //the request input to determine what view must be called next and what data
    //EXAMPLE: determines what models will be loaded and sent to the view to be displayed.
    public abstract void handleRequest(Request request);
}
