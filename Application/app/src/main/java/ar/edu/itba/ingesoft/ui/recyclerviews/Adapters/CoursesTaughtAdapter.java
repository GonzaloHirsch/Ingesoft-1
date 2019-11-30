package ar.edu.itba.ingesoft.ui.recyclerviews.Adapters;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Firebase.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnCoursesTaughtEventListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnListContentUpdatedListener;
import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;


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
