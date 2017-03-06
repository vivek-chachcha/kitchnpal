package kitchnpal.ui;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.RecipeSearch;
import kitchnpal.kitchnpal.User;
import kitchnpal.sql.UserDatabaseHelper;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class FridgeFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */


        private static final String ARG_SECTION_NUMBER = "section_number";

        String[] myIngredientsString;


        String array[] = {"Carrot", "Butter", "3 Eggs"};

        public FridgeFragment() {
           // UserDatabaseHelper helper = new UserDatabaseHelper(getContext());
           // User user = User.getInstance();

           // myIngredientsString = helper.getFridgeIngredients(user.getEmail());

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static FridgeFragment newInstance(int sectionNumber) {
            FridgeFragment fragment = new FridgeFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_fridge, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.fridge_tab_body));


            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            ListView list = getListView();
            // String[] nStringArray = new String[myIngredients.size()];
            // nStringArray = myIngredients.toArray(nStringArray);

            FloatingActionButton addIngr = (FloatingActionButton) view.findViewById(R.id.addIngredient);
            addIngr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIngr= new Intent(getContext(), FridgeActivity.class);
                    startActivity(addIngr);
                }
            });

            //Fridge List Contents

            list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array)); //use nStringArray
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            //String[] nStringArray = new String[myIngredients.size()];
           // nStringArray = myIngredients.toArray(nStringArray);

            //FRIDGE LIST ITEM FUNCTIONALITY HERE
            //Object o = array[position]; // use nStringArray
            //String pen = o.toString();
            // Toast.makeText(getContext(), "You selected: " + " " + pen, Toast.LENGTH_LONG).show();

            //REAL FRIDGE ITEM FUNCTIONALITY HERE
            // Object p = myIngredientsString
            Object p = array[position];
            String name = p.toString();

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Would you like to remove this ingredient from your fridge?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // TODO Remove ingredient
                            //user.removeIngredientFromFridge(ingredient);
                            //dbHelper.updateFridge(user);

                            //Intent i = new Intent(getContext(), SearchResultActivity.class);
                            //startActivity(i);
                        }})
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class RecipesFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        String array[] = { "Fried Rice", "Pesto Chicken Pasta", "Chocolate Cookies" };
        String[] myFavs;

        public RecipesFragment() {
//            UserDatabaseHelper helper = new UserDatabaseHelper(getContext());
//            User user = User.getInstance();
//            myFavs = helper.getFavourites(user.getEmail());
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static RecipesFragment newInstance(int sectionNumber) {
            RecipesFragment fragment = new RecipesFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.recipe_tab_body));
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            ListView list = getListView();

            //Recipe List Contents
            list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            //RECIPE LIST ITEM FUNCTIONALITY HERE
//            Object o = array[position];
//            String pen = o.toString();
//            Toast.makeText(getContext(), "You selected: " + " " + pen, Toast.LENGTH_LONG).show();

            //REAL LIST ITEM FUNCTIONALITY
            //Object p = myFavs[position];
            Object p = array[position];
            String name = p.toString();
            Intent i = new Intent(getContext(), RecipeDisplayActivity.class);
            i.putExtra("recipe_name", name);
            startActivity(i);
        }
    }

    public static class SearchFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        String array[] = { "Fried Rice", "Pesto Chicken Pasta", "Chocolate Cookies" };
        String[] myFavs;

        public SearchFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static SearchFragment newInstance(int sectionNumber) {
            SearchFragment fragment = new SearchFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_search, container, false);

            Button nameSearchBtn = (Button) rootView.findViewById(R.id.name_search);
            Button ingredientSearchBtn = (Button) rootView.findViewById(R.id.ingredient_search);
            Button allSearchBtn = (Button) rootView.findViewById(R.id.all_search);

            nameSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    final EditText input = new EditText(getActivity());
                    builder.setMessage("Enter the name of the recipe: ")
                            .setTitle("Search by Name")
                            .setView(input)
                            .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();
                                    ArrayList<String> recipeNames = new ArrayList<>();
                                    List<Recipe> recipes = new RecipeSearch().searchByName(input.getText().toString());
                                    if (recipes != null) {
                                        for (Recipe r : recipes) {
                                            recipeNames.add(r.getName());
                                        }
                                    }
                                    Intent i = new Intent(getContext(), SearchResultActivity.class);
                                    i.putStringArrayListExtra("recipe_names", recipeNames);
                                    startActivity(i);
                                }})
                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                     dialog.cancel();
                                }});

                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });

            ingredientSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> recipeNames = new ArrayList<>();
                    //TODO: get ingredients from the fridge
                    List<Recipe> recipes = new RecipeSearch().searchByIngredient("REPLACE_ME");
                    if (recipes != null) {
                        for (Recipe r : recipes) {
                            recipeNames.add(r.getName());
                        }
                    }
                    Intent i = new Intent(getContext(), SearchResultActivity.class);
                    i.putStringArrayListExtra("recipe_names", recipeNames);
                    startActivity(i);
                }
            });

            allSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    User user = User.getInstance();
                    UserDatabaseHelper dbHelper = new UserDatabaseHelper(getContext());
                    String preference = dbHelper.getUserPreferences(user.getEmail());
                    ArrayList<String> recipeNames = new ArrayList<>();
                    List<Recipe> recipes = new RecipeSearch().searchByPreference(preference);
                    if (recipes != null) {
                        for (Recipe r : recipes) {
                            recipeNames.add(r.getName());
                        }
                    }
                    Intent i = new Intent(getContext(), SearchResultActivity.class);
                    i.putStringArrayListExtra("recipe_names", recipeNames);
                    startActivity(i);
                }
            });

            return rootView;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            switch(getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    // Search Tab
                    textView.setText(getString(R.string.search_tab_body));
                    break;
                case 2:
                    // Recipe Tab
                    textView.setText(getString(R.string.recipe_tab_body));
                    break;
                case 3:
                    // Fridge Tab
                    textView.setText(getString(R.string.fridge_tab_body));
                    break;
                default:
                    textView.setText(getString(R.string.search_tab_body));
                    break;
            }
            //textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position + 1 == 3) {
                return FridgeFragment.newInstance(position + 1);
            } else if (position + 1 == 2) {
                return RecipesFragment.newInstance(position + 1);
            } else if (position + 1 == 1) {
                return SearchFragment.newInstance(position + 1);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Search";
                case 1:
                    return "Favourites";
                case 2:
                    return "Fridge";
            }
            return null;
        }
    }

    public class SearchSectionPagerAdapter extends FragmentPagerAdapter {

        public SearchSectionPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if (position + 1 == 2) {
                return FridgeFragment.newInstance(position + 1);
            } else if (position + 1 == 1) {
                return RecipesFragment.newInstance(position + 1);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Search";
                case 1:
                    return "Results";
            }
            return null;
        }
    }
}
