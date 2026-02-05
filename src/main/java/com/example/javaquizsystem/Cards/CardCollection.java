package com.example.javaquizsystem.Cards;

import java.io.IOException;
import java.util.ArrayList;

public class CardCollection {
    private int id;
    private ArrayList<Card> cards;
    private String name;
    private int size;

    public CardCollection(String name) {
        this.name = name;
        cards = new ArrayList<>();
        size = cards.size();
    }

    public void add(Card card) {
        cards.add(card);
        size++;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card get (int index) {
       return cards.get(index);
    }

    public void delete(Card card) {
        cards.remove(card);
        size--;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void exportCards() throws IOException {
        // TODO
    }

    public void importCards() throws IOException {
        // TODO
    }

}