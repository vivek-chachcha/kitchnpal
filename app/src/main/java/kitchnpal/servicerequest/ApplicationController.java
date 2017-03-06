package kitchnpal.servicerequest;


import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by linhphan on 17-03-05.
 */
public class ApplicationController extends Application {

    /**
     * Log or request TAG
     */
    public static final String TAG = "VolleyPatterns";

    /**
     * Global request queue for Volley
     */
    private RequestQueue mRequestQueue;

    /**
     * A singleton instance of the application class for easy access in other places
     */
    private static ApplicationController sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        // Initialize the singleton
        sInstance = this;
    }

    /**
     * @return ApplicationController singleton instance
     */

    public static synchronized ApplicationController getInstance() {
        return sInstance;
    }

    /**
     * @return the Volley Request queue, the queue will be created if it is null
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * Adds specified request to the global request queue, if tag is specified
     * then it is used else Default TAG is used
     */
    public <T> void addToRequestQueue(Request<T> req) {
        // set default tag if tag is empty
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    /**
     * Cancels all pending requests by the specified TAG, if it important
     * to specify a TAG so that the pending/ongoing requests can be cancelled
     */
    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

}
