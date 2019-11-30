package ar.edu.itba.ingesoft.Firebase;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

public class MessagingConnection {

    public static final String TAG = "MESSAGING CONNECTION";

    private FirebaseMessaging mMessaging = FirebaseMessaging.getInstance();

    public Task<Void> subscribeToChat(String chatID){
        return mMessaging.subscribeToTopic(chatID);
    }

    public void sendMessage(String chatID, String sender, String content){
        RemoteMessage message = new RemoteMessage.Builder(chatID)
                .addData("sender", sender)
                .addData("message", content)
                .build();

        mMessaging.send(message);
        Log.v(TAG, "Sent message");
    }
}
