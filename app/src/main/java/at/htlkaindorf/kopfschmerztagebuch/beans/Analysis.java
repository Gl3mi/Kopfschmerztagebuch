package at.htlkaindorf.kopfschmerztagebuch.beans;

import java.util.List;

public class Analysis {
    private final String commonArea;
    private final double averageDuration;
    private final int numberOfOccurrences;
    private final int streak;
    private final double averageIntensity;
    private String medics;
    private final int painless;
    private final List<String> percentage;

    public Analysis(String commonArea, double averageDuration, int numberOfOccurrences, int streak, double averageIntensity, String medics, List<String> percentage, int painless) {
        this.commonArea = commonArea;
        this.averageDuration = averageDuration;
        this.numberOfOccurrences = numberOfOccurrences;
        this.streak = streak;
        this.averageIntensity = averageIntensity;
        this.medics = medics;
        this.percentage = percentage;
        this.painless = painless;
    }

    public int getPainless() {
        return painless;
    }

    public String getCommonArea() {
        return commonArea;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public int getStreak() {
        return streak;
    }

    public double getAverageIntensity() {
        return averageIntensity;
    }

    public String getMedics() {
        return medics;
    }

    public void setMedics(String medics) {
        this.medics = medics;
    }

    public List<String> getPercentage() {
        return percentage;
    }
}
