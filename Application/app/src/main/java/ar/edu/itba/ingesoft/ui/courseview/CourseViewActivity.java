package ar.edu.itba.ingesoft.ui.courseview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.UsersAdapter;

public class CourseViewActivity extends AppCompatActivity {

    private RecyclerView courseViewTeachersRecyclerView;
    private LinearLayoutManager llm = new LinearLayoutManager(this);
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Course c = getIntent().getParcelableExtra("SelectedCourse");

        //ViewModelProviders.of(this).get(CourseViewViewModel.class);


        courseViewTeachersRecyclerView = findViewById(R.id.courseViewTeachersRecyclerView);
        courseViewTeachersRecyclerView.setLayoutManager(llm);
        adapter = new UsersAdapter(c.getCode());
        courseViewTeachersRecyclerView.setAdapter(adapter);

        ((TextView) findViewById(R.id.courseViewNameTextView)).setText(c.getName());
        ((TextView) findViewById(R.id.courseViewExtraTextView1)).setText(c.getCode());

    }

}
