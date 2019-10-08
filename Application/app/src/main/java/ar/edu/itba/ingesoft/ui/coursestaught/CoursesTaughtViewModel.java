package ar.edu.itba.ingesoft.ui.coursestaught;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;

import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;

public class CoursesTaughtViewModel extends ViewModel {

    private MutableLiveData<List<Course>> courses = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String email;
    private DatabaseConnection dbc;

    public CoursesTaughtViewModel(){
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        dbc = new DatabaseConnection();
    }

    public MutableLiveData<List<Course>> getCourses() {

        if(courses.getValue()==null){
            dbc.GetAllCourses(new OnCourseEventListener() {
                @Override
                public void onCourseRetrieved(Course course) {

                }

                @Override
                public void onCoursesRetrieved(List<Course> courss) {
                    courses.postValue(courss);
                }

                @Override
                public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {

                }

                @Override
                public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {

                }
            });
        }
        return courses;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }
}
