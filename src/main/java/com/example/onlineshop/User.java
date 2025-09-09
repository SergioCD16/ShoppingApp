package com.example.onlineshop;

public class User {
    private String Name;
    private String Email;
    private String Password;
    private String PhoneNumber;
    private Address Address;
    private CreditCard CreditCard;

    public User(String name, String email, String password, String phoneNumber, Address address, CreditCard creditCard) {
        this.Name = name;
        this.Email = email;
        this.Password = password;
        this.PhoneNumber = phoneNumber;
        this.Address = address;
        this.CreditCard = creditCard;
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

    public String toString() {
        return "Name :" + this.Name + ", Email: " + this.Email + ", Password: " + this.Password +
                ", Phone Number: " + this.PhoneNumber + ", Address: { " + (this.Address.toString()) +
                " }, Credit Card: { " + (this.CreditCard.toString()) + " }";
    }
}
