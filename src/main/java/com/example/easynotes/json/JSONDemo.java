package com.example.easynotes.json;/**
 * Created by admin on 8/2/18.
 */

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Java Program to show how to work with JSON in Java.
 * In this tutorial, we will learn creating
 * a JSON file, writing data into it and then reading from JSON file.
 *
 * @author Javin Paul
 */
public class JSONDemo{

    public static void main(String args[]) {

        // generate JSON String in Java
        writeJson("book.json");

        // let's read
        readJson("book.json");
    }
    /*
     * Java Method to read JSON From File
     */
    public static void readJson(String file) {
        JSONParser parser = new JSONParser();

        try {
            System.out.println("Reading JSON file from Java program");
            FileReader fileReader = new FileReader(file);
            JSONObject json = (JSONObject) parser.parse(fileReader);

            String title = (String) json.get("title");
            String author = (String) json.get("author");
            long price = (long) json.get("price");

            System.out.println("title: " + title);
            System.out.println("author: " + author);
            System.out.println("price: " + price);

//            JSONArray characters = (JSONArray) json.get("characters");
//            Iterator i = characters.iterator();

//            System.out.println("characters: ");
//            while (i.hasNext()) {
//                System.out.println(" " + i.next());
//            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    /*
     * Java Method to write JSON String to file
     */
    public static void writeJson(String file) {
        JSONObject jsons = new JSONObject();
        jsons.put("title", "Harry Potter and Half Blood Prince");
        jsons.put("author", "J. K. Rolling");
        jsons.put("price", 20);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add("Harry");
        jsonArray.add("Ron");
        jsonArray.add("Hermoinee");

//        json.put("characters", jsonArray);
        //json.put("data",json);

        try {
            System.out.println("Writting JSON into file ...");
            System.out.println(jsons);
            FileWriter jsonFileWriter = new FileWriter(file);
            jsonFileWriter.write(jsons.toJSONString());
            jsonFileWriter.flush();
            jsonFileWriter.close();
            System.out.println("Done");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


