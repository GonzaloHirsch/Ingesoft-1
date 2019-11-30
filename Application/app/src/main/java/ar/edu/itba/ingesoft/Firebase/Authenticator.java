package ar.edu.itba.ingesoft.Firebase;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;

import androidx.annotation.NonNull;
import ar.edu.itba.ingesoft.Classes.Universidad;
import ar.edu.itba.ingesoft.Classes.User;
import ar.edu.itba.ingesoft.R;

public class Authenticator {
    public static final String TAG = "Authenticator";

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    /** Creates an user and inserts it in the database */
    public Task<AuthResult> registerUser(String email, String password, String name, String surname, String university){
        User user = new User(name + " " + surname, email, university);
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signInAnonymousUser(){
        return this.auth.signInAnonymously();
    }

    public FirebaseUser getSignedInUser(){
        return this.auth.getCurrentUser();
    }

    public Task<AuthResult> registerUser(String email, String password){
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

    public GoogleSignInClient generateGoogleClient(Activity activity){
        //Login with Google Account
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        return GoogleSignIn.getClient(activity, gso);
    }

    /** Returns the task associated with the intent */
    public Task<AuthResult> getTask(Intent intent){
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            return firebaseAuth(account);
        } catch (ApiException e){
            Log.d("Auth", "auth:get_task_api_exception", task.getException());
        }
        return null;
    }


    public Task<SignInMethodQueryResult> verifyMail(String mail){
        return auth.fetchSignInMethodsForEmail(mail);
    }

    /** Makes the authentication with firebase */
    private Task<AuthResult> firebaseAuth(GoogleSignInAccount account){
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        return auth.signInWithCredential(credential);
    }

    public void signOut(Activity activity){
        // Normal sign out
        auth.signOut();

        // Google sign out
        generateGoogleClient(activity).signOut().addOnCompleteListener(activity,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.v(TAG, "Signed user out of google");
                    }
                });
    }
}
