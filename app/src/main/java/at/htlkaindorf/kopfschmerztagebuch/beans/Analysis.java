package at.htlkaindorf.kopfschmerztagebuch.beans;

import java.util.HashMap;
import java.util.List;

public class Analysis {
    private String commonArea;
    private String averageDuration;
    private int numberOfOccurrences;
    private int streak;
    private double averageIntensity;
    private String medics;
    private List<String> percentage;

    public Analysis(String commonArea, String averageDuration, int numberOfOccurrences, int streak, double averageIntensity, String medics, List<String> percentage) {
        this.commonArea = commonArea;
        this.averageDuration = averageDuration;
        this.numberOfOccurrences = numberOfOccurrences;
        this.streak = streak;
        this.averageIntensity = averageIntensity;
        this.medics = medics;
        this.percentage = percentage;
    }

    public String getCommonArea() {
        return commonArea;
    }

    public void setCommonArea(String commonArea) {
        this.commonArea = commonArea;
    }

    public String getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(String averageDuration) {
        this.averageDuration = averageDuration;
    }

    public int getNumberOfOccurrences() {
        return numberOfOccurrences;
    }

    public void setNumberOfOccurrences(int numberOfOccurrences) {
        this.numberOfOccurrences = numberOfOccurrences;
    }

    public int getStreak() {
        return streak;
    }

    public void setStreak(int streak) {
        this.streak = streak;
    }

    public double getAverageIntensity() {
        return averageIntensity;
    }

    public void setAverageIntensity(double averageIntensity) {
        this.averageIntensity = averageIntensity;
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

    public void setPercentage(List<String> percentage) {
        this.percentage = percentage;
    }
}
