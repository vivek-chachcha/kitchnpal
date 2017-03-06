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
        List<Recipe> myFavs = user.getFavourites();
        Recipe toDisplay = null;
        for (Recipe r: myFavs) {
            if (r.getName().equalsIgnoreCase(recipeName)) {
                toDisplay = r;
            }
        }
        if (toDisplay == null) {
            //TODO: Get Recipe from Server
            String name = "Cookies";
            ArrayList<Ingredient> ingreds = new ArrayList<Ingredient>();
            int id = 11221;
            ArrayList<String> steps = new ArrayList<String>();
            steps.add("Mix eggs");
            steps.add("Put in oven");
            toDisplay = new Recipe(name, id, ingreds, steps);
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
