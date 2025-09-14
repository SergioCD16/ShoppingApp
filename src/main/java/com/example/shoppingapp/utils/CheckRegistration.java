package com.example.shoppingapp.utils;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.regex.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CheckRegistration {

    // IndOrBus = true (individual user), IndOrBus = false (business user)
    public static boolean checkCompleteRegistration(boolean IndorBus, String username, String email, String DNICIF, String phoneNumber, String password1, String password2, String streetName, String number, String zipCode, String city, String creditCardNumber, String fullName, String CVV, LocalDate expirationDate) throws SQLException, ClassNotFoundException {

        // Firstly, it checks if any field is blank
        if (!checkBlankField(username)) {
            FXUtils.showError("Error in username field", "Username field is blank");
            return false;
        }
        if (!checkBlankField(email)) {
            FXUtils.showError("Error in email field", "Email field is blank");
            return false;
        }
        if (IndorBus) {
            if (!checkBlankField(DNICIF)) {
                FXUtils.showError("Error in DNI field", "DNI field is blank");
                return false;
            }
        } else {
            if (!checkBlankField(DNICIF)) {
                FXUtils.showError("Error in CIF field", "CIF field is blank");
                return false;
            }
        }
        if (!checkBlankField(phoneNumber)) {
            FXUtils.showError("Error in phone number field", "Phone number field is blank");
            return false;
        }
        if (!checkBlankField(password1)) {
            FXUtils.showError("Error in password field", "Password field is blank");
            return false;
        }
        if (!checkBlankField(password2)) {
            FXUtils.showError("Error in confirm password field", "Confirm password field is blank");
            return false;
        }
        if (!checkBlankField(streetName)) {
            FXUtils.showError("Error in street name field", "Street name field is blank");
            return false;
        }
        if (!checkBlankField(number)) {
            FXUtils.showError("Error in street number field", "Street number field is blank");
            return false;
        }
        if (!checkBlankField(zipCode)) {
            FXUtils.showError("Error in zip code field", "Zip code field is blank");
            return false;
        }
        if (!checkBlankField(city)) {
            FXUtils.showError("Error in city field", "City field is blank");
            return false;
        }
        if (!checkBlankField(creditCardNumber)) {
            FXUtils.showError("Error in credit card number field", "Credit card number field is blank");
            return false;
        }
        if (!checkBlankField(fullName)) {
            FXUtils.showError("Error in full name field", "Full name field is blank");
            return false;
        }
        if (!checkBlankField(CVV)) {
            FXUtils.showError("Error in CVV field", "CVV field is blank");
            return false;
        }
        if (!checkBlankFieldDate(expirationDate)) {
            FXUtils.showError("Error in expiration date field", "Expiration Date field is blank");
            return false;
        }

        // Secondly, it checks the format of the email, DNI or CIF, full name and expiration date
        if (!checkEmail(email)) {
            FXUtils.showError("Error in email field", "Incorrect format");
            return false;
        }
        if (IndorBus) {
            if (!checkDNI(DNICIF)) {
                FXUtils.showError("Error in DNI field", "Incorrect format");
                return false;
            }
        } else {
            if (!checkCIF(DNICIF)) {
                FXUtils.showError("Error in CIF field", "Incorrect format");
                return false;
            }
        }
        if (!checkFullName(fullName)) {
            FXUtils.showError("Error in full name field", "Incorrect format");
            return false;
        }
        if (!checkExpirationDate(expirationDate)) {
            FXUtils.showError("Error in expiration date field", "Incorrect format (dd-MM-yyyy)");
            return false;
        }

        // Thirdly, it checks the length of cvv, street number, zip code, phone number, credit card,
        // full name, username, email, password, street name, city
        if (!checkNumberLength(CVV, 5, 3)) {
            FXUtils.showError("Error in CVV field", "Incorrect length");
            return false;
        }
        if (!checkNumberLength(number, 5, 1)) {
            FXUtils.showError("Error in street number field", "Incorrect length");
            return false;
        }
        if (!checkNumberLength(zipCode, 7, 4)) {
            FXUtils.showError("Error in zip code field", "Incorrect length");
            return false;
        }
        if (!checkNumberLength(phoneNumber, 10, 7)) {
            FXUtils.showError("Error in phone number field", "Incorrect length");
            return false;
        }
        if (!checkNumberLength(creditCardNumber, 18, 14)) {
            FXUtils.showError("Error in credit card number field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(fullName, 70, 2)) {
            FXUtils.showError("Error in full name field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(username, 20, 3)) {
            FXUtils.showError("Error in username field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(email, 50, 9)) {
            FXUtils.showError("Error in email field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(password1, 30, 8)) {
            FXUtils.showError("Error in password field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(streetName, 30, 2)) {
            FXUtils.showError("Error in street name field", "Incorrect length");
            return false;
        }
        if (!checkStringLength(city, 30, 3)) {
            FXUtils.showError("Error in city field", "Incorrect length");
            return false;
        }

        // And lastly, it checks if the email, DNI or CIF already exist in the database
        // and if both password fields are equal
        if (emailExists(email)) {
            FXUtils.showError("Error in email field", "Email already exists");
            return false;
        }
        if (IndorBus) {
            if (dniExists(DNICIF)) {
                FXUtils.showError("Error in DNI field", "DNI already exists");
                return false;
            }
        } else {
            if (cifExists(DNICIF)) {
                FXUtils.showError("Error in CIF field", "CIF already exists");
                return false;
            }
        }
        if (!checkPasswordEquals(password1, password2)) {
            FXUtils.showError("Error in password field", "Confirm password field isn't equal to password field");
            return false;
        }
        return true;
    }

    /**
     * Checks an Email to have the correct format
     **/
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
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
            try {
                Integer.parseInt(number);
                return true;
            } catch (NumberFormatException excepcion) {
                return false;
            }
        } else {
            return false;
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

    public static boolean checkBlankFieldDate(LocalDate date) {
        return date != null;
    }


    public static boolean checkExpirationDate(LocalDate expDate) {
        if (expDate == null) return false;
        return !expDate.isBefore(LocalDate.now());
    }
}
