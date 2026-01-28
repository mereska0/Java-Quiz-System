package com.example.javaquizsystem.Gui;

import com.example.javaquizsystem.Cards.Card;
import com.example.javaquizsystem.Cards.CardCollection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private Stage primaryStage;
    private CardCollection cards;

    // Добавьте этот метод - он должен просто показывать главный экран
    public void showMainScreen() {
        // Создаем главное меню
        GridPane mainPane = new GridPane();
        mainPane.setHgap(10);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(20));
        mainPane.setAlignment(Pos.CENTER);

        Button collectionCreator = new Button("Create collection");
        Button quizCreator = new Button("Create quiz");

        collectionCreator.setOnAction(e -> {
            showCollectionCreator();
        });

        quizCreator.setOnAction(e -> {
            showQuiz();
        });

        mainPane.add(collectionCreator, 1, 3);
        mainPane.add(quizCreator, 1, 6);

        Scene scene = new Scene(mainPane, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Collection manager");
    }

    private void showCollectionCreator() {
        VBox textFieldController = new VBox(10);
        textFieldController.setStyle("-fx-padding: 20; -fx-alignment: center;");

        TextField collectionNameField = new TextField();
        collectionNameField.setPromptText("enter collection name");
        collectionNameField.setPrefWidth(200);

        Button colNameButton = new Button("create");
        colNameButton.setOnAction(ec -> {
            String colName = collectionNameField.getText().trim();
            if (!colName.isEmpty()) {
                System.out.println("Создана коллекция: " + colName);
                cards = new CardCollection(colName);
                CardCreaterWindow();
            }
        });

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> showMainScreen());

        textFieldController.getChildren().addAll(
                collectionNameField,
                colNameButton,
                backButton
        );

        Scene scene = new Scene(textFieldController, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.setTitle("creating collection");
    }

    private void CardCreaterWindow() {
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
            Card card = new Card(cardFaceField.getText(), cardContentField.getText());
            if (cards != null) {
                cards.add(card);
                System.out.println(cards.getSize() + ". Добавлена карта: " + cardFaceField.getText());
                cardFaceField.clear();
                cardContentField.clear();
                cardFaceField.requestFocus();
            }
        });

        startLearning.setOnAction(e -> {
            if (cards != null) {
                System.out.println("Всего добавлено " + cards.getSize() + " карточки");
                cardLearningWindow(cards);
            }
        });

        backButton.setOnAction(e -> showMainScreen());

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

    private void cardLearningWindow(CardCollection cards) {
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
        showMainScreen(); // Показываем главный экран при запуске
        primaryStage.show();
    }

    public void showQuiz() {
        QuizGui quizGui = new QuizGui(this);
        Scene scene = new Scene(quizGui, 600, 500); // Увеличьте размер окна
        primaryStage.setScene(scene);
        primaryStage.setTitle("Quiz creation");
    }

    public static void main(String[] args) {
        launch(args);
    }
}