package com.example.easynotes.quickpay;/**
 * Created by admin on 8/1/18.
 */

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author quickpay
 */
public class Quickpaygateway {

    private final String urlString_ = "https://checkout-test.quickpay.co.ke/chargetoken";
    private final int HTTP_CODE_OK = 200;
    private final String _Securekey;
    private int responseCode;
    private String tnxReference;
    private String orderInfo;
    private int amount;
    private String token;
    private String currency;

    /**
     * Initialize Credential Log in
     * @param key - private merchant key
     */
    public Quickpaygateway(String key) {
        this._Securekey = key;
    }

    /**
     * @param referenceNo
     * @param orderInfo
     * @param amount - Either donated amount or
     * @param token
     * @param currency
     * @return - Response for card transactions in JSON format.
     * @throws Exception
     */
    public JSONObject pack(String referenceNo,
                           String orderInfo,
                           int amount,
                           String token,
                           String currency) throws Exception {
        try {
            if (validate(referenceNo, orderInfo, amount, token, currency)) {
                this.tnxReference = referenceNo;
                this.orderInfo = orderInfo;
                this.amount = amount;
                this.token = token;
                this.currency = currency;

            }
        } catch (Exception ex) {
            throw ex;
        }
        return sendRequest();
    }

    private boolean validate(String referenceNo, String orderInfo, int amount, String token, String currency) throws Exception {
        boolean isvalid = true;
        try {
            if (referenceNo.length() < 0 || referenceNo.length() > 40) {
                errorMsg("Invalid length for field referenceNo. Length=", String.valueOf(referenceNo.length()));
            }
            if (orderInfo.length() < 0 || orderInfo.length() > 34) {
                errorMsg("Invalid length for field referenceNo. ", String.valueOf(referenceNo.length()));
            }

            if (token.length() < 1 || token.length() > 40) {
                errorMsg("Invalid length for field token. ", String.valueOf(token.length()));
            }

            if (!(currency.length() == 3)) {
                errorMsg("Invalid length for field currency. ", String.valueOf(currency.length()));
            }

            if (amount < 1 || amount > 2147483647) {
                errorMsg("Invalid input for amount. ", String.valueOf(amount));
            }

        } catch (Exception ex) {
            isvalid = false;
            throw ex;
        }

        return isvalid;
    }

    /**
     * Errors set up.
     *
     * @param msg
     * @param desc
     */
    private void errorMsg(String msg, String desc) {
        throw new IllegalArgumentException(msg + "  " + desc);
    }

    /**
     * Forwards request to gateway for processing. Generates secure hash
     */
    private JSONObject sendRequest() throws Exception {

        String payLoad = new JSONObject()
                .put("amount", amount)
                .put("userkey", _Securekey)
                .put("currency", currency)
                .put("token", token)
                .put("orderInfo", orderInfo)
                .put("reference", tnxReference).toString();

        return sendHttpPOST(payLoad);
    }

    /**
     * Send HTTP post request.
     *
     * @param payLoad
     * @return
     * @throws Exception
     */
    private JSONObject sendHttpPOST(String payLoad) throws Exception {
        URL url = new URL(urlString_);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod("POST");
        conn.connect();

        System.out.println("Card processing request: \n\t" + payLoad);
        byte[] outputBytes = payLoad.getBytes("UTF-8");
        OutputStream os = conn.getOutputStream();
        os.write(outputBytes);

        HttpURLConnection http_conn = (HttpURLConnection) conn;
        responseCode = http_conn.getResponseCode();

        BufferedReader reader;
        if (responseCode == HTTP_CODE_OK) {
            reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));
        } else {
            reader = new BufferedReader(new InputStreamReader(http_conn.getErrorStream()));
        }
        String response = reader.readLine();
        reader.close();

        return new JSONObject(response);
    }

}