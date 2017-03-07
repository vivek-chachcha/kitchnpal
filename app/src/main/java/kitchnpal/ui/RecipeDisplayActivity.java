package kitchnpal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.servicerequest.MakeRequest;
import kitchnpal.servicerequest.VolleySingleton;
import kitchnpal.sql.UserDatabaseHelper;

public class RecipeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

        String recipeName = getIntent().getStringExtra("recipe_name");

        UserDatabaseHelper helper = new UserDatabaseHelper(this);
        User user = User.getInstance();
        TextView myTextView = (TextView)findViewById(R.id.recipe_contents);
//        ArrayList<Recipe> myFavs = helper.getFavourites(user.getEmail());
        Recipe toDisplay = null;
//        for (Recipe r: myFavs) {
//            if (r.getName().equalsIgnoreCase(recipeName)) {
//                toDisplay = r;
//            }
//        }
        if (toDisplay == null) {
            MakeRequest mr = new MakeRequest();
            mr.getRecipeDetails(479101, myTextView, queue);
//            mr.getRecipeDetails(mr.cache.get(recipeName).getId());
//            ArrayList<String> inst = new ArrayList<>();
//            inst.add("First Step");
//            ArrayList<Ingredient> ing = new ArrayList<>();
//            ing.add(new Ingredient("Banana", 2));
//            toDisplay = new Recipe("Cookies", 11012, ing, inst);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
