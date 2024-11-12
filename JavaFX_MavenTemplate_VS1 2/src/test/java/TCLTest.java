import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Arrays;

import gamelogic.*;
import components.*;

public class TCLTest {

    @Test
    //testing that it correctly recognizes three of a kind
    public void testThreeOfAKind(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('H', 10);
        hand.add(first);
        Card second = new Card('D', 10);
        hand.add(second);
        Card third = new Card('C', 10);
        hand.add(third);

        assertEquals(2, ThreeCardLogic.evalHand(hand), "should be three of a kind value (2)");
    }

    @Test
    //testing that it correctly recognizes a straigh flush
    public void testStraightFlush(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('H', 14);
        hand.add(first);
        Card second = new Card('H', 13);
        hand.add(second);
        Card third = new Card('H', 12);
        hand.add(third);

        assertEquals(1, ThreeCardLogic.evalHand(hand), "should be a straight flush");
    }

    @Test
    //testing another combination of a straight flush
    public void testAnotherStraightFlush(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('S', 5);
        hand.add(first);
        Card second = new Card('S', 4);
        hand.add(second);
        Card third = new Card('S', 3);
        hand.add(third);

        assertEquals(1, ThreeCardLogic.evalHand(hand), "should be a straight flush");
    }

    @Test
    //testing that it correctly recognizes a pair
    public void testPair(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('S', 5);
        hand.add(first);
        Card second = new Card('D', 5);
        hand.add(second);
        Card third = new Card('S', 3);
        hand.add(third);

        assertEquals(5, ThreeCardLogic.evalHand(hand), "should be recognized as a pair");
    }

    @Test
    //testing that it correctly recognizes a flush
    public void testFlush(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('H', 6);
        hand.add(first);
        Card second = new Card('H', 5);
        hand.add(second);
        Card third = new Card('H', 2);
        hand.add(third);

        assertEquals(4, ThreeCardLogic.evalHand(hand), "should be recognized as a flush");
    }

    @Test
    //testing that it correctly recognizes a straight
    public void testStraight(){
        ArrayList<Card> hand = new ArrayList<>();
        System.out.println("IN THIS TEST");
        Card first = new Card('H', 4);
        hand.add(first);
        Card second = new Card('D', 5);
        hand.add(second);
        Card third = new Card('S', 3);
        hand.add(third);

        assertEquals(3, ThreeCardLogic.evalHand(hand), "should be recognized as a straight even if unordered");
    }

    @Test
    //testing that it correctly recognizes a high card
    public void testHighCard(){
        ArrayList<Card> hand = new ArrayList<>();

        Card first = new Card('H', 2);
        hand.add(first);
        Card second = new Card('D', 6);
        hand.add(second);
        Card third = new Card('S', 12);
        hand.add(third);

        assertEquals(0, ThreeCardLogic.evalHand(hand), "should be recognized as a high card");
    }

    @Test
    //testing tiebreaker with both having a highest card
    public void tieBreakerHighestCard(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('D', 7);
        playerHand.add(second);
        Card third = new Card('D', 6);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 11);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 5);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 12);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should beat dealerHand because they have a higher highest card");
    }

    @Test
    //testing tiebreaker with both having a highest card but having the same highest card so must compare second highest now
    public void tieBreakerSecondHighestCard(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('D', 12);
        playerHand.add(second);
        Card third = new Card('D', 6);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 13);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 5);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 11);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should beat dealerHand because they have a higher second highest card");
    }

    @Test
    //testing tiebreaker with both having a highest card but having the same highest two cards so must compare last card
    public void tieBreakerThirdHighestCard(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('D', 12);
        playerHand.add(second);
        Card third = new Card('D', 6);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 13);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 12);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 5);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should beat dealerHand because they have a higher last card");
    }

    @Test
    //test that it correctly returns a tie only when all cards are the same
    public void tieAllCardsAreTheSame(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('D', 12);
        playerHand.add(second);
        Card third = new Card('D', 6);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 13);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 12);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 6);
        dealerHand.add(dthird);

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand and dealerHand are the same so should return a tie");
    }

    @Test
    //testing three of a kind tie breaker - we go with the higher three of a kind
    public void threeOfAKindTie(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 3);
        playerHand.add(first);
        Card second = new Card('D', 3);
        playerHand.add(second);
        Card third = new Card('S', 3);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 2);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 2);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 2);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should win because even though they both have three of a kind playerHands has a higher value");
    }

    @Test
    //testing straight combo - the higher cards should be winning
    public void straightTie(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('D', 12);
        playerHand.add(second);
        Card third = new Card('S', 11);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 8);
        dealerHand.add(dfirst);
        Card dsecond = new Card('D', 9);
        dealerHand.add(dsecond);
        Card dthird = new Card('C', 7);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should win because even though they both have straights playerHand has higher cards");
    }

    @Test
    //testing a straight flush combo - higher cards shoould be the one winning
    public void straightFlushTie(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('H', 12);
        playerHand.add(second);
        Card third = new Card('H', 11);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 8);
        dealerHand.add(dfirst);
        Card dsecond = new Card('H', 9);
        dealerHand.add(dsecond);
        Card dthird = new Card('H', 7);
        dealerHand.add(dthird);

        assertEquals(2, ThreeCardLogic.compareHands(dealerHand, playerHand), "playerHand should win because even though they both have straights flushes playerHand has higher cards");
    }

    @Test
    //testing dealer ties - same test as above except dealer should be winning to make sure it goes both ways
    public void straightFlushTieDealer(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 8);
        playerHand.add(first);
        Card second = new Card('H', 9);
        playerHand.add(second);
        Card third = new Card('H', 7);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 13);
        dealerHand.add(dfirst);
        Card dsecond = new Card('H', 12);
        dealerHand.add(dsecond);
        Card dthird = new Card('H', 11);
        dealerHand.add(dthird);

        assertEquals(1, ThreeCardLogic.compareHands(dealerHand, playerHand), "dealerHand should win because even though they both have straights flushes dealerHand has higher cards");
    }

    @Test
    //should return 0 because they have the same straight flushes
    public void straightFlushTieDealerTie(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 8);
        playerHand.add(first);
        Card second = new Card('H', 9);
        playerHand.add(second);
        Card third = new Card('H', 7);
        playerHand.add(third);

        ArrayList<Card> dealerHand = new ArrayList<>();
        Card dfirst = new Card('H', 8);
        dealerHand.add(dfirst);
        Card dsecond = new Card('H', 9);
        dealerHand.add(dsecond);
        Card dthird = new Card('H', 7);
        dealerHand.add(dthird);

        assertEquals(0, ThreeCardLogic.compareHands(dealerHand, playerHand), "dealerHand and playerHand are the same so should return 0");
    }

    @Test
    //make sure it is returning correct amount of PP winnings with straight flush
    public void betStraightFlushRet(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 13);
        playerHand.add(first);
        Card second = new Card('H', 12);
        playerHand.add(second);
        Card third = new Card('H', 11);
        playerHand.add(third);

        assertEquals(200, ThreeCardLogic.evalPPWinnings(playerHand, 5), "playerHand should get a return bet of 200 because they bet 5 dollars and have a straight flush");
    }

    @Test
    //make sure it is returning correct amount for three of a kind
    public void betThreeOfAKindRet(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 3);
        playerHand.add(first);
        Card second = new Card('S', 3);
        playerHand.add(second);
        Card third = new Card('C', 3);
        playerHand.add(third);

        assertEquals(150, ThreeCardLogic.evalPPWinnings(playerHand, 5), "playerHand should get a return bet of 150 because they bet 5 dollars and have a three of a kind");
    }

    @Test
    //make sure they are not getting anything because they have a high card combination which does not fall under PP
    public void betHighestCardPPRet(){
        ArrayList<Card> playerHand = new ArrayList<>();
        Card first = new Card('H', 12);
        playerHand.add(first);
        Card second = new Card('S', 5);
        playerHand.add(second);
        Card third = new Card('C', 3);
        playerHand.add(third);

        assertEquals(0, ThreeCardLogic.evalPPWinnings(playerHand, 5), "playerHand should get a return bet of 0 because they bet 5 dollars and have a highest card");
    }

    @Test
    // test flush hand pair plus return
    public void betFlushRet() {
        ArrayList<Card> playerHand = new ArrayList<>();
        playerHand.add(new Card('H', 10));
        playerHand.add(new Card('H', 7));
        playerHand.add(new Card('H', 3));

        assertEquals(15, ThreeCardLogic.evalPPWinnings(playerHand, 5), "playerHand should get a return bet of 15 because they bet 5 dollars and have a flush");
    }

}