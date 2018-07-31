package com.example.easynotes.controller;

/**
 * Created by admin on 7/3/18.
 */


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collections;
import java.util.Map;

//import org.springframework.web.bind.annotation.*;

//import org.springframework.web.bind.annotation.*;

//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
public class MpesaController {

    @CrossOrigin
    @RequestMapping(value = "mpesastk", method = RequestMethod.GET, produces = "application/json")
    public Map getRev(@RequestParam(value="number", defaultValue="not available") String number ,
                      @RequestParam(value="amount", defaultValue="not available") String amount) throws IOException {

        System.out.print("###########################################################################################\n");
        System.out.print("\n");
        System.out.print(number);
        System.out.print("\n");
        System.out.print("\n");
        System.out.print(amount);
        System.out.print("\n");
        System.out.print("\n");
        System.out.print("###########################################################################################\n");
        System.out.print("\n");
        System.out.print("\n");

        Auth a = new Auth("GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V","oOpJICRVlyrGSAkM");

        a.authenticate();

        a.STKPushSimulation(amount , number );

        //String tombaone = a.tombaone();

        //return Collections.singletonMap(tombaone.toString());
        return Collections.singletonMap("","");
    }


    //test returns
    @CrossOrigin
    @RequestMapping(value = "mpesaworking", method = RequestMethod.GET, produces = "application/json")
    public String getRevo(@RequestParam(value="number", defaultValue="not available") String number ,
                          @RequestParam(value="amount", defaultValue="not available") String amount) throws IOException {


        Auth a = new Auth("GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V","oOpJICRVlyrGSAkM");

        a.authenticate();

        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type","application/json");
        con.setRequestProperty("authorization","Bearer "+a.authenticate());

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("BusinessShortCode", "174379");
        jsonObject.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNzAzMDYyMDIy");
        jsonObject.put("Timestamp", "20180703062022");
        jsonObject.put("TransactionType", "CustomerPayBillOnline");
        jsonObject.put("Amount",amount);
        jsonObject.put("PhoneNumber", number);
        jsonObject.put("PartyA", number);
        jsonObject.put("PartyB", "174379");
        jsonObject.put("CallBackURL", "http://51.15.242.122:8310/c2b/confirmation");
        jsonObject.put("AccountReference", "fredLTD");
        jsonObject.put("QueueTimeOutURL", "tp://51.15.242.122:8310/c2b/confirmation");
        jsonObject.put("TransactionDesc", "fredLTD");


        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");

        //String postJsonData = "{\"id\":5,\"countryName\":\"USA\",\"population\":8000}";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestJson);
        wr.flush();
        wr.close();


        int responseCode = con.getResponseCode();
        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post Data : " + requestJson);
        System.out.println(requestJson);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        System.out.print("###########################################################################################\n");
        System.out.print("\n");
        System.out.print("\n");
        //printing result from response
        System.out.println(response);
        System.out.print("###########################################################################################\n");
        System.out.print("\n");
        System.out.print("\n");
        System.out.println(response.toString());
        System.out.print("###########################################################################################\n");
        System.out.print("\n");
        System.out.print("\n");

        return response.toString();
    }


}
