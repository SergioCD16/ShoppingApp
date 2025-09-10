package com.example.onlineshop.classes;

import java.util.Random;

public class User {
    private String Name;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private Address Address;
    private CreditCard CreditCard;
    private int UserID;

    public User(String name, String email, String password, String phoneNumber, Address address, CreditCard creditCard) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.Address = address;
        this.CreditCard = creditCard;
        this.UserID = generateUserID();
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
    public Address getAddress() {
        return this.Address;
    }
    public CreditCard getCreditCard() {
        return this.CreditCard;
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
    public void setAddress(Address address) {
        this.Address = address;
    }
    public void setCreditCard(CreditCard creditCard) {
        this.CreditCard = creditCard;
    }
    public void setUserID(int userID) {
        this.UserID = userID;
    }

    public String toString() {
        return "Name :" + this.Name + ", Email: " + this.Email + ", Password: " + this.Password +
                ", Phone Number: " + this.PhoneNumber + ", Address: { " + (this.Address.toString()) +
                " }, Credit Card: { " + (this.CreditCard.toString()) + " }, UserID: " + this.UserID;
    }

    /**
     * Generates a unique UserID
     */
    public int generateUserID() {
        int timestamp = (int) System.currentTimeMillis();
        int randomNum = new Random().nextInt(1000000);
        return timestamp + randomNum;
    }
}
