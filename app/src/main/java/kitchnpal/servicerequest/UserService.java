package kitchnpal.servicerequest;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import kitchnpal.kitchnpal.User;
import kitchnpal.sql.UserDatabaseHelper;
import kitchnpal.util.EncodePasswordUtil;

public class UserService {

    public void createUser(final User user, RequestQueue queue, final UserDatabaseHelper dbHelper) {
        String url = "http://35.166.124.250:4567/users";
        StringRequest jsonRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userObj = new JSONObject(response).getJSONObject("user");
                            User.getInstance().setAccessToken(userObj.getString("accessToken"));
                            dbHelper.updateUserAccessToken(user);
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
                }) {
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
                params.put("password", EncodePasswordUtil.SHA1(user.getPassword()));
                params.put("calPerDay", user.getNumCalPerDay().toString());
                params.put("preferences", user.getPreference().toString().toLowerCase());
                params.put("diet", dietString.toString().toLowerCase());

                return params;
            }
        };
        queue.add(jsonRequest);
    }

    public void updateUser(final User user, RequestQueue queue) {
        String url = "http://35.166.124.250:4567/users/" + user.getEmail();
        StringRequest jsonRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
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
                params.put("password", EncodePasswordUtil.SHA1(user.getPassword()));
                params.put("calPerDay", user.getNumCalPerDay().toString());
                params.put("preferences", user.getPreference().toString().toLowerCase());
                params.put("accessToken", user.getAccessToken());
                params.put("diet", dietString.toString().toLowerCase());

                return params;
            }
        };
        queue.add(jsonRequest);
    }
}