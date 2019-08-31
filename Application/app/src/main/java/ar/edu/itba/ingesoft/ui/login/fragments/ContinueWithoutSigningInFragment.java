package ar.edu.itba.ingesoft.ui.login.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.itba.ingesoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContinueWithoutSigningInFragment extends Fragment {


    public ContinueWithoutSigningInFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_continue_without_signing_in, container, false);
        return view;
    }

}
