package com.example.easynotes.controller;


/**
 * Created by admin on 9/15/18.
 */

import com.example.easynotes.model.Betuser;
import com.example.easynotes.model.Incomingsmscallback;
import com.example.easynotes.repository.BetuserRepository;
import com.example.easynotes.repository.SmsRepository;
import com.example.easynotes.repository.UserRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.AsyncRestTemplate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.Executors;


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
    UserRepository userRepository;

    @Autowired
    BetuserRepository betuserRepository;

    //new user reg
    @CrossOrigin
    @RequestMapping(value = "africareceive", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Map<String,String>> registerNewUser(
            @RequestParam(value = "from") String from,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "date") String date,
            @RequestParam(value = "linkId") String linkId,
            @RequestParam(value = "text") String text) throws IOException, ParseException {

        Map<String,String> response = new HashMap<String, String>();
        
        response.put("code", "00");
        response.put("from", from);
        response.put("to", to);
        response.put("text", text);
        response.put("date", date);
        response.put("linkId", linkId);

        if(text.equals("balance")){
            String currentfrom = from.substring(4);
            String finalfrom = "0"+currentfrom;

            Betuser btu = betuserRepository.findByUsername(finalfrom);
            //User lst = userRepository.findByEmail(Email);

            if(btu == null){
                response.put("mg", "fail");
                response.put("code", "03");
                response.put("desc", "user not found");
                return ResponseEntity.ok().body(response);
            }else {
                String phone = btu.getUsername();
                String pin = btu.getPin();

                getuserinfo(phone, pin, text);
            }

            //getuserinfo(from);
        }else if(text.equals("play")){

            System.out.println(text);

            //String mynumberone = phone.substring(1);
            //String finalnumber = "+254"+mynumberone;
            String newpassword = ""+((int)(Math.random()*9000)+1000);

            String mynumberone = from.substring(4);
            String phone = "0"+mynumberone;

            Betuser bu = new Betuser();
            bu.setPin(newpassword);
            bu.setUsername(phone);
            bu.setStatus("1");

            betuserRepository.save(bu);
            //registeruser(phone);
            String messo = "Please Reply with 'Accept' to activate your account";
            //sendMessage(from, messo);
        }else if(text.equals("accept")){

            String mynumberone = from.substring(4);
            String phone = "0"+mynumberone;

            Betuser accept = betuserRepository.findByUsername(phone);

            if(accept == null){
                response.put("mg", "fail");
                response.put("code", "03");
                response.put("desc", "user not found");
                return ResponseEntity.ok().body(response);
            }else {

                String newphone = accept.getUsername();
                String pin = accept.getPin();
                String status = accept.getStatus();
                String newaccept = "Welcome "+newphone+" to Betting, your account is now activated. Your Pin is:" + pin;

                registeruser(phone, pin);

                if(status.equals("1")){
                    sendMessage(newphone,newaccept);

                    Betuser up = betuserRepository.findByUsername(newphone);

                    up.setStatus("0");

                    Betuser updatedstatus = betuserRepository.save(up);

                    System.out.println("\n");
                    System.out.println("User status updated !!!");
                    System.out.println(updatedstatus);
                    //Betuser bt = new Betuser();
                    //bt.setStatus("0");
                }else {
                    System.out.println("\n");
                    System.out.println("User is already registered !!!");
                }
                //getuserinfo(newphone, pin, text);
            }

            System.out.println(text);

//            activate(phone);
            //sendMessage(from, accept);
        }else if(text.startsWith("changepin")){
            System.out.println(text);
            String newpin = text.substring(9);
            System.out.print(newpin+"\n");

            String mynumberone = from.substring(4);
            String phone = "0"+mynumberone;

            Betuser finduser = betuserRepository.findByUsername(phone);

            String oldpin = finduser.getPin();

            //Betuser bu = new Betuser();
            //bu.setPin(newpin);

            changepassword(from, phone, newpin, oldpin);
        }else {
            System.out.println("unknown parameter");
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

    public static String  registeruser(String phone, String pin) throws IOException, ParseException {

        System.out.println("we are inside the register new user method \n");
        System.out.println(phone);
//        System.out.println(pin);
//        System.out.println(text);

        String sess = getSession();

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinnerone=new JSONObject();
        JSONObject jsonObjectinnertwo=new JSONObject();

        jsonObject.put("command", "register_user");

        jsonObjectinnerone.put("user_info",jsonObjectinnertwo);
        jsonObjectinnertwo.put("username", phone);
        jsonObjectinnertwo.put("password", pin);
        jsonObjectinnertwo.put("currency_name", "KES");
        jsonObjectinnertwo.put("site_id", 733);

        jsonObject.put("params", jsonObjectinnerone);

        System.out.println(jsonObject);

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
            System.out.println(pair.getKey() + " : " + pair.getValue());

//            if(pair.getKey().equals("user_id")){
//                //System.out.println("this is the sid");
//                System.out.println(pair.getValue());
//                String ls = pair.getValue().toString();
//
//                //test(sess);
//
//            }
        }
        return testblah();

    }

    public static String  changepassword(String from, String phone, String newpin, String oldpin) throws IOException, ParseException {

        System.out.println("we are inside the changepassword method \n");
        System.out.println(phone);
        System.out.println(from);
        System.out.println(newpin);
        System.out.println(oldpin);

        String sess = getSession();

        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        jsonObject.put("command", "login");

        jsonObjectinner.put("username", phone);
        jsonObjectinner.put("password", oldpin);

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
        Iterator<Map.Entry> iterator11 = data1.entrySet().iterator();
        while (iterator11.hasNext()) {
            Map.Entry pair = iterator11.next();
            //System.out.println(pair.getKey() + " : " + pair.getValue());
            if(pair.getKey().equals("user_id")){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();
                //test(sess);

            }
        }


        return changepasswordsms(from, oldpin, newpin, phone);

    }

    public static String testblah(){

        return "test freddy";
    }

    public static String changepasswordsms(String from, String oldpin, String newpin, String sess) throws IOException, ParseException {

        JSONArray jsonArray1=new JSONArray();
        JSONObject jsonObject1=new JSONObject();
        JSONObject jsonObjectinnerone1=new JSONObject();

        jsonObject1.put("command", "update_user_password");
        jsonObject1.put("params", jsonObjectinnerone1);
        jsonObjectinnerone1.put("password",oldpin);
        jsonObjectinnerone1.put("new_password", newpin);

        String url1 = "https://eu-swarm-test.betconstruct.com";
        URL obj = new URL(url1);
        HttpURLConnection connect = (HttpURLConnection) obj.openConnection();

        // Setting basic post request
        connect.setRequestMethod("POST");
        connect.setRequestProperty("User-Agent", "USER_AGENT");
        connect.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        connect.setRequestProperty("Content-Type", "application/json");
        connect.setRequestProperty("Swarm-Session", sess);

        jsonArray1.add(jsonObject1);

        String requestJson1=jsonArray1.toString().replaceAll("[\\[\\]]","");

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
        Iterator itr1 = findata.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());

//            if(pair.getKey().equals(text)){
//                //System.out.println("this is the sid");
//                System.out.println(pair.getValue());
//                String ls = pair.getValue().toString();
//
//                String mynumber = phone.substring(1);
//                String finalnumber = "+254"+mynumber;
////                sendMessage(finalnumber, ls);
//                //return ls;
//
//            }

        }

        //return findata.toString();



        if(statuscode1 == 0){
            System.out.println("send success sms to bet customer");
            Betuser bu = new Betuser();
            bu.setPin(newpin);
//            sendMessage(from ,"Your Password has been changed successfully");
        }else {
            System.out.println("an error occured");
        }

        return "success";
    }

    public static String  getuserinfo(String phone, String pin, String text) throws IOException, ParseException {

        System.out.println("we are inside the getuserinfo method");
        System.out.println(phone);
        System.out.println(pin);
        System.out.println(text);

        String sess = getSession();


        JSONArray jsonArray=new JSONArray();
        JSONObject jsonObject=new JSONObject();
        JSONObject jsonObjectinner=new JSONObject();
        jsonObject.put("command", "login");

        jsonObjectinner.put("username", phone);
        jsonObjectinner.put("password", pin);

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
        return test(sess, phone, text);

    }

    public static String test(String sess, String phone, String text) throws IOException, ParseException {
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

            if(pair.getKey().equals(text)){
                //System.out.println("this is the sid");
                System.out.println(pair.getValue());
                String ls = pair.getValue().toString();

                String mynumber = phone.substring(1);
                String finalnumber = "+254"+mynumber;
//                sendMessage(finalnumber, ls);
                //return ls;

            }

        }

        return findata.toString();
    }

    private static void sendMessage(String sender, String msg) {
        System.out.println("Sending text message now");
        System.out.println(sender);
        System.out.println(msg);
        MultiValueMap<String,String> map=new LinkedMultiValueMap<String,String>();
        String message=msg;
        HttpHeaders header=new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String,String>> r=new HttpEntity<>(map,header);
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(100)));
        asyncRestTemplate.exchange("https://api.africastalking.com/restless/send?username=stimapap&Apikey=95ed1013626cf5015ba5a198ffcb2f8cec922e42390883f2c450c02b92e81773&to="+sender+"&message="+msg, HttpMethod.GET,r,String.class)
                .addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
                    @Override
                    public void onSuccess(ResponseEntity<String> result) {
                        System.out.println(result);
                    }

                    @Override
                    public void onFailure(Throwable ex) {
                        ex.printStackTrace();
                    }
                });
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
                String lst = pair.getValue().toString();

                return lst;

            }
        }

        return data.toString();
    }
}


