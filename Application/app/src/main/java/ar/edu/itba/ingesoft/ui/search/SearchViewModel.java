package ar.edu.itba.ingesoft.ui.search;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.firestore.DocumentReference;

import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.CachedData.CoursesTeachersCache;
import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Firebase.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class SearchViewModel extends ViewModel {

    //private MutableLiveData<List<Map.Entry<Course, List<User>>>> displayedData = new MutableLiveData<>();
    private MutableLiveData<List<Course>> displayedData = new MutableLiveData<>();
    private DatabaseConnection dbc = new DatabaseConnection();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public SearchViewModel() {}

    public MutableLiveData<List<Course>> getDisplayedData(String university) {

        if(displayedData.getValue()==null){
            loading.postValue(true);
            Log.v("SearchViewModel", "MutableLiveDataCreation");
            dbc.GetAllCourses(university, new OnCourseEventListener() {
                @Override
                public void onCourseRetrieved(Course course) {                }

                @Override
                public void onCoursesRetrieved(List<Course> courses) {
                   // List<Course> finalList = CoursesTeachersCache.getCoursesWithTutors(courses);

                    CoursesTeachersCache.setCoursesList(courses);

                    dbc.GetUsers(new OnUserEventListener() {
                        @Override
                        public void onUserRetrieved(User user) {

                        }

                        @Override
                        public void onUsersRetrieved(List<User> users) {
                            CoursesTeachersCache.setUsersList(users);
                            CoursesTeachersCache.generateCourseTeachersHashMap();
                            displayedData.postValue(CoursesTeachersCache.getCoursesWithTutors(courses));
                            loading.postValue(false);
                        }

                        @Override
                        public void onTeachersRetrieved(List<User> teachers) {

                        }
                    });
                }
                @Override
                public void onCoursesReferencesRetrieved(List<DocumentReference> courses) {                }
                @Override
                public void onTeachersPerCourseRetrieved(Map<Course, List<User>> drToUser) {                }
            });
        }
        return displayedData;
    }

    public MutableLiveData<Boolean> getLoading(){
        return loading;
    }
}