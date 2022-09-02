package com.example.card;

public class Card {

    private String cardSource;
    private Integer numberOfFields;

    public String getCardSource() {
        return cardSource;
    }

    public void setCardSource(String cardSource) {
        this.cardSource = cardSource;
    }

    public Integer getNumberOfFields() {
        return numberOfFields;
    }

    public void setNumberOfFields(Integer numberOfFields) {
        this.numberOfFields = numberOfFields;
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
