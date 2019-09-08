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

import ar.edu.itba.ingesoft.R;


public class LoginFragmentMain extends Fragment {

    private TextView createAccountTextView;
    private TextView continueWithoutLoggingInTV;

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
        //TODO views y todo eso

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);

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
}
