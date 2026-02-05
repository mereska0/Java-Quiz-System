package com.example.javaquizsystem.DataBase;


import java.sql.*;

/*
ANOTATION

75% Deepseek created ;)
 */

public class DataBaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quize_system";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static boolean isUsernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }


    public static User addUser(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }

        String sql = "INSERT INTO users (username) VALUES (?)";
        if (isUsernameExists(username)) {
            throw new IllegalArgumentException("Пользователь '" + username + "' уже зарегистрирован");
        }

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, username.trim());
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    System.out.println("User '" + username + "' created with ID: " + generatedID);
                    return new User(generatedID, username);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
    }

    public static User getUser(String username) throws SQLException {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username can not be null or empty");
        }
        username.trim();
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String dbUsername = rs.getString("username");
                    return new User(id, dbUsername);
                } else {
                    throw new IllegalArgumentException("User with name: " + username + "not found");
                }
            }


        } catch (SQLException e) {
            System.out.println("User: " + username + "not found");
            System.out.println(e.getMessage());
            throw e;
        }

    }

//    public static List<CardCollection> getUsersCardCollections(int id) {
//        // TODO
//    }

//    public static CardCollection AddCardCollection(int userID) {
//        // TODO
//    }

//    public static Card addCard(int collectionID) {
//        // TODO
//    }

}
