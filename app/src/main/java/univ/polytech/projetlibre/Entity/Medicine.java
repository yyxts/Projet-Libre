package univ.polytech.projetlibre.Entity;

import java.sql.Date;

public class Medicine {
    //Entity for Medicines
    private String medname;
    private int medID;
<<<<<<< Updated upstream
    private int medexpiredate;

    //Construction Fonction
    public Medicine(int medID, String medname, int medexpiredate) {
        this.medname = medname;
        this.medID = medID;
        this.medexpiredate = medexpiredate;
=======
    private Date medexpire;

    //Construction Fonction
    public Medicine(String medname, int medID, Date medexpire) {
        this.medname = medname;
        this.medID = medID;
        this.medexpire = medexpire;
>>>>>>> Stashed changes
    }

    //Getter and Setter for Medicines
    public String getMedname() {

        return medname;
    }

    public void setMedname(String medname) {

        this.medname = medname;
    }

    public int getMedID() {

        return medID;
    }

    public void setMedID(int medID) {
        this.medID = medID;
    }

<<<<<<< Updated upstream
    public int getMedexpiredate() {
        return medexpiredate;
    }

    public void setMedexpiredate(int medexpiredate) {
        this.medexpiredate = medexpiredate;
    }


=======
    public Date getMeddate() {
        return medexpire;
    }

    public void setMeddate(Date medexpire) {
        this.medexpire = medexpire;
    }

>>>>>>> Stashed changes
}
