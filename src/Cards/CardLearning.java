package Cards;

import java.util.ArrayList;
import java.util.Scanner;

public class CardLearning {
    ArrayList<Card> cards;
    int index = 0;
    int size;
    ArrayList<Card> doneCards;
    ArrayList<Card> undoneCards;

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
    }

    public void putUndone() {
        undoneCards.add(cards.get(index));
    }

    public void learn(Scanner scanner) {
        while (index < cards.size()) {
            if (index != 0) {
                System.out.println("Если хочешь вернуться к предыдущей карточке введи prev");
            }
            String prev = scanner.next();
            if (prev.equals("prev")) {    // Исправлено: equals()
                this.prev();
                continue;
            }

            Card card = cards.get(index);
            System.out.println("Карточка - " + card.getFace());
            System.out.println("Введи show");
            String input = scanner.next();
            if (!input.equals("show")) {  // Исправлено: equals()
                continue;
            }
            String content = card.getBack();
            System.out.println("Там было написано - " + content);
            System.out.println("Введи done если усвоил и undone если надо повторить");
            String command = scanner.next();
            if (command.equals("done")) {    // Исправлено: equals()
                this.putDone();
            }
            if (command.equals("undone")) {  // Исправлено: equals()
                this.putUndone();
            }
            index++;
        }
    }
    public void switchCond() {
        cards = (ArrayList<Card>) undoneCards.clone();
        undoneCards.clear();
        index = 0;
    }

    public void main() {
        Scanner scanner = new Scanner(System.in);
        while (doneCards.size() < size) {
            this.learn(scanner);
            if (undoneCards.size() == 0) {
                break;
            }
            System.out.println("У тебя отслось " + undoneCards.size() + " невыученных карточек, желаешь продолжить?");
            if (index >= cards.size() && undoneCards.size() > 0) {
                switchCond();
            }
            String ans = scanner.next();
            if (ans.equals("нет")) {
                System.out.println("Неуч ебанный, пока");
                return;
            }
        }
        System.out.println("молодец, ты все выучил");
        scanner.close();
    }

}
