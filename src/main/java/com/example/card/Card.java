package com.example.card;

public class Card {

    private final String cardSource;

    private final Integer numberOfFields;

    public String getCardSource() {
        return cardSource;
    }

    public Integer getNumberOfFields() {
        return numberOfFields;
    }

    public Card(String cardSource, Integer numberOfFields) {
        this.cardSource = cardSource;
        this.numberOfFields = numberOfFields;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardSource='" + cardSource + '\'' +
                ", numberOfFields=" + numberOfFields +
                '}';
    }
}
