package gamelogic;

import java.util.ArrayList;
import java.util.Collections;

public class Deck extends ArrayList<Card>{

    private ArrayList<Character> suits = new ArrayList<>();
    private ArrayList<Integer> values = new ArrayList<>();

    public Deck(){
        suits.add('H');
        suits.add('S');
        suits.add('C');
        suits.add('D');

        for (int i = 2; i <= 14; i++){
            values.add(i);
        }
        newDeck();
    }

    public void newDeck(){

        this.clear();

        for (char suit : suits){
            for (Integer value : values){
                Card newCard = new Card(suit, value);
                this.add(newCard);
            }
        }

        Collections.shuffle(this);
    }
}