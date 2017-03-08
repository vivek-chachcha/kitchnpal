package kitchnpal.servicerequest;

import android.app.DownloadManager;
import android.graphics.drawable.Drawable;
import android.util.Base64;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public ConcurrentHashMap<String, Recipe> fullRecipeCache = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, String> searchCache = new ConcurrentHashMap<>();
    ImageView mImageView;
    
    public MakeRequest() {

    }
    
    public void getRecipesWithSearchTerm(String searchTerm, RequestQueue queue, final ArrayAdapter adapter, final ListView list) {
        User.getInstance().clearSearchResults();
        String url ="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/search";
        if (!searchTerm.equals("")) {
            url += "?query=";
            searchTerm = searchTerm.replaceAll("\\s","+");
            url += searchTerm;
        }

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                    null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    ArrayList<Recipe> results = getParsedRecipes(response.getJSONArray("results"), adapter, list);
                    User.getInstance().setSearchResults(results);
                } catch(JSONException e) {
                    e.printStackTrace();
                }
            }
            }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    public void getRecipesWithIngredients(ArrayList<String> ingredientsNames, RequestQueue queue, final ArrayAdapter adapter, final ListView list) {
        User.getInstance().clearSearchResults();
        String url ="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/findByIngredients";
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        sb.append("?ingredients=");
        for (int i = 0; i < ingredientsNames.size(); i++) {
            sb.append(ingredientsNames.get(i));
            if (i != ingredientsNames.size() - 1) {
                sb.append(",");
            }
        }
        url = sb.toString();

        // Request a string response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ArrayList<Recipe> results = getParsedRecipes(response, adapter, list);
                User.getInstance().setSearchResults(results);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(jsonRequest);
    }

    public void getRecipeDetails(int id, final TextView toDisplay, RequestQueue queue) {
        String url ="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe result = parseRecipeSteps(response, toDisplay);
                User.getInstance().setCurrentRecipe(result);
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                for (String s : error.networkResponse.headers.values()) {
                    Log.d("headers", s);
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                super.getHeaders();
                Map<String, String> headers = new HashMap<>();
                headers.put("X-Mashape-Key", "gY0JeRT5jTmsh9Ld5t3ez3DUrxWGp1wXcF9jsnhtvOpIsoXsyi");
                headers.put("Accept", "application/json");
                return headers;
            }
        };

        queue.add(jsonRequest);
    }

    public ArrayList<Recipe> getParsedRecipes(JSONArray array, ArrayAdapter adapter, ListView list) {
        ArrayList<Recipe> results = new ArrayList<Recipe>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = array.getJSONObject(i);
                String title = temp.getString("title").trim();
                int id = temp.getInt("id");
                String imageUrl = temp.getString("image");
                Recipe recipe = new Recipe(title.trim(), id);
                if (!searchCache.containsKey(title.trim())) {
                    searchCache.put(title.trim(), Integer.toString(id));
                }

//                InputStream is = (InputStream) new URL(imageUrl).getContent();
//                Drawable d = Drawable.createFromStream(is, "src name");
//                recipe.setImage(d);

                results.add(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String[] arr = new String[results.size()];
        for (int i = 0; i < results.size(); i++) {
            arr[i] = results.get(i).getName();
        }
        adapter.addAll(arr);
        list.setAdapter(adapter);
        return results;
    }

    public Recipe parseRecipeSteps(JSONObject temp, TextView myTextView) {
        try {
            String title = temp.getString("title");
            int id = temp.getInt("id");
            String imageUrl = temp.getString("image");
            JSONArray jsonIngredients = temp.getJSONArray("extendedIngredients");
            ArrayList<Ingredient> ingredients = parseIngredients(jsonIngredients);
            ArrayList<String> instructions = parseInstructions(temp.getString("instructions"));

            Recipe recipe = new Recipe(title, id, ingredients, instructions);
            if (fullRecipeCache.containsKey(Integer.toString(id))) {
                fullRecipeCache.remove(Integer.toString(id));
            }
            fullRecipeCache.put(Integer.toString(id), recipe);

//            InputStream is = (InputStream) new URL(imageUrl).getContent();
//            Drawable d = Drawable.createFromStream(is, "src name");
//            recipe.setImage(d);

            if (myTextView != null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(recipe.getName());
                stringBuilder.append("\n\n");
                stringBuilder.append("Instructions:\n");
                for (int i = 0; i < instructions.size(); i++) {
                    int stepNum = i + 1;
                    stringBuilder.append(stepNum);
                    stringBuilder.append(": ");
                    stringBuilder.append(instructions.get(i));
                    if (i != instructions.size() - 1) {
                        stringBuilder.append("\n\n");
                    }
                }
                stringBuilder.append("\n\n");
                stringBuilder.append("Ingredients:\n");
                for (int i = 0; i < ingredients.size(); i++) {
                    stringBuilder.append(ingredients.get(i).getIngredientName());
                    stringBuilder.append(", ");
                    stringBuilder.append(ingredients.get(i).getIngredientAmount());
                    stringBuilder.append(" ");
                    stringBuilder.append(ingredients.get(i).getIngredientQuantityType().getName());
                    if (i != ingredients.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                myTextView.setText(stringBuilder.toString());
            }
            return recipe;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Ingredient> parseIngredients(JSONArray ingredients) {
        ArrayList<Ingredient> results = new ArrayList<>();
        try {
            for (int i = 0; i < ingredients.length(); i++) {
                JSONObject obj = ingredients.getJSONObject(i);
                results.add(new Ingredient(obj.getString("name"), obj.getDouble("amount"), QuantityType.CUPS));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    public ArrayList<String> parseInstructions(String raw) {
        ArrayList<String> results = new ArrayList<>();
        try {
            int marker = 0;
            while (marker < raw.length() - 4 || raw.indexOf(".", marker) >= raw.length()) {
                String s = raw.substring(marker, raw.indexOf(".", marker));
                results.add(s);
                marker = raw.indexOf(".", marker) + 1;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return results;
    }

}

