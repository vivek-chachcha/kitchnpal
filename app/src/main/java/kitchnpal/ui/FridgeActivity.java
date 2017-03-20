package kitchnpal.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import kitchnpal.kitchnpal.Diet;
import kitchnpal.kitchnpal.Fridge;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.Intolerance;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.User;
import kitchnpal.sql.FridgeDatabaseHelper;
import kitchnpal.sql.UserDatabaseHelper;

import static android.R.attr.typeface;


/**
 * Created by Jerry on 2017-03-05.
 */

public class FridgeActivity extends AppCompatActivity {

    private UserDatabaseHelper dbHelper;
    private FridgeDatabaseHelper fridgeDbHelper;
    private Button quantityTypeBtn;
    private EditText ingredientNameView;
    private EditText ingredientAmountView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredient);

        final Fridge fridge = Fridge.getInstance();

        List<String> quantityTypes = QuantityType.stringValues();
        quantityTypeBtn = (Button) findViewById(R.id.ingredient_qtype);
        final SelectBtnListener quantityTypeBtnListener = new SelectBtnListener(this, quantityTypes, quantityTypeBtn);
        quantityTypeBtn.setOnClickListener(quantityTypeBtnListener);

        ingredientNameView = (EditText) findViewById(R.id.ingredient_name);
        ingredientAmountView = (EditText) findViewById(R.id.ingredient_amount);

        fridgeDbHelper = new FridgeDatabaseHelper(this);

        Button addIngrBtn = (Button) findViewById(R.id.add_ingredient_btn);
        addIngrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ingredientNameString = ingredientNameView.getText().toString();
                String ingredientAmountString = ingredientAmountView.getText().toString();
                String ingredientQuantityTypeString = quantityTypeBtn.getText().toString();

                if (ingredientNameString != "" && ingredientAmountString  != "" && ingredientQuantityTypeString  != "") {
                    Ingredient newIngredient = new Ingredient(ingredientNameString.trim(),
                            Float.valueOf(ingredientAmountString),
                            QuantityType.stringToType(ingredientQuantityTypeString));

                    fridge.addIngredient(newIngredient);
                    fridgeDbHelper.addIngredient(newIngredient);

                    nextPage();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(FridgeActivity.this);
                    builder.setMessage("Please complete all fields of the ingredient.")

                            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }});
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }
    private void nextPage() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        i.putExtra("page", 3);
        startActivity(i);
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
}