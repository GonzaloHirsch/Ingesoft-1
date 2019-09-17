package ar.edu.itba.ingesoft.ui.login;

import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        updateUI(new Authenticator().getSignedInUser());
    }

    private void updateUI(FirebaseUser fu){
        if (fu != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
