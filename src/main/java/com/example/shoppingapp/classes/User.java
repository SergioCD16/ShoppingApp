package com.example.shoppingapp.classes;

public class User {
    private String Name;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private int UserID;

    public User(String name, String email, String password, String phoneNumber) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.UserID = 0; // The actual UserID is generated in MySQL Database afterward
    }

    public User(String name, String email, String password, String phoneNumber, int userID) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.UserID = userID;
    }

    public String getName() {
        return this.Name;
    }
    public String getEmail() {
        return this.Email;
    }
    public String getPassword() {
        return this.Password;
    }
    public String getPhoneNumber() {
        return this.PhoneNumber;
    }
    public int getUserID() {
        return this.UserID;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setEmail(String email) {
        this.Email = email;
    }
    public void setPassword(String password) {
        this.Password = password;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.PhoneNumber = phoneNumber;
    }
    public void setUserID(int userID) {
        this.UserID = userID;
    }

    @Override
    public String toString() {
        return "Name :" + this.Name + ", Email: " + this.Email + ", Password: " + this.Password +
                ", Phone Number: " + this.PhoneNumber + ", UserID: " + this.UserID;
    }
}
