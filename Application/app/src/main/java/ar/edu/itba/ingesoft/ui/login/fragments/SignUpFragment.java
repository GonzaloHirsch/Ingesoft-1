package ar.edu.itba.ingesoft.ui.login.fragments;


import android.content.Intent;
import android.graphics.Color;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

import ar.edu.itba.ingesoft.Authentication.Authenticator;
import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.login.LoginActivity;
import ar.edu.itba.ingesoft.utils.Validations;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    public static final String TAG = "SignUpFragment";

    private TextView nameTV;
    private TextView surnameTV;
    private AutoCompleteTextView universityDropdown;
    private TextView emailTV;
    private TextView passwordTV;
    private TextView repeatPasswordTV;
    private TextView errorTV;

    private TextInputLayout nameLayout;
    private TextInputLayout surnameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout universityLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout repeatPasswordLayout;

    private NavController navController;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);

        this.nameTV = view.findViewById(R.id.signUpNameEditText);
        this.surnameTV = view.findViewById(R.id.signUpSurnameEditText);
        this.universityDropdown = view.findViewById(R.id.outlined_exposed_dropdown);
        this.emailTV = view.findViewById(R.id.signUpMailEditText);
        this.passwordTV = view.findViewById(R.id.signUpPassEditText);
        this.repeatPasswordTV = view.findViewById(R.id.signUpRepeatPassEditText);
        this.errorTV = view.findViewById(R.id.errorLabel);

        this.nameLayout = view.findViewById(R.id.signUpNameTextInputLayout);
        this.surnameLayout = view.findViewById(R.id.signUpSurnameTextInputLayout);
        this.emailLayout = view.findViewById(R.id.signUpMailTextInputLayout);
        this.universityLayout = view.findViewById(R.id.signUpUniversityTextInputLayout);
        this.passwordLayout = view.findViewById(R.id.signUpPassTextInputLayout);
        this.repeatPasswordLayout = view.findViewById(R.id.signUpRepeatPassTextInputLayout);

        String[] universities = getResources().getStringArray(R.array.universities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, universities);
        universityDropdown.setAdapter(adapter);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){
                    // Gets the user from the input data
                    User user = controlsToEntity();

                    String mail = user.getMail();
                    String pass = passwordTV.getText().toString();

                    new Authenticator().registerUser(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                AuthResult ar = task.getResult();

                                if (ar != null){
                                    errorTV.setVisibility(View.INVISIBLE);

                                    new DatabaseConnection().InsertUser(user);

                                    Intent intent = new Intent(getContext(), LoginActivity.class);
                                    startActivity(intent);
                                } else {
                                    showError(R.string.error_unknown);
                                }
                            } else {
                                try
                                {
                                    throw task.getException();
                                }
                                // if user enters wrong email.
                                catch (FirebaseAuthWeakPasswordException weakPassword)
                                {
                                    Log.d(TAG, "onComplete: weak_password");

                                    passwordLayout.setError(getString(R.string.error_password_weak));
                                    repeatPasswordLayout.setError(getString(R.string.error_password_weak));
                                }
                                // if user enters wrong password.
                                catch (FirebaseAuthInvalidCredentialsException malformedEmail)
                                {
                                    Log.d(TAG, "onComplete: malformed_email");

                                    emailLayout.setError(getString(R.string.error_email_invalid));
                                }
                                catch (FirebaseAuthUserCollisionException existEmail)
                                {
                                    Log.d(TAG, "onComplete: exist_email");

                                    emailLayout.setError(getString(R.string.error_email_used));
                                }
                                catch (Exception e)
                                {
                                    Log.d(TAG, "onComplete: " + e.getMessage());

                                    showError(R.string.error_unknown);
                                }
                            }
                        }
                    });
                }
            }
        });

        view.findViewById(R.id.goBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private void showError(int errorID){
        errorTV.setText(errorID);
        errorTV.setVisibility(View.VISIBLE);
    }

    private boolean validateData(){

        boolean status = true;

        if (passwordTV.getText().toString().isEmpty()){
            status = false;
            this.passwordLayout.setError(getString(R.string.error_complete_field));
        }

        if (repeatPasswordTV.getText().toString().isEmpty()){
            status = false;
            this.repeatPasswordLayout.setError(getString(R.string.error_complete_field));
        }

        if (!passwordTV.getText().toString().isEmpty() && !repeatPasswordTV.getText().toString().isEmpty()){
            if (!passwordTV.getText().toString().equals(repeatPasswordTV.getText().toString())) {
                status = false;
                this.passwordLayout.setError(getString(R.string.error_password_match));
                this.repeatPasswordLayout.setError(getString(R.string.error_password_match));
            } else if(passwordTV.getText().toString().length() > 15 || passwordTV.getText().toString().length() < 8 ){
                status = false;
                this.passwordLayout.setError(getString(R.string.error_password_length));
                this.repeatPasswordLayout.setError(getString(R.string.error_password_length));
            } else if (!Validations.ValidatePassword(passwordTV.getText().toString())){
                status = false;
                this.passwordLayout.setError(getString(R.string.error_password_invalid));
                this.repeatPasswordLayout.setError(getString(R.string.error_password_invalid));
            } else {
                this.passwordLayout.setErrorEnabled(false);
                this.repeatPasswordLayout.setErrorEnabled(false);
            }
        }

        if (nameTV.getText().toString().isEmpty()){
            status = false;
            this.nameLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.nameLayout.setErrorEnabled(false);
        }

        if (surnameTV.getText().toString().isEmpty()){
            status = false;
            this.surnameLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.surnameLayout.setErrorEnabled(false);
        }

        if (emailTV.getText().toString().isEmpty()){
            status = false;
            this.emailLayout.setError(getString(R.string.error_complete_field));
        } else if (!Validations.ValidateEmail(emailTV.getText().toString())){
            status = false;
            this.emailLayout.setError(getString(R.string.error_email_invalid));
        } else {
            this.emailLayout.setErrorEnabled(false);
        }

        if (universityDropdown.getText().toString().isEmpty()){
            status = false;
            this.universityLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.universityLayout.setErrorEnabled(false);
        }



        return status;
    }

    private User controlsToEntity(){
        User user = new User();
        user.setMail(emailTV.getText().toString());
        user.setName(nameTV.getText().toString() + " " + surnameTV.getText().toString());
        user.setProfessor(false);
        user.setUniversidad(new Universidad(universityDropdown.getText().toString()));

        return user;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
