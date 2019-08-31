package ar.edu.itba.ingesoft.Database;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Interfaces.DatabaseEventListeners.OnUserEventListener;
import ar.edu.itba.ingesoft.MainActivity;

public class DatabaseConnection {

    public static final String TAG = "DATABASE_CONNECTION";

    /*--------------------------------------USERS----------------------------------------*/

    /**
     * Inserts the user into the database.
     * @param user to be inserted
     */
    public void InsertUser(User user){
        // Add the new document for the user using the email as the ID of the document
        MainActivity.Instance.getDb().collection("Users")
                .document()
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "DocumentSnapshot added");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }

    /**
     * Updates the given user.
     * Uses the getter for the updateable fields.-
     * @param user to be updated.
     */
    public void UpdateUser(final User user){
        // Update the user in the given document
        MainActivity.Instance.getDb().collection("Users")
                .document(user.getMail())
                .update(user.getDataToUpdate())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "User " + user.getMail() + " updated");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error updating " + user.getMail() + " user", e);
                    }
                });
    }

    /**
     * Gets the user identified by the given email.
     * @param email of the user to get
     */
    public void GetUser(String email, final OnUserEventListener eventListener){

        // Add the new document for the user using the email as the ID of the document
        MainActivity.Instance.getDb().collection("Users")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                                Map<String, Object> data = document.getData();

                                if (data != null){
                                    // Stores all the info in the class
                                    User user = new User(data);
                                    eventListener.onUserRetrieved(user);
                                } else {
                                    Log.d(TAG, "No data in document");
                                }
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Getter for all the users in the database.
     * @param eventListener with callback to this function.
     */
    public void GetUsers(final OnUserEventListener eventListener){
        // Add the new document for the user using the email as the ID of the document
        MainActivity.Instance.getDb().collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            List<User> users = new ArrayList<>();

                            if (document != null){
                                for (DocumentSnapshot ds : document) {
                                    if (ds.getData() != null){
                                        users.add(new User(ds.getData()));
                                    }
                                }
                                eventListener.onUsersRetrieved(users);
                            } else {
                                Log.d(TAG, "Query GetUsers returned null");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    /**
     * Getter for all the teachers in the system.
     * @param eventListener with callback to this function.
     */
    public void GetTeachers(final OnUserEventListener eventListener){
        MainActivity.Instance.getDb().collection("Users")
                .whereEqualTo("isTeacher", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                            List<User> users = new ArrayList<>();

                            if (document != null){
                                for (DocumentSnapshot ds : document) {
                                    if (ds.getData() != null){
                                        users.add(new User(ds.getData()));
                                    }
                                }
                                eventListener.onTeachersRetrieved(users);
                            } else {
                                Log.d(TAG, "Query GetTeachers returned null");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

}
