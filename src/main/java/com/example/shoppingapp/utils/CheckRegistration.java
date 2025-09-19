package com.example.shoppingapp.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.*;
import java.time.LocalDate;

public class CheckRegistration {

    // IndOrBus = true (individual user), IndOrBus = false (business user)
    public static boolean checkUserRegistration(boolean IndorBus, String username, String email, String DNICIF, String phoneNumber, String password1, String password2) throws SQLException, ClassNotFoundException {
        // Check Username
        if (!checkBlankField(username)) {
            FXUtils.showError("Error in username field", "Username field is blank");
            return false;
        }
        if (!checkStringLength(username, 20, 3)) {
            FXUtils.showError("Error in username field", "Incorrect length");
            return false;
        }
        // Check Email
        if (!checkBlankField(email)) {
            FXUtils.showError("Error in email field", "Email field is blank");
            return false;
        }
        if (!checkEmail(email)) {
            FXUtils.showError("Error in email field", "Invalid format");
            return false;
        }
        if (!checkStringLength(email, 50, 9)) {
            FXUtils.showError("Error in email field", "Incorrect length");
            return false;
        }
        if (emailExists(email)) {
            FXUtils.showError("Error in email field", "This email is already registered");
            return false;
        }
        // Check DNI or CIF
        if (IndorBus) {
            if (!checkBlankField(DNICIF)) {
                FXUtils.showError("Error in DNI field", "DNI field is blank");
                return false;
            }
            if (!checkDNI(DNICIF)) {
                FXUtils.showError("Error in DNI field", "Invalid format");
                return false;
            }
            if (dniExists(DNICIF)) {
                FXUtils.showError("Error in DNI field", "This DNI is already registered");
                return false;
            }
        } else {
            if (!checkBlankField(DNICIF)) {
                FXUtils.showError("Error in CIF field", "CIF field is blank");
                return false;
            }
            if (!checkCIF(DNICIF)) {
                FXUtils.showError("Error in CIF field", "Invalid format");
                return false;
            }
            if (cifExists(DNICIF)) {
                FXUtils.showError("Error in CIF field", "This CIF is already registered");
                return false;
            }
        }
        // Check Phone Number
        if (!checkBlankField(phoneNumber)) {
            FXUtils.showError("Error in phone number field", "Phone number field is blank");
            return false;
        }
        if (!checkNumberIsInteger(phoneNumber)) {
            FXUtils.showError("Error in phone number field", "Invalid format");
            return false;
        }
        if (!checkNumberLength(phoneNumber, 10, 7)) {
            FXUtils.showError("Error in phone number field", "Incorrect length");
            return false;
        }
        // Check Password and Confirm Password fields
        if (!checkBlankField(password1)) {
            FXUtils.showError("Error in password field", "Password field is blank");
            return false;
        }
        if (!checkStringLength(password1, 30, 8)) {
            FXUtils.showError("Error in password field", "Incorrect length (minium 8 characters)");
            return false;
        }
        if (!checkBlankField(password2)) {
            FXUtils.showError("Error in confirm password field", "Confirm password field is blank");
            return false;
        }
        if (!checkPasswordEquals(password1, password2)) {
            FXUtils.showError("Error in password field", "Confirm password field isn't equal to password field");
            return false;
        }
        return true;
    }

    public static boolean checkAddressRegistration(String streetName, String number, String zipCode, String city) throws SQLException, ClassNotFoundException {
        // Check Street Name
        if (!checkBlankField(streetName)) {
            FXUtils.showError("Error in street name field", "Street name field is blank");
            return false;
        }
        if (!checkStringLength(streetName, 30, 2)) {
            FXUtils.showError("Error in street name field", "Incorrect length");
            return false;
        }
        // Check Street Number
        if (!checkBlankField(number)) {
            FXUtils.showError("Error in street number field", "Street number field is blank");
            return false;
        }
        if (!checkNumberIsInteger(number)) {
            FXUtils.showError("Error in street number field", "Invalid format");
            return false;
        }
        if (!checkNumberLength(number, 5, 1)) {
            FXUtils.showError("Error in street number field", "Incorrect length");
            return false;
        }
        // Check Zip Code
        if (!checkBlankField(zipCode)) {
            FXUtils.showError("Error in zip code field", "Zip code field is blank");
            return false;
        }
        if (!checkNumberIsInteger(zipCode)) {
            FXUtils.showError("Error in zip code field", "Invalid format");
            return false;
        }
        if (!checkNumberLength(zipCode, 7, 4)) {
            FXUtils.showError("Error in zip code field", "Incorrect length");
            return false;
        }
        // Check City
        if (!checkBlankField(city)) {
            FXUtils.showError("Error in city field", "City field is blank");
            return false;
        }
        if (!checkStringLength(city, 30, 3)) {
            FXUtils.showError("Error in city field", "Incorrect length");
            return false;
        }
        return true;
    }

    public static boolean checkCreditCardRegistration(String creditCardNumber, String fullName, String CVV, String expirationDate) throws SQLException, ClassNotFoundException {
        // Check Credit Card Number
        if (!checkBlankField(creditCardNumber)) {
            FXUtils.showError("Error in credit card number field", "Credit card number field is blank");
            return false;
        }
        if (!checkNumberIsInteger(creditCardNumber)) {
            FXUtils.showError("Error in credit card number field", "Incorrect format");
            return false;
        }
        if (!checkNumberLength(creditCardNumber, 18, 14)) {
            FXUtils.showError("Error in credit card number field", "Incorrect length");
            return false;
        }
        // Check Full Name
        if (!checkBlankField(fullName)) {
            FXUtils.showError("Error in full name field", "Full name field is blank");
            return false;
        }
        if (!checkFullName(fullName)) {
            FXUtils.showError("Error in full name field", "Invalid format");
            return false;
        }
        if (!checkStringLength(fullName, 70, 2)) {
            FXUtils.showError("Error in full name field", "Incorrect length");
            return false;
        }
        // Check CVV
        if (!checkBlankField(CVV)) {
            FXUtils.showError("Error in CVV field", "CVV field is blank");
            return false;
        }
        if (!checkNumberIsInteger(CVV)) {
            FXUtils.showError("Error in CVV field", "Invalid format");
            return false;
        }
        if (!checkNumberLength(CVV, 5, 3)) {
            FXUtils.showError("Error in CVV field", "Incorrect length");
            return false;
        }
        // Check Expiration Date
        if (!checkBlankField(expirationDate)) {
            FXUtils.showError("Error in expiration date field", "Expiration Date field is blank");
            return false;
        }
        if (!checkExpirationDateFormat(expirationDate)) {
            FXUtils.showError("Error in expiration date field", "Invalid format (dd-MM-yyyy)");
            return false;
        }
        if (!checkExpirationDateTime(expirationDate)) {
            FXUtils.showError("Error in expiration date field", "The date must be after today");
            return false;
        }
        return true;
    }

    public static boolean checkProductRegistration(String title, String price, String stock, String description, String picture) throws SQLException, ClassNotFoundException {
        // Check Title
        if (!checkBlankField(title)) {
            FXUtils.showError("Error in title field", "Username field is blank");
            return false;
        }
        if (!checkStringLength(title, 70, 10)) {
            FXUtils.showError("Error in title field", "Incorrect length");
            return false;
        }
        // Check Price
        if (!checkBlankField(price)) {
            FXUtils.showError("Error in price field", "Price field is blank");
            return false;
        }
        if (!checkNumberIsFloat(price)) {
            FXUtils.showError("Error in price field", "Incorrect format");
            return false;
        }
        if (!checkFloatLength(price, 5000F, 0.01F)) {
            FXUtils.showError("Error in price field", "Incorrect price, needs to be between 0.01 and 5000");
            return false;
        }
        // Check Stock
        if (!checkBlankField(stock)) {
            FXUtils.showError("Error in stock field", "Stock field is blank");
            return false;
        }
        if (!checkNumberIsInteger(stock)) {
            FXUtils.showError("Error in stock field", "Incorrect format");
            return false;
        }
        if (!checkNumberLength(stock, 1000, 1)) {
            FXUtils.showError("Error in stock field", "Incorrect stock, needs to be between 1 and 1000");
            return false;
        }
        // Check Picture

        // Check Description
        if (!checkBlankField(description)) {
            FXUtils.showError("Error in description field", "Description field is blank");
            return false;
        }
        if (!checkStringLength(description, 300, 10)) {
            FXUtils.showError("Error in description field", "Incorrect length");
            return false;
        }
        return true;
    }

    /**
     * Checks an Email to have the correct format
     **/
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        Matcher result = pattern.matcher(email);
        return result.matches();
    }

    public static boolean emailExists(String email) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM User WHERE Email = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks a DNI to have the correct format (8 numbers and 1 letter)
     **/
    public static boolean checkDNI(String dni) {
        if (dni.length() != 9 || !(Character.isLetter(dni.charAt(8)))) {
            return false;
        } else {
            String myDNI = "", number;
            String[] oneNine = {"0","1","2","3","4","5","6","7","8","9"};
            for (int i = 0; i < dni.length() - 1; i++) {
                number = dni.substring(i, i+1);
                for (int j = 0; j < oneNine.length; j++) {
                    if (number.equals(oneNine[j])) {
                        myDNI += oneNine[j];
                    }
                }
            }
            return myDNI.length() == 8;
        }
    }

    public static boolean dniExists(String dni) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM IndividualUser WHERE DNI = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks a CIF to have the correct format (1 letter and 8 numbers)
     **/
    public static boolean checkCIF(String cif) {
        if (cif.length() != 9 || (!Character.isLetter(cif.charAt(0)))) {
            return false;
        } else {
            String myCIF = "", number;
            String[] oneNine = {"0","1","2","3","4","5","6","7","8","9"};
            for (int i = 1; i < cif.length(); i++) {
                number = cif.substring(i, i+1);
                for (int j = 0; j < oneNine.length; j++) {
                    if (number.equals(oneNine[j])) {
                        myCIF += oneNine[j];
                    }
                }
            }
            return myCIF.length() == 8;
        }
    }

    public static boolean cifExists(String cif) throws SQLException, ClassNotFoundException {
        String sql = "SELECT 1 FROM BusinessUser WHERE CIF = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cif);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    /**
     * Checks a number to be whole: CVV (3-5 digits), Direction (1-5 digits), Zip Code (4-7 digits),
     * Phone Number (7-10 digits), Credit Card (14-18 digits)
     **/
    public static boolean checkNumberLength(String number, int upperLimit, int lowerLimit) {
        if (number.length() >= lowerLimit && number.length() <= upperLimit) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkNumberIsInteger(String number) {
        return number.matches("\\d+");
    }

    public static boolean checkFloatLength(String number, float upperLimit, float lowerLimit) {
        if (number.length() >= lowerLimit && number.length() <= upperLimit) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkNumberIsFloat(String number) {
        try {
            Float.parseFloat(number);
            return true; // valid float (integers like "123" also work)
        } catch (NumberFormatException e) {
            return false; // not a valid number
        }
    }

    /**
     * Checks the length of a String: Full Name (2-70 digits), Username (3-20 digits),
     * Email (9-50 digits), Password (8-30 digits), Street Name (2-30 digits), City (3-30 digits)
     **/
    public static boolean checkStringLength(String name, int upperLimit, int lowerLimit) {
        int length = name.trim().length();
        return length >= lowerLimit && length <= upperLimit;
    }

    public static boolean checkPasswordEquals(String password1, String password2) {
        return password1.equals(password2);
    }

    public static boolean checkFullName(String fullName) {
        String regex = "^[A-Za-zÀ-ÖØ-öø-ÿ]+([\\s'-][A-Za-zÀ-ÖØ-öø-ÿ]+)+$";
        String[] words = fullName.trim().split("\\s+");

        return (words.length >=2) && (fullName.trim().matches(regex));
    }

    public static boolean checkBlankField(String field) {
        return field != null && !field.isBlank();
    }

    public static boolean checkExpirationDateFormat(String expDateS) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate expDate = LocalDate.parse(expDateS, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static boolean checkExpirationDateTime(String expDateS) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate expDate = LocalDate.parse(expDateS, formatter);

        return !expDate.isBefore(LocalDate.now());
    }
}
