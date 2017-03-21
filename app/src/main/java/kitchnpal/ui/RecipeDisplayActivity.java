package kitchnpal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

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
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.back_arrow));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);

        RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
        ImageLoader loader = VolleySingleton.getInstance(getApplicationContext()).getImageLoader();
        NetworkImageView mImageView = (NetworkImageView)findViewById(R.id.networkImageView);

        final int recipeId = getIntent().getIntExtra("recipe_id", 114160);
        String recipeName = getIntent().getStringExtra("recipe_name");

        final UserDatabaseHelper helper = new UserDatabaseHelper(this);
        String accessToken = helper.getUserAccessToken(User.getInstance().getEmail());
        final User user = User.getInstance();
        user.setAccessToken(accessToken);
        TextView recipeTitleView = (TextView)findViewById(R.id.recipeTitle);
        recipeTitleView.setText(getIntent().getStringExtra("recipe_name"));
        TextView ingredientView = (TextView)findViewById(R.id.ingredients);
        TextView instructionView = (TextView)findViewById(R.id.instructions);
        ArrayList<Recipe> myFavs = helper.getFavourites(user.getEmail());
        final MakeRequest mr = new MakeRequest();
        Recipe toDisplay = null;
        for (Recipe r: myFavs) {
            if (r.getId() == recipeId) {
                mr.getRecipeDetails(user, recipeId, ingredientView, instructionView, queue, loader, mImageView);
                toDisplay = r;
            }
        }
        if (toDisplay == null) {
            mr.getRecipeDetails(user, recipeId, ingredientView, instructionView, queue, loader, mImageView);
        }

        final FloatingActionButton toggleFav = (FloatingActionButton) findViewById(R.id.toggleFavourite);
        boolean inThere = false;
        for (Recipe rec : myFavs) {
            if (recipeId == rec.getId()) {
                inThere = true;
            }
        }
        if (!inThere) {
            toggleFav.setImageResource(R.drawable.fav);
        } else {
            toggleFav.setImageResource(R.drawable.fav_set);
        }
        toggleFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Recipe recipe = mr.fullRecipeCache.get(Integer.toString(recipeId));
                boolean inThere = false;

                for (Recipe rec : helper.getFavourites(user.getEmail())) {
                    if (recipe.getId() == rec.getId()) {
                        inThere = true;
                    }
                }
                if (!inThere) {
                    user.addFavourite(recipe);
                    helper.updateUserFavourites(user);
                    Snackbar.make(view, "Added To Your Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    toggleFav.setImageResource(R.drawable.fav_set);
                }
                else {
                    user.removeFavourite(recipe);
                    helper.updateUserFavourites(user);
                    Snackbar.make(view, "Removed From Your Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    toggleFav.setImageResource(R.drawable.fav);
                }
            }
        });

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
