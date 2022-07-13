package at.htlkaindorf.kopfschmerztagebuch.beans;

import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Entry {
    private String kindOfPain;
    private String[] painArea;
    private int intensity;
    private Time from;
    private Time to;
    private LocalDate date;
    private String medics;
    private String[] attendantSymptoms;
    private String comment;
    private boolean checkMedic;

    public Entry(String kindOfPain, String comment, boolean checkMedic) {
        this.kindOfPain = kindOfPain;
        this.painArea = null;
        this.intensity = 0;
        this.from = null;
        this.to = null;
        this.date = null;
        this.medics = null;
        this.attendantSymptoms = null;
        this.comment = comment;
        this.checkMedic = checkMedic;
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

    public String[] getPainArea() {
        return painArea;
    }

    public void setPainArea(String[] painArea) {
        this.painArea = painArea;
    }

    public int getIntensity() {
        return intensity;
    }

    public void setIntensity(int intensity) {
        this.intensity = intensity;
    }

    public Time getFrom() {
        return from;
    }

    public void setFrom(Time from) {
        this.from = from;
    }

    public Time getTo() {
        return to;
    }

    public void setTo(Time to) {
        this.to = to;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getMedics() {
        return medics;
    }

    public void setMedics(String medics) {
        this.medics = medics;
    }

    public String[] getAttendantSymptoms() {
        return attendantSymptoms;
    }

    public void setAttendantSymptoms(String[] attendantSymptoms) {
        this.attendantSymptoms = attendantSymptoms;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "kindOfPain='" + kindOfPain + '\'' +
                ", painArea=" + Arrays.toString(painArea) +
                ", intensity=" + intensity +
                ", from=" + from +
                ", to=" + to +
                ", date=" + date +
                ", medics='" + medics + '\'' +
                ", attendantSymptoms=" + Arrays.toString(attendantSymptoms) +
                ", comment='" + comment + '\'' +
                '}';
    }
}
