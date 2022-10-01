[Back to documentation home](https://github.com/s3893749/RMIT-Further-Programming-A1)

# Response Object
The response object is the primary object that is used to communicate between the controller and the view, it is built by a controller and 
contains all the data and information that a view may need to display, this can include, errors, notifications, redirects
or any amount of object data such as arrays or integers.

---

## Creating a response object
Similar to a request we can create a response two ways, firstly we can create a blank response that contains
no data by default! or we can create a response from a request. Creating a response from a request will clone
all the data that is stored into the request class into the response.

Creating a blank response:
````java
Response response = new Response();
````
Creating a response from a request:
````java
Response response = new Response(request);
````

---

## Error methods
Our response class contains three error methods that the view can interactive with, setError, getError and containsError.
These methods provide functionality to provide errors to the view in a manor that it can detect and effectively show.

### Set error method
This method will primarily be used by the controller and will allow a controller to catch and pass an error
to a view, this error can then be detected and displayed. The below example shows a error being set based
on the user input type.

````java
//Firstly we check if the input is not an integer, in this menu only
//integers are valid input, so we can error here if It's anything else.
if(!request.isInteger() && request.containsUserInput()){
    response.setError("Invalid input provided '"+request.getUserInput()+"', please provide a valid number");
}
````

### Getting the error
This method will return the error to the caller as a string and well as appends the ascii red color to
the string.

Method:
````java
    public String getError(){
        return Ascii.red+this.data.get("error")+Ascii.black;
    }
````
In use example:
````java
//check if the response contains the error
if(response.containsError()){
    //if so then output the error
    System.out.println(response.getError());
}
````

### Contains error method
This boolean helper method will return true or false depending on if the error is set in the response.

Example:
````java
//check if the response contains the error
if(response.containsError()){
    //Error is set, do something
}
````

---

## Notification methods

The notification methods can be used to set and display notifications in green to the users view, the naming 
of these methods mirrors exactly the naming of the above error method stack.

### Setting the notification
To set the notification you can simply provide a string as a parameter to the "response.setNotification(string)"
method as shown in the example below.

Example:
````java
//set a notification that the given name is set
response.setNotification("Given name set to '" + request.getUserInput() + "'");
````

### Getting the notification
To get a notification you can simply call response.getNotification(), all notifications will be returned
with the green ascii text appended to them.

Method:
````java
//check if a notification is set.
if(response.containsNotification()){
    //if so then output the notification
    System.out.println(response.getNotification()+"\n");
}
````
In use example:
````java
//check if a notification is set, if so then show it
if(response.containsNotification()){
    System.out.println(response.getNotification());
}
````

### Contains notification method
This method will act as a gate / detector method and will return true or false depending on if a notification
has been set. This can be used to detect and show notifications in a view.

Example:
````java
//check if a notification is set, if so then show it
if(response.containsNotification()){
    //Do something as the notification is set!
}
````

---

## Redirect methods
The redirect methods can be used to redirect what active sub view is set on the current active controller, these must
be used in conjunction with the "this.setActiveController(key)" method to set the controller, then the
redirect will update the current active subview.

Similar to the request and response methods these have "set", "get" and "shouldRedirect" that can be used
to set the redirect, get the redirect location and detect if the system should redirect the user.

### Setting the redirect key
To set the redirect key, on the response object call "response.setRedirect(string)" and ensure that you
provide it a valid redirect subview for the active controller. The example below shows the user input
being detected and the redirect being set, followed by the controller being updated.

Example:
````java
//if the input is 1 then set the redirect to the menu and set the active controller to the menu controller
if(request.getUserInputAsInteger() == 1){
    response.setViewRedirect("menu");
    this.app.setActiveController("MenuController");
}
````

### Getting the redirect key & contains redirect method
This method can be used to get a redirect key and detect if a redirect is set, however it is detected and used by our core application
class and no developer created controllers should directly interact with it. Example below is provided
from the core "this.app.updateView(response)" method contains in our main Application object.

Example:
````java
if(response.shouldRedirect()){
    this.controllers.get(this.activeController).setCurrentView(response.getViewRedirect());
    this.handleRequest(new Request(response));
}
````

---

## Adding generic data to the response object
As the response objects are the primary way of interfacing with the view objects we need to be able to store any number
of different java objects in them, as a result the response contains a hashmap of key value pairs that views
can access and controllers can set. The examples below show a string, car object and an integer being passed to a view
via the data methods.

Controller Example Code:
````java
Response response = new Response();

response.add("my number", 1);
response.add("uni name", "RMIT");
response.add("yaris", Car.where("Vehicle ID","C001").getFirst());

return response;
````

View Example Code:
````java

//check if the response variable isset 
if(response.contains("my number")){
        //if set output the integer primative number
        System.out.println(response.get("my number"))
}

//check if the response variable isset
if(response.contains("uni name")){
        //if set output the string
        System.out.println(response.get("uni name"))
}

//check if the response variable isset
if(response.contains("yaris")){
        //if set output the car model
        System.out.println((Car)response.get("yaris").getModel());
}
````