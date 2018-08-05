package com.example.easynotes.controller;

/**
 * Created by admin on 7/3/18.
 */


import com.example.easynotes.model.MpesastkPush;
import com.example.easynotes.repository.MpesastkpushRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

//import org.json.JSONArray;
//import org.json.JSONObject;

//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;

//import javax.json.Json;
//import javax.json.JsonArray;
//import javax.json.JsonObject;
//import javax.json.JsonReader;
//import javax.json.JsonValue;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

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

    @Autowired
    MpesastkpushRepository msprepo;

    @CrossOrigin
    @RequestMapping(value = "mpesastk", method = RequestMethod.GET, produces = "application/json")
    public Map getRev(@RequestParam(value = "number", defaultValue = "not available") String number,
                      @RequestParam(value = "amount", defaultValue = "not available") String amount) throws IOException {

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

        Auth a = new Auth("GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V", "oOpJICRVlyrGSAkM");

        a.authenticate();

        a.STKPushSimulation(amount, number);

        //String tombaone = a.tombaone();

        //return Collections.singletonMap(tombaone.toString());
        return Collections.singletonMap("", "");
    }


    //test returns
    @CrossOrigin
    @RequestMapping(value = "mpesaworking", method = RequestMethod.GET, produces = "application/json")
    public String getRevo(@RequestParam(value = "number", defaultValue = "not available") String number,
                          @RequestParam(value = "amount", defaultValue = "not available") String amount) throws IOException, ParseException {


        Auth a = new Auth("GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V", "oOpJICRVlyrGSAkM");

        a.authenticate();

        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("authorization", "Bearer " + a.authenticate());

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");

        String dateString = format.format(new Date());
        System.out.println(dateString);

        String pass="174379"+"bfb279f9aa9bdbcf158e97dd71a467cd2e0c893059b10f78e6b72ada1ed2c919"+dateString;
        byte[] bytes = new byte[0];
        try {
            bytes = pass.getBytes("ISO-8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String password = Base64.getEncoder().encodeToString(bytes);

        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("BusinessShortCode", "174379");
//        jsonObject.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNzAzMDYyMDIy");
        jsonObject.put("Password", password);
//        jsonObject.put("Timestamp", "20180703062022");
        jsonObject.put("Timestamp", "");
        jsonObject.put("TransactionType", "CustomerPayBillOnline");
        jsonObject.put("Amount", amount);
        jsonObject.put("PhoneNumber", number);
        jsonObject.put("PartyA", number);
        jsonObject.put("PartyB", "174379");
        jsonObject.put("CallBackURL", "http://51.15.242.122:8310/c2b/confirmation");
        jsonObject.put("AccountReference", "fredLTD");
        jsonObject.put("QueueTimeOutURL", "tp://51.15.242.122:8310/c2b/confirmation");
        jsonObject.put("TransactionDesc", "fredLTD");

        jsonArray.add(jsonObject);

        String requestJson = jsonArray.toString().replaceAll("[\\[\\]]", "");

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

//        try {
            System.out.println("Reading JSON file from MPESA \n");
            JSONParser parser = new JSONParser();
            JSONObject mystring = (JSONObject) parser.parse(response.toString());

            String MerchantRequestID = (String) mystring.get("MerchantRequestID");
            String CheckoutRequestID = (String) mystring.get("CheckoutRequestID");
            String ResponseCode = (String) mystring.get("ResponseCode");
            String ResponseDescription = (String) mystring.get("ResponseDescription");
            String CustomerMessage = (String) mystring.get("CustomerMessage");

            System.out.print("###################################### START MPESA STK PARSE #####################################################\n");
            System.out.print("\n");
            System.out.print("\n");
            System.out.println("MerchantRequestID: " + MerchantRequestID);
            System.out.print("\n");
            System.out.println("CheckoutRequestID: " + CheckoutRequestID);
            System.out.print("\n");
            System.out.println("ResponseCode: " + Integer.parseInt(ResponseCode));
            System.out.print("\n");
            System.out.println("ResponseDescription: " + ResponseDescription);
            System.out.print("\n");
            System.out.println("CustomerMessage: " + CustomerMessage);
            System.out.print("\n");
            System.out.print("###################################### END MPESA STK PARSE #####################################################\n");


            HttpURLConnection http = (HttpURLConnection) obj.openConnection();
            int success = Integer.parseInt(ResponseCode);
            int statusCode = http.getResponseCode();
            System.out.print("The STATUS CODE "+ statusCode+ "\n");


        if(success == 0){
                MpesastkPush msp = new MpesastkPush();

                msp.setMerchantrequestid(MerchantRequestID);
                msp.setCheckoutrequestid(CheckoutRequestID);
                msp.setResponsecode(Integer.parseInt(ResponseCode));
                msp.setResponsedescription(ResponseDescription);
                msp.setCustomermessage(CustomerMessage);
                msp.setUserid(2882);

                msprepo.save(msp);

                return response.toString();
            }else {

                return "{\"fail\":1}";

            }

//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        return response.toString();
    }

    @CrossOrigin
    @RequestMapping(value = "mpesastkstatus", method = RequestMethod.GET, produces = "application/json")
    public String getRevo(@RequestParam(value = "checkoutRequestID", defaultValue = "not available") String checkoutRequestID) throws IOException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("BusinessShortCode", "174379");
        jsonObject.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNzAzMDYyMDIy");
        jsonObject.put("Timestamp", "20180703062022");
        jsonObject.put("CheckoutRequestID", checkoutRequestID);

        Auth a = new Auth("GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V", "oOpJICRVlyrGSAkM");

        a.authenticate();

        String url = "https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("authorization", "Bearer " + a.authenticate());

        jsonArray.add(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");

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

        return response.toString();
    }


}
