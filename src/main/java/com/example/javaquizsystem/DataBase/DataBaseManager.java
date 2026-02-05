package com.example.javaquizsystem.DataBase;


import com.example.javaquizsystem.Cards.Card;
import com.example.javaquizsystem.Cards.CardCollection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*
ANOTATION

50% Deepseek created ;)
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

    public static boolean isCollectionExists(String name) throws SQLException {
        String sql = "SELECT COUNT(*) FROM card_collection WHERE name = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, name);

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

    public static List<Card> getCards(int collectionID) throws SQLException {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM card WHERE card_collection_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1, collectionID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String face = rs.getString("face");
                    String content = rs.getString("content");
                    cards.add(new Card(id,face,content));
                }
                return cards;
            }
        } catch (SQLException e) {
            System.out.println("Failed to interacr with database: " + e.getMessage());
            throw e;
        }
    }

    public static List<CardCollection> getUsersCardCollections(int userID) throws SQLException {
        List<CardCollection> userCollections = new ArrayList<>();
        String sql = "Select * FROM card_collection WHERE user_id = ?";
        try (Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    List<Card> cards = getCards(id);
                    userCollections.add(new CardCollection(id, name, cards));
                }
                return userCollections;
            }
        } catch(SQLException e) {
            System.out.println("Failed to interacr with database: " + e.getMessage());
            throw e;
        }
    }

    public static CardCollection AddCardCollection(int userID, String name) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Collection name cannot be null or empty");
        }
        name = name.trim();
        String sql = "INSERT INTO card_collection (name, user_id) VALUES (?, ?)";
        if (isCollectionExists(name)) {
            throw new IllegalArgumentException("Collection" + name + "already registered");
        }
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name.trim());
            stmt.setInt(2,userID);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating collection failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    System.out.println("User '" + name + "' created with ID: " + generatedID);
                    return new CardCollection(generatedID, name);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        }
    }

    public static Card addCard(int collectionID, String face, String content) throws SQLException {
        String sql = "INSERT INTO card (face, content, card_collection_id) VALUES (?,?,?)";
        if (face == null || face.trim().isEmpty() || content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("card face or content cannot be null or empty");
        }
        face.trim();
        content.trim();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, face);
            stmt.setString(2, content);
            stmt.setInt(3, collectionID);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating collection failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedID = generatedKeys.getInt(1);
                    System.out.println("Card '" + face + "' created with ID: " + generatedID);
                    return new Card(generatedID, face, content);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
            throw e;
        }


    }

}
