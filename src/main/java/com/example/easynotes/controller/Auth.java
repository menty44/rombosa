package com.example.easynotes.controller;
/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */
/**
 * Created by admin on 7/4/18.
 */
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Base64;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

public class Auth {

    String appKey;
    String appSecret;


    public Auth(String app_key, String app_secret){

        appKey=app_key;
        appSecret=app_secret;
    }


    public String authenticate() throws IOException {
//        String app_key = appKey/*"GvzjNnYgNJtwgwfLBkZh65VPwfuKvs0V"*/;
        String app_key = "Gxg0Thwl5rDAHh1swnXjMDCS836M8nIj";
//        String app_secret = appSecret;
        String app_secret = "PoxJvbGbT2f3gNno";
        //String appKeySecret = app_key + ":" + app_secret;
        String appKeySecret = "Gxg0Thwl5rDAHh1swnXjMDCS836M8nIj" + ":" + "PoxJvbGbT2f3gNno";
        byte[] bytes = appKeySecret.getBytes("ISO-8859-1");
        String encoded = Base64.getEncoder().encodeToString(bytes);


        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/oauth/v1/generate?grant_type=client_credentials")
                .get()
                .addHeader("authorization", "Basic "+ encoded)
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject=new JSONObject(response.body().string());
//        JSONObject jsonObject=new JSONObject(response.body());
        System.out.println(jsonObject.getString("access_token"));
        return jsonObject.getString("access_token");
    }

    //STK Push simulation for mobile devices
    String STKPushSimulation(String amount, String phoneNumber) throws IOException {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("BusinessShortCode", "174379");
        jsonObject.put("Password", "MTc0Mzc5YmZiMjc5ZjlhYTliZGJjZjE1OGU5N2RkNzFhNDY3Y2QyZTBjODkzMDU5YjEwZjc4ZTZiNzJhZGExZWQyYzkxOTIwMTgwNzAzMDYyMDIy");
        jsonObject.put("Timestamp", "20180703062022");
        jsonObject.put("TransactionType", "CustomerPayBillOnline");
        jsonObject.put("Amount",amount);
        jsonObject.put("PhoneNumber", phoneNumber);
        jsonObject.put("PartyA", phoneNumber);
        jsonObject.put("PartyB", "174379");
        jsonObject.put("CallBackURL", "http://callback.blaqueyard.com/mpesa/callback.php");
        jsonObject.put("AccountReference", "MAKAOLTD");
        jsonObject.put("QueueTimeOutURL", "http://callback.blaqueyard.com/mpesa/callback.php");
        jsonObject.put("TransactionDesc", "MAKAO");


        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");

        OkHttpClient client = new OkHttpClient();
        String url="https://sandbox.safaricom.co.ke/mpesa/stkpush/v1/processrequest";
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+authenticate())
                .addHeader("cache-control", "no-cache")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        //System.out.println(response.body());
        String fredresponse = response.body().string();



        //tombaone();

        return fredresponse;
    }


    //STK STKPushTransactionStatus  for mobile devices
    public String STKPushTransactionStatus( String businessShortCode, String password, String timestamp, String checkoutRequestID) throws IOException {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("BusinessShortCode", businessShortCode);
        jsonObject.put("Password", password);
        jsonObject.put("Timestamp", timestamp);
        jsonObject.put("CheckoutRequestID", checkoutRequestID);


        jsonArray.put(jsonObject);

        String requestJson=jsonArray.toString().replaceAll("[\\[\\]]","");



        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestJson);
        Request request = new Request.Builder()
                .url("https://sandbox.safaricom.co.ke/mpesa/stkpushquery/v1/query")
                .post(body)
                .addHeader("authorization", "Bearer "+authenticate())
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();
        System.out.println(response.body().string());
        return response.body().toString();

    }




    public String tombaone(String fredresponse){

        String webserviceresponse = fredresponse;

        return webserviceresponse;
    }



}

