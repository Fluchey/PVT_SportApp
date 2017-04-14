package util;

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
    public static String[] connect(String URL, String method, String params) {
        HttpURLConnection connection = null;
        String[] test = new String[2];

        try {
            URL url = new URL(URL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
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

            JSONObject jsonObject = new JSONObject(out.toString());
            test[0] = jsonObject.getString("body");
            test[1] = "" + connection.getResponseCode();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return test;
    }
}
