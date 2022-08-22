package com.jackgharris.uber_eats_console.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class FileService {

    public static HashMap<String, String> loadConfig(String fileLocation){

        HashMap<String, String> output = new HashMap<String, String>();

        try{

            File file = new File(fileLocation);
            Scanner scanner = new Scanner(file);

            while(scanner.hasNextLine()){

                String line = scanner.nextLine();

                if(!line.contains("//")){

                    String[] data = line.split("=");

                    if(data.length == 1) {
                        output.put(data[0], "");
                    }else{
                        output.put(data[0], data[1]);
                    }
                }

            }

            scanner.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

}
