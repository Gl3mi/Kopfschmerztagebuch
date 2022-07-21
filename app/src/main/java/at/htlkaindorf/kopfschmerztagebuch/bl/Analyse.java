package at.htlkaindorf.kopfschmerztagebuch.bl;

import android.content.Context;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import at.htlkaindorf.kopfschmerztagebuch.beans.Analysis;
import at.htlkaindorf.kopfschmerztagebuch.beans.Entry;

public class Analyse {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH : mm");
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd LLL yyyy", Locale.GERMAN);
    private List<Entry> entries;
    private double averageIntensity = 0;
    private String medics = null;
    private int count = 0;
    private final double[] countKindOfPain = new double[5];
    private final int[] countArea = new int[9];
    private final double[] averageDuration;
    private List<Integer> day = new ArrayList<>();
    private int numberOfOccurrences = 1;
    List<String> test = new ArrayList<>();


    public Analyse(Context context) {
        Session session = new Session(context);
        this.entries = session.getEntries("data");

        if (entries == null) {
            entries = new ArrayList<>();
        }

        this.averageDuration = new double[entries.size()];
    }

    public Analysis createAnalysis() {
        String commonArea = null;
        int streak = 0;
        int painless = 0;
        List<String> percentage = new ArrayList<>();


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

                System.out.println(entry.getPainArea());

                Pattern p = Pattern.compile("[+-]?\\d+");
                Matcher m = p.matcher(entry.getPainArea());

                while ( m.find() ) {
                    test.add(entry.getPainArea().substring(m.start(), m.end()));

                    /*switch (Integer.parseInt(s)) {
                        case 1:
                            countArea[0]++;
                            break;
                        case 2:
                            countArea[1]++;
                            break;
                        case 3:
                            countArea[2]++;
                            break;
                        case 4:
                            countArea[3]++;
                            break;
                        case 5:
                            countArea[4]++;
                            break;
                        case 6:
                            countArea[5]++;
                            break;
                        case 7:
                            countArea[6]++;
                            break;
                        case 8:
                            countArea[7]++;
                            break;
                        case 9:
                            countArea[8]++;
                            break;
                    }*/
                }


                LocalTime from = LocalTime.parse(entry.getFrom(), timeFormatter);
                LocalTime to = LocalTime.parse(entry.getTo(), timeFormatter);
                StringBuilder builder = new StringBuilder();

                if (from.until(to, ChronoUnit.HOURS) > 0) {
                    builder.append(from.until(to, ChronoUnit.HOURS)).append(";");
                    builder.append(from.until(to.minusHours(from.until(to, ChronoUnit.HOURS)),
                            ChronoUnit.MINUTES));
                    String[] splitter = builder.toString().split(";");
                    averageDuration[count++] = Double.parseDouble(splitter[0]) + Double.parseDouble(splitter[1]) / 60;

                } else {
                    builder.append(from.until(to, ChronoUnit.MINUTES));
                    averageDuration[count++] = Double.parseDouble(builder.toString()) / 60;
                }
            }
        });

        System.out.println(test.toString());



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

        return new Analysis(commonArea, Arrays.stream(averageDuration).max().orElse(0) + " h",
                numberOfOccurrences - 1, streak, averageIntensity / numberOfOccurrences,
                medics, percentage, painless);
    }
}
