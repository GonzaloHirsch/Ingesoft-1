package ar.edu.itba.ingesoft.ui.coursestaught;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnListContentUpdatedListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.AddCourseAdapter;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.CoursesTaughtAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCourseFragment extends Fragment implements OnListContentUpdatedListener<String>, OnSelectionModeListener {


    CoursesTaughtViewModel viewModel;
    AddCourseAdapter addCourseAdapter;
    LinearLayoutManager linearLayoutManager;
    Button addCourseButton;


    public AddCourseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_course, container, false);



        viewModel = (CoursesTaughtViewModel) ViewModelProviders.of(getActivity()).get(CoursesTaughtViewModel.class);

        //RecyclerView
        RecyclerView rV = root.findViewById(R.id.addCourseRecyclerView);
        addCourseAdapter = viewModel.getAddCourseAdapterLiveData(this).getValue();
        rV.setAdapter(addCourseAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rV.setLayoutManager(linearLayoutManager);

        viewModel.getCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                addCourseAdapter.update(courses);
            }
        });

        addCourseButton = root.findViewById(R.id.addCourseButton);
        addCourseButton.setEnabled(false);
        addCourseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCourseAdapter.addSelectedCourses();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        addCourseAdapter.clearSelection();
    }

    @Override
    public void onSelectionModeEnabled() {
        addCourseButton.setEnabled(true);
    }

    @Override
    public void onSelectionModeDisabled() {
        addCourseButton.setEnabled(false);
    }

    @Override
    public void onContentUpdated(List<String> newList) {
        viewModel.getUser().getValue().getCourses().addAll(newList);
        (new DatabaseConnection()).UpdateCourses(FirebaseAuth.getInstance().getCurrentUser().getEmail(), viewModel.getUser().getValue().getCourses());
        Navigation.findNavController(getActivity(), R.id.coursesTaughtNavHost).popBackStack();
    }


}
