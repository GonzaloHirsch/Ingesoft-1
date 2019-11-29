package ar.edu.itba.ingesoft.ui.login.fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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
    private String[] universities;

    private AutoCompleteTextView univ_select;

    public ContinueWithoutSigningInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_continue_without_signing_in, container, false);

        navController = Navigation.findNavController(getActivity(), R.id.login_navHostFragment);

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



        univ_select = view.findViewById(R.id.univ_cont_with_sign_up);
        universities = getResources().getStringArray(R.array.universities);
        ArrayAdapter<String> univ_adapter = new ArrayAdapter<>(getContext(), R.layout.dropdown_menu_popup_item, universities);
        univ_select.setAdapter(univ_adapter);

        SharedPreferences sharedPreferences = getContext().getSharedPreferences(MainActivity.SP, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(MainActivity.UNIV_SP, univ_select.getText().toString());




        return view;
    }

}
