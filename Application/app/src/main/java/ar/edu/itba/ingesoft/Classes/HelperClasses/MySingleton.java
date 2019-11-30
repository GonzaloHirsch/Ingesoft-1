package ar.edu.itba.ingesoft.Classes.HelperClasses;


import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class MySingleton {

    public static final String TAG = "SINGLETON CLASS";

    private static MySingleton instance;
    private RequestQueue requestQueue;
    private Context ctx;

    private MySingleton(Context context) {
        ctx = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized MySingleton getInstance(Context context) {
        Log.v(TAG, "Getting instance");
        if (instance == null) {
            instance = new MySingleton(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        Log.v(TAG, "Getting queue");
        if (requestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        Log.v(TAG, "Adding to queue");
        getRequestQueue().add(req);
    }
}
