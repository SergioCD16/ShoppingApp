package com.example.shoppingapp.classes;

public class Address {
    private String StreetName;
    private String Number;
    private String ZipCode;
    private String City;

    public Address(String streetName, String number, String zipCode, String city) {
        this.StreetName = streetName;
        this.Number = number;
        this.ZipCode = zipCode;
        this.City = city;
    }

    public String getStreetName() {
        return this.StreetName;
    }
    public String getNumber() {
        return this.Number;
    }
    public String getZipCode() {
        return this.ZipCode;
    }
    public String getCity() {
        return this.City;
    }
    public void setStreetName(String streetName) {
        this.StreetName = streetName;
    }
    public void setNumber(String number) {
        this.Number = number;
    }
    public void setZipCode(String zipCode) {
        this.ZipCode = zipCode;
    }
    public void setCity(String city) {
        this.City = city;
    }

    @Override
    public String toString() {
        return ("Street Name: " + (this.StreetName) + ", Number: " + (this.Number) + ", Zip Code: " +
                (this.ZipCode) + ", City: " + (this.City));
    }

    public static String capitalizeWords(String input) {
        String[] words = input.trim().toLowerCase().split("\\s+");
        StringBuilder capitalized = new StringBuilder();

        for (String word : words) {
            capitalized.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1))
                    .append(" ");
        }

        return capitalized.toString().trim();
    }
}
