[Back to documentation home](https://github.com/s3893749/RMIT-Further-Programming-A1)

# Creating and managing views
This documentation will demonstrate how to create and use the view system that is implemented in this 
application. Views in a Model View Controller pattern show the user the interface and get the input
from the users.

##  How do views work?
In this project a view represents a method in a view class, this allows for multiple views to exist in the
same class file provided them relate to each other and reasonable and necessary to exist. As an example
in this project we have a 'SearchView' java class that contains four view methods all relating to searching
and displaying cars.

All of our use created views will extend the core view parent class with the parent class providing the 
user input functionality to each view method. The parent provides this to the child view by using the
protected function 'getUserInput()'
````java 
Request request = this.getUserInput(new Request())
```` 
This function will accept a request object that has been created, the method will then get the user input using the Java BufferedReader,
please it into the request before returning it back to the caller. In the event an error is detected then
the error message will be placed inside the request object is its error field. This error will then
be caught and handled by the main 'Application' object.

````java

protected Request getUserInput(Request request){

        try{
            request.setUserInput(this.br.readLine());
        } catch (IOException e) {
            request.setError(e.getMessage());
        }
        
        return request;
}

````

## Creating a child view
To get started creating a view we open the view package, then create a new class, this can be called any name but its advisable
to align the name of the view with the name of the controller that will be managing it. In this example view we can wee that this is called the 'BrandMenu'
and this is controlled by the 'SearchController'.

````java
    public Request brandMenu(Response response) {

        System.out.println("------------------------------------------------------------------");
        System.out.println("> Select from matching list | Filtering by Brand");
        System.out.println("------------------------------------------------------------------");

        int counter = 1;

        if (response.containsKey("carBrands")) {
            for (String brand : (String[]) response.get("carBrands")) {
                System.out.println("    "+counter+") "+brand);
                counter ++;
            }
        }

        System.out.println("    "+counter+") Go to main menu");
        System.out.println("\n");
        System.out.println("Please select the Brand number or enter the brand name as shown\nExample: 'Toyota' or '1'");
        System.out.println("\n");

        if(response.containsError()){
            System.out.println(response.getError());
        }

        return this.getUserInput(new Request());
    }
````

We can see that the method accepts a 'Response' object, this object will contain all the information, errors and
notifications that the controller has responded with. We can think of this response class in a similar capacity
to a HTTP response.

## Displaying errors

One of the core peaces of feedback the view responsible for is to display errors to the user, these can be set by a controller
and will tell the user when an error has occurred, these errors can be simple such as a invalid input or advanced and specific such as 
"Failed to load csv data". This response method provides the view with a single point of call to check and display errors.The user can program
a controller to provide as detailed and useful error messages as possible or as little and unhelpful as possible,
the limit is just set by the developer.

````java
if(response.containsError()){
    System.out.println(response.getError());
}
````
Example error message:
````
Invalid selection provided '5', please select a valid input from 1-4
````

## Displaying notifications 
The counter pop-up message to a error is a notification, these are shown in green and can be set by a controller
inside the response object. Notifications are a feedback item to indicate to the user that a event has happened
been successful.

````java
if(response.containsNotification()){
    System.out.println(response.getNotification());
}
````
Example notification message:
````
Pick-up date set for '16/09/2022'
````

## Getting response data
In addition to the error and notification data that is set in the response a controller can also be programmed
to insert and parse any data to the view itself, this can be detected via the "response.containsKey(key)" 
method call.
````java
if (response.containsKey("some-data")) {
    //Do something
}
````