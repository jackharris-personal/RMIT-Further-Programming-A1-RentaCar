package com.jackgharris.cosc2288.mycar.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileService {

    public static ArrayList<HashMap<String, String>> loadCarModels(String fileLocation){

        String[] keys = new String[11];
        ArrayList<HashMap<String, String>> output = new ArrayList<>();

        int currentLine = 0;

        try{
            File file = new File(fileLocation);
            Scanner scanner = new Scanner(file);

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

                    output.add(model);

                }

                currentLine++;


            }
        } catch (FileNotFoundException e) {
            output = new ArrayList<>();
        }

        return output;

    }

}
