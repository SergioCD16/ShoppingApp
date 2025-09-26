package com.example.shoppingapp.classes;

import java.util.ArrayList;

public class IndividualUser extends User {
    private String DNI;

    public IndividualUser(String name, String email, String password, String phoneNumber, String dni, String type) {
        super(name, email, password, phoneNumber, type);
        this.DNI = dni;
    }

    public IndividualUser(String name, String email, String password, String phoneNumber, int userID, String dni, String type) {
        super(name, email, password, phoneNumber, type, userID);
        this.DNI = dni;
    }

    public String getDNI() {
        return this.DNI;
    }
    public void setDNI(String dni) {
        this.DNI = dni;
    }

    @Override
    public String toString() {
        return super.toString() + ", DNI: " + (this.DNI);
    }
}
