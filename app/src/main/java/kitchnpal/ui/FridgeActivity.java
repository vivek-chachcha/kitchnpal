package kitchnpal.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kitchnpal.kitchnpal.Diet;
import kitchnpal.kitchnpal.Ingredient;
import kitchnpal.kitchnpal.Intolerance;
import kitchnpal.kitchnpal.QuantityType;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.User;
import kitchnpal.sql.FridgeDatabaseHelper;
import kitchnpal.sql.UserDatabaseHelper;


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
        final User user = User.getInstance();

        List<String> quantityTypes = QuantityType.stringValues();
        quantityTypeBtn = (Button) findViewById(R.id.ingredient_qtype);
        final SelectBtnListener quantityTypeBtnListener = new SelectBtnListener(this, quantityTypes, quantityTypeBtn, false);
        quantityTypeBtn.setOnClickListener(quantityTypeBtnListener);

        ingredientNameView = (EditText) findViewById(R.id.ingredient_name);
        ingredientAmountView = (EditText) findViewById(R.id.ingredient_amount);



    Button addIngrBtn = (Button) findViewById(R.id.add_ingredient_btn);
    addIngrBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Ingredient newIngredient = new Ingredient(ingredientNameView.getText().toString().trim(),
                    Float.valueOf(ingredientAmountView.getText().toString()),
                    QuantityType.stringToType(quantityTypeBtn.getText().toString()));

            user.addIngredientToFridge(newIngredient);
            //fridgeDbHelper.addIngredient(newIngredient);

            nextPage();

        }
    });
}
    private void nextPage() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
    }

    private static class SelectBtnListener implements View.OnClickListener {

        private CharSequence[] options;
        private List<CharSequence> selectedOptions;
        private Context context;
        private Button btn;
        private Boolean isMultiSelect;
        private CharSequence selectedOption;

        public SelectBtnListener(Activity activity, List<String> options, Button btn, Boolean isMultiSelect) {
            this.options = options.toArray(new CharSequence[options.size()]);
            this.selectedOptions = new ArrayList<>();
            this.context = activity;
            this.btn = btn;
            this.isMultiSelect = isMultiSelect;
            this.selectedOption = "";
        }
        @Override
        public void onClick(View view) {
            if (isMultiSelect) {
                showMultiDialog();
            } else {
                showSingleDialog();
            }
        }

        public List<CharSequence> getSelectedOptions() {
            return selectedOptions;
        }

        private void showMultiDialog() {
            boolean[] checkedItems = new boolean[options.length];
            for(int i = 0; i < options.length; i++) {
                checkedItems[i] = selectedOptions.contains(options[i]);
            }

            DialogInterface.OnMultiChoiceClickListener multiListener = new DialogInterface.OnMultiChoiceClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i, boolean isChecked) {
                    if(isChecked)
                        selectedOptions.add(options[i]);
                    else
                        selectedOptions.remove(options[i]);
                    onChangeSelected();
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Select");
            builder.setMultiChoiceItems(options, checkedItems, multiListener);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
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

        private void onChangeSelected() {
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < selectedOptions.size(); i++) {
                stringBuilder.append(selectedOptions.get(i));
                if (i != selectedOptions.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
            btn.setText(stringBuilder.toString());
        }
    }
}