package ar.edu.itba.ingesoft.Firebase;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsConnection {

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static final String SIGNUP_CREATE = "EMAIL CREATE ACCOUNT";
    public static final String SIGNUP_GOOGLE = "GOOGLE CREATE ACCOUNT";
    public static final String SIGNUP_ANON = "ANON CREATE ACCOUNT";

    private static final String CHAT_READ = "chat_read";
    private static final String CHAT_LISTENER_READ = "chat_listener_read";
    private static final String UNIVERSITY_READ = "university_read";
    private static final String USERS_READ = "users_read";
    private static final String USER_READ = "user_read";
    private static final String COURSES_READ = "courses_read";

    public static void GenerateInstance(Context ctx){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }

    public static boolean LogEvent_SignUp(String type){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, type);
            mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SIGN_UP, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_UniversityRead(){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            mFirebaseAnalytics.logEvent(UNIVERSITY_READ, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_UsersRead(){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            mFirebaseAnalytics.logEvent(USERS_READ, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_UserRead(){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            mFirebaseAnalytics.logEvent(USER_READ, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_ChatRead(String name){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
            mFirebaseAnalytics.logEvent(CHAT_READ, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_ChatListenerRead(String name){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
            mFirebaseAnalytics.logEvent(CHAT_LISTENER_READ, bundle);
            return true;
        } else {
            return false;
        }
    }

    public static boolean LogEvent_CoursesRead(){
        if (mFirebaseAnalytics != null){
            Bundle bundle = new Bundle();
            mFirebaseAnalytics.logEvent(COURSES_READ, bundle);
            return true;
        } else {
            return false;
        }
    }
}
