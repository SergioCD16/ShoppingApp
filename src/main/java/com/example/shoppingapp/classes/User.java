package com.example.shoppingapp.classes;

import org.mindrot.jbcrypt.BCrypt;

public class User {
    private String Name;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private int UserID;
    private String Type;

    public User(String name, String email, String password, String phoneNumber, String type, int userID) {
        this.UserID = userID;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.Type = type;
    }

    public User(String name, String email, String password, String phoneNumber, String type) {
        this.UserID = 0;
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.Type = type;
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
    public String getType() {
        return this.Type;
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
    public void setType(String type) {
        this.Type = type;
    }

    @Override
    public String toString() {
        return "Name :" + this.Name + ", Email: " + this.Email + ", Password: " + this.Password +
                ", Phone Number: " + this.PhoneNumber + ", UserID: " + this.UserID;
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
