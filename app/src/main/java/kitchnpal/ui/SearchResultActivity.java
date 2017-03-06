package kitchnpal.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.sql.UserDatabaseHelper;

import static java.security.AccessController.getContext;

/**
 * Created by Mandy on 2017-03-05.
 */

public class SearchResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_display);

        ListView list = (ListView) findViewById(R.id.search_result);


        ArrayList<String> recipeNames = getIntent().getStringArrayListExtra("recipe_names");
        final String[] recipeNameArray = recipeNames.toArray(new String[recipeNames.size()]);

        //TODO: remove stub data once API search calls are implemented
        final String array[] = { "Fried Rice", "Pesto Chicken Pasta", "Chocolate Cookies" };

        list.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, array));

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object p = array[i];
                String name = p.toString();
                Intent intent = new Intent(getApplicationContext(), RecipeDisplayActivity.class);
                intent.putExtra("recipe_name", name);
                startActivity(intent);
            }
        });
    }


}
