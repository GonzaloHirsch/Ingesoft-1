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
import ar.edu.itba.ingesoft.Database.DatabaseConnection;

import ar.edu.itba.ingesoft.Database.DatabaseConnection;

public class CoursesTaughtViewModel extends ViewModel {

    MutableLiveData<List<Course>> courses = new MutableLiveData<>();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String email;
    DatabaseConnection dbc;

    public CoursesTaughtViewModel(){
        email = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        dbc = new DatabaseConnection();
    }

    public MutableLiveData<List<Course>> getCourses() {

        if(courses.getValue()==null){
            db.collection("Users").document(email).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Map<String, Object> map = documentSnapshot.getData();
                    Log.v("CoursesTViewModel", map.get("name").toString() + " " + map.get("surname") + " " + map.get("mail"));
                    List<Task<DocumentSnapshot>> tasks = new ArrayList<>();
                    ArrayList<DocumentReference> courses = (ArrayList<DocumentReference>) map.get("courses");
                    for(DocumentReference dr : courses){
                        tasks.add(dr.get());
                    }
                    Tasks.whenAllSuccess(tasks).addOnSuccessListener(new OnSuccessListener<List<Object>>() {
                        @Override
                        public void onSuccess(List<Object> objects) {
                            for(Object o : objects){
                                DocumentSnapshot ds = (DocumentSnapshot) o;

                                Log.v("CoursesTViewModel", String.valueOf(ds.exists()));
                            }

                        }
                    });
                }

            });
        }
        return courses;
    }
}
