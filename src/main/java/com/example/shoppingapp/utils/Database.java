package com.example.shoppingapp.utils;

import com.example.shoppingapp.classes.*;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;


public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/shoppingapp?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "shopping_user";
    private static final String PASSWORD = "g76pL/~|oHAO0Z=";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addIndividualUser(User user, IndividualUser indUser, Address address, CreditCard creditCard) throws SQLException, ClassNotFoundException {
        try (Connection conn = Database.getConnection()) {

            String userSQL = "INSERT INTO user (Name, Email, Password, PhoneNumber, Type) VALUES (?, ?, ?, ?, ?)";
            int userID;
            try (PreparedStatement stmt = conn.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user instanceof IndividualUser ? "INDIVIDUAL" : "BUSINESS");

                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    userID = rs.getInt(1);
                }
            }

            String individualUserSQL = "INSERT INTO individualuser (UserID, DNI) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(individualUserSQL)) {
                stmt.setInt(1, userID);
                stmt.setString(2, indUser.getDNI());

                stmt.executeUpdate();
            }

            String addressSQL = "INSERT INTO address (StreetName, Number, ZipCode, City, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(addressSQL)) {
                stmt.setString(1, address.getStreetName());
                stmt.setString(2, address.getNumber());
                stmt.setString(3, address.getZipCode());
                stmt.setString(4, address.getCity());
                stmt.setInt(5, userID);

                stmt.executeUpdate();
            }

            String cardSQL = "INSERT INTO creditcard (Name, Number, ExpDate, CVV, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(cardSQL)) {
                stmt.setString(1, creditCard.getName());
                stmt.setString(2, creditCard.getNumber());

                LocalDate exp = creditCard.getExpDate();
                Date sqlExpDate = CreditCard.LocalDatetoSQLDate(exp);
                stmt.setDate(3, sqlExpDate);

                stmt.setString(4, creditCard.getCVV());
                stmt.setInt(5, userID);

                stmt.executeUpdate();
            }

            System.out.println("User saved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addBusinessUser(User user, BusinessUser busUser, Address address, CreditCard creditCard) throws SQLException, ClassNotFoundException {
        try (Connection conn = Database.getConnection()) {

            String userSQL = "INSERT INTO user (Name, Email, Password, PhoneNumber, Type) VALUES (?, ?, ?, ?, ?)";
            int userID;
            try (PreparedStatement stmt = conn.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user instanceof IndividualUser ? "INDIVIDUAL" : "BUSINESS");

                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    userID = rs.getInt(1);
                }
            }

            String businessUserSQL = "INSERT INTO businessuser (UserID, CIF) VALUES (?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(businessUserSQL)) {
                stmt.setInt(1, userID);
                stmt.setString(2, busUser.getCIF());

                stmt.executeUpdate();
            }

            String addressSQL = "INSERT INTO address (StreetName, Number, ZipCode, City, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(addressSQL)) {
                stmt.setString(1, address.getStreetName());
                stmt.setString(2, address.getNumber());
                stmt.setString(3, address.getZipCode());
                stmt.setString(4, address.getCity());
                stmt.setInt(5, userID);

                stmt.executeUpdate();
            }

            String cardSQL = "INSERT INTO creditcard (Name, Number, ExpDate, CVV, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(cardSQL)) {
                stmt.setString(1, creditCard.getName());
                stmt.setString(2, creditCard.getNumber());

                LocalDate exp = creditCard.getExpDate();
                Date sqlExpDate = CreditCard.LocalDatetoSQLDate(exp);
                stmt.setDate(3, sqlExpDate);

                stmt.setString(4, creditCard.getCVV());
                stmt.setInt(5, userID);

                stmt.executeUpdate();
            }

            System.out.println("User saved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
