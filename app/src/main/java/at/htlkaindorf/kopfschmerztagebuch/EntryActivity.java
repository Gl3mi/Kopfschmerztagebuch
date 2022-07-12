package at.htlkaindorf.kopfschmerztagebuch;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryActivity extends AppCompatActivity {
    private EditText t1;
    private EditText t2;
    private static final String MYPREFS = "myprefs";
    TextView textView;
    boolean[] selectedSymptom;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] symptomList = {"Übelkeit", "Schwindel", "Kotlin", "C", "Python", "Javascript"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        TextView symptoms = findViewById(R.id.symptoms);
        selectedSymptom = new boolean[symptomList.length];

        symptoms.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            // set title
            builder.setTitle("Beigleitsymptome");

            // set dialog non cancelable
            builder.setCancelable(false);

            builder.setMultiChoiceItems(symptomList, selectedSymptom, (dialog, which, isChecked) -> {
                if (isChecked) {
                    langList.add(which);
                    Collections.sort(langList);
                } else {
                    langList.remove(Integer.valueOf(which));
                }
            });

            builder.setPositiveButton("OK", (dialog, which) -> {
                StringBuilder stringBuilder = new StringBuilder();

                for (int i = 0; i < langList.size(); i++) {
                    // concat array value
                    stringBuilder.append(symptomList[langList.get(i)]);
                    // check condition
                    if (i != langList.size() - 1) {
                        // When j value  not equal
                        // to lang list size - 1
                        // add comma
                        stringBuilder.append(", ");
                    }
                }
                symptoms.setText(stringBuilder.toString());
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

            builder.setNeutralButton("Clear All", (dialog, which) -> {
                for (int i = 0; i < selectedSymptom.length; i++) {
                    // remove all selection
                    selectedSymptom[i] = false;
                    // clear language list
                    langList.clear();
                    // clear text view value
                    symptoms.setText("");
                }
            });
            builder.show();
        });

        Gson gson = new Gson();
        t1 = findViewById(R.id.bereich);
        t2 = findViewById(R.id.bereich2);
        Button bt = findViewById(R.id.btn);
        textView = findViewById(R.id.test);

        SharedPreferences sharedPreferences = getSharedPreferences(MYPREFS, Context.MODE_PRIVATE);
        @SuppressLint("CommitPrefEdits") SharedPreferences.Editor editor = sharedPreferences.edit();

        bt.setOnClickListener(view -> {
            Entry entry = new Entry(String.valueOf(t1.getText()), String.valueOf(t2.getText()));
            String json = gson.toJson(entry);
            editor.putString("testi", json).apply();
            viewModel.setLiveData(entry);

            super.onBackPressed();
        });
    }
}
