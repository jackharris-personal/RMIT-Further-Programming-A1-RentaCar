[Back to documentation home](https://github.com/s3893749/RMIT-Further-Programming-A1)

# Creating and using controllers
Controllers are a core part of the MVC design patten. They connect the views with the models and
provide logic to the program. This application uses a parent controller class with each controller extending
that parent. Each controller will then have at a minimum a 'updateView()' and 'handleRequest()'

##  Understanding Controllers
Controllers contain our logic for determining what actions should be taken after every request,
to understand how this works lets example our main menu controller.

When a controller is created it will pass an instance of our main application object, this object
must be parsed up to the parent via the super(app) call. The parent class will set this app instance
to a protected variable that we can then use later in the class. Next we will need to set the view 
object for this controller, in this case we can see that we are setting the view to a new instance
of our MenuView object.

Lastly we set the current subview, this is a string variable that must match a sub view inside the view
object. The currentView string must be set in the constructor as it will be called on default by the
main application and is only reset by a redirect request.

````java
    public MenuController(Application app) {
        super(app);
        this.view = new MenuView();
        this.currentView = "menu";
    }
````

##  Handle request method
The handle request method is the brains of our controller. This method will be called when the controller
is active and is where we place all of our application logic for that view. The best practice is to keep
this method as sort as possible. In the event that larger amounts of logic is required then you should
split that off into a private method that will perform the processing and simply call the method from
the handleRequest() method.

The handleRequest method will always be passed a request object that is created and returned from our view methods.
The method below is the logic for handling user input and selections for the main menu of the application. 
To streamline this code we are using a switch statement that is protected by a number of request gate methods.
These gate methods ("request.isInteger","request.containsUserInput") return boolean values and ensure that
any invalid input types are checked and blocked prior to the main switch statement.
````java
public void handleRequest(Request request) {

        //Declare our new response object that this request will return.
        Response response = new Response();


        //Firstly we check if the input is not an integer, in this menu only
        //integers are valid input, so we can error here if It's anything else.
        if(!request.isInteger() && request.containsUserInput()){
            response.setError("Invalid input provided '"+request.getUserInput()+"', please provide a valid number");

        //else then the input is an integer then we can proceed
        }else {

            //next we perform a second check to see if user input has been provided or
            //if this is a blank request, this is important as we don't want to process
            //this code for blank requests.
            if (request.containsUserInput()) {

                //This switch statement represents our menu with each case being an option
                switch (request.getUserInputAsInteger()) {

                    //case 1 is filter by brand, if selected we set the view redirect to the brandMenu view
                    //and then set the active controller to the search controller
                    case 1 -> {
                        response.setViewRedirect("brandMenu");
                        this.app.setActiveController("SearchController");
                    }

                    //case 2 is filter by car type, if selected we set the view redirect and then set the
                    //active controller to Search Controller
                    case 2 -> {
                        response.setViewRedirect("typeMenu");
                        this.app.setActiveController("SearchController");
                    }

                    //case 3 is filter by passengers, if selected we set the view redirect to passengers then
                    //set the active controller to your SearchController.
                    case 3 -> {
                        response.setViewRedirect("passengers");
                        this.app.setActiveController("SearchController");
                    }

                    //case 4 is our close application call, this calls our main application exit method.
                    case 4 -> this.app.exit();

                    //our default is the option when case 1-4 fail, in this case we want to provide the user with
                    //an error passage stating this was an invalid selection.
                    default -> response.setError("Invalid selection provided '" + request.getUserInputAsInteger() + "', please select a valid input from 1-4");
                }
            }
        }

````

##  Update view method
The update view method is called by the application once a response has been built from a controller.
This method will parse the response to the active view set via the "this.currentView" variable inside
the currently active controller.

As this view only has one method we can see that the simply check with an if statement is that view is
set and if sop load the view, else we throw an error to the console and return a new response! As the view 
redirects and default views are set by the developer this error should never be triggered, However it is
here as a worse case scenario check.

Lastly we can see that once we set our request to the result of that view we then call the "this.app.handleRequest()"
method providing it that new request object.

````java
public void updateView(Response response) {

        //firstly we declare our request local variable
        Request request;

        //Next we process our view, if the current view is the "menu" view then we set the request to the
        //menu view and pass it the response, else we state that an invalid view was called. As this controller
        //only has one view then we do not need a switch statement and this simply if you will suffice.
        if ("menu".equals(this.currentView)) {
            request = ((MenuView) this.view).menu(response);
        } else {
            System.out.println(Ascii.red+"Error: invalid view called"+Ascii.black);
            request = new Request();
        }

        //finally we tell the main application to the handle the request we just got from the view.
        this.app.handleRequest(request);
    }
````

##  Registering controllers in the application 
All our controllers are registered in a hash map located in the main application class, ensure that
all controllers are part of this hashmap. Once this is done we can set the current active controller to
the key that we have given to that controller, below we can see that this is the "MenuController".

````java
//instantiate our core controllers hashmap
this.controllers = new HashMap<>();

//add our controllers and pass them a reference to this application via 'this'
this.controllers.put("MenuController", new MenuController(this));
this.controllers.put("SearchController", new SearchController(this));
this.controllers.put("ReservationController", new ReservationController(this));

//set the default active controller
this.activeController = "MenuController";
````

##  Switching the active controllers
The active controller can then be switched from any of the other controllers using the public method
"this.app.setActiveController(String controller)". This method returns void and accepts a key as a string
this key must match the specific key of a controller in the hashmap. As there is no logic here to check for
valid key inputs its important that the developer does not open up this call to any user input and that
it is validated or locked beforehand.

In most cases you will want to set a view redirect as well as resetting the active controller.

Example:
````java
     case 1 -> {
          response.setViewRedirect("brandMenu");
          this.app.setActiveController("SearchController");
               }
````
