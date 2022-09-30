# <center> RMIT-Further-Programming-A1 | MyCar </center>
MyCar is a console based java booking program that allows users to search for and then book cars! This program impliments the core Java OOP standards and impliments the
very popular Model-View-Controller design pattern to split code and logic into its three easy to maintian respective catoeries or 'data', 'user interface', and 'logic'.
MyCar loads its data from the fleet.csv, this can have as many or a few car entires as we like, the program will adapt and accept 1 car or 100 cars.

<h3 align="center">
  <b>Documentation:</b><br><br>
  <a href="https://github.com/s3893749/RMIT-Further-Programming-A1/blob/master/docs/views.md">Views</a> |
  <a href="https://github.com/s3893749/RMIT-Further-Programming-A1/blob/master/docs/controllers.md">Controllers</a> |
  <a href="#">Responses</a> |
  <a href="https://github.com/s3893749/RMIT-Further-Programming-A1#getting-searching-and-sorting-car-models">Car Model & Collection</a> |
  <a href="#">Requests</a>
</h3>

<br>
<p align="center" style="padding-top: 64px"> 
<img width="612" alt="MyCarCoverImage" align="center" src="https://user-images.githubusercontent.com/79836947/192490153-c494e461-f543-4357-8940-49adb9a106d1.png">
</p>
<p align="center">Image displaying the main my car menu.</p>

<br>

***

## Getting, Searching and Sorting Car Models
To load and reference our model data effectively the 'fleet.csv' file is loaded into a static array of cars, this can then be refereced by any caller that wants via our
static getters methods. To complement this apporch i have created a custom collection style class that is chainable and familer to anyone who has used any SQL. This
'CarCollection' object will be returned from all static 'Car' getter facade calls.

Lets now explore the calls we can use to sort and search for cars

---

### Car Static Getter: Where(Key, Value)
This method will return a Java array of cars where the key matches a value in the car model, in the example here we can see we are getting the Cars where the brand is
ford. Note: after any '.Where()' method call we must used '.get()' to return our filtered list. 
```java
//Get Where Brand is Ford
Car[] cars = Car.where("Brand","Ford").get();

//Iterate our returned array
for (Car car: cars) {
  System.out.println(car.getID()+" ) "+car.getBrand()+" "+car.getModel());
}

```
output:
```
C008 ) Ford Focus
C009 ) Ford Puma
```

---

### Car Static Getter: OrderBy(Key, Order Type)
The OrderBy method will take in a key and a order type from our Sort Enum either 'Sort.ASCENDING' or 'Sort.DESCENDING' and will order the values by the result of the keys
that were targeted, note this can work both with String and with integers. For example we can use this method on the car 'Brand' key as well as the car 'No. of seats'
```java
//String Ordering
Car[] cars = Car.orderBy("Brand",Sort.ASCENDING).get();

//Integer Ordering
Car[] cars2 = Car.orderBy("No. of seats",Sort.DESCENDING).get();

//We can then iterate over our array again to validate the output, in this case we will output orderBy Brand via ascending
for (Car car: cars) {
  System.out.println(car.getID()+" ) "+car.getBrand()+" "+car.getModel());
}

```
output:
```
C004 ) Audi A3
C006 ) BMW X5
C007 ) BMW 320i
C008 ) Ford Focus
C009 ) Ford Puma
C005 ) Holden Cruze
C001 ) Toyota Yaris
C002 ) Toyota Corolla
C003 ) Toyota Kluger
```

---

### Car Static Getter: GetWhereSeats(number, sort type)

This method accepts a number and a sorting type and will return all the cars with a seat count that is either greater than or less than the value provided, the sort type
must be from our Sort Enum and either 'Sort.GREATER_THAN' or 'Sort.LESS_THAN'. 

```java
//Get cars where seats are greater than 5
Car[] cars = Car.getWhereSeats(5, Sort.GREATER_THAN).get();

//Iterate our our cars and output them and the seat count
for (Car car: cars) {
  System.out.println(car.getID()+" ) "+car.getBrand()+" "+car.getModel()+" "+car.getSeatCount()+" seats");
}

````
output:
````
C003 ) Toyota Kluger 7 seats
C006 ) BMW X5 7 seats
````

---

### Car Static Getter: Chaining Querys

Similer to SQL we can chain querys here to further refine our search, for example lets search for all cars where the brand is Toyota, and the type is a sedan, then
lets ordeer by the seats.

````java
//Build our query and keep appending on new statements
Car[] cars = Car.where("Brand","Toyota").where("Type","Sedan").orderBy("No. of seats", Sort.ASCENDING).get();

//Iterate over our results array to confirm our expectations, in this case we would expect 1 car to be in the ouput.
for (Car car: cars) {
  System.out.println(car.getID()+" ) "+car.getBrand()+" "+car.getModel()+" "+car.getSeatCount()+" seats");
}

````
output:
````
C001 ) Toyota Yaris 4 seats
````

---

### Car Static Getter:  get() VS getFirst()

After your build query with statements 1 or many you can call either 'get()' or 'getFirst', get will return a java array of cars to you, where as get first will return
the first car from that array, this can be extreamly helpful if you know your query will only return 1 car, for example if we do a where statement on the vehicle id we
would only ever expect 1 result to return and thus get first would be the more preferable way to acccess it.

````java

//Get our result, this time delcaring it as a single Car object not an array
Car yaris = Car.where("Vehicle ID","C001").getFirst();

//Output the result to confirm
System.out.println(yaris.getID()+" ) is our "+yaris.getModel()+" car model");

````
output:
````
C001 ) is our Yaris car model
````

---

### Binding Our CSV To Our Model Data
There are a number of ways that we could store the core model data that is used to gernate our CarCollection objects that we are then querying, The apporch i have taken
is to build a helper method in a FileService class that will return a ArrayList of HashMaps that contains each Key=>Value pair from the csv file. This is loaded once at
the start of the program.

Note: If a file fails to load the 'FileService.loadCarModels()' method will simply return an ArrayList of no entires to the 'Car.bindModelData()' method.

```java
//set our working directory
String directory = System.getProperty("user.dir");

//bind Car model data to the car models that are contained inside the fleet.csv file
Car.bindModelData(FileService.loadCarModels(directory+"/fleet.csv"));
```
csv file:

Vehicle ID | Brand  | Model  | Type | Year of Manufacture | No. of seats | Color | Rental per day (AUD) |  Insurance per day (AUD) | Service fee (AUD) | Discount
--- | --- | --- | --- | --- | --- | --- | --- | --- | --- | ---
C001 | Toyota	    | Yaris	  | Sedan	| 2012	| 4	| Blue	| 50	| 15	| 10	| 10
C002 | Toyota	    | Corolla	| Hatch	| 2020	| 4 |	White	| 45	| 20	| 10	| 10
C003 | Toyota	    | Kluger	| SUV	  | 2019	| 7	| Grey	| 70	| 20	| 20	| 10
C004 | Audi	      | Sedan	  | A3    | 2015	| 5	| Red	  | 65	| 10	| 20	| 10
C005 | Holden	    | Cruze	  | Hatch	| 2020	| 4	| Green	| 70	| 10	| 10	| 10
C006 | BMW	   	  | SUV	    | X5    | 2018	| 7	| White	| 100	| 25	| 20	| 10
C007 | BMW	     	| Sedan	  | 320i  | 2021	| 5	| Grey  |	75	| 10	| 15	| N/A
C008 | Ford     	| Sedan	  | Focus | 2014	| 5	| Red	  | 45	| 10	| 10	| N/A
C009 | Ford	    	| SUV	    | Puma  | 2015	| 5	| Black	| 70	| 20	| 15	| 20




![image](https://user-images.githubusercontent.com/79836947/160737604-273c62fd-1503-4ce6-a292-a351665cc2e1.png#gh-dark-mode-only)
![image](https://user-images.githubusercontent.com/79836947/160738358-eaa88731-2a44-4004-ab9a-3d83a2268742.png#gh-light-mode-only)



# RMIT University | Academic Integrity Warning

Cheating is a serious offense:

> "What happens if you get caught cheating at RMIT? For serious breaches of academic integrity, students can be charged with academic misconduct. Possible penalties > include cancellation of results and expulsion resulting in the cancellation of a student's program."

Academic integrity - RMIT University

### Links:

 [RMIT Academic Integrity Awarness Micro Credential](https://www.rmit.edu.au/study-with-us/levels-of-study/short-courses/academic-integrity-awareness)
 
 [Academic Integrity at RMIT](https://www.rmit.edu.au/students/my-course/assessment-results/academic-integrity)
