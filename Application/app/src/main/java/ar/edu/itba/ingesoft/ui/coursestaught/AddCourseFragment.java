package ar.edu.itba.ingesoft.ui.coursestaught;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ar.edu.itba.ingesoft.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourseFragment extends Fragment {


    public AddCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_course, container, false);

        return root;
    }

}
