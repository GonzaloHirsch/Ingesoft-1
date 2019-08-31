package ar.edu.itba.ingesoft.ui.login;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.TextView;

import ar.edu.itba.ingesoft.R;

public class LoginActivity extends AppCompatActivity {

    TextView orCreateNAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        orCreateNAccount = findViewById(R.id.createNewAccountTV);
        orCreateNAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View x) {
                Intent intent = new Intent(LoginActivity.this, SignUp.class);
                startActivity(intent);
            }
        });
    }

}
