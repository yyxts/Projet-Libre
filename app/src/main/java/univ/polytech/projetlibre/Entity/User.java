package univ.polytech.projetlibre.Entity;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{
    //Entity for Users
    private String lastname;
    private String firstname;
    private int userID;
    private String address;
    private Date birthday;
    private int sex;
    private String phone;

    //Construction Fonction
    public User(String lastname, String firstname, int userID, String address, Date birthday, int sex, String phone) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.userID = userID;
        this.address = address;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
    }

    //Getter and Setter for Users
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
