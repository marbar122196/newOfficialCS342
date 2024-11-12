package gamelogic;

public class Card {
    private char suit;
    private int value;

    public Card(char suit, int value) {
        this.suit = suit;
        this.value = value;
    }

    public char getSuit() {
        return suit;
    } //returns suit

    public int getValue() {
        return value;
    } //returns value of card
}