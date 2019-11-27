package ar.edu.itba.ingesoft.ui.login.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContinueWithoutSigningInFragment extends Fragment {

    private NavController navController;
    private AutoCompleteTextView universitySpinner;
    private TextInputLayout universityLayout;


    public ContinueWithoutSigningInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_continue_without_signing_in, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);
        this.universitySpinner = view.findViewById(R.id.university_dropdown);
        this.universityLayout = view.findViewById(R.id.continueWithoutSigningInTextInputLayout);
        String[] universities = getResources().getStringArray(R.array.universities);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, universities);
        universitySpinner.setAdapter(adapter);

        view.findViewById(R.id.continueWithoutSigningInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
            }
        });

        //Sign Up TextView
        view.findViewById(R.id.goBackButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    private boolean validateData() {
        boolean status = true;
        if (universitySpinner.getText().toString().isEmpty()){
            status = false;
            this.universityLayout.setError(getString(R.string.error_complete_field));
        } else {
            this.universityLayout.setErrorEnabled(false);
        }
        return status;

    }

}
