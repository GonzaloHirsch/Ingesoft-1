package ar.edu.itba.ingesoft.ui.courseview;

import android.content.Intent;
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
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.UserCoursesAdapter;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.UsersAdapter;
import ar.edu.itba.ingesoft.ui.userview.UserViewActivity;

public class CourseViewActivity extends AppCompatActivity {

    private RecyclerView courseViewTeachersRecyclerView;
    private LinearLayoutManager llm = new LinearLayoutManager(this);
    private UsersAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_view);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Course c = getIntent().getParcelableExtra("SelectedCourse");

        //ViewModelProviders.of(this).get(CourseViewViewModel.class);

        getSupportActionBar().setTitle(c.getCode() + " - " + c.getName());
        courseViewTeachersRecyclerView = findViewById(R.id.courseViewTeachersRecyclerView);
        courseViewTeachersRecyclerView.setLayoutManager(llm);
        adapter = new UsersAdapter(c.getCode(), new UsersAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(User u) {
                Intent intent = new Intent(getApplicationContext(), UserViewActivity.class);
                intent.putExtra("SelectedUser", u);
                startActivity(intent);
            }
        });
        courseViewTeachersRecyclerView.setAdapter(adapter);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
