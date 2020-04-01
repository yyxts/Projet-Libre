package univ.polytech.projetlibre.Entity;

public class Medicine {
    //Entity for Medicines
    private String medname;
    private int medID;
    private int medexpiredate;

    //Construction Fonction
    public Medicine(int medID, String medname, int medexpiredate) {
        this.medname = medname;
        this.medID = medID;
        this.medexpiredate = medexpiredate;
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

    public int getMedexpiredate() {
        return medexpiredate;
    }

    public void setMedexpiredate(int medexpiredate) {
        this.medexpiredate = medexpiredate;
    }


}
