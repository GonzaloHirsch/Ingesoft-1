package ar.edu.itba.ingesoft.ui.login.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

import ar.edu.itba.ingesoft.Firebase.AnalyticsConnection;
import ar.edu.itba.ingesoft.Firebase.Authenticator;
import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Firebase.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.utils.Validations;

import static android.content.Context.MODE_PRIVATE;


public class LoginFragmentMain extends Fragment {

    public static final String TAG = "LogInFragment";
    private static final int RC_SIGN_IN_GOOGLE = 7;


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

        //Regular Login with email and password
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

        view.findViewById(R.id.google_signin_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Authenticator authenticator = new Authenticator();
                Intent intent = authenticator.generateGoogleClient(getActivity()).getSignInIntent();
                startActivityForResult(intent, RC_SIGN_IN_GOOGLE);
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
                new Authenticator().signInAnonymousUser().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            AnalyticsConnection.LogEvent_SignUp(AnalyticsConnection.SIGNUP_ANON);
                            Intent intent = new Intent(getContext(), MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            if (getActivity() != null)
                                getActivity().finish();
                            Log.e(TAG, "Logged user anonymously");
                        } else {
                            Log.e(TAG, task.getException().getMessage());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "An error occurred", Toast.LENGTH_SHORT).show();
                        Log.e(TAG, e.getMessage());
                    }
                });
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN_GOOGLE){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account);
            } catch (ApiException e){
                Log.d(TAG, "auth:get_task_api_exception", task.getException());
            }
        }
    }

    private void firebaseAuth(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG,"sign_in_with_google:success");
                    FirebaseUser firebaseUser = new Authenticator().getSignedInUser();
                    AnalyticsConnection.LogEvent_SignUp(AnalyticsConnection.SIGNUP_GOOGLE);

                    String univ = "N/a";
                    if (getContext() != null){
                        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
                        univ = sharedPreferences.getString(MainActivity.UNIV_SP, "");
                    }

                    String finalUniv = univ;
                    new DatabaseConnection().GetUser(firebaseUser.getEmail(), new OnUserEventListener() {
                        @Override
                        public void onUserRetrieved(User user) {
                            if (user == null){
                                User newUser = new User(firebaseUser.getDisplayName(), firebaseUser.getEmail(), finalUniv);
                                new DatabaseConnection().InsertUser(newUser);
                            } else {
                                // Updating shared prefs to the selected user university
                                SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SP, MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(MainActivity.UNIV_SP, user.getUniversidad());
                                editor.apply();
                            }
                        }

                        @Override
                        public void onUsersRetrieved(List<User> users) {
                            //
                        }

                        @Override
                        public void onTeachersRetrieved(List<User> teachers) {
                            //
                        }
                    });

                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.w(TAG, "sign_in_with_google:failure", task.getException());
                }
            }
        });
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

        if(emailTV.getText().toString().isEmpty()) {
            status = false;
            this.emailLayout.setError(getString(R.string.error_complete_field));
        } else if(!Validations.ValidateEmail(emailTV.getText().toString())){
            status = false;
            this.emailLayout.setError(getString(R.string.error_email_invalid));
        } else {
            this.emailLayout.setErrorEnabled(false);
        }

        return status;
    }
}
