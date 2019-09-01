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

public class Authenticator {
    private  FirebaseAuth auth = FirebaseAuth.getInstance();

    public Task<AuthResult> registerUser(String email, String password){
        return auth.createUserWithEmailAndPassword(email, password);
    }

    public Task<AuthResult> signInUser(String email, String password){
        return auth.signInWithEmailAndPassword(email, password);
    }

    FirebaseUser getCurrentUser(){
        return auth.getCurrentUser();
    }

    public Intent signInIntent(Activity activity, GoogleSignInOptions gso){
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(activity, gso);
        return googleSignInClient.getSignInIntent();
    }

    public AuthCredential getAccount(Intent intent) throws ApiException{
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
        GoogleSignInAccount account = task.getResult(ApiException.class);
        if(account == null)
            throw new IllegalStateException("Login failed");
        return GoogleAuthProvider.getCredential(account.getIdToken(), null);
    }

    public void signOut(){
        auth.signOut();
    }
}
