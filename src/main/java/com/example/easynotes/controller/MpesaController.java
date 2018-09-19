package com.example.easynotes.controller;

/**
 * Created by admin on 7/3/18.
 */


import com.example.easynotes.model.MpesastkPush;
import com.example.easynotes.repository.MpesastkpushRepository;
import com.zkteco.biometric.FingerprintSensorErrorCode;
import com.zkteco.biometric.FingerprintSensorEx;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@RestController
public class MpesaController {

//    start biometric
//the width of fingerprint image
int fpWidth = 0;
    //the height of fingerprint image
    int fpHeight = 0;
    //for verify test
    private byte[] lastRegTemp = new byte[2048];
    //the length of lastRegTemp
    private int cbRegTemp = 0;
    //pre-register template
    private byte[][] regtemparray = new byte[3][2048];
    //Register
    private boolean bRegister = false;
    //Identify
    private boolean bIdentify = true;
    //finger id
    private int iFid = 1;

    private int nFakeFunOn = 1;
    //must be 3
    static final int enroll_cnt = 3;
    //the index of pre-register function
    private int enroll_idx = 0;

    private byte[] imgbuf = null;
    private byte[] template = new byte[2048];
    private int[] templateLen = new int[1];


    private boolean mbStop = true;
    private long mhDevice = 0;
    private long mhDB = 0;

    String radioISO = null;
    String radioANSI = null;
    String radioZK = null;
    private WorkThread workThread = null;
//    end biometric

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

    @GetMapping("/biometric")
    public String test(){
        //return categoryRepository.findAll();
        // TODO Auto-generated method stub
        if (0 != mhDevice)
        {
            //already inited
            //textArea.setText("Please close device first!\n");
            return "Please close device first!\n";
        }
        int ret = FingerprintSensorErrorCode.ZKFP_ERR_OK;
        //Initialize
        cbRegTemp = 0;
        bRegister = false;
        bIdentify = false;
        iFid = 1;
        enroll_idx = 0;
        if (FingerprintSensorErrorCode.ZKFP_ERR_OK != FingerprintSensorEx.Init())
        {
            //textArea.setText("Init failed!\n");
            return "Init failed!\n";
        }
        ret = FingerprintSensorEx.GetDeviceCount();
        if (ret < 0)
        {
            //textArea.setText("No devices connected!\n");
            FreeSensor();
            return "No devices connected!\n";
        }
        if (0 == (mhDevice = FingerprintSensorEx.OpenDevice(0)))
        {
            //textArea.setText("Open device fail, ret = " + ret + "!\n");
            FreeSensor();
            return "Open device fail, ret = " + ret + "!\n";
        }
        if (0 == (mhDB = FingerprintSensorEx.DBInit()))
        {
            //textArea.setText("Init DB fail, ret = " + ret + "!\n");
            FreeSensor();
            return "Init DB fail, ret = " + ret + "!\n";
        }

        //For ISO/Ansi
        int nFmt = 0;	//Ansi
//        if (radioISO.isSelected())
//        {
//            nFmt = 1;	//ISO
//        }
        FingerprintSensorEx.DBSetParameter(mhDB,  5010, nFmt);
        //For ISO/Ansi End

        //set fakefun off
        //FingerprintSensorEx.SetParameter(mhDevice, 2002, changeByte(nFakeFunOn), 4);

        byte[] paramValue = new byte[4];
        int[] size = new int[1];
        //GetFakeOn
        //size[0] = 4;
        //FingerprintSensorEx.GetParameters(mhDevice, 2002, paramValue, size);
        //nFakeFunOn = byteArrayToInt(paramValue);

        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 1, paramValue, size);
        fpWidth = byteArrayToInt(paramValue);
        size[0] = 4;
        FingerprintSensorEx.GetParameters(mhDevice, 2, paramValue, size);
        fpHeight = byteArrayToInt(paramValue);

        imgbuf = new byte[fpWidth*fpHeight];
        //btnImg.resize(fpWidth, fpHeight);
        mbStop = false;
        workThread = new WorkThread();
        workThread.start();// 线程启动
        //textArea.setText("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight +"\n");
        System.out.print("Open succ! Finger Image Width:" + fpWidth + ",Height:" + fpHeight +"\n");

        return "{'message':'hello'}";
    }

    private void FreeSensor(){
        mbStop = true;
        try {		//wait for thread stopping
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (0 != mhDB)
        {
            FingerprintSensorEx.DBFree(mhDB);
            mhDB = 0;
        }
        if (0 != mhDevice)
        {
            FingerprintSensorEx.CloseDevice(mhDevice);
            mhDevice = 0;
        }
        FingerprintSensorEx.Terminate();
    }

    public static void writeBitmap(byte[] imageBuf, int nWidth, int nHeight,
                                   String path) throws IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(path);
        java.io.DataOutputStream dos = new java.io.DataOutputStream(fos);

        int w = (((nWidth+3)/4)*4);
        int bfType = 0x424d; // 位图文件类型（0—1字节）
        int bfSize = 54 + 1024 + w * nHeight;// bmp文件的大小（2—5字节）
        int bfReserved1 = 0;// 位图文件保留字，必须为0（6-7字节）
        int bfReserved2 = 0;// 位图文件保留字，必须为0（8-9字节）
        int bfOffBits = 54 + 1024;// 文件头开始到位图实际数据之间的字节的偏移量（10-13字节）

        dos.writeShort(bfType); // 输入位图文件类型'BM'
        dos.write(changeByte(bfSize), 0, 4); // 输入位图文件大小
        dos.write(changeByte(bfReserved1), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfReserved2), 0, 2);// 输入位图文件保留字
        dos.write(changeByte(bfOffBits), 0, 4);// 输入位图文件偏移量

        int biSize = 40;// 信息头所需的字节数（14-17字节）
        int biWidth = nWidth;// 位图的宽（18-21字节）
        int biHeight = nHeight;// 位图的高（22-25字节）
        int biPlanes = 1; // 目标设备的级别，必须是1（26-27字节）
        int biBitcount = 8;// 每个像素所需的位数（28-29字节），必须是1位（双色）、4位（16色）、8位（256色）或者24位（真彩色）之一。
        int biCompression = 0;// 位图压缩类型，必须是0（不压缩）（30-33字节）、1（BI_RLEB压缩类型）或2（BI_RLE4压缩类型）之一。
        int biSizeImage = w * nHeight;// 实际位图图像的大小，即整个实际绘制的图像大小（34-37字节）
        int biXPelsPerMeter = 0;// 位图水平分辨率，每米像素数（38-41字节）这个数是系统默认值
        int biYPelsPerMeter = 0;// 位图垂直分辨率，每米像素数（42-45字节）这个数是系统默认值
        int biClrUsed = 0;// 位图实际使用的颜色表中的颜色数（46-49字节），如果为0的话，说明全部使用了
        int biClrImportant = 0;// 位图显示过程中重要的颜色数(50-53字节)，如果为0的话，说明全部重要

        dos.write(changeByte(biSize), 0, 4);// 输入信息头数据的总字节数
        dos.write(changeByte(biWidth), 0, 4);// 输入位图的宽
        dos.write(changeByte(biHeight), 0, 4);// 输入位图的高
        dos.write(changeByte(biPlanes), 0, 2);// 输入位图的目标设备级别
        dos.write(changeByte(biBitcount), 0, 2);// 输入每个像素占据的字节数
        dos.write(changeByte(biCompression), 0, 4);// 输入位图的压缩类型
        dos.write(changeByte(biSizeImage), 0, 4);// 输入位图的实际大小
        dos.write(changeByte(biXPelsPerMeter), 0, 4);// 输入位图的水平分辨率
        dos.write(changeByte(biYPelsPerMeter), 0, 4);// 输入位图的垂直分辨率
        dos.write(changeByte(biClrUsed), 0, 4);// 输入位图使用的总颜色数
        dos.write(changeByte(biClrImportant), 0, 4);// 输入位图使用过程中重要的颜色数

        for (int i = 0; i < 256; i++) {
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(i);
            dos.writeByte(0);
        }

        byte[] filter = null;
        if (w > nWidth)
        {
            filter = new byte[w-nWidth];
        }

        for(int i=0;i<nHeight;i++)
        {
            dos.write(imageBuf, (nHeight-1-i)*nWidth, nWidth);
            if (w > nWidth)
                dos.write(filter, 0, w-nWidth);
        }
        dos.flush();
        dos.close();
        fos.close();
    }

    public static byte[] changeByte(int data) {
        return intToByteArray(data);
    }

    public static byte[] intToByteArray (final int number) {
        byte[] abyte = new byte[4];
        // "&" 与（AND），对两个整型操作数中对应位执行布尔代数，两个位都为1时输出1，否则0。
        abyte[0] = (byte) (0xff & number);
        // ">>"右移位，若为正数则高位补0，若为负数则高位补1
        abyte[1] = (byte) ((0xff00 & number) >> 8);
        abyte[2] = (byte) ((0xff0000 & number) >> 16);
        abyte[3] = (byte) ((0xff000000 & number) >> 24);
        return abyte;
    }

    public static int byteArrayToInt(byte[] bytes) {
        int number = bytes[0] & 0xFF;
        // "|="按位或赋值。
        number |= ((bytes[1] << 8) & 0xFF00);
        number |= ((bytes[2] << 16) & 0xFF0000);
        number |= ((bytes[3] << 24) & 0xFF000000);
        return number;
    }

    private class WorkThread extends Thread {
        @Override
        public void run() {
            super.run();
            int ret = 0;
            while (!mbStop) {
                templateLen[0] = 2048;
                if (0 == (ret = FingerprintSensorEx.AcquireFingerprint(mhDevice, imgbuf, template, templateLen)))
                {
                    if (nFakeFunOn == 1)
                    {
                        byte[] paramValue = new byte[4];
                        int[] size = new int[1];
                        size[0] = 4;
                        int nFakeStatus = 0;
                        //GetFakeStatus
                        ret = FingerprintSensorEx.GetParameters(mhDevice, 2004, paramValue, size);
                        nFakeStatus = byteArrayToInt(paramValue);
                        System.out.println("ret = "+ ret +",nFakeStatus=" + nFakeStatus);
                        if (0 == ret && (byte)(nFakeStatus & 31) != 31)
                        {
                            //textArea.setText("Is a fake finger?\n");
                            System.out.print("Is a fake finger?\n");

                            return;
                        }
                    }
                    OnCatpureOK(imgbuf);
                    OnExtractOK(template, templateLen[0]);
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void OnCatpureOK(byte[] imgBuf)
    {
        try {
            writeBitmap(imgBuf, fpWidth, fpHeight, "fingerprint.bmp");
            //btnImg.setIcon(new ImageIcon(ImageIO.read(new File("fingerprint.bmp"))));
            new ImageIcon(ImageIO.read(new File("fingerprint.bmp")));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void OnExtractOK(byte[] template, int len)
    {
        if(bRegister)
        {
            int[] fid = new int[1];
            int[] score = new int [1];
            int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
            if (ret == 0)
            {
                //textArea.setText("the finger already enroll by " + fid[0] + ",cancel enroll\n");
                System.out.print("the finger already enroll by " + fid[0] + ",cancel enroll\n");
                bRegister = false;
                enroll_idx = 0;
                return;
            }
            if (enroll_idx > 0 && FingerprintSensorEx.DBMatch(mhDB, regtemparray[enroll_idx-1], template) <= 0)
            {
                //textArea.setText("please press the same finger 3 times for the enrollment\n");
                System.out.print("please press the same finger 3 times for the enrollment");
                return ;

            }
            System.arraycopy(template, 0, regtemparray[enroll_idx], 0, 2048);
            enroll_idx++;
            if (enroll_idx == 3) {
                int[] _retLen = new int[1];
                _retLen[0] = 2048;
                byte[] regTemp = new byte[_retLen[0]];

                if (0 == (ret = FingerprintSensorEx.DBMerge(mhDB, regtemparray[0], regtemparray[1], regtemparray[2], regTemp, _retLen)) &&
                        0 == (ret = FingerprintSensorEx.DBAdd(mhDB, iFid, regTemp))) {
                    iFid++;
                    cbRegTemp = _retLen[0];
                    System.arraycopy(regTemp, 0, lastRegTemp, 0, cbRegTemp);
                    //Base64 Template
                    //textArea.setText("enroll succ:\n");
                    System.out.print("enroll succ:\n");
                } else {
                    //textArea.setText("enroll fail, error code=" + ret + "\n");
                    System.out.print("enroll fail, error code=" + ret + "\n");
                }
                bRegister = false;
            } else {
                //textArea.setText("You need to press the " + (3 - enroll_idx) + " times fingerprint\n");
                System.out.print("You need to press the " + (3 - enroll_idx) + " times fingerprint\n");
            }
        }
        else
        {
            if (bIdentify)
            {
                int[] fid = new int[1];
                int[] score = new int [1];
                int ret = FingerprintSensorEx.DBIdentify(mhDB, template, fid, score);
                if (ret == 0)
                {
                    //textArea.setText("Identify succ, fid=" + fid[0] + ",score=" + score[0] +"\n");
                    System.out.print("Identify succ, fid=" + fid[0] + ",score=" + score[0] +"\n");
                }
                else
                {
                    //textArea.setText("Identify fail, errcode=" + ret + "\n");
                    System.out.print("Identify fail, errcode=" + ret + "\n");
                }

            }
            else
            {
                if(cbRegTemp <= 0)
                {
                    //textArea.setText("Please register first!\n");
                    System.out.print("Please register first! ");
                }
                else
                {
                    int ret = FingerprintSensorEx.DBMatch(mhDB, lastRegTemp, template);
                    if(ret > 0)
                    {
                        //textArea.setText("Verify succ, score=" + ret + "\n");
                        System.out.print("Verify succ, score=" + ret + "\n");
                    }
                    else
                    {
                        //textArea.setText("Verify fail, ret=" + ret + "\n");
                        System.out.print("Verify fail, ret=" + ret + "\n");
                    }
                }
            }
        }
    }


}
