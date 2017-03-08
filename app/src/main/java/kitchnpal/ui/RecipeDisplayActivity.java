package kitchnpal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
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
        setTitle(getIntent().getStringExtra("recipe_name"));
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageButton star = (ImageButton) findViewById(R.id.toggleFavourite);

        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();

        int recipeId = getIntent().getIntExtra("recipe_id", 114160);

        UserDatabaseHelper helper = new UserDatabaseHelper(this);
        User user = User.getInstance();
        TextView ingredientView = (TextView)findViewById(R.id.ingredients);
        TextView instructionView = (TextView)findViewById(R.id.instructions);
        ArrayList<Recipe> myFavs = user.getFavourites();
        MakeRequest mr = new MakeRequest();
        Recipe toDisplay = null;
        for (Recipe r: myFavs) {
            if (r.getId() == recipeId) {
                mr.getRecipeDetails(recipeId, ingredientView, instructionView, queue);
                toDisplay = r;
            }
        }
        if (toDisplay == null) {
            mr.getRecipeDetails(recipeId, ingredientView, instructionView, queue);
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
