package kitchnpal.servicerequest;

import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.UnsupportedEncodingException;

import kitchnpal.kitchnpal.R;

/**
 * Created by linhphan on 17-03-05.
 */
public class MakeRequest {

    TextView mTxtDisplay;
    ImageView mImageView;
    
    private String type;
    private String recipe;
    
    public MakeRequest(String type, String recipe, int textViewId) {
        this.type = type;
        this.recipe = recipe;
        mTxtDisplay = (TextView) findViewById(textViewId);
    }
    
    public sendRequest() {
        // Instantiate the RequestQueue.
        RequestQueue queue = ApplicationController.getInstance().getRequestQueue();
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            // Display the first 500 characters of the response string.
            mTextDisplay.setText("Response is: "+ response.toString());
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            mTextDisplay.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }
    


}

