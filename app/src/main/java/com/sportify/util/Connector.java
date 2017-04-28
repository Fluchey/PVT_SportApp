package com.sportify.util;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Maja on 2017-04-14.
 */

public class Connector {
    public static String[] connect(String URL, String method, String params, String token) {
        if (token == null) token = "";
        HttpURLConnection connection = null;
        String[] responseFromRest = new String[3];

        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.setRequestProperty("Authorization", token);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(params);
            osw.flush();
            osw.close();

            BufferedReader br;
            if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            } else {
                br = new BufferedReader(new InputStreamReader((connection.getErrorStream())));
            }

            StringBuilder out = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                out.append(line);
            }

            Log.d("Out:", out.toString());

            JSONObject jsonObject = new JSONObject(out.toString());
            responseFromRest[0] = jsonObject.getString("body");
            responseFromRest[1] = "" + connection.getResponseCode();

            try {
                responseFromRest[2] = jsonObject.getString("command");
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return responseFromRest;
    }
}
