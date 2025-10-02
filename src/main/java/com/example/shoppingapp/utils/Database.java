package com.example.shoppingapp.utils;

import com.example.shoppingapp.classes.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


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
                stmt.setString(5, "INDIVIDUAL");

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
                stmt.setString(5, "BUSINESS");

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

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addAdminUser(User user) {
        try (Connection conn = Database.getConnection()) {

            String userSQL = "INSERT INTO user (Name, Email, Password, PhoneNumber, Type) VALUES (?, ?, ?, ?, ?)";
            int userID;
            try (PreparedStatement stmt = conn.prepareStatement(userSQL, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPassword());
                stmt.setString(4, user.getPhoneNumber());
                stmt.setString(5, "ADMIN");

                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addProduct(Product product) {
        String sql = "INSERT INTO Product (Title, Category, Description, Picture, Price, Stock, EntryDate) " + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, product.getTitle());
            stmt.setString(2, product.getCategory());
            stmt.setString(3, product.getDescription());

            byte[] picture = product.getPicture();
            if (picture == null) {
                File defaultImage = new File("src/main/resources/products/NotAvailable.png");
                try (FileInputStream fis = new FileInputStream(defaultImage)) {
                    picture = fis.readAllBytes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            stmt.setBytes(4, picture);

            stmt.setBigDecimal(5, BigDecimal.valueOf(product.getPrice()));
            stmt.setInt(6, product.getStock());
            stmt.setTimestamp(7, java.sql.Timestamp.valueOf(product.getEntryDate()));

            stmt.executeUpdate();
        }

        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT UserID, Name, Email, PhoneNumber, Type FROM user";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("Name"),
                        rs.getString("Email"),
                        null,
                        rs.getString("PhoneNumber"),
                        rs.getString("Type"),
                        rs.getInt("UserID")
                );
                if (!user.getType().equals("ADMIN")) {
                    users.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String sql = "SELECT ProductID, Title, Category, Description, Picture, Price, Stock, EntryDate FROM product";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("EntryDate");
                LocalDateTime entryDate = sqlDate.toLocalDate().atStartOfDay();

                Product product = new Product(
                        rs.getString("Title"),
                        rs.getString("Category"),
                        rs.getString("Description"),
                        rs.getBytes("Picture"),
                        rs.getFloat("Price"),
                        rs.getInt("Stock"),
                        rs.getInt("ProductID"),
                        entryDate
                );
                    products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public static User getUserByID(int userID) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM User WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new User(
                            rs.getString("Name"),
                            rs.getString("Email"),
                            rs.getString("Password"),
                            rs.getString("PhoneNumber"),
                            rs.getString("Type")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static BusinessUser getBusinessUserByID(int userID, User user) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM BusinessUser WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new BusinessUser(user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(),
                            rs.getString("CIF"), user.getType());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IndividualUser getIndividualUserByID(int userID, User user) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM IndividualUser WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new IndividualUser(user.getName(), user.getEmail(), user.getPassword(), user.getPhoneNumber(),
                            rs.getString("DNI"), user.getType());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static CreditCard getCreditCardsByUserID(int userID) {
        List<CreditCard> cards = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM CreditCard WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new CreditCard(
                            rs.getString("Name"),
                            rs.getString("Number"),
                            rs.getString("CVV"),
                            rs.getDate("ExpDate").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Address getAddressesByUserID(int userID) {
        List<Address> addresses = new ArrayList<>();
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM Address WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return new Address(
                            rs.getString("StreetName"),
                            rs.getString("Number"),
                            rs.getString("ZipCode"),
                            rs.getString("City")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Product getProductByID(int userID) {
        try (Connection conn = Database.getConnection()) {
            String sql = "SELECT * FROM Product WHERE ProductID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, userID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    Date sqlDate = rs.getDate("EntryDate");
                    LocalDateTime entryDate = sqlDate.toLocalDate().atStartOfDay();

                    return new Product(
                            rs.getString("Title"),
                            rs.getString("Category"),
                            rs.getString("Description"),
                            rs.getBytes("Picture"),
                            rs.getFloat("Price"),
                            rs.getInt("Stock"),
                            rs.getInt("ProductID"),
                            entryDate
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void updateIndividualUser(User user, IndividualUser indUser, Address address, CreditCard creditCard) {
        try (Connection conn = Database.getConnection()) {
             conn.setAutoCommit(false);

             try (PreparedStatement stmt = conn.prepareStatement("UPDATE user SET Name=?, Email=?, PhoneNumber=? WHERE UserID=?")) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhoneNumber());
                stmt.setInt(4, user.getUserID());
                stmt.executeUpdate();
             }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE individualuser SET DNI=? WHERE UserID=?")) {
                stmt.setString(1, indUser.getDNI());
                stmt.setInt(2, user.getUserID());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE address SET StreetName=?, Number=?, ZipCode=?, City=? WHERE UserID=?")) {
                stmt.setString(1, address.getStreetName());
                stmt.setString(2, address.getNumber());
                stmt.setString(3, address.getZipCode());
                stmt.setString(4, address.getCity());
                stmt.setInt(5, user.getUserID());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE creditcard SET Name=?, Number=?, CVV=?, ExpDate=? WHERE UserID=?")) {
                stmt.setString(1, creditCard.getName());
                stmt.setString(2, creditCard.getNumber());
                stmt.setString(3, creditCard.getCVV());
                LocalDate exp = creditCard.getExpDate();
                Date sqlExpDate = CreditCard.LocalDatetoSQLDate(exp);
                stmt.setDate(4, sqlExpDate);
                stmt.setInt(5, user.getUserID());
                stmt.executeUpdate();
            }
             conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateBusinessUser(User user, BusinessUser busUser, Address address, CreditCard creditCard) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement("UPDATE user SET Name=?, Email=?, PhoneNumber=? WHERE UserID=?")) {
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getEmail());
                stmt.setString(3, user.getPhoneNumber());
                stmt.setInt(4, user.getUserID());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE businessuser SET CIF=? WHERE UserID=?")) {
                stmt.setString(1, busUser.getCIF());
                stmt.setInt(2, user.getUserID());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE address SET StreetName=?, Number=?, ZipCode=?, City=? WHERE UserID=?")) {
                stmt.setString(1, address.getStreetName());
                stmt.setString(2, address.getNumber());
                stmt.setString(3, address.getZipCode());
                stmt.setString(4, address.getCity());
                stmt.setInt(5, user.getUserID());
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("UPDATE creditcard SET Name=?, Number=?, CVV=?, ExpDate=? WHERE UserID=?")) {
                stmt.setString(1, creditCard.getName());
                stmt.setString(2, creditCard.getNumber());
                stmt.setString(3, creditCard.getCVV());
                LocalDate exp = creditCard.getExpDate();
                Date sqlExpDate = CreditCard.LocalDatetoSQLDate(exp);
                stmt.setDate(4, sqlExpDate);
                stmt.setInt(5, user.getUserID());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateProduct(Product product) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement("UPDATE product SET Title=?, Price=?, Stock=?, Description=?, Category=?, Picture=? WHERE ProductID=?")) {
                stmt.setString(1, product.getTitle());
                stmt.setFloat(2, product.getPrice());
                stmt.setInt(3, product.getStock());
                stmt.setString(4, product.getDescription());
                stmt.setString(5, product.getCategory());
                stmt.setBytes(6, product.getPicture());
                stmt.setInt(7, product.getProductID());
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(int userId) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM CreditCard WHERE UserID = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Address WHERE UserID = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM BusinessUser WHERE UserID = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM IndividualUser WHERE UserID = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM User WHERE UserID = ?")) {
                stmt.setInt(1, userId);
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteProduct(int productId) {
        try (Connection conn = Database.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt = conn.prepareStatement("DELETE FROM Product WHERE ProductID = ?")) {
                stmt.setInt(1, productId);
                stmt.executeUpdate();
            }
            conn.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
