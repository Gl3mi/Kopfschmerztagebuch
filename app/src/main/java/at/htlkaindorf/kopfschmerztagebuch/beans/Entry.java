package at.htlkaindorf.kopfschmerztagebuch.beans;

import java.sql.Time;
import java.time.LocalDate;

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

}
