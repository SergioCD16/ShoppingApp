package com.example.shoppingapp.classes;

import java.util.regex.*;

public class CheckInput {
    /**
     * Checks an Email to have the correct format
     **/
    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        Matcher result = pattern.matcher(email);
        return result.matches();
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

    /**
     * Checks a number to be whole: Direction (5 digits max), Zip Code (5 digits max),
     * Phone Number (9 digits max), Credit Card (16 digits max)
     **/
    public static boolean checkNumber(String number, int limit) {
        if (number.length() == limit) {
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
}
