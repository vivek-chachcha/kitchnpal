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
import kitchnpal.kitchnpal.Intolerance;
import kitchnpal.kitchnpal.R;
import kitchnpal.kitchnpal.User;
import kitchnpal.sql.UserDatabaseHelper;

public class UserPreferenceActivity extends AppCompatActivity {

    private Button dietRestrictionsBtn;
    private Button allergiesBtn;
    private Button userPreferencesBtn;
    private AutoCompleteTextView userNameView;
    private EditText userCaloriesView;
    private UserDatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_preferences);

        List<String> dietRestrictions = Diet.stringValues();
        dietRestrictionsBtn = (Button) findViewById(R.id.user_diet_restrictions);
        final SelectBtnListener dietBtnListener = new SelectBtnListener(this, dietRestrictions, dietRestrictionsBtn, true);
        dietRestrictionsBtn.setOnClickListener(dietBtnListener);

        List<String> allergies = Intolerance.stringValues();
        allergiesBtn = (Button) findViewById(R.id.user_allergies);
        final SelectBtnListener allergyBtnListener = new SelectBtnListener(this, allergies, allergiesBtn, true);
        allergiesBtn.setOnClickListener(allergyBtnListener);

        List<String> preferences = Arrays.asList("Lowest Price", "Lowest Calories");
        userPreferencesBtn = (Button) findViewById(R.id.user_preferences);
        final SelectBtnListener preferenceBtnListener = new SelectBtnListener(this, preferences, userPreferencesBtn, false);
        userPreferencesBtn.setOnClickListener(preferenceBtnListener);

        userNameView = (AutoCompleteTextView) findViewById(R.id.user_name);
        userCaloriesView = (EditText) findViewById(R.id.user_calories);

        dbHelper = new UserDatabaseHelper(this);

        Button savePreferenceBtn = (Button) findViewById(R.id.save_preferences_btn);
        savePreferenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = User.getInstance();
                user.setName(userNameView.getText().toString().trim());
                for (CharSequence s : dietBtnListener.getSelectedOptions()) {
                    user.addDietRestriction(Diet.stringToDiet(s.toString().trim()));
                }
                for (CharSequence s : allergyBtnListener.getSelectedOptions()) {
                    user.addAllergy(Intolerance.stringToIntolerance(s.toString().trim()));
                }
                for (CharSequence s : preferenceBtnListener.getSelectedOptions()) {
                    user.setPreference(s.toString().trim());
                }
                user.setNumCalPerDay(Integer.parseInt(userCaloriesView.getText().toString().trim()));
                dbHelper.updateUser(user);

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
