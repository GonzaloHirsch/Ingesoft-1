package ar.edu.itba.ingesoft.Database;

import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.MainActivity;

public class DatabaseConnection {

    public static final String TAG = "DATABASE_CONNECTION";

    /**--------------------------------------USERS----------------------------------------*/

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
     * Gets the user identified by the given email.
     * @param email of the user to get
     */
    public void GetUser(String email){

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

                                // Stores all the info in the class
                                User user = new User(document.getData());
                            } else {
                                Log.d(TAG, "No such document");
                            }
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

    public void GetUsers(){
        // Add the new document for the user using the email as the ID of the document
        MainActivity.Instance.getDb().collection("Users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            QuerySnapshot document = task.getResult();

                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                });
    }

}
