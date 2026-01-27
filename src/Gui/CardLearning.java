package Gui;

import Cards.Card;
import Cards.CardCollection;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.util.Duration;

import java.util.ArrayList;

public class CardLearning {
    ArrayList<Card> cards;
    int index = 0;
    int size;
    ArrayList<Card> doneCards;
    ArrayList<Card> undoneCards;
    private Node currentCard;
    private double mouseAnchorX;
    private double mouseAnchorY;


    public CardLearning(CardCollection cards) {
        this.cards = cards.getCards();
        size = this.cards.size();
        doneCards = new ArrayList<>();
        undoneCards = new ArrayList<>();
    }

    public CardLearning(ArrayList<Card> cards) {
        this.cards = cards;
        size = this.cards.size();
        doneCards = new ArrayList<>();
        undoneCards = new ArrayList<>();
    }

    public int sizeDoneCards() {
        return doneCards.size();
    }

    public int sizeUndoneCards() {
        return undoneCards.size();
    }

    public int sizeCards() {
        return cards.size();
    }

    public int getIndex() {
        return index;
    }

    public Card getCard(int index) {
        return cards.get(index);
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


    public Card prev() {
        if (index == 0) {
            return null;
        }
        index--;
        totalRemove(index);
        return cards.get(index);
    }

    public void putDone() {
        doneCards.add(cards.get(index));
        index++;
    }

    public void putUndone() {
        undoneCards.add(cards.get(index));
        index++;
    }

    private void swipeLeft() {
        Timeline timeline = new Timeline();

        KeyValue kvX = new KeyValue(currentCard.translateXProperty(), -500);
        KeyValue kvOpacity = new KeyValue(currentCard.opacityProperty(),0);

        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvOpacity);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(e -> {
            putUndone();
        });
    }

    private void swipeRight() {
        Timeline timeline = new Timeline();

        KeyValue kvX = new KeyValue(currentCard.translateXProperty(), 500);
        KeyValue kvOpacity = new KeyValue(currentCard.opacityProperty(),0);

        KeyFrame kf = new KeyFrame(Duration.millis(300), kvX, kvOpacity);
        timeline.getKeyFrames().add(kf);
        timeline.play();
        timeline.setOnFinished(e -> {
            putDone();
        });
    }




    public void round() {
        while (index < cards.size()) {
            Button prevButton = new Button("Предыдущая карточка");
            prevButton.setOnAction(e -> {
                if (index > 0) {
                    this.prev();
                    System.out.println("Вернемся к предыдущей карточке");
                } else {
                    System.out.println("Это первая карточка");
                }
            });

            Card card = cards.get(index);
            card.setShowFace(true);
            currentCard = card.getUICard();
            myVariant();


        }
    }

    public void switchCond() {
        cards = (ArrayList<Card>) undoneCards.clone();
        undoneCards.clear();
        index = 0;
    }

    public void myVariant() {

        currentCard.setOnMousePressed(pressed -> {
            mouseAnchorX = pressed.getSceneX() - currentCard.getTranslateX();
        });
        currentCard.setOnMouseDragged(dragged -> {
            double newX = dragged.getSceneX() - mouseAnchorX;
            double maxX = 50;

            double finalX;
            if (newX < 0) {
                finalX = 0;
            } else if (newX > maxX) {
                finalX = maxX;
                swipeRight();
            } else {
                finalX = newX;
            }

            currentCard.setTranslateX(finalX);
        });
    }

//    public void learn() {
//        Scanner scanner = new Scanner(System.in);
//        while (doneCards.size() < size) {
//            this.round(scanner);
//            if (undoneCards.size() == 0) {
//                break;
//            }
//            System.out.println("У тебя отслось " + undoneCards.size() + " невыученных карточек, желаешь продолжить?");
//            if (index >= cards.size() && undoneCards.size() > 0) {
//                switchCond();
//            }
//            String ans = scanner.next();
//            if (ans.equals("нет")) {
//                System.out.println("Неуч ебанный, пока");
//                return;
//            }
//        }
//        System.out.println("молодец, ты все выучил");
//        scanner.close();
//    }

}
