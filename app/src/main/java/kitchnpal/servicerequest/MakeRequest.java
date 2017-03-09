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
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import kitchnpal.kitchnpal.Diet;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

/**
 * Created by linhphan on 17-03-05.
 */
public class MakeRequest {
    public ConcurrentHashMap<String, Recipe> fullRecipeCache = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String, String> searchCache = new ConcurrentHashMap<>();
    ImageView mImageView;
    
    public MakeRequest() {

    }

    public void createUser(final User user, RequestQueue queue) {
        String url = "http://35.166.124.250:4567/users";
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                            JSONObject user = new JSONObject(response).getJSONObject("user");
                            User.getInstance().setAccessToken(user.getString("accessToken"));
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        StringBuilder dietString = new StringBuilder();
                        for (int i = 0; i < user.getDietRestrictions().size(); i++) {
                            dietString.append(user.getDietRestrictions().get(i).getName());
                            if (i != user.getDietRestrictions().size() - 1) {
                                dietString.append(", ");
                            }
                        }
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", user.getEmail());
                        params.put("name", user.getName());
                        params.put("password", SHA1(user.getPassword()));
                        params.put("calPerDay", user.getNumCalPerDay().toString());
                        params.put("preferences", user.getPreference().toString().toLowerCase());
                        params.put("diet", dietString.toString());

                        return params;
                    }
                };
        queue.add(jsonRequest);
    }

    public void updateUser(final User user, RequestQueue queue) {
        String url = "http://35.166.124.250:4567/users/" + user.getEmail().toString();
        StringRequest jsonRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        StringBuilder dietString = new StringBuilder();
                        for (int i = 0; i < user.getDietRestrictions().size(); i++) {
                            dietString.append(user.getDietRestrictions().get(i).getName());
                            if (i != user.getDietRestrictions().size() - 1) {
                                dietString.append(", ");
                            }
                        }
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("email", user.getEmail());
                        params.put("name", user.getName());
                        params.put("password", SHA1(user.getPassword()));
                        params.put("calPerDay", user.getNumCalPerDay().toString());
                        params.put("preferences", user.getPreference().toString().toLowerCase());
			params.put("accessToken", user.getAccessToken().toString();
                        params.put("diet", dietString.toString());

                        return params;
                    }
                };
        queue.add(jsonRequest);
    }	


    public void getUser(final User user, RequestQueue queue) {
        String url = "http://35.166.124.250:4567/users/" + user.getEmail().toString() + "?accessToken=" + user.getAccessToken().toString();
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                        } catch(JSONException e) {
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

    public void getRecipes(final User user, RequestQueue queue, String name, ArrayList<String> ingredients) {
        String url = "http://35.166.124.250:4567/recipes?accessToken=" + user.getAccessToken().toString() + "&name=" + name;
	StringBuilder sb = new StringBuilder();
	for (String s : ingredients)
	{
	    sb.append(s);
	    sb.append("\t");
	}
	String escapedIngredients = escapeHtml4(sb.toString());
	url += "&ingredients=" + escapedIngredients;
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            System.out.println(response);
                        } catch(JSONException e) {
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

    private static String convertToHex(byte[] data) {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } while(two_halfs++ < 1);
        }
        return buf.toString();
    }

    public static String SHA1(String text) {
        try {
            MessageDigest md;
            md = MessageDigest.getInstance("SHA-1");
            byte[] sha1hash = new byte[40];
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
            sha1hash = md.digest();
            return convertToHex(sha1hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
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

    public void getRecipeDetails(int id, final TextView ingredientView, final TextView instructionView, RequestQueue queue) {
        String url ="https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/recipes/" + id + "/information";

        // Request a string response from the provided URL.
        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Recipe result = parseRecipeSteps(response, instructionView, ingredientView);
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

    public Recipe parseRecipeSteps(JSONObject temp, TextView instructionView, TextView ingredientView) {
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

            if (instructionView != null) {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < instructions.size(); i++) {
                    int stepNum = i + 1;
                    stringBuilder.append(stepNum);
                    stringBuilder.append(": ");
                    stringBuilder.append(instructions.get(i));
                    if (i != instructions.size() - 1) {
                        stringBuilder.append("\n\n");
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

