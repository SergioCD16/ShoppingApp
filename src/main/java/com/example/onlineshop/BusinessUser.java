package com.example.onlineshop;

import java.util.ArrayList;

public class BusinessUser extends User {
    private String CIF;
    public static ArrayList<BusinessUser> BusinessUserList = new ArrayList<BusinessUser>();

    public BusinessUser(String name, String email, String password, String phoneNumber, Address address, CreditCard creditCard, String cif) {
        super(name, email, password, phoneNumber, address, creditCard);
        this.CIF = cif;
    }

    public String getCIF() {
        return this.CIF;
    }
    public ArrayList<BusinessUser> getBusinessUserList() {
        return BusinessUserList;
    }
    public void setCIF(String cif) {
        this.CIF = cif;
    }

    @Override
    public String toString() {
        return super.toString() + ", CIF: " + (this.CIF);
    }
}
