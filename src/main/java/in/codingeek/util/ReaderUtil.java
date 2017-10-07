package in.codingeek.util;

import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ReaderUtil {

    private static JSONObject parseRequest(HttpServletRequest request) {

        try (InputStream inputStream = request.getInputStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer input = new StringBuffer();

            String line = null;
            while((line = reader.readLine()) != null) {
                input.append(line);
            }
            return new JSONObject(input.toString());
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject getRequestParameters(HttpServletRequest request) {
        JSONObject requestObject = parseRequest(request);
        JSONObject detailsJson = new JSONObject();

        if(requestObject.has("entry")) {
            JSONArray entryArray = requestObject.getJSONArray("entry");
            JSONObject entry = entryArray.getJSONObject(0);

            JSONArray messagingArray = entry.getJSONArray("messaging");
            JSONObject messaging = messagingArray.getJSONObject(0);

            JSONObject sender = messaging.getJSONObject("sender");
            JSONObject recipient = messaging.getJSONObject("recipient");

            String senderId = sender.getString("id");
            String recipientId = recipient.getString("id");

            JSONObject message = messaging.getJSONObject("message");
            String text = message.getString("text");

            if(message.has("quick_reply")) {
                JSONObject quickReply = message.getJSONObject("quick_reply");
                detailsJson.put("payload", quickReply.getString("payload"));
            }

            detailsJson.put("senderId", senderId);
            detailsJson.put("recipient", recipientId);
            detailsJson.put("message", text);

            return detailsJson;
        }
        return null;
    }
}
