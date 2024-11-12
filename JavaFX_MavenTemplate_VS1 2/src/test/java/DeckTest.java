import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.*;
import components.*;

public class DeckTest {

    @Test
    //make sure the constructor initializes the deck
    public void testDeckContructorInit() {
        Deck deck = new Deck();
        assertNotNull(deck, "should not be null");
    }

    @Test
    //test that the deck is not empty when constructor is called
    public void testDeckNotEmpty() {
        Deck deck = new Deck();

        assertTrue(!deck.isEmpty(), "deck should not be empty ever");
    }

    @Test
    //make sure it constructor populates with 52 cards
    public void testDeckSizeInit(){
        Deck deck = new Deck();
        assertEquals(52, deck.size(), "Should contain 52 cards");
    }

    @Test
    //each value should have 4 cards for it so it totals to 52 cards
    public void testDeck4CardsPerNumber() {
        Deck deck = new Deck();
        for (int value = 2; value <= 14; value++) {
            int count = 0;
            for (Card card : deck) {
                if (card.getValue() == value) {
                    count++;
                }
            }
            assertEquals(4, count, "Deck should have four cards with value " + value);
        }
    }

    @Test
    //deck should have 13 cards per suit
    public void test13CardsPSuit() {
        Deck deck = new Deck();
        char[] suits = {'H', 'S', 'C', 'D'};

        for (char suit : suits) {
            int count = 0;
            for (Card card : deck) {
                if (card.getSuit() == suit){
                    count++;
                }
            }
            assertEquals(13, count, "Deck should have 13 cards per suit");
        }
    }

    @Test
    //different initializes of decks should have different ordering of cards
    public void testDeckShuffling() {
        Deck firstD = new Deck();
        Deck secondD = new Deck();

        assertNotEquals(firstD, secondD, "Should not be the same");
    }

    @Test
    //test that new deck does refill the deck after cards being removed
    public void testDeckRefilling() {
        Deck deck = new Deck();

        deck.remove(0);
        deck.remove(0);
        deck.remove(0);
        deck.remove(0);

        deck.newDeck();
        assertEquals(52, deck.size(), "Deck should be reset to 52 cards");
    }

    @Test
    //check that every card in the deck has a minimum value of 2 and no greater than 14
    public void testCardVals() {
        Deck deck = new Deck();

        for (Card c : deck){
            assertTrue(c.getValue() >= 2, "Card should not be less than 2");
            assertTrue(c.getValue() <= 14, "Card value should not be greater than 14");
        }
    }

    @Test
    //test that new deck has a re ordering of cards after we call newDeck
    public void testNewDeckReshuffle() {
        Deck deck = new Deck();

        Card card = deck.get(0);

        deck.newDeck();

        assertNotEquals(card, deck.get(0), "Cards should not be the same after a reshuffling");
    }

    @Test
    // test that each card in the deck is unique
    public void testUniqueCardsInDeck() {
        Deck deck = new Deck();
        ArrayList<String> cards = new ArrayList<>();

        for (Card card : deck) {
            String suitAndVal = card.getSuit() + String.valueOf(card.getValue());
            assertFalse(cards.contains(suitAndVal), "Deck contains duplicate card");
            cards.add(suitAndVal);
        }

        assertEquals(52, cards.size(), "All cards in the deck should be unique.");
    }
}