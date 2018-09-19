package com.example.easynotes.controller;


/**
 * Created by admin on 9/15/18.
 */

import com.example.easynotes.model.Incomingsmscallback;
import com.example.easynotes.repository.SmsRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
@RequestMapping("/sms")
public class BetConstructController {

    @Autowired
    SmsRepository smsRepository;

    @Autowired
    UserController userController;

    //new user reg
    @CrossOrigin
    @RequestMapping(value = "africareceive", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String,String>> registerNewUser(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "linkId") String linkId,
            @RequestParam(value = "text") String text) throws IOException {

        Map<String,String> response = new HashMap<String, String>();
        
        response.put("code", "00");
        response.put("from", from);
        response.put("to", to);
        response.put("text", text);
        response.put("date", date);
        response.put("linkId", linkId);

        if(text.equals("balance")){

        }

        Saveincomingsms(from, text, date,linkId, to);

        return ResponseEntity.accepted().body(response);

    }

    public void Saveincomingsms(String from,String text,String date,String linkId,String to){

        Incomingsmscallback sms = new Incomingsmscallback();


//        sms.setId(generatedLong);
        sms.setMessfrom(from);
        sms.setMessto(to);
        sms.setText(text);
        sms.setDate(date);
        sms.setLinkid(linkId);

        smsRepository.save(sms);

    }

    public static String  getuserinfo() throws IOException, ParseException {

        String sess = getSession();

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        jsonObject.put("command", "login");

        jsonObjectinner.put("username", "0720106420");
        jsonObjectinner.put("password", "7777");

        jsonObject.put("params", jsonObjectinner);

        String url = "https://eu-swarm-test.betconstruct.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Swarm-Session", sess);

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
        //System.out.println(requestJson);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //System.out.println("Reading JSON file from Response \n");
        JSONParser parser = new JSONParser();
        JSONObject mystring = (JSONObject) parser.parse(response.toString());

        ;
        Long statuscode = (Long) mystring.get("code");
//            String mydata = (String) mystring.get("data");
//        System.out.println("my status code");
//        System.out.println(statuscode);

        // getting data object for displating the sid
        Map data1 = ((Map)mystring.get("data"));
//            System.out.println("my data\n");
//            System.out.println(data);

        // iterating address Map
        Iterator<Map.Entry> iterator1 = data1.entrySet().iterator();
        while (iterator1.hasNext()) {
            Map.Entry pair = iterator1.next();
            //System.out.println(pair.getKey() + " : " + pair.getValue());

            if(pair.getKey().equals("user_id")){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();

                //test(sess);

            }
        }
        return test(sess);

    }

    public static String test(String sess) throws IOException, ParseException {
        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        JSONObject jsonObjectone    =new JSONObject();
        JSONObject jsonObjectinners =new JSONObject();

        jsonObjectone.put("command", "get_user");

        jsonObjectone.put("params", "");

        System.out.println(jsonObjectone);

        String url = "https://eu-swarm-test.betconstruct.com";
        URL obj = new URL(url);
        HttpURLConnection connect = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        connect.setRequestMethod("POST");
        connect.setRequestProperty("User-Agent", "USER_AGENT");
        connect.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Swarm-Session", sess);

        jsonArray.add(jsonObjectone);

        String requestJson1=jsonArray.toString().replaceAll("[\\[\\]]","");

        // Send post request
        connect.setDoOutput(true);
        DataOutputStream write = new DataOutputStream(connect.getOutputStream());
        write.writeBytes(requestJson1);
        write.flush();
        write.close();

        int responseCode1 = connect.getResponseCode();
        //String responseContent = connect.getResponseMessage().toString();
        //System.out.println("\nSending 'POST' request to URL : " + url);
        //System.out.println("Post Data : " + requestJson);
        //System.out.println(requestJson);
        System.out.println("Response Code : " + responseCode1);
        //System.out.println("Response Content : " + responseContent);

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(connect.getInputStream()));
        String output1;
        StringBuffer responses = new StringBuffer();

        while ((output1 = bufferedReader.readLine()) != null) {
            responses.append(output1);
        }
        bufferedReader.close();

        //System.out.println("Reading JSON file from Response \n");
        JSONParser parser1 = new JSONParser();
        JSONObject mystring1 = (JSONObject) parser1.parse(responses.toString());

        Long statuscode1 = (Long) mystring1.get("code");
//            String mydata = (String) mystring.get("data");
//        System.out.println("my status code");
//        System.out.println(statuscode);

        // getting data object for displating the sid
        Map findata = ((Map)mystring1.get("data"));
//            System.out.println("my data\n");

        // iterating address Map
        Iterator<Map.Entry> itr1 = findata.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
//            System.out.println(pair.getKey() + " : " + pair.getValue());

            if(pair.getKey().equals("balance")){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();

                //return ls;

            }

        }
        return findata.toString();
    }

    public static String  login() throws IOException, ParseException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        jsonObject.put("command", "login");

        jsonObjectinner.put("username", "0720106420");
        jsonObjectinner.put("password", "7777");

        jsonObject.put("params", jsonObjectinner);

        String url = "https://eu-swarm-test.betconstruct.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        String session = getSession();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Swarm-Session", session);

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
        //System.out.println(requestJson);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //System.out.println("Reading JSON file from Response \n");
        JSONParser parser = new JSONParser();
        JSONObject mystring = (JSONObject) parser.parse(response.toString());

        ;
        Long statuscode = (Long) mystring.get("code");
//            String mydata = (String) mystring.get("data");
//        System.out.println("my status code");
//        System.out.println(statuscode);

        // getting data object for displating the sid
        Map data = ((Map)mystring.get("data"));
//            System.out.println("my data\n");
//            System.out.println(data);

        // iterating address Map
        Iterator<Map.Entry> itr1 = data.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            //System.out.println(pair.getKey() + " : " + pair.getValue());

            if(pair.getKey().equals("user_id")){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();

                return ls;

            }
        }

        return data.toString();
    }

    public void  Sendsms(){

    }

    public static String  getSession() throws IOException, ParseException {

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        jsonObject.put("command", "request_session");

        jsonObjectinner.put("site_id", 733);
        jsonObjectinner.put("language", "eng");

        jsonObject.put("params", jsonObjectinner);

        String url = "https://eu-swarm-test.betconstruct.com";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", "USER_AGENT");
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        con.setRequestProperty("Content-Type", "application/json");
        //con.setRequestProperty("authorization", "Bearer " + a.authenticate());

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
        //System.out.println(requestJson);
        //System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String output;
        StringBuffer response = new StringBuffer();

        while ((output = in.readLine()) != null) {
            response.append(output);
        }
        in.close();

        //System.out.println("Reading JSON file from Response \n");
        JSONParser parser = new JSONParser();
        JSONObject mystring = (JSONObject) parser.parse(response.toString());

        ;
        Long statuscode = (Long) mystring.get("code");
//            String mydata = (String) mystring.get("data");
//        System.out.println("my status code");
//        System.out.println(statuscode);

        // getting data object for displating the sid
        Map data = ((Map)mystring.get("data"));
//            System.out.println("my data\n");
//            System.out.println(data);

        // iterating address Map
        Iterator<Map.Entry> itr1 = data.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            //System.out.println(pair.getKey() + " : " + pair.getValue());

            if(pair.getKey().equals("sid")){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();

                return ls;

            }
        }

        return data.toString();
    }
}


