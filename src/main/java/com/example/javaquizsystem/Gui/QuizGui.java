package com.example.javaquizsystem.Gui;

import com.example.javaquizsystem.Quiz.Quiz;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class QuizGui extends GridPane {
    private Main main;
    Quiz quiz;

    public QuizGui(Main main) {
        this.main = main;
        setHgap(10);
        setVgap(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);
        initializeQuiz();
    }

    private void initializeQuiz() {
        Label nameLabel = new Label("NAME:");
        TextField nameField = new TextField();
        nameField.setPromptText("type your name");
        setAlignment(Pos.TOP_CENTER);
        setPadding(new javafx.geometry.Insets(10));
        nameField.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");
        Button confirmButton = new Button("confirm");
        EventHandler<ActionEvent> createQuestions = ec -> {
            quiz = new Quiz(nameField.getText());
            getChildren().clear();
            initializeQuestion();
        };
        confirmButton.setOnAction(createQuestions);
        add(nameLabel, 0, 0);
        add(nameField, 2, 0);
        add(confirmButton, 4, 0);

    }

    private void initializeQuestion() {
        List<TextField> inputs = new ArrayList<>();
        Label QuizName = new Label(quiz.getName());
        QuizName.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        final int[] InputIndex = {1};
        Label QuestionTextLabel = new Label("Text:");
        TextArea QuestionText = new TextArea();
        QuestionText.setPromptText("type text...");

        Label Answers = new Label("Answers:");
        inputs.add(new TextField());
        Button addButton = new Button("add");

        Label Correct = new Label("Choose correct");
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().add("1");

        Button nextButton = new Button("Next question");
        Button finishButton = new Button("Finish");
        nextButton.setStyle("-fx-font-size:20;");
        finishButton.setStyle("-fx-font-size:20;");

        EventHandler<ActionEvent> addInput = ec -> {
            inputs.add(new TextField());
            add(inputs.get(InputIndex[0]), 1, 3 + InputIndex[0]);
            getChildren().remove(addButton);
            add(addButton, 2, 3 + InputIndex[0] + 1);
            InputIndex[0]++;
            comboBox.getItems().add(InputIndex[0] + "");
            getChildren().remove(comboBox);
            add(comboBox, 1, 3 + InputIndex[0] + 4);
            getChildren().remove(Correct);
            add(Correct, 0, 3 + InputIndex[0] + 4);
            getChildren().remove(nextButton);
            add(nextButton, 0, 3 + InputIndex[0] + 8);
            getChildren().remove(finishButton);
            add(finishButton, 1, 3 + InputIndex[0] + 8);
        };
        EventHandler<ActionEvent> next = ec -> {
            getChildren().clear();
            List<String> ans = new ArrayList<>();
            for (TextField tf : inputs) {
                ans.add(tf.getText());
            }
            quiz.createQuestion(QuestionText.getText(), ans, Integer.parseInt(comboBox.getValue()));
            System.out.println("question has been created");
            initializeQuestion();
        };
        EventHandler<ActionEvent> finish = ec -> {
            getChildren().clear();
            List<String> ans = new ArrayList<>();
            for (TextField tf : inputs) {
                ans.add(tf.getText());
            }
            quiz.createQuestion(QuestionText.getText(), ans, Integer.parseInt(comboBox.getValue()));
            main.showMainScreen();
        };
        addButton.setOnAction(addInput);
        nextButton.setOnAction(next);
        finishButton.setOnAction(finish);

        add(QuizName, 0, 0);
        add(QuestionTextLabel, 0, 1);
        add(QuestionText, 1, 1);
        add(Answers, 0, 2);
        add(inputs.getFirst(), 1, 2);
        add(addButton, 2, 3);
        add(Correct, 0, 6);
        add(comboBox, 1, 6);
        add(nextButton, 0, 10);
        add(finishButton, 1, 10);
        //TODO QuizLearning gui + error handling
    }
}

