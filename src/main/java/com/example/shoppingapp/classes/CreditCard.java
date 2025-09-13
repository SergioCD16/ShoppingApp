package com.example.shoppingapp.classes;

import java.util.Date;

public class CreditCard {
    private String Name;
    private String Number;
    private String CVV;
    private Date ExpDate;

    public CreditCard(String name, String number, String cvv, Date expDate) {
        this.Name = name;
        this.Number = number;
        this.CVV = cvv;
        this.ExpDate = expDate;
    }

    public String getName() {
        return this.Name;
    }
    public String getNumber() {
        return this.Number;
    }
    public String getCVV() {
        return this.CVV;
    }
    public Date getExpDate() {
        return this.ExpDate;
    }
    public void setName(String name) {
        this.Name = name;
    }
    public void setNumber(String number) {
        this.Number = number;
    }
    public void setCVV(String cvv) {
        this.CVV = cvv;
    }
    public void setExpDate(Date expDate) {
        this.ExpDate = expDate;
    }

    public String toString() {
        return ("Holder Name: " + (this.Name) + ", Number: " + (this.Number) +
                ", CVV: " + (this.CVV) + ", Expiration Date: " + (this.ExpDate));
    }
}
