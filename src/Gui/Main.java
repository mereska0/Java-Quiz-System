package Gui;

import Cards.Card;
import Cards.CardCollection;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    private CardCollection cards; // Выводим текущую колллекцию отделынм полем

    private void CardCreaterWindow() { // Добавляем карты в колллекцию
        Stage cardInputStage = new Stage();

        VBox mainBox = new VBox(10);
        mainBox.setStyle("-fx-padding: 20;"); // Добавляем отсупы в 20 пикселей по краям VBox


        TextField cardFaceField = new TextField("Название карточки");
        TextField cardContentField = new TextField("Содержание карточки");

        Button cardCreater = new Button("Добавить карточку");
        Button finish = new Button("Завершить");

        cardCreater.setOnAction(e -> {
            Card card = new Card(cardFaceField.getText(), cardContentField.getText());
            cards.add(card);
            System.out.println(cards.getSize() + ". Добавлена карта: " + cardFaceField.getText());

            // Очищем поля
            cardFaceField.clear();
            cardContentField.clear();
            cardFaceField.requestFocus(); // "Активное" поле (Ввод поступит туда)

        });

        finish.setOnAction(e -> {
            System.out.println("Всего добавлено " + cards.getSize() + " карточкиф" );
            cardInputStage.close();
            cardLearningWindow(cards);

        });

        mainBox.getChildren().addAll(
                cardFaceField,
                cardContentField,
                cardCreater,
                finish
        );

        Scene cardInputScene = new Scene(mainBox, 400, 200);
        cardInputStage.setScene(cardInputScene);
        cardInputStage.show();

    }

    private void cardLearningWindow(CardCollection cards) {

    }


    @Override
    public void start(Stage primaryStage) {
        Button collectionCreater = new Button("Создать колллекцию");

        collectionCreater.setOnAction(e -> { // Логика кнопки "Создать коллекцию"
            Stage inputStage = new Stage();
            inputStage.setTitle("Создание коллекции");

            TextField collectionNameField = new TextField("Введите название коллекции");
            collectionNameField.setPrefWidth(200);

            Button colNameButton = new Button("Создать");
            EventHandler<ActionEvent> createColection = ec -> { // Создаем коллекцию с определенным именем
                String colName = collectionNameField.getText().trim();
              if (!colName.isEmpty()) {
                  System.out.println("Создана коллекция: " + colName);
                  cards = new CardCollection(colName);

                  ((Stage) collectionNameField.getScene().getWindow()).close();

                  CardCreaterWindow();
              }
            };
            colNameButton.setOnAction(createColection);

            VBox textFieldController =  new VBox(10, collectionNameField, colNameButton);
            textFieldController.setStyle("-fx-padding: 20; -fx-alignment: center;");

            Scene creatCollectionScene = new Scene(textFieldController, 300, 150);
            inputStage.setScene(creatCollectionScene);
            inputStage.show();
        });


        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(collectionCreater);
        Scene scene = new Scene(mainPane, 400, 300);

        primaryStage.setTitle("Менеджер коллекций");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}