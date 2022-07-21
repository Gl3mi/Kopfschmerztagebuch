package at.htlkaindorf.kopfschmerztagebuch.beans;


import androidx.annotation.NonNull;

import java.util.Objects;


public class Entry {
    private String kindOfPain;
    private String painArea;
    private int intensity;
    private String from;
    private String to;
    private String date;
    private String medics;
    private String attendantSymptoms;
    private String comment;
    private boolean checkMedic;

    public Entry(String kindOfPain, String painArea, int intensity, String from,
                 String to, String date, String medics, String attendantSymptoms,
                 String comment, boolean checkMedic) {
        this.kindOfPain = kindOfPain;
        this.painArea = painArea;
        this.intensity = intensity;
        this.from = from;
        this.to = to;
        this.date = date;
        this.medics = medics;
        this.attendantSymptoms = attendantSymptoms;
        this.comment = comment;
        this.checkMedic = checkMedic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entry entry = (Entry) o;
        return Objects.equals(date, entry.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }

    public boolean getCheckMedic() {
        return checkMedic;
    }

    public void setCheckMedic(boolean checkMedic) {
        this.checkMedic = checkMedic;
    }

    public String getKindOfPain() {
        return kindOfPain;
    }

    public void setKindOfPain(String kindOfPain) {
        this.kindOfPain = kindOfPain;
    }

    public String getPainArea() {
        return painArea;
    }

    public void setPainArea(String painArea) {
        this.painArea = painArea;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getMedics() {
        return medics;
    }

    public void setMedics(String medics) {
        this.medics = medics;
    }

    public String getAttendantSymptoms() {
        return attendantSymptoms;
    }

    public void setAttendantSymptoms(String attendantSymptoms) {
        this.attendantSymptoms = attendantSymptoms;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @NonNull
    @Override
    public String toString() {
        return "Entry{" +
                "kindOfPain='" + kindOfPain + '\'' +
                ", painArea=" + painArea +
                ", intensity=" + intensity +
                ", from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", medics='" + medics + '\'' +
                ", attendantSymptoms=" + attendantSymptoms +
                ", comment='" + comment + '\'' +
                '}';
    }
}
