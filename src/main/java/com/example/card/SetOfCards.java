package com.example.card;

import com.example.util.UtilHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SetOfCards {

    private static final String CARDS_PROPERTIES = "src/main/resources/imageSources.properties";

    private final List<Card> listOfCards = new ArrayList<>();

    private Card card1;
    private Card card2;
    private Card card3;
    private Card card4;
    private Card specialCard;

    public List<Card> getListOfCards() {
        return listOfCards;
    }

    public void addCards(List<Card> listOfCards, Card card, Integer numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            listOfCards.add(card);
        }
    }

    public void readCardImageSources() {
        try {
            Properties p = new Properties();
            p.load(new FileInputStream(CARDS_PROPERTIES));

            card1 = new Card(p.getProperty("cardOne"), 1);
            card2 = new Card(p.getProperty("cardTwo"), 2);
            card3 = new Card(p.getProperty("cardThree"), 3);
            card4 = new Card(p.getProperty("cardFour"), 4);
            specialCard = new Card(p.getProperty("specialCard"), 0);

        } catch (IOException e) {
            UtilHelper.logExceptions(SetOfCards.class, e);
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
