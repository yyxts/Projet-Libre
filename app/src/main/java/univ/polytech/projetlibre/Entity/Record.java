package univ.polytech.projetlibre.Entity;


import java.sql.Date;
import java.sql.Time;

public class Record {

    //String sql3 = "create table if not exists record(" +
    //                "recid integer NOT NULL PRIMARY KEY, " +
    //                "recdate integer, " +
    //                "rectime integer, " +
    //                "recdosage text, " +
    //                "reccomment text, " +
    //                "recuserid integer, " +
    //                "recmedid integer, " +
    //                "FOREIGN KEY(recuserid) REFERENCES user(userid), " +
    //                "FOREIGN KEY(recmedid) REFERENCES medicine(medid))";

    //Entity for Records
    private int recID;
    private int userID;
    private int medID;
    private int recdate;
    private int rectime;
    private String recdosage;
    private String reccomment;


    //Construction Fonction
    public Record(int recID, int recdate, int rectime, String recdosage, String reccomment, int userID, int medID) {
        this.recID = recID;
        this.userID = userID;
        this.medID = medID;
        this.recdate = recdate;
        this.rectime = rectime;
        this.recdosage = recdosage;
        this.reccomment = reccomment;
    }

    //Getter and Setter for Records
    public int getRecID() {
        return recID;
    }

    public void setRecID(int recID) {
        this.recID = recID;
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

    public String getRecdosage() {
        return recdosage;
    }

    public void setRecdosage(String recdosage) {
        this.recdosage = recdosage;
    }

    public String getReccomment() {
        return reccomment;
    }

    public void setReccomment(String reccomment) {
        this.reccomment = reccomment;
    }

}
