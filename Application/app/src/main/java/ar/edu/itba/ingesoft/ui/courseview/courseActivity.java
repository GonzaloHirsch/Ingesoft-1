package ar.edu.itba.ingesoft.ui.courseview;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import ar.edu.itba.ingesoft.R;

public class courseActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_recycler_view);

        recyclerView = (RecyclerView) findViewById(R.id.courseInsideRecyclerview);


    }
}
