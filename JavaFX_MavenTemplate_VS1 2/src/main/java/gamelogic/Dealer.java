package gamelogic;

import java.util.ArrayList;

public class Dealer {

    private static Deck theDeck; // we CANNOT change signature so this cannot be static :(
    private ArrayList<Card> dealersHand;

    public Dealer(){
        theDeck = new Deck();
        dealersHand = new ArrayList<>();
    }

    public ArrayList<Card> dealHand() { //deals hand to dealer
        if (theDeck.size() <= 34){
            theDeck.newDeck();
        }

        dealersHand.clear();

        for (int i = 0; i < 3; i++){
            dealersHand.add(theDeck.get(0));
            theDeck.remove(0);
        }

        return dealersHand;
    }

    public void dealPlayer(Player player){ //deals hand to player
        if (theDeck.size() <= 34){
            theDeck.newDeck();
        }

        ArrayList<Card> hand = player.getHand();
        hand.clear(); //clear the hand before adding to it

        for (int i = 0; i < 3; i++){
            hand.add(theDeck.get(0));
            theDeck.remove(0);
        }

        player.setHand(hand);
    }

    public Deck getDeck(){
        return theDeck;
    } //return the deck of cards

    public ArrayList<Card> getHand(){
        return dealersHand;
    } //returns the hand of the dealer

}