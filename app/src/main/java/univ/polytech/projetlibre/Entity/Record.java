package univ.polytech.projetlibre.Entity;


import java.sql.Date;
import java.sql.Time;

public class Record {
    //Entity for Records
    private int userID;
    private int medID;
    private int recdate;
    private int rectime;

    //Construction Fonction
    public Record(int userID, int medID, int recdate, int rectime) {
        this.userID = userID;
        this.medID = medID;
        this.recdate = recdate;
        this.rectime = rectime;
    }

    //Getter and Setter for Records
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

    public int getRecdate() {
        return recdate;
    }

    public void setRecdate(int recdate) {
        this.recdate = recdate;
    }

    public int getRectime() {
        return rectime;
    }

    public void setRectime(int rectime) {
        this.rectime = rectime;
    }
}
