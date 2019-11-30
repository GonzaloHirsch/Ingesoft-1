package ar.edu.itba.ingesoft.ui.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.login.fragments.LoginFragmentMain;

public class LoginActivity extends AppCompatActivity {

    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        navController = Navigation.findNavController(this, R.id.login_navHostFragment);


        SharedPreferences sp = getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
        String univ = sp.getString(MainActivity.UNIV_SP, "");


        if(!univ.equals("")){
            navController.navigate(R.id.action_continueWithoutSigningInFragment_to_loginFragmentMain);
        }








        updateUI(new Authenticator().getSignedInUser());
    }

    private void updateUI(FirebaseUser fu){
        if (fu != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

}
