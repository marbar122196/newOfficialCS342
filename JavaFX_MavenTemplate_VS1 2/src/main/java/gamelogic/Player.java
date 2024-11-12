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
    } //creates new arraylist

    public void setAnteBet(int anteBet){
        this.anteBet = anteBet;
    } //setter for ante

    public void setPairPlusBet(int pairPlusBet){
        this.pairPlusBet = pairPlusBet;
    } //setter for PP bet

    public void setPlayBet(int playBet){
        this.playBet = playBet;
    } //setter for play bet

    public void setHand(ArrayList<Card> hand){
        this.hand = hand;
    } //setter for hand

    public void setTotalWinnings(int totalWinnings){
        this.totalWinnings = totalWinnings;
    } //setter for winnings

    public int getAnteBet(){
        return anteBet;
    } //getter for ante

    public int getPlayBet(){
        return playBet;
    } //getter for play bet

    public int getPairPlusBet(){
        return pairPlusBet;
    } //getter for PP bet

    public int getTotalWinnings(){
        return totalWinnings;
    } //getter for winnings

    public ArrayList<Card> getHand(){
        return hand;
    } //getter for hand

}

