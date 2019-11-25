package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnCoursesTaughtEventListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnListContentUpdatedListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;
import ar.edu.itba.ingesoft.R;
import ar.edu.itba.ingesoft.ui.recyclerviews.diffutil_callbacks.CourseListDiffUtil;


public class CoursesTaughtAdapter extends  CourseListAdapter {

    public CoursesTaughtAdapter(){
        //todo complete/delete?
    }


    public CoursesTaughtAdapter(OnCoursesTaughtEventListener listener){
        this.listener = listener;
    }

    //todo hacer una clase abstracta con este metodo... Ya lo use en SearchCoursesAdapter y el comportamiento es el mismo


    public void deleteSelectedItems(){
        List<Course> newCourses = new ArrayList<>(courses);
        newCourses.removeAll(selectedCourses);
        List<String> courseIds = new ArrayList<>();
        selectedCourses.clear();
        //Este for es necesario para obtener los courseIds
        for(Course c : newCourses){
            courseIds.add(c.getCode());
        }
        clearSelection();
        ((OnSelectionModeListener)listener).onSelectionModeDisabled();
        ((OnListContentUpdatedListener<String>)listener).onContentUpdated(courseIds);

        //update firebase
        (new DatabaseConnection()).UpdateCourses(FirebaseAuth.getInstance().getCurrentUser().getEmail(), courseIds);

        update(newCourses);
        //notifyDataSetChanged();
    }

}
