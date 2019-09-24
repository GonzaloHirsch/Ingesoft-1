package ar.edu.itba.ingesoft.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Map.Entry<Course, List<User>>>> displayedData = new MutableLiveData<>();
    private DatabaseConnection dbc = new DatabaseConnection();


    public SearchViewModel() {
    }

    public MutableLiveData<List<Map.Entry<Course, List<User>>>> getDisplayedData() {

        if(displayedData.getValue()==null){

            Log.v("SearchViewModel", "MutableLiveDataCreation");
            displayedData.setValue(new ArrayList<Map.Entry<Course, List<User>>>());
            dbc.GetTeachersPerCourse(new OnCourseEventListener() {
                @Override
                public void onCourseRetrieved(Course course) {

                }

                @Override
                public void onCoursesRetrieved(List<Course> courses) {

                }

                @Override
                public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {

                }

                @Override
                public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {
                    Log.v("SearchViewModel", "Retrieved sth?");


                    for(Map.Entry<Course, List<User>> e : drToUser.entrySet()){
                        Log.v("SearchViewModel", "Retrieved Teachers" + e.getKey().getCode() + " ");
                    }
                }
            });
        }

        //todo test
        Map<Course,List<User>> list = new HashMap<>();
        Course c = new Course();
        c.setCode("code");
        c.setName("name");
        User u = new User();
        u.setName("i");
        u.setProfessor(true);
        u.setMail("i@mail.ru");
        u.setSurname("q");
        Universidad ud = new Universidad("Itba");
        u.setUniversidad(ud);
        List<User> l = new ArrayList<>();
        l.add(u);
        list.put(c, l);

        displayedData.setValue(new ArrayList<>(list.entrySet()));

        return displayedData;
    }
}