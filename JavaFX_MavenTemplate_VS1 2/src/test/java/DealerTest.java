import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.*;
import components.*;

public class DealerTest{


    @Test
    //Since we initialize the dealer with a deck and hand they should not be null
    public void testDealerConstructor() {
        Dealer dealer = new Dealer();

        assertNotNull(dealer.getDeck());
        assertNotNull(dealer.getHand());
    }

    @Test
    //checking sizes of initialized variables
    public void TestDealerConstructorEmpty() {
        Dealer dealer = new Dealer();


        assertEquals(52, dealer.getDeck().size(), "Deck should be the size of 52 cards because we just initilized it ");
        assertEquals(0, dealer.getHand().size(), "Since we have not called dealDeck dealers hand should be empty");
    }

    @Test
    //dealers deck should not be empty after dealing
    public void dealersHandNotEmpty(){
        Dealer dealer = new Dealer();

        dealer.dealHand();

        ArrayList<Card> hand = dealer.getHand();
        assertFalse(hand.isEmpty(), "Dealers hand sould not be empty");
    }
    @Test
    //check that it correctly returns 3 cards to dealer when deals deck
    public void getTestDealerConstructor() {
        Dealer dealer = new Dealer();
        ArrayList<Card> dealerHand = dealer.dealHand();
        assertEquals(3, dealerHand.size(), "dealer hand should have 3 cards after dealing");
    }

    @Test
    //test dealer gave player three cards
    public void testDealPlayer() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        dealer.dealPlayer(player);
        ArrayList<Card> playerHand = player.getHand();

        assertEquals(3, playerHand.size(), "player hand should now be populated with three cards");

    }

    @Test
    //test that after multiple deals player hand only ever gets 3 cards
    public void testDealPlayeTwicer() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        dealer.dealPlayer(player);
        ArrayList<Card> playerHand = player.getHand();

        assertEquals(3, playerHand.size(), "player hand should now be populated with three cards");

        dealer.dealPlayer(player);
        playerHand = player.getHand();

        assertEquals(3, playerHand.size(), "player should still have 3 cards after multiple deals");
    }

    @Test
    //test that the deck gets reduced after each deal
    public void testDeckReduction() {
        Dealer dealer = new Dealer();
        ArrayList<Card> deck = dealer.getDeck();

        dealer.dealHand();

        assertEquals(49, dealer.getDeck().size(), "Deck size should be 49 after dealing 3 cards since 52 - 3 = 49");
    }

    @Test
    //test that the deck gets refilled if it gets lowe than 34 cards
    public void testRefillDeck() {
        Dealer dealer = new Dealer();

        while (dealer.getDeck().size() > 34){
            dealer.dealHand();
        }

        dealer.dealHand();
        assertEquals(49, dealer.getDeck().size(), "The deck should be refilled back to 52 cards but because we are dealing it will be 49 ");
    }

    @Test
    //every time the dealer deals he should be giving out diff cards
    public void testDifferentDealsGetDiffCards() {
        Dealer dealer = new Dealer();

        dealer.dealHand();
        ArrayList<Card> fHand = new ArrayList<Card>(dealer.getHand());
        dealer.dealHand();
        ArrayList<Card> sHand = new ArrayList<Card>(dealer.getHand());

        assertNotEquals(fHand, sHand, "they should not be the same deck of cards");
    }

    @Test
    //players should also not be recieving the same cards twice if the dealer deals to them twice
    void playerHandsNotEqualAfterDeals() {
        Dealer dealer = new Dealer();
        Player player = new Player();

        int initialDeckSize = dealer.getDeck().size();

        dealer.dealPlayer(player);
        int deckSizeAfterFirstDeal = dealer.getDeck().size();
        assertEquals(initialDeckSize - 3, deckSizeAfterFirstDeal, "Deck size should decrease by 3 after dealing to player.");

        ArrayList<Card> firstHand = new ArrayList<>(player.getHand());

        dealer.dealPlayer(player);
        int deckSizeAfterSecondDeal = dealer.getDeck().size();
        assertEquals(deckSizeAfterFirstDeal - 3, deckSizeAfterSecondDeal, "Deck size should decrease by another 3 after dealing a second hand.");

        ArrayList<Card> secondHand = new ArrayList<>(player.getHand());

        assertNotEquals(firstHand, secondHand, "Two successive hands dealt to the player should not be identical.");
    }

}