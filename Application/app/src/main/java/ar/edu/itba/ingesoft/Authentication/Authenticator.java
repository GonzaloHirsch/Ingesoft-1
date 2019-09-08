package ar.edu.itba.ingesoft.Authentication;

import android.app.Activity;
import android.content.Intent;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.Database.DatabaseConnection;

public class Authenticator {
    private  FirebaseAuth auth = FirebaseAuth.getInstance();

    /** Creates an user and inserts it in the database */
    public Task<AuthResult> registerUser(String email, String password, String name, String surname, String university){
        User user = new User(name, surname, email, new Universidad(university));
        DatabaseConnection.InsertUser(user);
        return auth.createUserWithEmailAndPassword(email, password);
    }

    /** Authenticates the email and password in Firebase */
    public Task<AuthResult> signInUser(String email, String password){
        return auth.signInWithEmailAndPassword(email, password);
    }

    /** Returns the currently logged user */
    FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    /** Return an Intent associated with the activity and GoogleSignInOptions */
    public Intent signInIntent(Activity activity, GoogleSignInOptions gso){
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);
        return googleSignInClient.getSignInIntent();
    }

    /** Returns the task associated with the intent */
    public Task<AuthResult> getTask(Intent intent) throws ApiException{
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        if(account == null)
            return null;
        else
            return firebaseAuth(account);
    }

    /** Makes the authentication with firebase */
    private Task<AuthResult> firebaseAuth(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return auth.signInWithCredential(credential);
    }

    public void signOut(){
        auth.signOut();
    }
}
