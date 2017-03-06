package kitchnpal.servicerequest;

import android.app.DownloadManager;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;

/**
 * Created by linhphan on 17-03-05.
 */
public class MakeRequest {
    public ConcurrentHashMap<String, Recipe> cache = new ConcurrentHashMap<>();
    ImageView mImageView;
    private String userUrl = "http://35.163.134.8/users";
    private String recipeUrl = "http://35.163.134.8/recipes";
    
    public MakeRequest() {
    }
    
    public void getRecipesWithSearchTerm(String searchTerm) {
        User.getInstance().clearSearchResults();
        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, recipeUrl,
                    null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<Recipe> results = getParsedRecipes(response.getJSONArray("results"));
                    User.getInstance().setSearchResults(results);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        ApplicationController.getInstance().getRequestQueue().add(jsonRequest);
    }

    public void getRecipesWithIngredients(ArrayList<String> ingredients) {
        User.getInstance().clearSearchResults();
        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, recipeUrl,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Recipe> results = getParsedRecipes(response);
                User.getInstance().setSearchResults(results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        ApplicationController.getInstance().getRequestQueue().add(jsonRequest);
    }

    public void getRecipeDetails(int id) {
        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, recipeUrl,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe result = parseRecipeSteps(response);
                User.getInstance().setCurrentRecipe(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        // Add the request to the RequestQueue.
        ApplicationController.getInstance().getRequestQueue().add(jsonRequest);
    }

    private ArrayList<Recipe> getParsedRecipes(JSONArray array) {
        ArrayList<Recipe> results = new ArrayList<Recipe>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = array.getJSONObject(i);
                String title = temp.getString("title").trim();
                int id = temp.getInt("id");
                String imageUrl = temp.getString("image");
                Recipe recipe = new Recipe(title, id);
                if (!cache.containsKey(title)) {
                    cache.put(title.trim(), recipe);
                }

                InputStream is = (InputStream) new URL(imageUrl).getContent();
                Drawable d = Drawable.createFromStream(is, "src name");
                recipe.setImage(d);

                results.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public Recipe parseRecipeSteps(JSONObject temp) {
        try {
            String title = temp.getString("title");
            int id = temp.getInt("id");
            String imageUrl = temp.getString("image");
            JSONArray jsonIngredients = temp.getJSONArray("extendedIngredients");
            ArrayList<Ingredient> ingredients = parseIngredients(jsonIngredients);
            ArrayList<String> instructions = parseInstructions(temp.getString("instructions"));

            Recipe recipe = new Recipe(title, id, ingredients, instructions);

            InputStream is = (InputStream) new URL(imageUrl).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            recipe.setImage(d);
            return recipe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<Ingredient> parseIngredients(JSONArray ingredients) {
        ArrayList<Ingredient> results = new ArrayList<>();
        try {
            for (int i = 0; i < ingredients.length(); i++) {
                JSONObject obj = ingredients.getJSONObject(i);
                results.add(new Ingredient(obj.getString("name"), (float)obj.get("amount"), QuantityType.stringToType(obj.getString("unit"))));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private ArrayList<String> parseInstructions(String raw) {
        ArrayList<String> results = new ArrayList<>();
        try {
            String[] pieces = raw.split("   ");
            for (String s : pieces) {
                results.add(s);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}

