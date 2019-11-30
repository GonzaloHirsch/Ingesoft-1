package ar.edu.itba.ingesoft.Services;

import android.app.PendingIntent;
import android.content.Intent;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Set;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import ar.edu.itba.ingesoft.Firebase.Authenticator;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.chats.ChatMessagesActivity;

public class NotificationService extends FirebaseMessagingService {
    public static final String TAG = "NOTIFICATION SERVICE";

    public NotificationService() {
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        //sendRegistrationToServer(token);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        FirebaseUser fu = new Authenticator().getSignedInUser();

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            Set<String> a = data.keySet();

            if (!data.get("email").equals(fu.getEmail())){

                Intent intent = new Intent(getApplicationContext(), ChatMessagesActivity.class);
                intent.putExtra(MainActivity.CHAT_ID_EXTRA, data.get("id"));
                intent.putExtra(MainActivity.CHAT_RECIPIENT_NAME_EXTRA, data.get("receiver_name"));
                intent.putExtra(MainActivity.CHAT_RECIPIENT_EXTRA, data.get("receiver_email"));
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "MSG")
                        .setSmallIcon(R.drawable.ic_send_black_24dp)
                        .setContentTitle(data.get("name"))
                        .setContentText(data.get("message"))
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(data.get("message")))
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setContentIntent(pendingIntent);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

                notificationManager.notify(1, builder.build());
            }
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

}
