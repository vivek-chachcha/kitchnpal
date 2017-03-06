package kitchnpal.servicerequest;

import android.app.DownloadManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.util.ArrayList;

import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;

/**
 * Created by linhphan on 17-03-05.
 */
public class MakeRequest {

    TextView mTxtDisplay;
    ImageView mImageView;
    
    private String type;
    private String recipe;
    
    public MakeRequest(String type, String recipe, TextView textView) {
        this.type = type;
        this.recipe = recipe;
        mTxtDisplay = textView;
    }
    
    public void getRecipe() {
        // Instantiate the RequestQueue.
        String url ="http://www.google.com";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe result = getParsedRecipe(response);
                User.getInstance().setCurrentRecipe(result);
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            mTxtDisplay.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        ApplicationController.getInstance().getRequestQueue().add(jsonRequest);
    }

    private Recipe getParsedRecipe(JSONObject response) {
        //TODO: parse json response
        String name = "fromreq";
        ArrayList<String> ingreds = new ArrayList<String>();
        ArrayList<String> steps = new ArrayList<String>();
        return new Recipe(name, ingreds, steps);
    }
    


}

