package ar.edu.itba.ingesoft.ui.login.fragments;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;

import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.utils.Validations;

/**
 * A simple {@link Fragment} subclass.
 */
public class SignUpFragment extends Fragment {

    private TextView nameTV;
    private TextView surnameTV;
    private TextView universityTV;
    private TextView emailTV;
    private TextView passwordTV;
    private TextView repeatPasswordTV;

    private TextInputLayout nameLayout;
    private TextInputLayout surnameLayout;
    private TextInputLayout universityLayour;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayour;
    private TextInputLayout repeatPasswordLayout;

    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);

        this.nameTV = view.findViewById(R.id.signUpNameEditText);
        this.surnameTV = view.findViewById(R.id.signUpSurnameEditText);
        this.universityTV = view.findViewById(R.id.signUpUniversityEditText);
        this.emailTV = view.findViewById(R.id.signUpMailEditText);
        this.passwordTV = view.findViewById(R.id.signUpPassEditText);
        this.repeatPasswordTV = view.findViewById(R.id.signUpRepeatPassEditText);

        this.nameLayout = view.findViewById(R.id.signUpNameTextInputLayout);
        this.surnameLayout = view.findViewById(R.id.signUpSurnameTextInputLayout);
        this.universityLayour = view.findViewById(R.id.signUpUniversityTextInputLayout);
        this.emailLayout = view.findViewById(R.id.signUpMailTextInputLayout);
        this.passwordLayour = view.findViewById(R.id.signUpPassTextInputLayout);
        this.repeatPasswordLayout = view.findViewById(R.id.signUpRepeatPassTextInputLayout);

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()){

                }
            }
        });

        return view;
    }

    private boolean validateData(){

        boolean status = true;

        if (passwordTV.getText().toString().isEmpty() || repeatPasswordTV.getText().toString().isEmpty()){
            status = false;
        }
        else if (!Validations.ValidatePassword(passwordTV.getText().toString())){
            status = false;
        }

        return status;
    }

//    private User controlsToEntity(){
//
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
