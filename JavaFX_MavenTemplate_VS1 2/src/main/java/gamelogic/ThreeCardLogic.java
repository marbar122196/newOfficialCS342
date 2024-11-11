package gamelogic;

import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.ArrayList;

public class ThreeCardLogic{
    private static final int[][] straight = { //combination of all straights
            {12, 13, 14},
            {11, 12, 13},
            {10, 11, 12},
            {9, 10, 11},
            {8, 9, 10},
            {7, 8, 9},
            {6, 7, 8},
            {5, 6, 7},
            {4, 5, 6},
            {3, 4, 5},
            {2, 3, 4},
            {2, 3, 14},
    };

    public static int getHighest(ArrayList<Card> hand){ //to be able to get the highest card - for tiebreaker
        int max = 0;

        for (int i = 0; i < 3; i++){
            if (hand.get(i).getValue() > max){
                max = hand.get(i).getValue();
            }
        }

        return max;
    }

    //takes an arraylist and evaluates the hand
    public static int evalHand(ArrayList<Card> hand){
        int[] combo = new int[3]; //to be able to compare with the 2d array above
        boolean isPair = false;
        boolean isFlush = false;
        boolean isStraight = false;
        boolean isACombo = false;
        boolean matchFound = false;

        if (hand.get(0).getSuit() == hand.get(1).getSuit() && hand.get(0).getSuit() == hand.get(2).getSuit() && hand.get(1).getSuit() == hand.get(2).getSuit()){
            isFlush = true; //checks if all are the same suit
        }

        for (int i = 0; i < 3; i++){ //pushes it into combo array
            combo[i] = hand.get(i).getValue();
        }

        Arrays.sort(combo); //sorts array from lowest to highest

        if (combo[0] == combo[1] && combo[0] == combo[2] && combo[1] == combo[2]){ // check for three of a kind
            return 2;
        }
        else if (combo[0] == combo[1] || combo[1] == combo[2]){ //check for pair
            return 5;
        }

        for (int i = 0; i < straight.length; i++){  //begins to loop through 2d array
            matchFound = false;
            isACombo = true; //starts off as a combo
            for (int j = 0; j < straight[i].length; j++){ //loops through specific combinations to be able to compare
                if (combo[j] == straight[i][j]){ //they should be in the same place since they are sorted so it compares indices
                    if (j == 2 && isACombo){ //if it is at the last element and IsACombo is still true that means all numbers matched
                        matchFound = true; //means we have found a match! so lets break
                        break;
                    }
                }
                else{
                    isACombo = false; //means they did not match with the current combination
                }
            }

            if (matchFound){ //we need to break again to get out of both for loops
                break;
            }
        }

        if (matchFound && isFlush){ //if it is a flush and we found a straight combo - straight flush
            return 1;
        }
        else if (matchFound) { // if not flush but a combo that means it is a straight
            return 3;
        }
        else if (isFlush){ //if it is just a flush
            return 4;
        }

        return 0; //otherwise return 0;
    }

    public static int evalPPWinnings(ArrayList<Card> dealer, int bet){

        int handValue = evalHand(dealer);

        //multiply bet based off hand

        if (handValue == 1){ //straight flush
            return bet * 40;
        }
        if (handValue == 2){ //three of a kind
            return bet * 30;
        }
        if (handValue == 3){ //straight
            return bet * 6;
        }
        if (handValue == 4){ //flush
            return bet * 3;
        }
        if (handValue == 5){ //pair
            return bet;
        }

        return 0;
    }


    //check this to see if we can do it differently
    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){
        int playerVal = evalHand(player);
        int dealerVal = evalHand(dealer);

        if (playerVal < dealerVal && playerVal != 0){
            return 2;
        }
        else if (playerVal == 0 && dealerVal > 0){
            return 1;
        }
        else if (playerVal > dealerVal && dealerVal != 0){
            return 1;
        }
        else if (playerVal > 0 && dealerVal == 0){
            return 2;
        }
        else{

            int dealerHighestCard = getHighest(dealer);
            int playersHighestCard = getHighest(player);

            if (playersHighestCard < dealerHighestCard){
                return 1;
            }
            else if (playersHighestCard > dealerHighestCard){
                return 2;
            }
            else{
               return 0;
            }

        }
    }
}