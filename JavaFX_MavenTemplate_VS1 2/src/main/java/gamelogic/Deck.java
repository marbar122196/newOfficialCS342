package gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card>{

    private ArrayList<Character> suits = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();

    public Deck(){ //initialize the suits array with head spades clubs and diamond
        suits.add('H');
        suits.add('S');
        suits.add('C');
        suits.add('D');

        for (int i = 2; i <= 14; i++){ //add values 2 to 14 no 1 because an Ace in this game does not represent a 1 but rather a 14
            values.add(i);
        }
        newDeck();
    }

    public void newDeck(){ //creates a new deck

        this.clear();

        for (char suit : suits){
            for (Integer value : values){
                Card newCard = new Card(suit, value);
                this.add(newCard);
            }
        }

        Collections.shuffle(this); //shuffles deck
    }
}