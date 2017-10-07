package in.codingeek.util;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessageUtil {

    public static final String access_token = "EAAGY3lJdhysBAIW69vQFMzZC56KANwJXVoURwdZB3wMZBZCwtOmt9So0EvPgNQortFnLbRF5BtfFlC2mTky0YkHZBrHZBesCZCErkPR93CaOc3NO6G5d1gLc0EkUiHBZBgBIflCouK5ZACneuJnkwT0ZClCl7km9LZBRNjA6UuGAtCmoQZDZD";

    public static void sendTextResponse(JSONObject query) {
        String baseUrl = "https://graph.facebook.com/v2.6/me/messages?access_token=" + access_token;
        String headerKeys[] = {"Content-Type"};
        String headerValues[] = {"application/json"};

        String message = createTextMessage(query);
        HttpUtil.makePost(baseUrl, headerKeys, headerValues, message);
    }

    public static void sendQuickResponse(JSONObject query) {
        String baseUrl = "https://graph.facebook.com/v2.6/me/messages?access_token=" + access_token;
        String headerKeys[] = {"Content-Type"};
        String headerValues[] = {"application/json"};

        String message = createQuickReply(query);
        HttpUtil.makePost(baseUrl, headerKeys, headerValues, message);
    }

    private static String createTextMessage(JSONObject query) {

        if(query.has("payload")) {
            System.out.println("Received payload object: " + query.getString("payload"));
        }
        JSONObject response = new JSONObject();
        JSONObject recipient = new JSONObject();
        recipient.put("id", query.getString("senderId"));

        JSONObject message = new JSONObject();
        message.put("text", "A successful man is one who makes more money than his wife can spend.");

        response.put("recipient", recipient);
        response.put("message", message);

        return response.toString();
    }

    private static String createQuickReply(JSONObject query) {
        JSONObject response = new JSONObject();
        JSONObject recipient = new JSONObject();
        recipient.put("id", query.getString("senderId"));

        JSONObject message = new JSONObject();
        message.put("text", "Rate the joke to get next joke");

        JSONArray quickReplies = new JSONArray();
        JSONObject quickReply = new JSONObject();

        JSONObject payload = new JSONObject();
        payload.put("id", "1");
        payload.put("action", "like");

        quickReply.put("content_type", "text");
        quickReply.put("title", "Like");
        quickReply.put("image_url", "https://vignette2.wikia.nocookie.net/thelegend-of-zelda-fanon/images/f/fb/%28y%29.png/revision/latest?cb=20130313002640&path-prefix=es");
        quickReply.put("payload", payload.toString());
        quickReplies.put(quickReply);

        payload = new JSONObject();
        payload.put("id", "1");
        payload.put("action", "dislike");

        quickReply = new JSONObject();

        quickReply.put("content_type", "text");
        quickReply.put("title", "Dislike");
        quickReply.put("image_url", "http://i1.wp.com/www.notforhiremedia.org/wp-content/uploads/2016/03/Facebook-thumbs-down-670.png");
        quickReply.put("payload", payload.toString());
        quickReplies.put(quickReply);

        message.put("quick_replies", quickReplies);

        response.put("recipient", recipient);
        response.put("message", message);

        return response.toString();
    }
}
