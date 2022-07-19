package at.htlkaindorf.kopfschmerztagebuch.ui.calendar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.NoSuchElementException;

import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;
import at.htlkaindorf.kopfschmerztagebuch.bl.Session;
import at.htlkaindorf.kopfschmerztagebuch.databinding.FragmentCalendarBinding;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;
    private TextView chosenDate;
    private List<Entry> entries = new ArrayList<>();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH : mm");

    @SuppressLint("SetTextI18n")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Session session = new Session(requireContext());

        binding = FragmentCalendarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView kindOfPain = binding.kindOfPain;
        TextView painArea = binding.painArea;
        TextView intensity = binding.intensity;
        TextView medics = binding.medics;
        TextView duration = binding.duration;

        chosenDate = binding.date;
        chosenDate.setText(getTodaysDate());
        CalendarView calendar = binding.calendarView;

        entries = session.getEntries("data");

        calendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            chosenDate.setText("" + dayOfMonth);

            try {
                Entry entry = entries.stream().filter(e -> {
                    String[] date = e.getDate().split(" ");
                    int month2 = -1;

                    switch (date[1]) {
                        case "Jan":
                            month2 = 0;
                            break;
                        case "Feb":
                            month2 = 1;
                            break;
                        case "Mär":
                            month2 = 2;
                            break;
                        case "Apr":
                            month2 = 3;
                            break;
                        case "Mai":
                            month2 = 4;
                            break;
                        case "Jun":
                            month2 = 5;
                            break;
                        case "Jul":
                            month2 = 6;
                            break;
                        case "Aug":
                            month2 = 7;
                            break;
                        case "Sep":
                            month2 = 8;
                            break;
                        case "Okt":
                            month2 = 9;
                            break;
                        case "Nov":
                            month2 = 10;
                            break;
                        case "Dez":
                            month2 = 11;
                            break;
                    }

                    return Integer.parseInt(date[0]) == dayOfMonth && month2 == month &&
                            Integer.parseInt(date[2]) == year;
                }).findFirst().get();

                LocalTime from = LocalTime.parse(entry.getFrom(), formatter);
                LocalTime to = LocalTime.parse(entry.getTo(), formatter);
                StringBuilder builder = new StringBuilder();

                if (from.until(to, ChronoUnit.HOURS) > 0) {
                    builder.append(from.until(to, ChronoUnit.HOURS)).append(" h ");
                    builder.append(from.until(to.minusHours(from.until(to, ChronoUnit.HOURS)),
                            ChronoUnit.MINUTES)).append(" m");

                } else {
                    builder.append("0 h ").append(from.until(to, ChronoUnit.MINUTES)).append(" m");
                }

                kindOfPain.setText(entry.getKindOfPain());
                painArea.setText(entry.getPainArea());
                intensity.setText(entry.getIntensity() + "");
                medics.setText(entry.getMedics());
                duration.setText(builder);

            } catch (NoSuchElementException | IllegalArgumentException e) {
                e.printStackTrace();
                kindOfPain.setText("");
                painArea.setText("");
                intensity.setText("");
                medics.setText("");
                duration.setText("");
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @NonNull
    private String getTodaysDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return "" + day;
    }
}