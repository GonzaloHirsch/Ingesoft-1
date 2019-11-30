package ar.edu.itba.ingesoft.ui.profile;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import ar.edu.itba.ingesoft.Classes.Course;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnCourseEventListener;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;
import ar.edu.itba.ingesoft.MainActivity;

import static android.content.Context.MODE_PRIVATE;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<User> currentUserLiveData = new MutableLiveData<>();
    private MutableLiveData<String> coursesTaughtLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>(true);
    private String university = "";

    public ProfileViewModel() {

        Log.v("ProfileViewModel", "ViewModel Initialized");

    }

    //todo mover a DatabaseConnection

    public MutableLiveData<User> getCurrentUserLiveData() {
        if(currentUserLiveData.getValue() == null){

            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

            if (currentUser != null && !currentUser.isAnonymous()){
                FirebaseFirestore.getInstance().collection("Users").document(currentUser.getEmail()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                        loading.setValue(true);
                        new DatabaseConnection().GetUser(currentUser.getEmail(), new OnUserEventListener() {
                            @Override
                            public void onUserRetrieved(User user) {
                                currentUserLiveData.postValue(user);
                                Log.v("ProfileViewModel", "");
                    /*
                    List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    for(DocumentReference documentReference : user.getCourses()){
                        Task<DocumentSnapshot> documentSnapshotTask = documentReference.get();
                        tasks.add(documentSnapshotTask);
                    }
                    Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                        @Override
                        public void onSuccess(List<Object> objects) {
                            //todo get courses
                    }});
                     */
                                loading.setValue(false);
                            }
                            @Override
                            public void onUsersRetrieved(List<User> users) {}

                            @Override
                            public void onTeachersRetrieved(List<User> teachers) {}
                        });
                    }
                });
            } else {
                currentUserLiveData.postValue(User.GenerateAnonymousUser(this.university));
                loading.setValue(false);
            }
        }
        return currentUserLiveData;
    }

    public MutableLiveData<String> getCourses(){
       if(coursesTaughtLiveData.getValue() == null){

       }
       return coursesTaughtLiveData;
    }

    public void setUniversity(String university){ this.university = university; }

    public MutableLiveData<Boolean> getLoading(){
        return loading;
    }
}