package at.htlkaindorf.kopfschmerztagebuch.bl;

import android.content.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import at.htlkaindorf.kopfschmerztagebuch.beans.Analysis;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class Analyse {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH : mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd LLL yyyy", Locale.GERMAN);

    private String medics = "";
    private int count = 0;
    private int numberOfOccurrences = 0;

    private double averageIntensity = 0;
    private final double[] averageDuration;
    private final double[] countKindOfPain = new double[5];

    private List<Entry> entries;
    private List<Integer> day = new ArrayList<>();
    private final List<Integer> commonAreas = new ArrayList<>();

    public Analyse(Context context) {
        Session session = new Session(context);
        this.entries = session.getEntries("data");

        if (entries == null) {
            entries = new ArrayList<>();
        }

        this.averageDuration = new double[entries.size()];
    }

    public Analysis createAnalysis() {
        int streak = 0;
        int painless = 0;
        String commonArea = null;
        List<String> percentage = new ArrayList<>();
        Pattern p = Pattern.compile("[+-]?\\d+");

        entries.forEach(entry -> {
            if (LocalDate.parse(entry.getDate(), dateFormatter).getMonth() == LocalDate.now().getMonth()) {
                numberOfOccurrences++;
                medics += entry.getMedics();
                averageIntensity += entry.getIntensity();

                day.add(LocalDate.parse(entry.getDate(), dateFormatter).getDayOfMonth());

                switch (entry.getKindOfPain()) {
                    case "Migräne":
                        countKindOfPain[0]++;
                        break;
                    case "Spannungskopfschmerzen":
                        countKindOfPain[1]++;
                        break;
                    case "Cluster-Kopfschmerzen":
                        countKindOfPain[2]++;
                        break;
                    case "Sinusitis-Kopfschmerzen":
                        countKindOfPain[3]++;
                        break;
                    case "Sonstige Kopfschmerzen":
                        countKindOfPain[4]++;
                        break;
                }

                Matcher m = p.matcher(entry.getPainArea());

                while (m.find()) {
                    commonAreas.add(Integer.parseInt(entry.getPainArea().substring(m.start(), m.end())));
                }

                LocalTime from = LocalTime.parse(entry.getFrom(), timeFormatter);
                LocalTime to = LocalTime.parse(entry.getTo(), timeFormatter);
                StringBuilder builder = new StringBuilder();

                if (from.until(to, ChronoUnit.HOURS) > 0) {
                    builder.append(from.until(to, ChronoUnit.HOURS)).append(";");
                    builder.append(from.until(to.minusHours(from.until(to, ChronoUnit.HOURS)),
                            ChronoUnit.MINUTES));
                    String[] splitter = builder.toString().split(";");
                    averageDuration[count++] = Double.parseDouble(splitter[0]) +
                            Double.parseDouble(splitter[1]) / 60;

                } else {
                    builder.append(from.until(to, ChronoUnit.MINUTES));
                    averageDuration[count++] = Double.parseDouble(builder.toString()) / 60;
                }
            }
        });

        try {
            switch (mostFrequent(commonAreas)) {
                case 1:
                    commonArea = "1-Oben";
                    break;
                case 2:
                    commonArea = "2-Stirn";
                    break;
                case 3:
                    commonArea = "3-Hinterkopf";
                    break;
                case 4:
                    commonArea = "4-Wange L";
                    break;
                case 5:
                    commonArea = "5-Wange R";
                    break;
                case 6:
                    commonArea = "6-Schläfe L";
                    break;
                case 7:
                    commonArea = "7-Schläfe R";
                    break;
                case 8:
                    commonArea = "8-Augenbereich L";
                    break;
                case 9:
                    commonArea = "9-Augenbereich R";
                    break;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            commonArea = "-";
        }

        day = day.stream().sorted().collect(Collectors.toList());

        if (day.size() != 0) {
            if (day.get(day.size() - 1) != LocalDate.now().getDayOfMonth()) {
                painless = LocalDate.now().minusDays(day.get(day.size() - 1)).getDayOfMonth();
            }

            streak = 1;
            for (int i = 0; i < day.size() - 1; i++) {
                if (day.get(i) == (day.get(i + 1) - 1)) {
                    streak++;
                }
            }
        }

        percentage.add("Migräne;" + countKindOfPain[0] /
                Arrays.stream(countKindOfPain).sum() * 100);
        percentage.add("Spannungskopfschmerzen;" + countKindOfPain[1] /
                Arrays.stream(countKindOfPain).sum() * 100);
        percentage.add("Cluster-Kopfschmerzen;" + countKindOfPain[2] /
                Arrays.stream(countKindOfPain).sum() * 100);
        percentage.add("Sinusitis-Kopfschmerzen;" + countKindOfPain[3] /
                Arrays.stream(countKindOfPain).sum() * 100);
        percentage.add("Sonstige Kopfschmerzen;" + countKindOfPain[4] /
                Arrays.stream(countKindOfPain).sum() * 100);

        return new Analysis(commonArea, Arrays.stream(averageDuration).max().orElse(0),
                numberOfOccurrences, streak, averageIntensity / numberOfOccurrences,
                medics, percentage, painless);
    }

    public static Integer mostFrequent(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }

        Map<Integer, Integer> counterMap = new HashMap<>();
        Integer maxValue = 0;
        Integer mostFrequentValue = null;

        for (Integer valueAsKey : list) {
            Integer counter = counterMap.get(valueAsKey);
            counterMap.put(valueAsKey, counter == null ? 1 : counter + 1);
            counter = counterMap.get(valueAsKey);

            if (counter > maxValue) {
                maxValue = counter;
                mostFrequentValue = valueAsKey;
            }
        }
        return mostFrequentValue;
    }
}
