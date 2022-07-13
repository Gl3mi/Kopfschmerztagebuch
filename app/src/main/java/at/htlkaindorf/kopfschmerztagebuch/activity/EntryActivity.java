package at.htlkaindorf.kopfschmerztagebuch.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryActivity extends AppCompatActivity {
    private EditText t1;
    private EditText t2;
    private static final String MYPREFS = "myprefs";
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private String chosendate;

    private TimePickerDialog timePickerDialog;
    private TimePickerDialog timePickerDialog2;
    private Button timeButtonStart;
    private Button timeButtonEnd;
    private String chosenStart;
    private String chosenEnd;



    TextView textView;
    boolean[] selectedSymptom;
    ArrayList<Integer> langList = new ArrayList<>();
    String[] symptomList = {"Übelkeit", "Schwindel", "Kotlin", "C", "Python", "Javascript"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        initTimePicker();
        String currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) + " : " + Calendar.getInstance().get(Calendar.MINUTE);
        timeButtonStart = findViewById(R.id.timePickerButtonV);
        timeButtonStart.setText(currentTime);
        timeButtonEnd = findViewById(R.id.timePickerButtonB);
        timeButtonEnd.setText(currentTime);

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
        t1 = findViewById(R.id.medikamente1);
        t2 = findViewById(R.id.anmerkung);
        Button bt = findViewById(R.id.addToList);
        //textView = findViewById(R.id.test);

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

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {

        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            chosendate = makeDateString(day, month, year);
            dateButton.setText(chosendate);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void initTimePicker()
    {

        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            chosenStart = makeTimeString(h, m);
            timeButtonStart.setText(chosenStart);
        };

        TimePickerDialog.OnTimeSetListener timeSetListener2 = (timePicker, h, m) -> {
            chosenEnd = makeTimeString(h, m);
            timeButtonEnd.setText(chosenEnd);
        };

        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, h, m, true);
        timePickerDialog2 = new TimePickerDialog(this, style, timeSetListener2, h, m, true);
    }

    private String makeDateString(int day, int month, int year)
    {
        return day + " " + getMonthFormat(month) + " " + year;
    }

    private String makeTimeString(int h, int m)
    {
        return h + " : " + m;
    }

    private String getMonthFormat(int month)
    {
        switch (month){
            case 1: return "JAN";
            case 2: return "FEB";
            case 3: return "MAR";
            case 4: return "APR";
            case 5: return "MAY";
            case 6: return "JUN";
            case 7: return "JUL";
            case 8: return "AUG";
            case 9: return "SEP";
            case 10: return "OCT";
            case 11: return "NOV";
            case 12: return "DEC";
        }

        //default should never happen"09:00
        //12:50""0
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
    public void openTimePicker(View view)
    {
        timePickerDialog.show();
    }
    public void openTimePicker2(View view)
    {
        timePickerDialog2.show();
    }
}