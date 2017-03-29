package kitchnpal.servicerequest;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import kitchnpal.sql.UserDatabaseHelper;

/**
 * Created by linhphan on 17-03-05.
 */
public class KitchnPalService {
    public ConcurrentHashMap<String, Recipe> fullRecipeCache = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, String> searchCache = new ConcurrentHashMap<>();

    public void getIngredientByUpcCode(String upc, RequestQueue queue, final TextView ingredientText) {
        String url ="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/products/upc/" + upc;

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                setParsedIngredient(response, ingredientText);
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

    public void getRecipesWithSearchTerm(User user, String searchTerm, RequestQueue queue, final Context context, final ListView list) {
        User.getInstance().clearSearchResults();
        if (!searchTerm.equals("")) {
            searchTerm = searchTerm.replaceAll("\\s","+");
        }
        String url = "http://35.166.124.250:4567/recipes?accessToken=" + user.getAccessToken() + "&name=" + searchTerm + "&ingredients=";

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Recipe> results = getParsedRecipes(new JSONObject(response).getJSONArray("recipes"), context, list, false);
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
                });
        queue.add(jsonRequest);
    }

    public void getRecipesWithIngredients(User user, ArrayList<String> ingredients, RequestQueue queue, final Context context, final ListView list) {
        User.getInstance().clearSearchResults();

        String url = "http://35.166.124.250:4567/recipes?accessToken=" + user.getAccessToken() + "&name=";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < ingredients.size(); i++) {
            sb.append(ingredients.get(i));
            if (i != ingredients.size() - 1) {
                sb.append(",");
            }
        }
        String escapedIngredients = sb.toString().replaceAll("\\s","+");;
        url += "&ingredients=" + escapedIngredients;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            ArrayList<Recipe> results = getParsedRecipes(new JSONObject(response).getJSONArray("recipes"), context, list, true);
                            User.getInstance().setSearchResults(results);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });
        queue.add(jsonRequest);
    }

    public void getRecipeDetails(User user, int id, final TextView ingredientView, final TextView instructionView,
                                 RequestQueue queue, final ImageLoader loader, final NetworkImageView imageView) {
        String url ="http://35.166.124.250:4567/recipes/" + id + "?accessToken=" + user.getAccessToken();

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe result = parseRecipeSteps(response, instructionView, ingredientView, loader, imageView);
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
        });

        queue.add(jsonRequest);
    }

    ///////////////////////////// HELPERS /////////////////////////////////

    private void setParsedIngredient(JSONObject response, TextView ingredientText) {
        try {
            String ingredient = response.getString("title");
            if (ingredient == null) {
                ingredientText.setText(response.getString("message"));
            } else {
                ingredientText.setText(ingredient);
            }
        } catch (JSONException e) {
            ingredientText.setText("Could not find the product.");
        }
    }

    private ArrayList<Recipe> getParsedRecipes(JSONArray array, Context context, ListView list, boolean ingSearch) {
        ArrayList<Recipe> results = new ArrayList<Recipe>();
        try {
            for (int i = 0; i < array.length(); i++) {
                JSONObject temp = array.getJSONObject(i);
                String title = temp.getString("title").trim();
                int id = temp.getInt("id");
                if (!searchCache.containsKey(title.trim())) {
                    searchCache.put(title.trim(), Integer.toString(id));
                }
                if (ingSearch) {
                    int missedIngredientCount = temp.getInt("missedIngredientCount");
                    Recipe recipe = new Recipe(title.trim(), id, missedIngredientCount);
                    results.add(recipe);
                } else {
                    Recipe recipe = new Recipe(title.trim(), id);
                    results.add(recipe);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (ingSearch) {
            List<Map<String, String>> data = new ArrayList<Map<String, String>>();
            for (int i = 0; i < results.size(); i++) {
                Map<String, String> datum = new HashMap<String, String>(2);
                datum.put("title", results.get(i).getName());
                datum.put("count", "    Missing " + Integer.toString(results.get(i).getMissedIngredientCount()) + " Ingredients");
                data.add(datum);
            }
            SimpleAdapter adapter = new SimpleAdapter(context, data,
                    android.R.layout.simple_list_item_2,
                    new String[] {"title", "count"},
                    new int[] {android.R.id.text1,
                            android.R.id.text2});
            list.setAdapter(adapter);
        }
        else {
            String[] arr = new String[results.size()];
            for (int i = 0; i < results.size(); i++) {
                arr[i] = results.get(i).getName();
            }
            ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, arr);
            list.setAdapter(adapter);
        }
        return results;
    }

    private Recipe parseRecipeSteps(JSONObject temp, TextView instructionView, TextView ingredientView,
				  ImageLoader loader, NetworkImageView imageView) {
        try {
            JSONObject recipeObj = temp.getJSONObject("recipe");
            String title = recipeObj.getString("title");
            int id = recipeObj.getInt("id");
            String imageUrl = recipeObj.getString("image");
            JSONArray jsonIngredients = recipeObj.getJSONArray("extendedIngredients");
            ArrayList<Ingredient> ingredients = parseIngredients(jsonIngredients);
            ArrayList<String> instructions = parseInstructions(recipeObj.getString("instructions"));

            Recipe recipe = new Recipe(title, id, ingredients, instructions);
            if (fullRecipeCache.containsKey(Integer.toString(id))) {
                fullRecipeCache.remove(Integer.toString(id));
            }
            fullRecipeCache.put(Integer.toString(id), recipe);
	
	        loader.get(imageUrl, ImageLoader.getImageListener(imageView, R.drawable.kitchnpalhatlogo, android.R.drawable.ic_dialog_alert));
            imageView.setImageUrl(imageUrl, loader);

            if (instructionView != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < instructions.size(); i++) {
                    int stepNum = i + 1;
                    stringBuilder.append(stepNum);
                    stringBuilder.append(": ");
                    stringBuilder.append(instructions.get(i));
                    if (i != instructions.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                instructionView.setText(stringBuilder.toString());
            }

            if (ingredientView != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < ingredients.size(); i++) {
                    String name = ingredients.get(i).getIngredientName();
                    stringBuilder.append(name.substring(0, 1).toUpperCase() + name.substring(1));
                    stringBuilder.append(": ");
                    stringBuilder.append(ingredients.get(i).getIngredientAmount());
                    stringBuilder.append(" ");
                    stringBuilder.append(ingredients.get(i).getIngredientQuantityType().getName().toLowerCase());
                    if (i != ingredients.size() - 1) {
                        stringBuilder.append("\n");
                    }
                }
                ingredientView.setText(stringBuilder.toString());
            }
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
                results.add(new Ingredient(obj.getString("name"), obj.getDouble("amount"), QuantityType.CUPS));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return results;
    }

    private ArrayList<String> parseInstructions(String raw) {
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

