package univ.polytech.projetlibre.Entity;

public class Medicine {
    //Entity for Medicines
    private String medname;
    private int medID;

    //Construction Fonction
    public Medicine(String medname, int medID) {
        this.medname = medname;
        this.medID = medID;
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
}
