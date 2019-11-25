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

        // Setting the support for the up button
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null)
            ab.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();

        // Parse the chat id from the intent
        Long chatID = Long.parseLong(intent.getStringExtra(MainActivity.CHAT_ID_EXTRA));

        // Generate the instance of the fragment
        ChatMessagesFragment cmf = ChatMessagesFragment.newInstance();

        // Set the chat id in the fragment
        cmf.setChatID(chatID);

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
