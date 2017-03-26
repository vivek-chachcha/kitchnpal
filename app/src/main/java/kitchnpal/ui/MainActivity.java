package kitchnpal.ui;


import android.app.Activity;
import android.content.Context;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.speech.RecognizerIntent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.Fridge;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.servicerequest.KitchnPalService;
import kitchnpal.servicerequest.VolleySingleton;
import kitchnpal.sql.FridgeDatabaseHelper;
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
    private final int REQ_CODE_SEARCH_INPUT = 100;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    static private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        activity = this;

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabTextColors(Color.parseColor("#D3D3D3"), Color.parseColor("#FF7F50"));
        int defaultValue = 0;
        int page = getIntent().getIntExtra("page", defaultValue);
        mViewPager.setCurrentItem(page);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                promptSpeechInput();
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
    
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SEARCH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    public static class FridgeFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private FridgeDatabaseHelper helper;
        private Fridge fridge;

        private List<Ingredient> myIngredients;
        private List<String> ingredientsString;

        public FridgeFragment() {
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
            helper = new FridgeDatabaseHelper(getActivity());
            fridge = Fridge.getInstance();
            myIngredients = helper.getIngredients();
            ingredientsString = Ingredient.ingredientsToString(myIngredients);

            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            ListView list = getListView();

            Button addIngr = (Button) view.findViewById(R.id.addIngredient);
            addIngr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent addIngr = new Intent(getContext(), FridgeActivity.class);
                    startActivity(addIngr);
                }
            });

            Button barcodeScan = (Button) view.findViewById(R.id.barcodeScan);
            barcodeScan.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
                    scanIntegrator.setCaptureActivity(CustomScannerActivity.class);
                    scanIntegrator.initiateScan();
                }
            });

            //Fridge List Contents
            if (ingredientsString != null) {
                list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, ingredientsString)); //use nStringArray
            }
        }

        @Override
        public void onListItemClick(ListView l, View v, final int position, long id) {
            super.onListItemClick(l, v, position, id);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Would you like to remove this ingredient from your fridge?")

                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            // Remove ingredient
                            helper.removeIngredient(myIngredients.get(position));

                            Intent i = new Intent(getContext(), MainActivity.class);
                            i.putExtra("page", 3);
                            startActivity(i);
                        }})
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQ_CODE_SEARCH_INPUT: {
                if (resultCode == RESULT_OK && null != intent) {
                    ArrayList<String> result = intent
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0) != null) {
                        String myResult = result.get(0);
                        int type = getVoiceType(myResult);
                        switch (type) {
                            case 1:
                                displaySearchResults(myResult);
                                return;
                            case 2:
                                handleFridgeVoiceResult(myResult);
                                return;
                            default:
                                displaySearchResults(myResult);
                                return;
                        }
                    } else {
                        return;
                    }

                }
                super.onActivityResult(requestCode, resultCode, intent);
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult.getContents() != null) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);

            String scanContent = scanningResult.getContents();

            LayoutInflater li = LayoutInflater.from(this);
            View view = li.inflate(R.layout.barcode_popup, null);
            builder.setView(view);
            final TextView tv = (TextView) view.findViewById(R.id.ingredientName);
            final Button quantityTypeBtn = (Button) view.findViewById(R.id.ingredient_qtype_popup);
            List<String> quantityTypes = QuantityType.stringValues();
            SelectBtnListener quantityTypeBtnListener = new SelectBtnListener(this, quantityTypes, quantityTypeBtn);
            quantityTypeBtn.setOnClickListener(quantityTypeBtnListener);
            final EditText ingredientAmountView = (EditText) view.findViewById(R.id.ingredient_amount_popup);
            final Context activityContext = this;
            final FridgeDatabaseHelper fridgeDbHelper = new FridgeDatabaseHelper(activityContext);
            final Fridge fridge = Fridge.getInstance();

            KitchnPalService mr = new KitchnPalService();
            RequestQueue queue = VolleySingleton.getInstance(getApplicationContext()).getRequestQueue();
            mr.getIngredientByUpcCode(scanContent.trim(), queue, tv);
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            String ingredientNameString = tv.getText().toString();
                            String ingredientAmountString = ingredientAmountView.getText().toString();
                            String ingredientQuantityTypeString = quantityTypeBtn.getText().toString();

                            if (ingredientNameString != "" && ingredientAmountString  != "" && ingredientQuantityTypeString  != "") {
                                Ingredient newIngredient = new Ingredient(ingredientNameString.trim(),
                                        Float.valueOf(ingredientAmountString),
                                        QuantityType.stringToType(ingredientQuantityTypeString));

                                if (fridge.isIngredientInFridge(ingredientNameString, activityContext)) {
                                    double addedAmount = newIngredient.getIngredientAmount();
                                    double currentAmount = fridgeDbHelper.getIngredientAmount(ingredientNameString);
                                    double totalAmount = addedAmount + currentAmount;
                                    Ingredient updatedIngredient = new Ingredient(ingredientNameString.trim(), totalAmount,
                                            QuantityType.stringToType(ingredientQuantityTypeString));
                                    fridgeDbHelper.updateIngredient(updatedIngredient);
                                } else {
                                    fridgeDbHelper.addIngredient(newIngredient);
                                }

                                dialog.dismiss();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                i.putExtra("page", 3);
                                startActivity(i);
                            } else {
                                builder.setMessage("Please complete all fields.");
                            }
                        }})
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }});

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }
    
    private int getVoiceType(String myResult) {
        if (myResult.contains("I have")) {
            return 2;
        } else {
            return  1;
        }
    }

    private void displaySearchResults(String myResult) {
        if (myResult.length() > 14) {
            myResult = myResult.substring(14);
        }

        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("page", 0);
        i.putExtra("VoiceResults", myResult.trim());
        startActivity(i);
    }

    private void handleFridgeVoiceResult(String myResult) {

    }

    private static class SelectBtnListener implements View.OnClickListener {

        private CharSequence[] options;
        private Context context;
        private Button btn;
        private CharSequence selectedOption;

        public SelectBtnListener(Activity activity, List<String> options, Button btn) {
            this.options = options.toArray(new CharSequence[options.size()]);
            this.context = activity;
            this.btn = btn;
            this.selectedOption = "";
        }
        @Override
        public void onClick(View view) {
            showSingleDialog();
        }

        private void showSingleDialog() {
            int checkedItem = -1;
            for(int i = 0; i < options.length; i++) {
                if (selectedOption.equals(options[i])) {
                    checkedItem = i;
                }
            }

            DialogInterface.OnClickListener singleListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    selectedOption = options[i];
                    btn.setText(selectedOption.toString());
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Select");
            builder.setSingleChoiceItems(options, checkedItem, singleListener);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

public static class RecipesFragment extends ListFragment {
        
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ArrayList<Recipe> myFavs;
        private UserDatabaseHelper helper;
        private User user;

        public RecipesFragment() {
        }

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

            helper = new UserDatabaseHelper(getActivity());
            user = User.getInstance();
            myFavs = helper.getFavourites(user.getEmail());
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            ListView list = getListView();
            String[] array = new String[myFavs.size()];
            for (int i = 0; i < myFavs.size(); i++) {
                array[i] = myFavs.get(i).getName();
            }
            //Recipe List Contents
            list.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, array));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);
            Intent i = new Intent(getContext(), RecipeDisplayActivity.class);
            i.putExtra("recipe_id", myFavs.get(position).getId());
            i.putExtra("recipe_name", myFavs.get(position).getName());
            startActivity(i);
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class SearchFragment extends ListFragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private static KitchnPalService mr = new KitchnPalService();
        private static UserDatabaseHelper userDbHelper;
        private final int REQ_CODE_SEARCH_INPUT = 100;

        public SearchFragment() {
            mr = new KitchnPalService();
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
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView topView = (TextView) rootView.findViewById(R.id.section_label);
            userDbHelper = new UserDatabaseHelper(getActivity());

            String body = "Search Results for: ";
            topView.setText(body);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            final TextView topView = (TextView) view.findViewById(R.id.section_label);
            final ListView list = getListView();

            Button nameSearchBtn = (Button) view.findViewById(R.id.name_search);
            Button ingredientSearchBtn = (Button) view.findViewById(R.id.ingredient_search);
            Button allSearchBtn = (Button) view.findViewById(R.id.all_search);
            
            String voiceResults = getActivity().getIntent().getStringExtra("VoiceResults");
            if (voiceResults != null && !voiceResults.equals("")) {
                String accessToken = userDbHelper.getUserAccessToken(User.getInstance().getEmail());
                User user = User.getInstance();
                user.setAccessToken(accessToken);
                displayNewNameResults(user, voiceResults, list);
                String body = "Search Results for: " + voiceResults;
                topView.setText(body);
            }

            nameSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    LayoutInflater li = LayoutInflater.from(getContext());
                    final View view1 = li.inflate(R.layout.name_search_popup, null);
                    builder.setTitle("Search by Name")
                            .setView(view1)
                            .setPositiveButton("Search", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.dismiss();

                                    String accessToken = userDbHelper.getUserAccessToken(User.getInstance().getEmail());
                                    User user = User.getInstance();
                                    user.setAccessToken(accessToken);
                                    EditText input = (EditText) view1.findViewById(R.id.name_search_text);
                                    String text = input.getText().toString().trim();
                                    displayNewNameResults(user, text, list);
                                    String body = "Search Results for: " + text;
                                    topView.setText(body);
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
                    FridgeDatabaseHelper dbHelper = new FridgeDatabaseHelper(getActivity());
                    ArrayList<String> ingredients = new ArrayList<String>();
                    for (Ingredient i : dbHelper.getIngredients()) {
                        ingredients.add(i.getIngredientName());
                    }
                    String accessToken = userDbHelper.getUserAccessToken(User.getInstance().getEmail());
                    User user = User.getInstance();
                    user.setAccessToken(accessToken);
                    displayNewIngredientResults(user, ingredients, list);
                    String body = "Searching by Fridge Ingredients: ";
                    topView.setText(body);
                }
            });

            allSearchBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String accessToken = userDbHelper.getUserAccessToken(User.getInstance().getEmail());
                    User user = User.getInstance();
                    user.setAccessToken(accessToken);
                    displayNewNameResults(user, "", list);
                    String body = "Searching All Recipes: " ;
                    topView.setText(body);
                }
            });
        }


        private void displayNewIngredientResults(User user, ArrayList<String> ingredients, ListView list) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);

            RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
            mr.getRecipesWithIngredients(user, ingredients, queue, arrayAdapter, list);
        }

        private void displayNewNameResults(User user, String searchTerm, ListView list) {
            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1);

            RequestQueue queue = VolleySingleton.getInstance(getContext()).getRequestQueue();
            mr.getRecipesWithSearchTerm(user, searchTerm, queue, arrayAdapter, list);
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            super.onListItemClick(l, v, position, id);

            //Look at individual recipe
            Object p = l.getItemAtPosition(position);
            String name = p.toString().trim();
            int recipeId = Integer.parseInt(mr.searchCache.get(name));
            Intent i = new Intent(getContext(), RecipeDisplayActivity.class);
            i.putExtra("recipe_name", name);
            i.putExtra("recipe_id", recipeId);
            startActivity(i);
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
            }
            else if (position + 1 == 2) {
                return RecipesFragment.newInstance(position + 1);
            }
            return SearchFragment.newInstance(position + 1);
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
}
