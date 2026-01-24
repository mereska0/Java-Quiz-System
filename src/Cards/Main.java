package Cards;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        Circle bottomCircle = new Circle(50);
        Circle midCircle = new Circle(40);
        Circle topCircle = new Circle(30);
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
                0.0, 0.0,
                30.0, 10.0,
                0.0, 20.0
        );
        triangle.setFill(Color.ORANGE);
        bottomCircle.setStroke(Color.BLACK);
        bottomCircle.setStrokeWidth(5);
        bottomCircle.setFill(Color.WHITE);
        midCircle.setStroke(Color.BLACK);
        midCircle.setStrokeWidth(5);
        midCircle.setFill(Color.WHITE);
        topCircle.setStroke(Color.BLACK);
        topCircle.setStrokeWidth(5);
        topCircle.setFill(Color.WHITE);
        GridPane pane = new GridPane();


        pane.add(topCircle, 1, 0);     // Верхний круг в строке 0
        pane.add(midCircle, 1, 1);     // Средний круг в строке 1
        pane.add(bottomCircle, 1, 2);  // Нижний круг в строке 2
        pane.add(triangle, 2, 0);          // Нос в строке 0, колонка 2

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
        test.main();//NOTE возможно стоит это перенести в Gui
    }
}