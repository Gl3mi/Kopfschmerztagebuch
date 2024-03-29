package at.htlkaindorf.kopfschmerztagebuch.activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Contract;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import at.htlkaindorf.kopfschmerztagebuch.R;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.bl.Operator;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;

public class EntryActivity extends AppCompatActivity {
    private List<Entry> entries = new ArrayList<>();
    private Entry entry;
    private Session session;

    private int intensity = 0;

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

    private final String[] painAreaList = {"1-Oben", "2-Stirn", "3-Hinterkopf", "4-Wange L",
            "5-Wange R", "6-Schläfe L", "7-Schläfe R", "8-Augenbereich L", "9-Augenbereich R"};

    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd LLL yyyy", Locale.GERMAN);

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

        Button add = findViewById(R.id.addToList);

        if (session.getEntries("data") != null) {
            entries = session.getEntries("data");
        }

        Intent intent = getIntent();

        if (intent != null && intent.hasExtra("kindOfPain")) {
            kindOfPain.setText(intent.getStringExtra("kindOfPain"));
            painArea.setText(intent.getStringExtra("painArea"));
            timeButtonFrom.setText(intent.getStringExtra("from"));
            timeButtonTo.setText(intent.getStringExtra("to"));
            dateButton.setText(intent.getStringExtra("date"));
            medics.setText(intent.getStringExtra("medics"));
            symptoms.setText(intent.getStringExtra("symptoms"));
            comment.setText(intent.getStringExtra("comment"));

            switch (Integer.parseInt(intent.getStringExtra("intensity"))) {
                case 1:
                    intensity = 1;
                    intensity1.setChecked(true);
                    intensity1.setBackgroundResource(R.drawable.smiley);
                    break;
                case 2:
                    intensity = 2;
                    intensity2.setChecked(true);
                    intensity2.setBackgroundResource(R.drawable.smiley);
                    break;
                case 3:
                    intensity = 3;
                    intensity3.setChecked(true);
                    intensity3.setBackgroundResource(R.drawable.smiley);
                    break;
                case 4:
                    intensity = 4;
                    intensity4.setChecked(true);
                    intensity4.setBackgroundResource(R.drawable.smiley);
                    break;
                case 5:
                    intensity = 5;
                    intensity5.setChecked(true);
                    intensity5.setBackgroundResource(R.drawable.smiley);
                    break;
            }

            boolean medic = !String.valueOf(medics.getText()).equals("");

            entry = new Entry(intent.getStringExtra("kindOfPain"),
                    intent.getStringExtra("painArea"),
                    Integer.parseInt(intent.getStringExtra("intensity")),
                    intent.getStringExtra("from"), intent.getStringExtra("to"),
                    intent.getStringExtra("date"), intent.getStringExtra("medics"),
                    intent.getStringExtra("symptoms"), intent.getStringExtra("comment"),
                    medic);
        }

        add.setOnClickListener(view -> {
            if (kindOfPain.getText().equals("") || painArea.getText().equals("") || intensity == 0) {
                Toast.makeText(this, "Es wurden nicht alle Pflichtfelder ausgefüllt!",
                        Toast.LENGTH_SHORT).show();
            } else if (entries.contains(entry)) {
                boolean medic = !String.valueOf(medics.getText()).equals("");

                entry = entries.get(entries.indexOf(entry));

                entry.setKindOfPain(String.valueOf(kindOfPain.getText()));
                entry.setPainArea(String.valueOf(painArea.getText()));
                entry.setIntensity(intensity);
                entry.setFrom(String.valueOf(timeButtonFrom.getText()));
                entry.setTo(String.valueOf(timeButtonTo.getText()));
                entry.setDate(String.valueOf(dateButton.getText()));
                entry.setMedics(String.valueOf(medics.getText()));
                entry.setAttendantSymptoms(String.valueOf(symptoms.getText()));
                entry.setComment(String.valueOf(comment.getText()));
                entry.setCheckMedic(medic);

                session.getEditor().clear().apply();
                session.putString(entries);

                super.onBackPressed();
            } else if (checkDate()) {
                Toast.makeText(this, "Eintrag mit dem Datum schon vorhanden!",
                        Toast.LENGTH_SHORT).show();
            } else {
                boolean medic = !String.valueOf(medics.getText()).equals("");

                entry = new Entry(String.valueOf(kindOfPain.getText()),
                        String.valueOf(painArea.getText()), intensity, chosenFrom, chosenTo,
                        chosenDate, String.valueOf(medics.getText()),
                        String.valueOf(symptoms.getText()), String.valueOf(comment.getText()), medic);

                entries.add(entry);

                entries.sort((o1, o2) -> LocalDate.parse(o2.getDate(), dateFormatter)
                        .compareTo(LocalDate.parse(o1.getDate(), dateFormatter)));

                session.putString(entries);

                super.onBackPressed();
            }
        });
    }

    public boolean checkDate() {
        return entries.stream().anyMatch(entry -> entry.getDate().equals(chosenDate));
    }

    @SuppressLint("NonConstantResourceId")
    public void onRadioButtonClicked(@NonNull View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()) {
            case R.id.intensity1:
                if (checked) {
                    intensity = 1;
                    intensity1.setBackgroundResource(R.drawable.smiley);
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
                    intensity2.setBackgroundResource(R.drawable.smiley);
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
                    intensity3.setBackgroundResource(R.drawable.smiley);
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
                    intensity4.setBackgroundResource(R.drawable.smiley);
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
                    intensity5.setBackgroundResource(R.drawable.smiley);
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
