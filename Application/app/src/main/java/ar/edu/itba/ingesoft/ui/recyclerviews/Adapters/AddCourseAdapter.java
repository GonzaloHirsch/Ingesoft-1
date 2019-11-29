package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnCoursesTaughtEventListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnListContentUpdatedListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;

public class AddCourseAdapter extends CourseListAdapter {


    public AddCourseAdapter(OnCoursesTaughtEventListener listener){
        this.listener = listener;
    }

    public void setListener(OnCoursesTaughtEventListener listener){this.listener = listener;}

    public void addSelectedCourses(){
        List<String> courseIds = new ArrayList<>();
        for(Course c : selectedCourses){
                courseIds.add(c.getCode());
        }
        selectedCourses.clear();
        //Este for es necesario para obtener los courseIds

        clearSelection();

        ((OnSelectionModeListener)listener).onSelectionModeDisabled();
        ((OnListContentUpdatedListener<String>)listener).onContentUpdated(courseIds);
    }

}
