package com.example.shoppingapp.controllers;

import com.example.shoppingapp.classes.*;

public class UserStore {

    private static UserStore instance;

    private User user;
    private IndividualUser indUser;
    private BusinessUser busUser;
    private Address address;
    private CreditCard creditCard;
    private int userID;

    private UserStore() {}

    public static UserStore getInstance() {
        if (instance == null) {
            instance = new UserStore();
        }
        return instance;
    }

    public void setUser(User user, IndividualUser indUser, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.indUser = indUser;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public void setUser(User user, IndividualUser indUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.indUser = indUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public void setUser(User user, BusinessUser busUser, Address address, CreditCard creditCard, int userID) {
        this.user = user;
        this.busUser = busUser;
        this.address = address;
        this.creditCard = creditCard;
        this.userID = userID;
    }

    public User getUser() { return user; }
    public IndividualUser getIndUser() { return indUser; }
    public BusinessUser getBusUser() { return busUser; }
    public Address getAddress() { return address; }
    public CreditCard getCreditCard() { return creditCard; }
    public int getUserID() { return userID; }
}