package com.example.shoppingapp.classes;

import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;


public class Database {
    private static final String URL = "jdbc:mysql://localhost:3306/shoppingapp?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USER = "shopping_user";
    private static final String PASSWORD = "g76pL/~|oHAO0Z=";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void addUser(User user, Address address, CreditCard creditCard) throws SQLException, ClassNotFoundException {
        try (Connection conn = Database.getConnection()) {

            String userSql = "INSERT INTO User (Name, Email, Password, PhoneNumber, Type) VALUES (?, ?, ?, ?, ?)";
            int userId;
            try (PreparedStatement stmt = conn.prepareStatement(userSql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, user instanceof IndividualUser ? "INDIVIDUAL" : "BUSINESS");

                stmt.executeUpdate();
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    rs.next();
                    userId = rs.getInt(1);
                }
            }

            String addressSql = "INSERT INTO Address (StreetName, Number, ZipCode, City, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(addressSql)) {
                stmt.setString(1, address.getStreetName());
                stmt.setString(2, address.getNumber());
                stmt.setString(3, address.getZipCode());
                stmt.setString(4, address.getCity());
                stmt.setInt(5, userId);

                stmt.executeUpdate();
            }

            String cardSql = "INSERT INTO CreditCard (Name, Number, ExpDate, CVV, UserID) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(cardSql)) {
                stmt.setString(1, creditCard.getName());
                stmt.setString(2, creditCard.getNumber());

                java.util.Date exp = creditCard.getExpDate();
                stmt.setDate(3, new java.sql.Date(exp.getTime()));

                stmt.setString(4, creditCard.getCVV());
                stmt.setInt(5, userId);

                stmt.executeUpdate();
            }

            System.out.println("User, Address and CreditCard saved successfully");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
