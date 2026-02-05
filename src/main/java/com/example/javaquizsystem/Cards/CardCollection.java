package com.example.javaquizsystem.Cards;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardCollection {
    private int id;
    private List<Card> cards;
    private String name;
    private int size;

    public CardCollection(int id, String name) {
        this.id = id;
        this.name = name;
        cards = new ArrayList<>();
        size = cards.size();
    }

    public CardCollection(int id, String name, List<Card> cards) {
        this.id = id;
        this.name = name;
        this.cards = cards;
        size = cards.size();
    }

    public void add(Card card) {
        cards.add(card);
        size++;
    }

    public List<Card> getCards() {
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