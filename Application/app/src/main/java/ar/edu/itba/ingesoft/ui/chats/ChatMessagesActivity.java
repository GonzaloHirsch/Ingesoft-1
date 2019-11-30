package ar.edu.itba.ingesoft.ui.chats;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;

import android.content.Intent;
import android.os.Bundle;

public class ChatMessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_messages_activity);

        Intent intent = getIntent();

        // Parse the chat id from the intent
        String chatID = intent.getStringExtra(MainActivity.CHAT_ID_EXTRA);
        String chatRecipient = intent.getStringExtra(MainActivity.CHAT_RECIPIENT_EXTRA);
        String chatRecipientName = intent.getStringExtra(MainActivity.CHAT_RECIPIENT_NAME_EXTRA);

        // Setting the support for the up button
        ActionBar ab = getSupportActionBar();
        if (ab != null){
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(chatRecipientName);
        }

        // Generate the instance of the fragment
        ChatMessagesFragment cmf = ChatMessagesFragment.newInstance();

        // Set the chat id in the fragment
        cmf.setChatID(chatID);
        // Set the recipient in the fragment
        cmf.setRecipient(chatRecipient, chatRecipientName);

        // Load the fragment
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, cmf)
                    .commitNow();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
