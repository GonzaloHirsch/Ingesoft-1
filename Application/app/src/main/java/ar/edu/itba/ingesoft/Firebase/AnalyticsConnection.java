package ar.edu.itba.ingesoft.Firebase;

import android.content.Context;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

public class AnalyticsConnection {

    private static FirebaseAnalytics mFirebaseAnalytics;

    public static void GenerateInstance(Context ctx){
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(ctx);
    }

    /*
    public static void LogEvent(FirebaseAnalytics.Event event, FirebaseAnalytics.Param itemID, FirebaseAnalytics.Param itemName){
        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, id);
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name);
        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

     */

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
}
