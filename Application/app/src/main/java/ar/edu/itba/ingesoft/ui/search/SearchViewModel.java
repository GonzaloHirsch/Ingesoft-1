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

    //private MutableLiveData<List<Map.Entry<Course, List<User>>>> displayedData = new MutableLiveData<>();
    private MutableLiveData<List<Course>> displayedData = new MutableLiveData<>();
    private DatabaseConnection dbc = new DatabaseConnection();
    private MutableLiveData<Boolean> loading = new MutableLiveData<>();

    public SearchViewModel() {}

    public MutableLiveData<List<Course>> getDisplayedData() {

        if(displayedData.getValue()==null){
            loading.postValue(true);
            Log.v("SearchViewModel", "MutableLiveDataCreation");
            dbc.GetAllCourses(new OnCourseEventListener() {
                @Override
                public void onCourseRetrieved(Course course) {                }

                @Override
                public void onCoursesRetrieved(List<Course> courses) {
                    displayedData.postValue(courses);
                    loading.postValue(false);
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