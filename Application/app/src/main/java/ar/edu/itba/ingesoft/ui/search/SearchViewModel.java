package ar.edu.itba.ingesoft.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.utils.Pair;

public class SearchViewModel extends ViewModel {

    private MutableLiveData<List<Pair<Course, List<User>>>> displayedData = new MutableLiveData<>();

    public SearchViewModel() {
    }

    public MutableLiveData<List<Pair<Course, List<User>>>> getDisplayedData() {
        if(displayedData.getValue()==null){
            List<Pair<Course, List<User>>> aux = new ArrayList<Pair<Course, List<User>>>();
            //todo  get list of  courses taught by at least one student
        }

        return displayedData;
    }
}