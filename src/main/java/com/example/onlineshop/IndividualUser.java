package com.example.onlineshop;

import java.util.ArrayList;

public class IndividualUser extends User {
    private String DNI;
    public static ArrayList<IndividualUser> IndividualUserList = new ArrayList<IndividualUser>();

    public IndividualUser(String name, String email, String password, String phoneNumber, Address address, CreditCard creditCard, String dni) {
        super(name, email, password, phoneNumber, address, creditCard);
        this.DNI = dni;
    }

    public String getDNI() {
        return this.DNI;
    }
    public ArrayList<IndividualUser> getIndividualUserList() {
        return IndividualUserList;
    }
    public void setDNI(String dni) {
        this.DNI = dni;
    }

    @Override
    public String toString() {
        return super.toString() + ", DNI: " + (this.DNI);
    }
}
