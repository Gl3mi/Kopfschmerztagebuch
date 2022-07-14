package at.htlkaindorf.kopfschmerztagebuch.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.google.gson.Gson;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import at.htlkaindorf.kopfschmerztagebuch.bl.Operator;
import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;
import at.htlkaindorf.kopfschmerztagebuch.ui.SharedViewModel;

public class EntryActivity extends AppCompatActivity {
    private List<Entry> entries = new ArrayList<>();
    private Session session;

    private int intensity = 0;

    private TextView kindOfPain;
    private TextView painArea;
    private TextView symptoms;

    private EditText medics;
    private EditText comment;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialogFrom;
    private TimePickerDialog timePickerDialogTo;

    private Button dateButton;
    private Button timeButtonFrom;
    private Button timeButtonTo;

    private String chosenDate;
    private String chosenFrom;
    private String chosenTo;

    boolean[] selectedSymptom;
    boolean[] selectedPainArea;
    boolean[] selectedKindOfPain;

    private RadioButton intensity1;
    private RadioButton intensity2;
    private RadioButton intensity3;
    private RadioButton intensity4;
    private RadioButton intensity5;

    private final ArrayList<Integer> langListSymptom = new ArrayList<>();
    private final ArrayList<Integer> langListPainArea = new ArrayList<>();
    private final ArrayList<Integer> langListKindOfPain = new ArrayList<>();

    private final String[] symptomList = {"Übelkeit", "Schwindel", "laufende Nase", "Fieber",
            "Lichtempfindlichkeit", "Lärmempfindlichkeit", "Appetitlosigkeit",
            "Tränenfluss und/oder gerötete Bindehaut", "Schweißausbrüche"};

    private final String[] kindOfPainList = {"Migräne", "Spannungskopfschmerzen",
            "Cluster-Kopfschmerzen", "Sinusitis-Kopfschmerzen", "Sonstige Kopfschmerzen"};

    private final String[] painAreaList = {"Oben", "Unten", "Links", "Rechts", "Mitte",
            "Vorne", "Hinten", "Schläfe", "Augenbereich"};

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);

        session = new Session(this);

        intensity1 = findViewById(R.id.intensity1);
        intensity2 = findViewById(R.id.intensity2);
        intensity3 = findViewById(R.id.intensity3);
        intensity4 = findViewById(R.id.intensity4);
        intensity5 = findViewById(R.id.intensity5);

        kindOfPain = findViewById(R.id.kindOfPain);
        painArea = findViewById(R.id.painArea);
        symptoms = findViewById(R.id.symptoms);

        medics = findViewById(R.id.medics);
        comment = findViewById(R.id.comment);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
        chosenDate = getTodaysDate();

        initTimePicker();
        String currentTime = makeTimeString(Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
                Calendar.getInstance().get(Calendar.MINUTE));

        timeButtonFrom = findViewById(R.id.timePickerButtonFrom);
        timeButtonFrom.setText(currentTime);
        timeButtonTo = findViewById(R.id.timePickerButtonTo);
        timeButtonTo.setText(currentTime);
        chosenFrom = currentTime;
        chosenTo = currentTime;

        //SharedViewModel viewModel = new ViewModelProvider(this).get(SharedViewModel.class);

        TextView symptoms = findViewById(R.id.symptoms);
        selectedSymptom = new boolean[symptomList.length];

        TextView painArea = findViewById(R.id.painArea);
        selectedPainArea = new boolean[painAreaList.length];

        TextView kindOfPain = findViewById(R.id.kindOfPain);
        selectedKindOfPain = new boolean[kindOfPainList.length];

        kindOfPain.setOnClickListener(new Operator(this, langListKindOfPain,
                kindOfPainList, kindOfPain, "kindOfPain"));
        painArea.setOnClickListener(new Operator(this, langListPainArea,
                painAreaList, painArea, "painArea"));
        symptoms.setOnClickListener(new Operator(this, langListSymptom,
                symptomList, symptoms, "symptoms"));


        Gson gson = new Gson();
        Button bt = findViewById(R.id.addToList);

        if (session.getEntries("data") != null) {
            entries = session.getEntries("data");
        }

        bt.setOnClickListener(view -> {
            if (kindOfPain.getText().equals("") || painArea.getText().equals("")) {
                Toast.makeText(this, "Es wurden nicht alle Pflichtfelder ausgefüllt!",
                        Toast.LENGTH_SHORT).show();
            } else {
                Entry entry = new Entry(String.valueOf(kindOfPain.getText()),
                        String.valueOf(painArea.getText()), 0, chosenFrom, chosenTo,
                        chosenDate, String.valueOf(medics.getText()),
                        String.valueOf(symptoms.getText()), String.valueOf(comment.getText()), false);

                entries.add(entry);

                String json = gson.toJson(entries);
                session.getEditor().putString("data", json).apply();
                super.onBackPressed();
            }
        });
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(@NonNull View view) {
        boolean checked = ((RadioButton) view).isChecked();


        switch (view.getId()) {
            case R.id.intensity1:
                if (checked) {
                    intensity = 1;
                    intensity1.setBackgroundResource(R.drawable.rounded_corners);
                    intensity2.setBackgroundColor(Color.WHITE);
                    intensity3.setBackgroundColor(Color.WHITE);
                    intensity4.setBackgroundColor(Color.WHITE);
                    intensity5.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.intensity2:
                if (checked) {
                    intensity = 2;
                    intensity1.setBackgroundColor(Color.WHITE);
                    intensity2.setBackgroundResource(R.drawable.rounded_corners);
                    intensity3.setBackgroundColor(Color.WHITE);
                    intensity4.setBackgroundColor(Color.WHITE);
                    intensity5.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.intensity3:
                if (checked) {
                    intensity = 3;
                    intensity1.setBackgroundColor(Color.WHITE);
                    intensity2.setBackgroundColor(Color.WHITE);
                    intensity3.setBackgroundResource(R.drawable.rounded_corners);
                    intensity4.setBackgroundColor(Color.WHITE);
                    intensity5.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.intensity4:
                if (checked) {
                    intensity = 4;
                    intensity1.setBackgroundColor(Color.WHITE);
                    intensity2.setBackgroundColor(Color.WHITE);
                    intensity3.setBackgroundColor(Color.WHITE);
                    intensity4.setBackgroundResource(R.drawable.rounded_corners);
                    intensity5.setBackgroundColor(Color.WHITE);
                }
                break;
            case R.id.intensity5:
                if (checked) {
                    intensity = 5;
                    intensity1.setBackgroundColor(Color.WHITE);
                    intensity2.setBackgroundColor(Color.WHITE);
                    intensity3.setBackgroundColor(Color.WHITE);
                    intensity4.setBackgroundColor(Color.WHITE);
                    intensity5.setBackgroundResource(R.drawable.rounded_corners);
                }
                break;
        }
    }

    @NonNull
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            chosenDate = makeDateString(day, month, year);
            dateButton.setText(chosenDate);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style,
                dateSetListener, year, month, day);

        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
    }

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = (timePicker, h, m) -> {
            chosenFrom = makeTimeString(h, m);
            timeButtonFrom.setText(chosenFrom);
        };

        TimePickerDialog.OnTimeSetListener timeSetListener2 = (timePicker, h, m) -> {
            chosenTo = makeTimeString(h, m);
            timeButtonTo.setText(chosenTo);
        };

        Calendar cal = Calendar.getInstance();
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);

        int style = android.app.AlertDialog.THEME_HOLO_LIGHT;

        timePickerDialogFrom = new TimePickerDialog(this, style,
                timeSetListener, h, m, true);

        timePickerDialogTo = new TimePickerDialog(this, style,
                timeSetListener2, h, m, true);
    }

    @NonNull
    private String makeDateString(int day, int month, int year) {
        if (day < 10) {
            return "0" + day + " " + getMonthFormat(month) + " " + year;
        } else {
            return day + " " + getMonthFormat(month) + " " + year;
        }

    }

    @NonNull
    @Contract(pure = true)
    private String makeTimeString(int h, int m) {
        String hour = "" + h;
        String minute = "" + m;

        if (h < 10) {
            hour = "0" + h;
        }
        if (m < 10) {
            minute = "0" + m;
        }
        return hour + " : " + minute;
    }

    @NonNull
    @Contract(pure = true)
    private String getMonthFormat(int month) {
        switch (month) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mär";
            case 4:
                return "Apr";
            case 5:
                return "Mai";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Okt";
            case 11:
                return "Nov";
            case 12:
                return "Dez";
        }

        return "Jan";
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();
    }

    public void openTimePickerFrom(View view) {
        timePickerDialogFrom.show();
    }

    public void openTimePickerTo(View view) {
        timePickerDialogTo.show();
    }
}
