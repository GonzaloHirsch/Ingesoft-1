package ar.edu.itba.ingesoft.ui.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Course>> coursesTaughtLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>(true);

    public ProfileViewModel() {

        Log.v("ProfileViewModel", "ViewModel Initialized");

    }

    public MutableLiveData<User> getCurrentUserLiveData() {
        if(currentUserLiveData.getValue() == null){

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            loading.setValue(true);
            DatabaseConnection.GetUser(currentUser.getEmail(), new OnUserEventListener() {
                @Override
                public void onUserRetrieved(User user) {
                    currentUserLiveData.postValue(user);
                    loading.setValue(false);
                    Log.v("ProfileViewModel", "");
                }

                @Override
                public void onUsersRetrieved(List<User> users) {}

                @Override
                public void onTeachersRetrieved(List<User> teachers) {}
            });
        }
        return currentUserLiveData;
    }

    public MutableLiveData<List<Course>> getCourses(){
       if(coursesTaughtLiveData.getValue() == null){

       }
       return coursesTaughtLiveData;
    }

    public MutableLiveData<Boolean> getLoading(){
        return loading;
    }
}