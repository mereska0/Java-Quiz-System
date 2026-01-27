package Gui;

import Cards.CardCollection;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CardLearningPane extends StackPane {
    private final CardLearning cardLearning;
    private Pane cardContainer;
    private Button exitButton;
    private Button relearnButton;
    private Label statusLabel;
    private Button prevButton;

    public CardLearningPane(CardCollection cardCollection) {
        this.cardLearning = new CardLearning(cardCollection);

        // Вот эта строка связывает CardLearning с CardLearningPane!
        cardLearning.setOnSwipeComplete(() -> {
            // После свайпа обновляем интерфейс и показываем следующую карточку
            startLearning();
        });

        initializeUI();
        startLearning();

    }

    private void initializeUI() {
        cardContainer = new StackPane();
        cardContainer.setPrefSize(400, 300);

        statusLabel = new Label();
        exitButton = new Button("Выйти");

        relearnButton = new Button("Доучить карточки");
        relearnButton.setVisible(false);
        relearnButton.setOnAction(relearn -> {
            cardLearning.switchCond();
            relearnButton.setVisible(false);
            startLearning();
        });

        prevButton = new Button("Предыдущая карточка");
        prevButton.setOnAction(prev -> {
            if (cardLearning.getIndex() > 0) {
                cardLearning.prev();
                startLearning();
            } else {
                System.out.println("Это первая карточка");
            }
        });

        // Создаем HBox для кнопок
        HBox buttonBox = new HBox(10, prevButton, exitButton, relearnButton);

        // Создаем VBox и добавляем все элементы
        VBox mainLayout = new VBox(20, cardContainer, statusLabel, buttonBox);
        this.getChildren().add(mainLayout);
    }

    private void startLearning() {

        updateStatus();

        if (cardLearning.sizeDoneCards() >= cardLearning.sizeCards()) {
            cardContainer.getChildren().clear();
            return;
        }

        if (cardLearning.getIndex() >= cardLearning.currentSizeCards()) {
            if (cardLearning.sizeUndoneCards() > 0) {
                statusLabel.setText("Осталось невыученных: " + cardLearning.sizeUndoneCards());
                relearnButton.setVisible(true);
            }
            cardContainer.getChildren().clear();
            return;
        }

        // Вызываем round() - он установит currentCard
        cardLearning.round();

        // ПОСЛЕ round() получаем актуальную карточку
        Node currentCard = cardLearning.getCurrentCard();

        if (currentCard != null) {
            cardContainer.getChildren().clear();
            cardContainer.getChildren().add(currentCard);
        } else {
            System.out.println("ОШИБКА: currentCard равен null!");
        }
    }


    private void updateStatus() {
        statusLabel.setText(
                "Прогресс: " + cardLearning.sizeDoneCards() + "/" + cardLearning.sizeCards() +
                        " | Осталось: " + cardLearning.sizeUndoneCards()
        );
    }

    public Button getExitButton() {
        return exitButton;
    }
}