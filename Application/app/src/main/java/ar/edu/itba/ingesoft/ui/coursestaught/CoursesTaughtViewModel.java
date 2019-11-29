package ar.edu.itba.ingesoft.ui.coursestaught;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;

import ar.edu.itba.ingesoft.Interfaces.Adapters.OnSelectionModeListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.AddCourseAdapter;
import ar.edu.itba.ingesoft.ui.recyclerviews.Adapters.CoursesTaughtAdapter;

public class CoursesTaughtViewModel extends ViewModel {

    private MutableLiveData<List<Course>> courses = new MutableLiveData<>();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private MutableLiveData<CoursesTaughtAdapter> coursesTaughtAdapterLiveData = new MutableLiveData<>();
    private MutableLiveData<AddCourseAdapter> addCourseAdapterMutableLiveData = new MutableLiveData<>();
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

    public MutableLiveData<CoursesTaughtAdapter> getCoursesTaughtAdapterLiveData(OnSelectionModeListener listener) {
        if(coursesTaughtAdapterLiveData.getValue()==null)
            coursesTaughtAdapterLiveData.setValue(new CoursesTaughtAdapter(listener));
        return coursesTaughtAdapterLiveData;
    }
    public MutableLiveData<AddCourseAdapter> getAddCourseAdapterLiveData(OnSelectionModeListener listener) {
        if(addCourseAdapterMutableLiveData.getValue()==null)
            addCourseAdapterMutableLiveData.setValue(new AddCourseAdapter(listener));
        addCourseAdapterMutableLiveData.getValue().setListener(listener);
        return addCourseAdapterMutableLiveData;
    }
}
