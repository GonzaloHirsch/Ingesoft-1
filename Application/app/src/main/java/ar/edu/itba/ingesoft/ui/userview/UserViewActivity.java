package ar.edu.itba.ingesoft.ui.userview;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.TextView;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.UserCoursesAdapter;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.UsersAdapter;

public class UserViewActivity extends AppCompatActivity {

    private RecyclerView courseViewTeachersRecyclerView;
    private LinearLayoutManager llm = new LinearLayoutManager(this);
    private UserCoursesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        User u = getIntent().getParcelableExtra("SelectedUser");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(u.getName());
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseViewTeachersRecyclerView = findViewById(R.id.userViewRecyclerView);
        courseViewTeachersRecyclerView.setLayoutManager(llm);
        adapter = new UserCoursesAdapter(u.getCourses());
        courseViewTeachersRecyclerView.setAdapter(adapter);

        ((TextView) findViewById(R.id.userViewNameTextView)).setText(u.getName());
        ((TextView) findViewById(R.id.userViewExtraTextView1)).setText(u.getUniversidad());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
