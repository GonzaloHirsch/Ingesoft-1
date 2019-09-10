package ar.edu.itba.ingesoft.ui.login.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;


public class LoginFragmentMain extends Fragment {

    public static final String TAG = "LogInFragment";


    private TextView createAccountTextView;
    private TextView continueWithoutLoggingInTV;
    private TextView emailTV;
    private TextView passwordTV;
    private TextView errorTV;

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;

    private NavController navController;

    public static LoginFragmentMain newInstance() {
        return new LoginFragmentMain();
    }

    public LoginFragmentMain() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment_main, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);

        this.passwordTV = view.findViewById(R.id.signInPassEditText);
        this.emailTV = view.findViewById(R.id.signInUsernameEditText);
        this.errorTV = view.findViewById(R.id.errorLabel);

        this.emailLayout = view.findViewById(R.id.signInUsernameTextInputLayout);
        this.passwordLayout = view.findViewById(R.id.signInPasswordTextInputLayout);

        view.findViewById(R.id.regular_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateData()) {
                    new Authenticator().signInUser(emailTV.getText().toString(), passwordTV.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "signInWithEmail:success");
                                AuthResult ar = task.getResult();

                                if (ar != null) {
                                    errorTV.setVisibility(View.INVISIBLE);

                                    Intent intent = new Intent(getContext(), MainActivity.class);
                                    startActivity(intent);
                                } else {
                                    showError(R.string.error_unknown);
                                }
                            } else {
                                try {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthInvalidUserException invalidUser) {
                                    Log.d(TAG, "onComplete: no_account_email");

                                    showError(R.string.error_email_not_registered);
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException wrongPassword) {
                                    Log.d(TAG, "onComplete: wrong_password");

                                    showError(R.string.error_password_incorrect);
                                } catch (Exception e) {
                                    Log.d(TAG, "signInWithEmail:failure", task.getException());

                                    showError(R.string.error_unknown);
                                }
                            }
                        }
                    });
                }
            }
        });

        //Sign Up TextView
        createAccountTextView = view.findViewById(R.id.createNewAccountTV);
        createAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragmentMain_to_signUpFragment);
            }
        });


        //Continue Without Logging In Button
        continueWithoutLoggingInTV = view.findViewById(R.id.proceedWithoutLoginTV);
        continueWithoutLoggingInTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_loginFragmentMain_to_continueWithoutSigningInFragment);
            }
        });

        return view;
    }



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    private void showError(int errorID){
        errorTV.setText(errorID);
        errorTV.setVisibility(View.VISIBLE);
    }

    private boolean validateData() {

        boolean status = true;

        if (passwordTV.getText().toString().isEmpty()) {
            status = false;
            this.passwordLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.passwordLayout.setErrorEnabled(false);
        }

        if(emailTV.getText().toString().isEmpty()){
            status = false;
            this.emailLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.emailLayout.setErrorEnabled(false);
        }

        return status;
    }
}
