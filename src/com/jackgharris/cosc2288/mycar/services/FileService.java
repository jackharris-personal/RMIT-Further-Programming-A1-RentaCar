//**** SET PACKAGE ****\\
package com.jackgharris.cosc2288.mycar.services;

//**** PACKAGE IMPORTS ****\\
//Packages and classes required by this class file
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

//**** START CLASS ****\\
public class FileService {

    //**** STATIC LOAD CAR MODEL METHOD ****\\
    //This method accepts a file location and then returns an array list of hash maps
    //containing the key value pairs for each model.
    public static ArrayList<HashMap<String, String>> loadCarModels(String fileLocation){

        //create our array of keys, this can be a set size as we only have 11 keys total
        String[] keys = new String[11];
        //create our output array list
        ArrayList<HashMap<String, String>> output = new ArrayList<>();

        //track what our current line is
        int currentLine = 0;

        try{
            //try to load the file from the file location
            File file = new File(fileLocation);
            //create our scanner and parse it the file
            Scanner scanner = new Scanner(file);

            //while the scanner has a next line to read then read it
            while(scanner.hasNextLine()){
                //set our line to the scanner next line.
                String[] line = scanner.nextLine().split(",");

                //check if this csv file has a header line, if so then we load the keys into an array for use
                if(currentLine < 1){

                    //set our int i counter variable
                    int i = 0;
                    //whilst "i" is less than our length of keys we push the line value at i to the key value at i
                    while(i < keys.length){
                        keys[i] = line[i];
                        //lastly we increment i to loop over all the line/key input values;
                       i++;
                    }

                //else then we are processing the model input not the key input
                }else{

                    //create our model hashmap of data
                    HashMap<String, String> model = new HashMap<>();

                    //put all our data values into the hashmap with the respective key
                    model.put(keys[0],line[0]);
                    model.put(keys[1],line[1]);
                    model.put(keys[2],line[2]);
                    model.put(keys[3],line[3]);
                    model.put(keys[4],line[4]);
                    model.put(keys[5],line[5]);
                    model.put(keys[6],line[6]);
                    model.put(keys[7],line[7]);
                    model.put(keys[8],line[8]);
                    model.put(keys[9],line[9]);
                    model.put(keys[10],line[10]);

                    //put this model hashmap into the output array
                    output.add(model);
                }

                //increase our current line
                currentLine++;
            }
        } catch (FileNotFoundException e) {

            //else then if we do not find the file then set the output to a new array list
            output = new ArrayList<>();
        }

        //Finally then return the output to the caller
        return output;
    }

}
