package com.example.shoppingapp.classes;

import java.sql.Date;
import java.time.LocalDate;

public class CreditCard {
    private String name;
    private String number;
    private String cvv;
    private LocalDate expDate;

    public CreditCard(String name, String number, String cvv, LocalDate expDate) {
        this.name = name;
        this.number = number;
        this.cvv = cvv;
        this.expDate = expDate;
    }

    public String getName() {
        return this.name;
    }
    public String getNumber() {
        return this.number;
    }
    public String getCVV() {
        return this.cvv;
    }
    public LocalDate getExpDate() {
        return this.expDate;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public void setCVV(String cvv) {
        this.cvv = cvv;
    }
    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        return "Holder Name: " + this.name + ", Number: " + this.number +
                ", CVV: " + this.cvv + ", Expiration Date: " + this.expDate;
    }

    public static Date LocalDatetoSQLDate(LocalDate localDate) {
        return (localDate != null) ? Date.valueOf(localDate) : null;
    }

    public static LocalDate SQLDatetoLocalDate(Date sqlDate) {
        return (sqlDate != null) ? sqlDate.toLocalDate() : null;
    }
}

