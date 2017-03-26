package kitchnpal.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import java.util.Locale;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.Recipe;
import kitchnpal.kitchnpal.User;
import kitchnpal.servicerequest.KitchnPalService;
import kitchnpal.servicerequest.VolleySingleton;
import kitchnpal.sql.UserDatabaseHelper;

public class RecipeDisplayActivity extends AppCompatActivity implements OnInitListener {

    private TextToSpeech myTTS;
    private final int MY_DATA_CHECK_CODE = 0;
    private int currentStep = 0;
    private final int REQ_CODE_RECIPE_INPUT = 200;

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
        final KitchnPalService mr = new KitchnPalService();
        Recipe toDisplay = null;
        for (Recipe r : myFavs) {
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
                } else {
                    user.removeFavourite(recipe);
                    helper.updateUserFavourites(user);
                    Snackbar.make(view, "Removed From Your Favourites", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    toggleFav.setImageResource(R.drawable.fav);
                }
            }
        });



        FloatingActionButton textToSpeech = (FloatingActionButton) findViewById(R.id.fab);
        final Context activityContext = this;
        textToSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        currentStep = 0;
                        AlertDialog.Builder builder = new AlertDialog.Builder(activityContext);
                        LayoutInflater li = LayoutInflater.from(activityContext);
                        final View view1 = li.inflate(R.layout.recipe_voice_popup, null);
                        builder.setTitle("Recipe now in read-aloud mode")
                                .setView(view1)
                                .setNegativeButton("Stop", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }});
                        AlertDialog dialog = builder.create();
                        dialog.show();


                final List<String> instructions = user.getRecipe().getInstructions();


                speakWords(instructions.get(currentStep).replace("Directions", "").replace("[",""));


                Button repeat = (Button) view1.findViewById(R.id.instruction_repeat);

                Button next = (Button) view1.findViewById(R.id.instruction_next);

                Button previous = (Button) view1.findViewById(R.id.instruction_previous);

                repeat.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        if (currentStep == 0){
                            speakWords(instructions.get(currentStep).replace("Directions", "").replace("[",""));
                        }
                        else {
                            speakWords(instructions.get(currentStep));
                        }
                    }
                });

                next.setOnClickListener(new View.OnClickListener() {
                                            public void onClick(View v) {
                                                if (currentStep < instructions.size() - 1){
                                                    currentStep += 1;
                                                    speakWords(instructions.get(currentStep));
                                                }
                                                else {
                                                    speakWords(instructions.get(instructions.size() - 1));
                                                }
                                            }
                                        });

                previous.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        if (currentStep > 0) {
                            currentStep -= 1;

                            if (currentStep == 0) {
                                speakWords(instructions.get(currentStep).replace("Directions", "").replace("[", ""));
                            } else {
                                speakWords(instructions.get(currentStep));
                            }
                        }
                        else {
                                speakWords(instructions.get(0).replace("Directions", "").replace("[", ""));
                            }

                    }
                });

            }


        });
        Intent checkTTSIntent = new Intent();
        checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
    }

    private void speakWords(String speech) {

        //speak straight away
        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_CODE_RECIPE_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    if (result.get(0) != null) {
                        String myResult = result.get(0);
                        
                        //Do Stuff With Voice Result here
                        if (myResult.trim().equalsIgnoreCase("next")) {

                        } else if (myResult.trim().equalsIgnoreCase("repeat")) {

                        }
                        else {
                            
                        }
                    } else {
                        return;
                    }

                }
                super.onActivityResult(requestCode, resultCode, data);
                return;
            }
            case MY_DATA_CHECK_CODE: {
                if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                    //the user has the necessary data - create the TTS
                    myTTS = new TextToSpeech(this, this);
                }
                else {
                    //no data - install it now
                    Intent installTTSIntent = new Intent();
                    installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                    startActivity(installTTSIntent);
                }
            }
        }
    }

    public void onInit(int initStatus) {

        //check for successful instantiation
        if (initStatus == TextToSpeech.SUCCESS) {
            if (myTTS.isLanguageAvailable(Locale.US) == TextToSpeech.LANG_AVAILABLE)
                myTTS.setLanguage(Locale.US);
        } else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }


    @Override
    protected void onDestroy() {

        //Close the Text to Speech Library
        if(myTTS != null) {

            myTTS.stop();
            myTTS.shutdown();
        }
        super.onDestroy();
    }
    
    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_RECIPE_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }
}

