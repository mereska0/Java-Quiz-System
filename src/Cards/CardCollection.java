package Cards;

import java.io.IOException;
import java.util.ArrayList;

public class CardCollection {
    private ArrayList<Card> cards;
    private String name;
    private int size;

    public CardCollection(String name) {
        this.name = name;
        size = 0;
        cards = new ArrayList<>();
    }

    public void add(Card card) {
        cards.add(card);
        size++;
    }

    protected ArrayList<Card> getCards() {
        return cards;
    }

    public Card get (int index) {
       return cards.get(index);
    }

    public void delete(Card card) {
        cards.remove(card);
        size--;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void exportCards() throws IOException {

    }

    public void importCards() throws IOException {

    }

}