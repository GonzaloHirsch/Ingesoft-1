package ar.edu.itba.ingesoft.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import ar.edu.itba.ingesoft.Firebase.AnalyticsConnection;
import ar.edu.itba.ingesoft.Firebase.Authenticator;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.login.fragments.LoginFragmentMain;

public class LoginActivity extends AppCompatActivity {

    private NavController navController;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        if (isNetworkAvailable()){
            AnalyticsConnection.GenerateInstance(this);

            navController = Navigation.findNavController(this, R.id.login_navHostFragment);

            // Check if there is a university in the SP
            SharedPreferences sp = getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
            String univ = sp.getString(MainActivity.UNIV_SP, "");

            // Navigate to the log in window
            if(univ != null && !univ.equals("")){
                // First check for the logged user
                updateUI(new Authenticator().getSignedInUser());
                navController.navigate(R.id.action_continueWithoutSigningInFragment_to_loginFragmentMain);
            }
        } else {
            Toast.makeText(this, "Network connection needed", Toast.LENGTH_LONG).show();
        }
    }

    private void updateUI(FirebaseUser fu){
        if (fu != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
