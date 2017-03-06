package kitchnpal.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.servicerequest.MakeRequest;
import kitchnpal.sql.UserDatabaseHelper;

public class RecipeDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_display);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String recipeName = getIntent().getStringExtra("recipe_name");

        UserDatabaseHelper helper = new UserDatabaseHelper(this);
        User user = User.getInstance();
        ArrayList<Recipe> myFavs = helper.getFavourites(user.getEmail());
        Recipe toDisplay = null;
        for (Recipe r: myFavs) {
            if (r.getName().equalsIgnoreCase(recipeName)) {
                toDisplay = r;
            }
        }
        if (toDisplay == null) {
//            MakeRequest mr = new MakeRequest();
//            mr.getRecipeDetails(mr.cache.get(recipeName).getId());
//            toDisplay = user.getRecipe();
            toDisplay = new Recipe("Cookies", 11012);
        }

        TextView myTextView = (TextView)findViewById(R.id.recipe_contents);
        if (myTextView != null) {
            List<String> steps = toDisplay.getInstructions();
            List<Ingredient> ingreds = toDisplay.getIngredients();

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(toDisplay.getName());
            stringBuilder.append("\n\n");
            stringBuilder.append("Instructions:\n");
            for (int i = 0; i < steps.size(); i++) {
                int stepNum = i + 1;
                stringBuilder.append(stepNum);
                stringBuilder.append(": ");
                stringBuilder.append(steps.get(i));
                if (i != steps.size() - 1) {
                    stringBuilder.append("\n");
                }
            }
            stringBuilder.append("\n\n");
            stringBuilder.append("Ingredients:\n");
            for (int i = 0; i < ingreds.size(); i++) {
                stringBuilder.append(ingreds.get(i).getIngredientName());
                if (i != ingreds.size() - 1) {
                    stringBuilder.append("\n");
                }
            }
            myTextView.setText(stringBuilder.toString());
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
