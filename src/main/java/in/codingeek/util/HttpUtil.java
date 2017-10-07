package in.codingeek.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {

    public static void makePost(String baseUrl, String[] headerKeys, String[] headerValues, String body) {
        StringBuffer response = null;
        HttpURLConnection connection = null;
        BufferedReader in = null;

        try {
            URL obj = new URL(baseUrl);
            connection = (HttpURLConnection) obj.openConnection();

            connection.setRequestMethod("POST");

            if(headerKeys != null && headerValues != null) {
                for(int i=0; i<headerKeys.length;i++) {
                    connection.setRequestProperty(headerKeys[i], headerValues[i]);
                }
            }

            connection.setDoOutput(true);

            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(body);
            wr.flush();
            wr.close();

            connection.getResponseCode();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
