package com.example.javaquizsystem.Gui;

import com.example.javaquizsystem.Cards.Card;
import com.example.javaquizsystem.Cards.CardCollection;
import com.example.javaquizsystem.DataBase.DataBaseManager;
import com.example.javaquizsystem.DataBase.User;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.List;


public class Main extends Application {
    private User currentUser;
    private Stage primaryStage;
    // NOTE: Поле cards удалено, так как оно не используется в новой логике

    public void enterScreen() {
        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);

        Button regButton = new Button("Registration");
        Button loginButton = new Button("Login");

        regButton.setOnAction(e -> {
            registrationScreen();
        });

        loginButton.setOnAction(e -> {
            loginScreen();
        });

        mainPane.add(regButton, 1, 3);
        mainPane.add(loginButton, 1, 6);
        Scene scene = new Scene(mainPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("enter view");
    }

    private void registrationScreen() {
        // Создаем новое окно
        Stage regStage = new Stage();
        regStage.setTitle("Registration");

        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(20));

        Label label = new Label("Enter username:");
        TextField usernameField = new TextField();
        Button submitButton = new Button("Submit");
        Button cancelButton = new Button("Cancel");

        // Обработка кнопки Submit
        submitButton.setOnAction(e -> {
            String username = usernameField.getText().trim();

            if (username.isEmpty()) {
                showAlert("Error", "Username cannot be empty");
                usernameField.requestFocus(); // Фокус обратно на поле
                return;
            }

            try {
                currentUser = DataBaseManager.addUser(username);
                showAlert("Success", "User '" + username + "' created!");
                regStage.close();
                // Переходим дальше
                showMainScreen();
            } catch (IllegalArgumentException ex) {
                // Показываем ошибку и очищаем поле для нового ввода
                showAlert("Error", ex.getMessage());
                usernameField.clear();
                usernameField.requestFocus(); // Фокус обратно на поле
            } catch (SQLException ex) {
                showAlert("Database Error", ex.getMessage());
                regStage.close();
            }
        });

        // Обработка кнопки Cancel
        cancelButton.setOnAction(e -> regStage.close());

        // Нажимать Enter в TextField = нажать Submit
        usernameField.setOnAction(e -> submitButton.fire());

        vbox.getChildren().addAll(label, usernameField, submitButton, cancelButton);

        Scene scene = new Scene(vbox, 300, 200);
        regStage.setScene(scene);
        regStage.show();
    }

    private void loginScreen() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Login");

        VBox vbox = new VBox(10);
        vbox.setPadding(new javafx.geometry.Insets(20));

        Label label = new Label("Enter username:");
        TextField usernameField = new TextField();
        Button submitButton = new Button("Login");
        Button cancelButton = new Button("Cancel");

        submitButton.setOnAction(e -> {
            String username = usernameField.getText().trim();

            if (username.isEmpty()) {
                showAlert("Error", "Username cannot be empty");
                usernameField.requestFocus();
                return;
            }

            try {
                currentUser = DataBaseManager.getUser(username);
                showAlert("Success", "Welcome, " + username + "!");
                loginStage.close();
                showMainScreen();
            } catch (IllegalArgumentException ex) {
                showAlert("Error", ex.getMessage());
                usernameField.clear();
                usernameField.requestFocus(); // Даем ввести заново
            } catch (SQLException ex) {
                showAlert("Database Error", ex.getMessage());
                loginStage.close();
            }
        });

        cancelButton.setOnAction(e -> loginStage.close());
        usernameField.setOnAction(e -> submitButton.fire());

        vbox.getChildren().addAll(label, usernameField, submitButton, cancelButton);

        Scene scene = new Scene(vbox, 300, 200);
        loginStage.setScene(scene);
        loginStage.show();
    }

    // Простой метод для показа сообщений
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Добавьте этот метод - он должен просто показывать главный экран
    public void showMainScreen() {
        // Создаем главное меню
        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);

        Button showCollections = new Button("My collections");
        Button quizCreator = new Button("Create quiz");

        showCollections.setOnAction(e -> {
            MyCollectionScreen();
        });

        quizCreator.setOnAction(e -> {
            showQuiz();
        });

        mainPane.add(showCollections, 1, 3);
        mainPane.add(quizCreator, 1, 6);

        Scene scene = new Scene(mainPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Collection manager");
    }

    private void MyCollectionScreen() {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Заголовок
        Label titleLabel = new Label("My Collections");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Список коллекций
        VBox collectionsList = new VBox(10);
        collectionsList.setPadding(new Insets(10));
        collectionsList.setStyle("-fx-border-color: #ccc; -fx-border-radius: 5; -fx-padding: 10;");

        // Загружаем коллекции пользователя
        if (currentUser != null) {
            try {
                List<CardCollection> collections = DataBaseManager.getUsersCardCollections(currentUser.getId());

                for (CardCollection collection : collections) {
                    // Создаем кнопку для каждой коллекции
                    Button collectionButton = new Button(collection.getName());
                    collectionButton.setMaxWidth(Double.MAX_VALUE);
                    collectionButton.setStyle("-fx-font-size: 14px; -fx-padding: 10;");

                    // При нажатии переходим в коллекцию
                    collectionButton.setOnAction(e -> {
                        openCollection(collection);
                    });

                    collectionsList.getChildren().add(collectionButton);
                }

                // Если коллекций нет
                if (collections.isEmpty()) {
                    Label noCollectionsLabel = new Label("No collections yet. Create your first one!");
                    noCollectionsLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
                    collectionsList.getChildren().add(noCollectionsLabel);
                }

            } catch (SQLException e) {
                Label errorLabel = new Label("Error loading collections: " + e.getMessage());
                errorLabel.setStyle("-fx-text-fill: red;");
                collectionsList.getChildren().add(errorLabel);
            }
        } else {
            Label noUserLabel = new Label("Please login first");
            noUserLabel.setStyle("-fx-text-fill: red;");
            collectionsList.getChildren().add(noUserLabel);
        }

        // Кнопка создания коллекции
        Button createButton = new Button("Create New Collection");
        createButton.setStyle("-fx-font-size: 16px; -fx-padding: 10 20;");

        // Действие кнопки (пока заглушка)
        createButton.setOnAction(e -> {
            // Здесь будет логика создания коллекции
            showCollectionCreator();
        });

        // Кнопка назад
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainScreen());

        // Горизонтальная панель для кнопок
        HBox buttonPanel = new HBox(10);
        buttonPanel.setAlignment(Pos.CENTER);
        buttonPanel.getChildren().addAll(createButton, backButton);

        // Добавляем все элементы
        mainLayout.getChildren().addAll(
                titleLabel,
                collectionsList,
                buttonPanel
        );

        // Прокрутка если коллекций много
        ScrollPane scrollPane = new ScrollPane(mainLayout);
        scrollPane.setFitToWidth(true);

        Scene scene = new Scene(scrollPane, 500, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("My Collections");
    }


    // Метод для открытия коллекции
    private void openCollection(CardCollection collection) {
        // NOTE: Открываем GUI для просмотра коллекции с карточками
        showCollectionScreen(collection);
    }

    private void showCollectionScreen(CardCollection collection) {
        VBox mainLayout = new VBox(15);
        mainLayout.setPadding(new Insets(20));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Заголовок с именем коллекции
        Label titleLabel = new Label("Collection: " + collection.getName());
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        // NOTE: Добавляем поля для ввода новой карточки прямо в этот экран
        HBox addCardPanel = new HBox(10);
        addCardPanel.setAlignment(Pos.CENTER);

        TextField faceField = new TextField();
        faceField.setPromptText("Card face");
        faceField.setPrefWidth(150);

        TextField contentField = new TextField();
        contentField.setPromptText("Card content");
        contentField.setPrefWidth(150);

        Button addButton = new Button("Add");

        // NOTE: Логика добавления карточки прямо здесь
        addButton.setOnAction(e -> {
            String face = faceField.getText().trim();
            String content = contentField.getText().trim();

            if (face.isEmpty() || content.isEmpty()) {
                showAlert("Error", "Both fields must be filled");
                return;
            }

            try {
                // Добавляем карточку в базу
                DataBaseManager.addCard(collection.getId(), face, content);

                // Очищаем поля
                faceField.clear();
                contentField.clear();

                // Обновляем окно
                showCollectionScreen(collection);

            } catch (SQLException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        addCardPanel.getChildren().addAll(
                new Label("Face:"), faceField,
                new Label("Content:"), contentField,
                addButton
        );

        // Контейнер для карточек
        VBox cardsContainer = new VBox(15);
        cardsContainer.setPadding(new Insets(10));
        cardsContainer.setAlignment(Pos.CENTER);

        // Загружаем карточки из коллекции
        try {
            List<Card> cards = DataBaseManager.getCards(collection.getId());

            for (Card card : cards) {
                // NOTE: Используем готовый метод getUICard() из карточки
                cardsContainer.getChildren().add(card.getUICard());
            }

            if (cards.isEmpty()) {
                Label noCardsLabel = new Label("No cards in this collection yet");
                noCardsLabel.setStyle("-fx-text-fill: gray; -fx-font-style: italic;");
                cardsContainer.getChildren().add(noCardsLabel);
            }

        } catch (SQLException e) {
            Label errorLabel = new Label("Error loading cards: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red;");
            cardsContainer.getChildren().add(errorLabel);
        }

        // NOTE: Прокрутка для карточек
        ScrollPane scrollPane = new ScrollPane(cardsContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefHeight(400);

        // NOTE: Кнопки управления
        HBox buttonPanel = new HBox(10);
        buttonPanel.setAlignment(Pos.CENTER);

        Button startLearningButton = new Button("Start Learning");
        Button backButton = new Button("Back");

        // NOTE: Логика кнопки Start Learning
        startLearningButton.setOnAction(e -> {
            try {
                List<Card> cards = DataBaseManager.getCards(collection.getId());
                if (cards != null && !cards.isEmpty()) {
                    cardLearningWindow(cards);
                } else {
                    showAlert("Info", "No cards in this collection yet");
                }
            } catch (SQLException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        // NOTE: Кнопка Back возвращает в список коллекций
        backButton.setOnAction(e -> MyCollectionScreen());

        buttonPanel.getChildren().addAll(startLearningButton, backButton);

        // NOTE: Добавляем панель добавления карточек
        mainLayout.getChildren().addAll(
                titleLabel,
                addCardPanel,
                scrollPane,
                buttonPanel
        );

        Scene scene = new Scene(mainLayout, 700, 650);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Collection: " + collection.getName());
    }

    private void showCollectionCreator() {
        // NOTE: Создаем диалоговое окно для ввода имени коллекции
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Create Collection");
        dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
        dialogStage.initOwner(primaryStage);

        VBox dialogBox = new VBox(10);
        dialogBox.setPadding(new Insets(20));
        dialogBox.setAlignment(Pos.CENTER);

        Label label = new Label("Enter collection name:");
        TextField nameField = new TextField();
        nameField.setPromptText("Collection name");

        Button createButton = new Button("Create");
        Button cancelButton = new Button("Cancel");

        createButton.setOnAction(e -> {
            String collectionName = nameField.getText().trim();
            if (collectionName.isEmpty()) {
                showAlert("Error", "Collection name cannot be empty");
                return;
            }

            try {
                // NOTE: Создаем коллекцию в базе данных
                DataBaseManager.AddCardCollection(currentUser.getId(), collectionName);
                dialogStage.close();

                // NOTE: Обновляем текущее окно MyCollectionScreen
                MyCollectionScreen();

            } catch (SQLException ex) {
                showAlert("Error", ex.getMessage());
            }
        });

        cancelButton.setOnAction(e -> dialogStage.close());

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(createButton, cancelButton);

        dialogBox.getChildren().addAll(label, nameField, buttonBox);

        Scene dialogScene = new Scene(dialogBox, 300, 150);
        dialogStage.setScene(dialogScene);
        dialogStage.show();
    }

    private void CardCreaterWindow(int collectionID) {
        VBox mainBox = new VBox(10);
        mainBox.setStyle("-fx-padding: 20;");

        TextField cardFaceField = new TextField();
        TextField cardContentField = new TextField();
        cardFaceField.setPromptText("Card name");
        cardContentField.setPromptText("card info");

        Button cardCreater = new Button("add");
        Button startLearning = new Button("start learning");
        Button backButton = new Button("Back");

        cardCreater.setOnAction(e -> {
            try {
                // NOTE: Сохраняем карточку в базу данных
                Card card = DataBaseManager.addCard(collectionID, cardFaceField.getText(), cardContentField.getText());
                // NOTE: Убрано обращение к cards.getSize(), так как cards удалено
                System.out.println("Добавлена карта: " + cardFaceField.getText());
                cardFaceField.clear();
                cardContentField.clear();
                cardFaceField.requestFocus();
            } catch(SQLException error) {
                System.out.println(error.getMessage());
                // NOTE: Можно добавить показ ошибки пользователю
                showAlert("Error", error.getMessage());
            }
        });

        startLearning.setOnAction(e -> {
            try {
                List<Card> cards = DataBaseManager.getCards(collectionID);
                if (cards != null && !cards.isEmpty()) {
                    System.out.println("Всего добавлено " + cards.size() + " карточек");
                    cardLearningWindow(cards);
                } else {
                    showAlert("Info", "No cards in this collection yet");
                }

            } catch(SQLException sqlError) {
                System.out.println(sqlError.getMessage());
                showAlert("Error", sqlError.getMessage());
            }

        });

        // NOTE: Возвращаемся в список коллекций
        backButton.setOnAction(e -> MyCollectionScreen());

        mainBox.getChildren().addAll(
                cardFaceField,
                cardContentField,
                cardCreater,
                startLearning,
                backButton
        );

        Scene scene = new Scene(mainBox, 400, 250);
        primaryStage.setScene(scene);
    }

    private void cardLearningWindow(List<Card> cards) {
        Stage learningStage = new Stage();
        learningStage.setTitle("Card learning");

        CardLearningPane learningPane = new CardLearningPane(cards);
        learningPane.getExitButton().setOnAction(e -> {
            learningStage.close();
            System.out.println("learning is finished");
        });

        Scene scene = new Scene(learningPane, 600, 500);
        learningStage.setScene(scene);
        learningStage.show();
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        enterScreen(); // Показываем окно регистрации при запуске
        primaryStage.show();
    }

    public void showQuiz() {
        QuizGui quizGui = new QuizGui(this);
        Scene scene = new Scene(quizGui, 600, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz creation");
    }

    public static void main(String[] args) {
        launch(args);
    }
}