package com.example.card;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SetOfCards {

    protected List<Card> listOfCards = new ArrayList<>();

    public List<Card> getListOfCards() {
        return listOfCards;
    }

    public void setListOfCards(List<Card> listOfCards) {
        this.listOfCards = listOfCards;
    }

    protected Card card1;
    protected Card card2;
    protected Card card3;
    protected Card card4;
    protected Card specialCard;

    private static final String cardsProperties = "src/main/resources/imageSources.properties";

    public void addCards(List<Card> listOfCards, Card card, Integer numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            listOfCards.add(card);
        }
    }

    public void readCardImageSources() {
        try {

            Properties p = new Properties();
            p.load(new FileInputStream(cardsProperties));

            card1 = new Card(p.getProperty("cardOne"), 1);
            card2 = new Card(p.getProperty("cardTwo"), 2);
            card3 = new Card(p.getProperty("cardThree"), 3);
            card4 = new Card(p.getProperty("cardFour"), 4);
            specialCard = new Card(p.getProperty("specialCard"), 0);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public SetOfCards() {
        readCardImageSources();
        addCards(listOfCards, card1, 10);
        addCards(listOfCards, card2, 10);
        addCards(listOfCards, card3, 10);
        addCards(listOfCards, card4, 10);
        addCards(listOfCards, specialCard, 12);
        Collections.shuffle(listOfCards);
    }

    @Override
    public String toString() {
        return "SetOfCards{" +
                "listOfCards=" + listOfCards +
                '}';
    }
}
