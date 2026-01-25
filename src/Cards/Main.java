package Cards;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    private boolean showFace = true;
    @Override
    public void start(Stage primaryStage) {
        Rectangle card = new Rectangle(200, 300);
        card.setFill(Color.WHITE);
        card.setStroke(Color.BLACK);
        card.setStrokeWidth(5);
        Text text = new Text("Тайна");
        text.setStyle("-fx-font-size: 20");
        card.setOnMouseClicked(e -> {
            if (showFace) {
                text.setText("Тайна");
            } else {
                text.setText("Я тебя люблю");
            }
            showFace = !showFace;
        });
        Button button = new Button("Change");
        StackPane pane = new StackPane();
        pane.getChildren().add(card);
        pane.getChildren().add(text);
        Scene scene = new Scene(pane, 400, 400);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите название коллекции");
        String collectionName = scanner.next();
        CardCollection cards = new CardCollection(collectionName);

        while (true) {
            System.out.println("Введите название карточки или напишите \"finish\"");
            String name = scanner.next();
            if (name.equals("finish")) {  // Исправлено: equals()
                System.out.println("popal");
                break;
            }
            System.out.println("Введите содержание карточки");
            String back = scanner.next().trim().toLowerCase();
            Card card = new Card(name, back);
            cards.add(card);
        }

        CardLearning test = new CardLearning(cards);
        test.main();
    }
}