[Back to documentation home](https://github.com/s3893749/RMIT-Further-Programming-A1)

# Request object
The request object is returned from a view and contains the user input as well as data that has been
captured from the last request. Furthermore, it provides the key gate methods that can be used to lock and 
validate what the specific user input is that is contained inside the request.

---

## Creating a request object
There are two main ways we can create a new request, the first is by creating a standard blank request
object via the "new Request()" call. The second is to call "new Request()" and parse it the response object
in this case all the data contained in the response will be cloned to the request object excluding the user
input and any redirect's.

Creating a blank request:
````java
Request request = new Request();
````
Creating a request from a response:
````java
Request request = new Request(response);
````

---

## Getting and managing user inputs

### Setting the user input
To add the user input to the request, you simply call "this.getUserInput(Request)" in the view method and
pass it this request as a parameter. This can be bundled into the single return line.

````java
return this.getUserInput(request);
````
### Getting the user input error
In the event that the user input is not able to be gotten then the "this.getUserInput()" method will set
the error variable in the request to the user input error. This can be accessed and checked via the 
"request.getError()" method.

````java
request.getError();
````

### Getting the user input as an integer
This method will return the user input as an integer to the calling class, when using this method ensure
you protected it behind the gate method "request.isInteger" to validate the input is a integer first.
````java
request.getUserInputAsInteger();
````

### Resetting the ser input
You may wish to reset the user input if you clone the request and response data into the new objects, to do this call this
"request.resetUserInput()" method to null it out.
````java
request.resetUserInput();
````
---

## Request 'gate' methods
Gate methods in the request serve as the primary way to ensure that user input provided to the system meets
specific criteria! the gate methods will validate the input against these criteria and return true or false
depending on if the current user input matches that criteria.

### isInteger method
This gate method will ensure that the input provided to the system is a valid integer input and will return
true or false depending on that. It can be used to validate the input or determine  what logic should be run.

````java
if(request.isInteger()){
    //its a integer!
        }else{
    //its a string!
}
````

### isDate method
This method will return true or false depending on if the request user input is a valid date format
````java
if(request.isDate()){
    //its a date in the format of day/month/year - 30/09/2022
        }else{
    //its not a date! :(
}
````

### dateIsUpComingOnly method
This method will validate that the date provided is an upcoming date! this means that it will reject
dates that are in the past! This method must be used in conjunction with isDate to ensure that the input
is a date first, then that the date is upcoming.
````java
if(request.isDate()){
        if(request.dateIsUpComingOnly()){
            //Success! valid date provided
        }else{
            System.out.println("date is in the past, please select a upcoming date")
        }
    }else{
    System.out.println("invalid date format")
}
````

### isEmail method
This method will use regex to validate that a user input is a valid email format using the 'name@example.com'
format.
````java
if(request.isEmail()){
      //Valid user email!
    }else{
    System.out.println("invalid email provided!")
}
````

### isString method
This method will ensure that only a string was provided, it will check that the input is not an email, date or integer
and then return the result of all those gate methods. This method can be useful when you want to prevent
people entering a date or email as a name.
````java
if(request.isString()){
      //valid input that is not a integer, date or email
    }else{
      //invalid input provided, the input is either a date, email or integer 
}
````

---