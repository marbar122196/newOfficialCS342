package gamelogic;

import java.util.ArrayList;

public class Player{

    private ArrayList<Card> hand;
    private int playBet;
    private int pairPlusBet;
    private int anteBet;
    private int totalWinnings;

    public Player(){
        hand = new ArrayList<>();
    }

    public void setAnteBet(int anteBet){
        this.anteBet = anteBet;
    }

    public void setPairPlusBet(int pairPlusBet){
        this.pairPlusBet = pairPlusBet;
    }

    public void setPlayBet(int playBet){
        this.playBet = playBet;
    }

    public void setHand(ArrayList<Card> hand){
        this.hand = hand;
    }

    public void setTotalWinnings(int totalWinnings){
        this.totalWinnings = totalWinnings;
    }

    public int getAnteBet(){
        return anteBet;
    }

    public int getPlayBet(){
        return playBet;
    }

    public int getPairPlusBet(){
        return pairPlusBet;
    }

    public int getTotalWinnings(){
        return totalWinnings;
    }

    public ArrayList<Card> getHand(){
        return hand;
    }



//    public void resetWinnings(){}
//    public boolean lockInBets(){}
//    public boolean isBetsLocked(){}
}

