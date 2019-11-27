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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import ar.edu.itba.ingesoft.MainActivity;
import ar.edu.itba.ingesoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContinueWithoutSigningInFragment extends Fragment {

    private NavController navController;
    private Spinner universitySpinner;


    public ContinueWithoutSigningInFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_continue_without_signing_in, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);
        this.universitySpinner = view.findViewById(R.id.signUpUniversitySpinner);
        String[] universities = getResources().getStringArray(
                R.array.universities);

        ArrayAdapter<CharSequence> spinnerArrayAdapter = new ArrayAdapter<CharSequence>(
                getContext(), android.R.layout.simple_spinner_dropdown_item, universities){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0){
                    // Set the hint text color gray
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };
        AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0)
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.GRAY);
                else
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        universitySpinner.setAdapter(spinnerArrayAdapter);
        universitySpinner.setOnItemSelectedListener(spinnerListener);


        view.findViewById(R.id.continueWithoutSigningInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
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

}
