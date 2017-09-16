package com.example.mst.bprecorder;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by mst on 2017/05/07.
 */
public class MySingleton {
    private static MySingleton ourInstance;
    private RequestQueue mRequestQueue;
    private static Context mCtx;

    public static synchronized MySingleton getInstance(Context context) {
        if (ourInstance == null) {
            return ourInstance = new MySingleton(context);
        }
        return ourInstance;
    }

    private MySingleton(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();

    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req)
    {
        getRequestQueue().add(req);
    }


}

