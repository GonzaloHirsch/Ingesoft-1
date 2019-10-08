package ar.edu.itba.ingesoft.ui.coursestaught;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.api.Distribution;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.CoursesTaughtAdapter;

public class CoursesTaughtActivity extends AppCompatActivity {

    CoursesTaughtViewModel viewModel;
    RecyclerView coursesTaughtRecyclerView;
    CoursesTaughtAdapter coursesTaughtAdapter = new CoursesTaughtAdapter();
    LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_taught);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        RecyclerView rV = findViewById(R.id.coursesTaughtRecyclerView);
        rV.setAdapter(coursesTaughtAdapter);
        linearLayoutManager = new LinearLayoutManager(this);
        rV.setLayoutManager(linearLayoutManager);


        //guardo el User que retrievee en la MainActivity
        viewModel = (CoursesTaughtViewModel) ViewModelProviders.of(this).get(CoursesTaughtViewModel.class);
        viewModel.getUser().setValue(getIntent().getExtras().getParcelable(getString(R.string.user_parcel)));
        //Log.v("Courses Taught", us.getCourses().get(0));

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
