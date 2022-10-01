//**** PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.models;

//**** PACKAGE IMPORTS ****\\
//Packages and class files this class is using.
import com.jackgharris.cosc2288.mycar.core.Ascii;
import com.jackgharris.cosc2288.mycar.core.Model;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

//**** START CLASS FILE ****\\
//This Reservation extends the parent "Model" class
public class Reservation extends Model {

    //**** PRIVATE VARIABLES ****\\

    //Date Range Error is an error that will be set and return
    //if an invalid date range is set in the reservation.
    private String dateRangeError;
    //Duration is the duration of days that is booked by the user
    private int duration;
    //Has discount will return true or false if a discount is in effect
    private boolean hasDiscount;

    //Car model, this is the car that is selected by the user
    private Car car;

    //**** CONSTRUCTOR ****\\
    //This constructor would be used if the reservation is loaded by a CSV or database
    //to set the hashmap of data.
    public Reservation(HashMap<String, String> data) {
        //parse the data to the parent
        super(data);
        //set the discount to false
        this.hasDiscount = false;
    }

    //**** CONSTRUCTOR ****\\
    //This constructor is used when building the reservation object from a controller
    //based on user input.
    public Reservation(){
        //create a blank hashmap and parse it to the parent.
        super(new HashMap<String, String>());
        //set the discount to false
        this.hasDiscount = false;
    }

    //-------------------------  RESERVATION MODEL DATA GETTERS AND SETTERS -------------------------\\

    //**** SET USER GIVEN NAME ****\\
    //This method set the user given name to a string, returns void
    public void setUserGivenName(String name){
        this.data.put("user_given_name",name);
    }

    //**** GET USER GIVEN NAME ****\\
    //Returns the given name of the user in this method, returns a string
    public String getUserGivenName(){
        return this.data.get("user_given_name");
    }

    //**** SET USER SURNAME ****\\
    //Sets the user surname, returns void
    public void setUserSurname(String name){
        this.data.put("user_surname", name);
    }

    //**** GET USER SURNAME ****\\
    //Returns the user surname as a string.
    public String getUserSurname(){
        return this.data.get("user_surname");
    }

    //**** SET USER EMAIL ****\\
    //Sets the user email to the email provided, returns null.
    public void setUserEmail(String email){
        this.data.put("user_email", email);
    }

    //**** GET USER EMAIL ****\\
    //Returns the user email to the caller, returns a string.
    public String getUserEmail(){
        return this.data.get("user_email");
    }

    //**** SET PASSENGER COUNT ****\\
    //Set the selected passenger count for this model returns null.
    public void setPassengerCount(int passengerCount){
        this.data.put("user_passenger_count", String.valueOf(passengerCount));
    }

    //**** GET PASSENGER COUNT ****\\
    //Returns the passenger count as an integer to the caller.
    public int getPassengerCount(){
        return Integer.parseInt(this.data.get("user_passenger_count"));
    }

    //**** SET PICKUP DATE ****\\
    //This method sets the pickup date, it accepts the date as a string.
    public void setPickUpDate(String date){
        this.data.put("pickup_date",date);
    }

    //**** SET DROP OFF DATE ****\\
    //This method sets the drop-off date, it accepts the date as a string
    public void setDropOffDate(String date){
        this.data.put("dropoff_date",date);
    }

    //**** GET PICK UP DATE ****\\
    //Returns the pickup date as a string to the caller
    public String getPickUpDate(){
        return this.data.get("pickup_date");
    }

    //**** GET DROP OFF DATE ****\\
    //Returns the drop off date as a string to the caller
    public String getDropOffDate(){
        return this.data.get("dropoff_date");
    }

    //**** SET CAR ****\\
    //This method set the car that has been selected by the user.
    public void setCar(Car car){
        this.car = car;
    }

    //**** GET CAR ****\\
    //Get car returns the car object to the caller
    public Car getCar(){
        return this.car;
    }

    //**** HAS DISCOUNT ****\\
    //Returns true or false depending on if the car has a discount that is greater than 0
    public boolean hasDiscount(){
        return this.car.getDiscount() != 0 && this.duration >= 7;
    }

    //**** GET DATE RANGE ERROR ****\\
    //Returns the date range error to the caller
    public String getDateRangeError(){
        return this.dateRangeError;
    }

    //**** GET DURATION ****\\
    //Returns the duration of dates that the reservation is booked for to the caller
    //as a integer.
    public int getDuration(){
        return this.duration;
    }


    //-------------------------  UTILITY METHODS -------------------------\\

    //**** GET RENTAL COST ****\\
    //This method returns the rental cost for the duration of days that has been
    //set by the user
    public int getRentalCost(){
        return this.car.getRentalPricePerDay() * this.duration;
    }

    //**** GET DISCOUNT PRICE ****\\
    //This method returns the price for the days factoring in the discount percentage.
    public int getDiscountPrice(){

        //set the discount to a float value
        float discount = this.car.getDiscount();

        //check if the discount is 0, or has a value
        if(discount != 0){
            //set the hasDiscount boolean value to true
            this.hasDiscount = true;
        }

        //perform the equation and return the result.
        return (int) (this.getRentalCost()-(this.getRentalCost()*(discount/100)));
    }

    //**** GET INSURANCE COST ****\\
    //Returns the insurance cost * the duration set by the user to the caller as a integer.
    public int getInsuranceCost(){
        return this.car.getInsurancePerDay() * this.duration;
    }

    //**** GET SERVICE FEE ****\\
    //Returns the service fee for the selected car.
    public int getServiceFee(){
        return this.car.getServiceFee();
    }

    //**** GET TOTAL COST ****\\
    //This method returns the total cost of the rental cost for the duration,
    //plus the insurance cost and service fee
    public int getTotalCost(){

        //declare the rental cost integer
        int rentalCost;

        //check if this reservation has a discount
        if(this.hasDiscount){
            //if so set the rental cost to the discounted price
            rentalCost = this.getDiscountPrice();
        }else{
            //else set the rental cost to the standard price
            rentalCost = this.getRentalCost();
        }

        //perform the calculation and return the result.
        return rentalCost+this.getInsuranceCost()+this.getServiceFee();
    }

    //**** VALIDATE DATE RANGE ****\\
    //This method validates the date range that has been provided by the user, this will
    //return true or false depending on if a valida date range is set or not.
    public boolean validateDateRange(){

        //create our simple date format and set the format to day/month/year
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/mm/yyyy", Locale.ENGLISH);

        //set the outcome to true, if we fail the try catch we will set this to false
        boolean outcome = true;

        try{
            //try to parse the pickup and drop off date
            Date pickUp = simpleDateFormat.parse(this.data.get("pickup_date"));
            Date dropOff = simpleDateFormat.parse(this.data.get("dropoff_date"));

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/mm/yyyy");
            LocalDateTime now = LocalDateTime.now();
            Date today = simpleDateFormat.parse(dtf.format(now));

            //check if the pickup date is in the past
            //set the outcome to false.
            outcome = !today.before(pickUp);

            //create the time difference between the two dates
            long timeDiff = Math.abs(dropOff.getTime() -  pickUp.getTime());

            //set the duration to the days between the two dates
            //this.duration = (int) TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);

            //duration test code
            this.duration = (int) ((dropOff.getTime() - pickUp.getTime()) / (1000 * 60 * 60 * 24)) +1;


            //check if the duration  is less than 0, if so then set the error to tell the user the drop-off date
            //cannot be before the pickup date.
            if(this.duration < 0){
                //set the outcome to false
                outcome = false;
                //set the error
                this.dateRangeError = "drop off date cannot be before the pick up date";
            }

            //check if the duration is 0, if so then this indicates that this is the same date and we should return
            //false
            if(this.duration == 0){
                //set the outcome to false
                outcome = false;
                //set the error
                this.dateRangeError = "drop off and pickup date cannot be the same date";
            }

        } catch (ParseException e) {
            //We should not catch an error here, if we do then print a reservation model error, all dates should be validated
            //prior to this step, this could be considered a fatal error.
            System.out.println(Ascii.red+"FATAL RESERVATION MODEL ERROR: Invalid Date Parsed"+Ascii.black);
        }

        //return the outcome.
        return outcome;
    }


    //**** GET DISCOUNT CALCULATION ****\\
    //returns the discount calculation as a string
    public String getDiscountCalculation(){
        //get the discount as a float value
        float discount = this.car.getDiscount();
        //get the discount cost per day
        float discountPricePerDay = this.car.getRentalPricePerDay() -  ((discount /100) *this.car.getRentalPricePerDay());
        //build and then return our string
        return "($"+discountPricePerDay+" * "+this.duration+" days )";
    }

    //**** GET INSURANCE CALCULATION ****\\
    //Returns the calculation that is undertaken to calculate the insurance per day
    public String getInsuranceCalculation(){
        return "($"+this.car.getInsurancePerDay()+" * "+this.duration+" days )";
    }

    //**** GET STANDARD CALCULATION ****\\
    //Returns the standard non-discounted calculation for days
    public String getStandardCalculation(){
        return "($"+this.car.getRentalPricePerDay()+" * "+this.duration+" days )";
    }


}
