package ar.edu.itba.ingesoft.Firebase;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.HelperClasses.MySingleton;
import ar.edu.itba.ingesoft.MainActivity;

public class MessagingConnection {

    public static final String TAG = "MESSAGING CONNECTION";

    private final String FCM_API = "https://fcm.googleapis.com/fcm/send";
    private final String SERVER_KEY = "key=" + "AAAA07L81s8:APA91bGLUZLJ26AzzipLJ7AFmx7MCVxX4eWh_c8N_OANwRS7FV84zE1uhAYCqfjxpr37hAOcWH3x5cHEslOnwQl8TwsmS6Zpu7un9yFw1E99TTkWM108eTF9zs_fVPeYaiLmN4mbic-J";
    private final String CONTENT_TYPE = "application/json";
    private final String TOPIC = "/topics/";

    private FirebaseMessaging mMessaging = FirebaseMessaging.getInstance();

    public Task<Void> subscribeToChat(String chatID){
        return mMessaging.subscribeToTopic(chatID);
    }

    public void sendMessage(Context ctx, String chatID, String senderName, String senderEmail, String receiverName, String receiverEmail, String content){
        String topic = TOPIC + chatID;

        JSONObject notification = new JSONObject();
        JSONObject notifcationBody = new JSONObject();
        Log.v(TAG, "Creating objects");

        try {
            notifcationBody.put("name", senderName);
            notifcationBody.put("message", content);
            notifcationBody.put("email", senderEmail);
            notifcationBody.put("id", chatID);
            notifcationBody.put("receiver_name", receiverName);
            notifcationBody.put("receiver_email", receiverEmail);

            notification.put("to", topic);
            notification.put("data", notifcationBody);
            Log.v(TAG, "Adding data");
        } catch (JSONException e) {
            Log.e(TAG, "onCreate: " + e.getMessage() );
        }
        sendNotification(ctx, notification);
        Log.v(TAG, "Sent message");
    }

    private void sendNotification(Context ctx, JSONObject notification) {
        Log.v(TAG, "Creating request");
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(FCM_API, notification,
                response -> Log.i(TAG, "onResponse: " + response.toString()),
                error -> Log.i(TAG, "onErrorResponse: Didn't work")){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("Authorization", SERVER_KEY);
                params.put("Content-Type", CONTENT_TYPE);
                Log.v(TAG, "Added headers");
                return params;
            }
        };
        MySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
        Log.v(TAG, "Adding to queue");
    }
}
