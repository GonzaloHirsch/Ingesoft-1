package ar.edu.itba.ingesoft.ui.coursestaught;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnListContentUpdatedListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.CoursesTaughtAdapter;

public class CoursesTaughtListFragment extends Fragment implements OnSelectionModeListener, OnListContentUpdatedListener<String> {

    NavController navController;
    CoursesTaughtViewModel viewModel;
    RecyclerView coursesTaughtRecyclerView;
    CoursesTaughtAdapter coursesTaughtAdapter;
    LinearLayoutManager linearLayoutManager;
    FloatingActionButton fab;
    Menu actionBarMenu;
    boolean selectionMode = false;

    //Lifecycle methods

    public CoursesTaughtListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root =  inflater.inflate(R.layout.fragment_courses_taught_list, container, false);
        viewModel = (CoursesTaughtViewModel) ViewModelProviders.of(getActivity()).get(CoursesTaughtViewModel.class);


        navController = Navigation.findNavController(getActivity(), R.id.coursesTaughtNavHost);


        //Fab
        fab = getActivity().findViewById(R.id.coursesTaughtAdd);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //coursesTaughtAdapter.clearSelection();
                navController.navigate(R.id.action_coursesTaughtListFragment_to_addCourseFragment);
                fab.setVisibility(View.GONE);
                //getActivity().setTitle(getActivity().getResources().getString(R.string.add_course));
            }
        });

        //RecyclerView
        RecyclerView rV = root.findViewById(R.id.coursesTaughtRecyclerView);
        coursesTaughtAdapter = viewModel.getCoursesTaughtAdapterLiveData(this).getValue();
        rV.setAdapter(coursesTaughtAdapter);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        rV.setLayoutManager(linearLayoutManager);
        rV.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0 || dy < 0 && fab.isShown())
                    fab.hide();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !selectionMode)
                    fab.show();
                super.onScrollStateChanged(recyclerView, newState);
            }
        });


        //guardo el User que retrievee en la MainActivity


        //Log.v("Courses Taught", us.getCourses().get(0));

        //observar cambios en la lista total de cursos
        viewModel.getCourses().observe(this, new Observer<List<Course>>() {
            @Override
            public void onChanged(List<Course> courses) {
                List<Course> aux = new ArrayList<>();
                for(Course c : courses){
                    if(viewModel.getUser().getValue().getCourses().contains(c.getCode()))
                        aux.add(c);
                }
                coursesTaughtAdapter.update(aux);
            }
        });

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(fab.getVisibility()==View.GONE)
            fab.setVisibility(View.VISIBLE);
        //getActivity().setTitle(getActivity().getResources().getString(R.string.courses_taught));
    }

    //End of lifecycle methods

    //ActionBar methods

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        getActivity().getMenuInflater().inflate(R.menu.courses_taught_menu, menu);
        actionBarMenu = menu;
        menu.findItem(R.id.menuItemDelete).setVisible(false);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.menuItemDelete){
            coursesTaughtAdapter.deleteSelectedItems();
        }
        return true;
    }

    //End of ActionBar methods

    @Override
    public void onSelectionModeEnabled() {
        selectionMode=true;
        actionBarMenu.findItem(R.id.menuItemDelete).setVisible(true);
        fab.hide();
    }

    @Override
    public void onSelectionModeDisabled() {
        selectionMode=false;
        actionBarMenu.findItem(R.id.menuItemDelete).setVisible(false);
        fab.show();
    }

    @Override
    public void onContentUpdated(List<String> newList) {
        viewModel.getUser().getValue().setCourses(newList);
    }
}