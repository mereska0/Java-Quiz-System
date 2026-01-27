package Gui;

import Cards.Card;
import Cards.CardCollection;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.util.Duration;

import java.util.ArrayList;

public class CardLearning {
    ArrayList<Card> cards;
    private int index = 0;
    private final int size;
    ArrayList<Card> doneCards;
    ArrayList<Card> undoneCards;
    private Node currentCard;
    private double mouseAnchorX;
    private Runnable onSwipeComplete;
    private static final double MAX_X = 250;
    private static final double MIN_X = -250;




    public CardLearning(CardCollection cards) {
        this.cards = cards.getCards();
        size = this.cards.size();
        doneCards = new ArrayList<>();
        undoneCards = new ArrayList<>();
    }

    public void setOnSwipeComplete(Runnable callback) {
        this.onSwipeComplete = callback;
    }



    public int sizeDoneCards() {
        return doneCards.size();
    }

    public int sizeUndoneCards() {
        return undoneCards.size();
    }

    public int sizeCards() {
        return size;
    }

    public int currentSizeCards() {
        return cards.size();
    }

    public int getIndex() {
        return index;
    }

    protected Node getCurrentCard() {
        return currentCard;
    }

    private void safeRemove(int index, ArrayList<Card> collection) {
        Card card = cards.get(index);
        if (collection.contains(card)) {
            collection.remove(card);
        }
    }

    private void totalRemove(int index) {
        safeRemove(index, doneCards);
        safeRemove(index, undoneCards);
    }


    public void prev() {
        if (index == 0) {
            System.out.println("first card");
            return;
        }
        index--;
        totalRemove(index);
    }

    public void putDone() {
        doneCards.add(cards.get(index));
        index++;
    }

    public void putUndone() {
        undoneCards.add(cards.get(index));
        index++;
    }

    private void swipeRight() {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(currentCard.translateXProperty(), 500);
        KeyValue kvOpacity = new KeyValue(currentCard.opacityProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvOpacity);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        timeline.setOnFinished(e -> {
            putDone();
            // Вызываем callback после свайпа
            if (onSwipeComplete != null) {
                onSwipeComplete.run();
            }
        });
    }

    private void swipeLeft() {
        Timeline timeline = new Timeline();
        KeyValue kvX = new KeyValue(currentCard.translateXProperty(), -500);
        KeyValue kvOpacity = new KeyValue(currentCard.opacityProperty(), 0);
        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvOpacity);
        timeline.getKeyFrames().add(kf);
        timeline.play();

        timeline.setOnFinished(e -> {
            putUndone();
            // Вызываем callback после свайпа
            if (onSwipeComplete != null) {
                onSwipeComplete.run();
            }
        });
    }

    public void round() {
        if (index < cards.size()) {
            Card card = cards.get(index);
            card.setShowFace(true);
            currentCard = card.getUICard();
            resetCardProperties();
            cardInteraction();
        } else {
            System.out.println("Индекс выходит за границы!");
        }
    }

    public void switchCond() {
        cards = (ArrayList<Card>) undoneCards.clone();
        undoneCards.clear();
        index = 0;
    }

    public void cardInteraction() {
        currentCard.setOnMousePressed(pressed -> {
            mouseAnchorX = pressed.getSceneX() - currentCard.getTranslateX();
        });

        currentCard.setOnMouseDragged(dragged -> {
            double newX = dragged.getSceneX() - mouseAnchorX;

            double finalX;
            if (newX < MIN_X) {
                finalX = MIN_X;
            } else if (newX > MAX_X) {
                finalX = MAX_X;
            } else {
                finalX = newX;
            }

            currentCard.setTranslateX(finalX);
        });

        currentCard.setOnMouseReleased(released -> {
            double currentTranslateX = currentCard.getTranslateX();
            double swipeTrigger = 100;

            if (currentTranslateX >= swipeTrigger) {
                // Свайп вправо - карточка выучена
                swipeRight();
            } else if (currentTranslateX <= -swipeTrigger) {
                // Свайп влево - карточка не выучена
                swipeLeft();
            } else {
                // Возвращаем на место
                Timeline reset = new Timeline();
                KeyValue kv = new KeyValue(currentCard.translateXProperty(), 0);
                KeyFrame kf = new KeyFrame(Duration.millis(200), kv);
                reset.getKeyFrames().add(kf);
                reset.play();
            }
        });
    }

    private void resetCardProperties() {
        if (currentCard != null) {
            currentCard.setTranslateX(0);  // ← СБРОС сдвига!
            currentCard.setOpacity(1.0);   // ← СБРОС прозрачности!
            currentCard.setVisible(true);  // ← Убедимся что видима!
        }
    }



}
