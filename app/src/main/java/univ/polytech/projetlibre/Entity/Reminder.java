package univ.polytech.projetlibre.Entity;

import java.sql.Time;

public class Reminder {
    //Entity for Reminder
    private int remID;
    private int userID;
    private int medID;
    private int remtime;

    //Construction Fonction
    public Reminder(int remID, int userID, int medID, int remtime) {
        this.remID = remID;
        this.userID = userID;
        this.medID = medID;
        this.remtime = remtime;
    }

    //Getter and Setter for Reminders
    public int getRemID() {
        return remID;
    }

    public void setRemID(int remID) {
        this.remID = remID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getMedID() {
        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

    public int getRemtime() {
        return remtime;
    }

    public void setRemtime(int remtime) {
        this.remtime = remtime;
    }
}
