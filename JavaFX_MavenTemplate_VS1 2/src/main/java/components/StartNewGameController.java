package components;

import gamelogic.*;
import javafx.stage.Modality;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.application.Platform;

import java.util.ArrayList;
import javafx.scene.image.Image;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class StartNewGameController {
    private Dealer dealer;
    private Player playerOne;
    private Player playerTwo;

    private Stage primaryStage;  // Primary stage for the main game

    private OptionsMenuController optionsMenuController; // Store reference to OptionsMenuController



    private boolean isPlayerTwo = false;
    private ArrayList<Card> dealerHand;
    private ArrayList<Card> playerOneHand;
    private ArrayList<Card> playerTwoHand;
    private ArrayList<String> diffHands = new ArrayList<>();

    private boolean playerOnePress = false;
    private boolean playerTwoPress = false;

    private boolean playerOnePressPlay = false;
    private boolean playerTwoPressPlay = false;

    private boolean playerOnePressFold = false;
    private boolean playerTwoPressFold = false;

    @FXML private BorderPane rootPane;

    // Top Buttons
    @FXML private Button optionsButton;
    @FXML private Button rulesButton;

    // Player 1 Controls
    @FXML private TextField playPlayerOne;
    @FXML private TextField antePlayerOne;
    @FXML private TextField pairPlusPlayerOne;
    @FXML private TextField amtWinningsPlayerOne;
    @FXML private TextField namePlayerOne;
    @FXML private ImageView p1c1Image1;
    @FXML private ImageView p1c1Image2;
    @FXML private ImageView p1c1Image3;
    @FXML private Button playerOnePlay;
    @FXML private Button playerOneFold;
    @FXML private Label handNamePlayerOne;


    // Player 2 Controls
    @FXML private TextField playPlayerTwo;
    @FXML private TextField antePlayerTwo;
    @FXML private TextField pairPlusPlayerTwo;
    @FXML private TextField amtWinningsPlayerTwo;
    @FXML private TextField namePlayerTwo;
    @FXML private ImageView p2c1Image1;
    @FXML private ImageView p2c2Image2;
    @FXML private ImageView p2c3Image3;
    @FXML private Button playerTwoPlay;
    @FXML private Button playerTwoFold;
    @FXML private Label handNamePlayerTwo;

    // Dealer Controls
    @FXML private ImageView dc1Image1;
    @FXML private ImageView dc2Image2;
    @FXML private ImageView dc3Image3;
    @FXML private Button dealGame;

    // Commentary TextArea
    @FXML private TextArea gameCommentary;


    public void initialize() {
        //to initialize the game
        gameCommentary.setText("Hello! Please check rules before getting started.");
        initializeGame(playerOne, playerTwo, dealer,primaryStage);

        optionsButton.setOnAction(event -> showOptionsMenu());
        rulesButton.setOnAction(event -> showRulesScreen());

        //to handle the name setting
        namePlayerOne.setOnAction(event -> {
            String nameP1 = namePlayerOne.getText();
            namePlayerOne.setEditable(false); //should not be able to change name after entering
            namePlayerOne.setText(nameP1);
            enablePlayerTwoNameAndAnte();
            enableDeal();
        });

        //to handle the ante setting
        antePlayerOne.setOnAction(event -> {
            //if enable deal returns a 1 that means the bets are valid and we can proceed
            if (enableDeal() == 1){
                String anteP1 = antePlayerOne.getText();
                antePlayerOne.setText(anteP1);
                pairPlusPlayerOne.setDisable(false); //must at least have an ante to have a pair
                enablePlayerTwoNameAndAnte();
                setupListeners();
            }
        });

        //to handle pairplus setting
        pairPlusPlayerOne.setOnAction(event ->{
            if(checkPairPlus() == 0){
                gameCommentary.setText("Player 1's pair plus bet is valid");
                pairPlusPlayerOne.setEditable(false);
                String pairPlusP1 = pairPlusPlayerOne.getText();
                pairPlusPlayerOne.setText(pairPlusP1);
                playerOne.setPairPlusBet(Integer.parseInt(pairPlusPlayerOne.getText()));
            }
            else{
                dealGame.setDisable(true);
                gameCommentary.setText("Pair plus bet is invalid");
            }
        });

        amtWinningsPlayerOne.setEditable(false);

        namePlayerTwo.setOnAction(event -> {
            String nameP2 = namePlayerTwo.getText();
            namePlayerTwo.setEditable(false);
            antePlayerTwo.setEditable(true);
            namePlayerTwo.setText(nameP2);
            isPlayerTwo = true;
            enableDeal();
        });

        antePlayerTwo.setOnAction(event -> {
            if (enableDeal() == 1){
                String anteP1 = antePlayerTwo.getText();
                pairPlusPlayerTwo.setDisable(false);
                antePlayerTwo.setText(anteP1);

//                //if there is a player two this will not go off in player ones setAction so we put it here
//                anteP1 = antePlayerOne.getText();
//                antePlayerOne.setText(anteP1);
//                pairPlusPlayerOne.setDisable(false);
//                antePlayerOne.setEditable(false);
                setupListeners();
            }
        });

        pairPlusPlayerTwo.setOnAction(event ->{
            if(checkPairPlus() == 0){
                gameCommentary.setText("Player Twos pair plus bet is valid!");
                pairPlusPlayerTwo.setEditable(false);
                String pairPlusP2 = pairPlusPlayerTwo.getText();
                pairPlusPlayerTwo.setText(pairPlusP2);
                playerTwo.setPairPlusBet(Integer.parseInt(pairPlusPlayerTwo.getText()));
            }
            else{
                dealGame.setDisable(true);
                gameCommentary.setText("Pair plus bet is invalid");
            }
        });

        amtWinningsPlayerTwo.setEditable(false);

        dealGame.setOnAction(event -> startDeal());
    }

@FXML
private void showOptionsMenu() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OptionsMenu.fxml"));
        Parent root = loader.load();

        OptionsMenuController controller = loader.getController();
        controller.initializeData(playerOne, playerTwo, dealer, primaryStage);

        // Set the stage reference in the OptionsMenuController
        Stage optionsStage = new Stage();
        controller.setStage(optionsStage);

        Scene optionsScene = new Scene(root);
        optionsStage.initModality(Modality.APPLICATION_MODAL);
        optionsStage.setTitle("Options");
        optionsStage.setScene(optionsScene);
        optionsStage.initOwner(primaryStage);
        optionsScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        optionsStage.showAndWait();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    @FXML
    private void handleNewLook() {
        String newStyle = primaryStage.getScene().getStylesheets().contains(getClass().getResource("/invert.css").toExternalForm())
                ? "/style.css" : "/invert.css";

        primaryStage.getScene().getStylesheets().clear();
        primaryStage.getScene().getStylesheets().add(getClass().getResource(newStyle).toExternalForm());

        // Apply the new stylesheet to the OptionsMenu if it’s open
        if (optionsMenuController != null && optionsMenuController.getStage().isShowing()) {
            optionsMenuController.getStage().getScene().getStylesheets().clear();
            optionsMenuController.getStage().getScene().getStylesheets().add(getClass().getResource(newStyle).toExternalForm());
        }
    }



    @FXML
    private void showRulesScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/RulesScreen.fxml"));
            Parent root = loader.load();

            Scene rulesScene = new Scene(root);
            rulesScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            Stage rulesStage = new Stage();
            rulesStage.initModality(Modality.APPLICATION_MODAL);
            rulesStage.setTitle("Rules");
            rulesStage.setScene(rulesScene);
            rulesStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void initializeGame(Player playerOne, Player playerTwo, Dealer dealer,Stage primaryStage) {

        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.dealer = dealer;
        this.primaryStage = primaryStage;

        namePlayerOne.setEditable(true);
        antePlayerOne.setEditable(true);
        pairPlusPlayerOne.setDisable(true);
        pairPlusPlayerTwo.setDisable(true);

        namePlayerTwo.setEditable(false);
        antePlayerTwo.setEditable(false);

        populateDiffHands();
        resetImagesFaceDown();

    }

    private void populateDiffHands() {
        diffHands.clear();
        diffHands.add("High-Card");
        diffHands.add("Straight Flush");
        diffHands.add("Three of a Kind");
        diffHands.add("Straight");
        diffHands.add("Flush");
        diffHands.add("Pair");
    }

    public String getHandLabel(int Hand){

        return diffHands.get(Hand);
    }

    private void resetImagesFaceDown() {
        setCardImage(p1c1Image1, "/facedown.png");
        setCardImage(p1c1Image2, "/facedown.png");
        setCardImage(p1c1Image3, "/facedown.png");
        setCardImage(p2c1Image1, "/facedown.png");
        setCardImage(p2c2Image2, "/facedown.png");
        setCardImage(p2c3Image3, "/facedown.png");
        setCardImage(dc1Image1, "/facedown.png");
        setCardImage(dc2Image2, "/facedown.png");
        setCardImage(dc3Image3, "/facedown.png");
    }

    private void enablePlayerTwoNameAndAnte() {
        if (!namePlayerOne.getText().isEmpty() && !antePlayerOne.getText().isEmpty()) {
            namePlayerTwo.setEditable(true); // Enable Player 2's name input
            antePlayerTwo.setEditable(true); // Enable Player 2's ante input
            gameCommentary.setText("if a second player would like to play please enter information now :]");
        }
    }

    private void setupListeners() {

        playerOnePlay.setOnAction(event -> {
            playerOnePlay.setDisable(true);
            playerOneFold.setDisable(true);
            playerOnePressPlay = true;
            playerOnePress = true;
            bothPlayersReady();
        });

        playerOneFold.setOnAction(event -> {
            playerOnePlay.setDisable(true);
            playerOneFold.setDisable(true);
            playerOnePress = true;
            playerOnePressFold = true;
            bothPlayersReady();
        });

        playerTwoPlay.setOnAction(event -> {
            playerTwoPlay.setDisable(true);
            playerTwoFold.setDisable(true);
            playerTwoPress = true;
            playerTwoPressPlay = true;
            bothPlayersReady();
        });

        playerTwoFold.setOnAction(event -> {
            playerTwoPlay.setDisable(true);
            playerTwoFold.setDisable(true);
            playerTwoPress = true;
            playerTwoPressFold = true;
            bothPlayersReady();
        });
    }


    public int checkBetValid(int bet){
        if (bet < 5 || bet > 25){
            return -1;
        }
        return 0;
    }

    public int enableDeal() {
        // Retrieve and validate Player 1's name and ante

        String nameP1 = namePlayerOne.getText();
        String anteTextP1 = antePlayerOne.getText();
        boolean isAlphabeticalP1 = anteTextP1.matches("[a-zA-Z]+"); //checks if letters were entered in bets

        if (isAlphabeticalP1){
            dealGame.setDisable(true);
            gameCommentary.setText("No letters allowed!");
            return 0;
        }

        boolean hasNameP1 = !nameP1.isEmpty();
        int anteP1 = Integer.parseInt(anteTextP1);
        int p1BetStatus = checkBetValid(anteP1);


        // If there are two players, retrieve and validate Player 2's name and ante
        if (isPlayerTwo) {
            String nameP2 = namePlayerTwo.getText();

            String anteTextP2 = antePlayerTwo.getText();
            boolean isAlphabeticalP2 = anteTextP2.matches("[a-zA-Z]+"); // to see if they entered a letter in the bet which is not allowed

            if (isAlphabeticalP2){
                dealGame.setDisable(true);
                gameCommentary.setText("No letters allowed!!");
                return 0;
            }
            boolean hasNameP2 = !nameP2.isEmpty();

            // check both players have entered their names
            if (hasNameP1 && hasNameP2) {
                int anteP2 = Integer.parseInt(anteTextP2);
                int p2BetStatus = checkBetValid(anteP2);

                // cetermine the status of both players' bets -1 means invalid bet 0 means a good bet was made
                if (p1BetStatus == -1 && p2BetStatus == 0) {
                    gameCommentary.setText("Player 1's bet is invalid, please try again");
                    return 0;
                }
                else if (p2BetStatus == -1 && p1BetStatus == 0) {
                    gameCommentary.setText("Player 2's bet is invalid, please try again");
                    return 0;
                }
                else if (p1BetStatus == -1 && p2BetStatus == -1) {
                    gameCommentary.setText("Both bets are invalid, please try again.");
                    return 0;
                }
                else {
                    // both bets are valid
                    gameCommentary.setText("Bets look good!");
                    playerOne.setAnteBet(anteP1);
                    playerTwo.setAnteBet(anteP2);
                    dealGame.setDisable(false); //now we should be able to deal the game so enable the button
                    return 1;
                }
            }
            else {
                dealGame.setDisable(true);
            }
        }

        // if only Player 1 is playing or Player 2 is not yet enabled
        if (p1BetStatus == -1) {
            gameCommentary.setText("Bet is invalid, please try again.");
            return 0;
        } else {
            gameCommentary.setText("Looks great!");
            playerOne.setAnteBet(anteP1);
            dealGame.setDisable(false);
            return 1;
        }
    }

    // since we dont lock bets after they are entered we need to make sure they have valid bets when the deal button is enabled
    private int checkBetsDealEnabled(){
        String anteTextP1 = antePlayerOne.getText();

        // check if anteTextP1 is empty or contains only alphabetical characters
        boolean isAlphabeticalP1 = anteTextP1.matches("[a-zA-Z]+");
        if (anteTextP1.isEmpty() || isAlphabeticalP1) { //if they delete their bet disable the game
            gameCommentary.clear();
            gameCommentary.setText("Invalid bet! Trying to be sneaky?.");
            dealGame.setDisable(true);
            return 0;
        }

        int anteP1 = Integer.parseInt(anteTextP1);  // now since we checked if it was empty we can parse it
        if (checkBetValid(anteP1) == -1) {
            gameCommentary.clear();
            gameCommentary.setText("Bet cant be be more than 25 or less than 5!!");
            dealGame.setDisable(true);
            return 0;
        }

        if (isPlayerTwo) { //same as above but for player two
            String anteTextP2 = antePlayerTwo.getText();

            // Check if anteTextP2 is empty or contains only alphabetical characters
            boolean isAlphabeticalP2 = anteTextP2.matches("[a-zA-Z]+");
            if (anteTextP2.isEmpty() || isAlphabeticalP2) {
                gameCommentary.clear();
                gameCommentary.setText("Invalid bet for Player 2! Trying to be sneaky?");
                dealGame.setDisable(true);
                return 0;
            }

            int anteP2 = Integer.parseInt(anteTextP2);  // checked if empty so now we can parse
            if (checkBetValid(anteP2) == -1) {
                gameCommentary.clear();
                gameCommentary.setText("Bet cant be be more than 25 or less than 5!!");
                dealGame.setDisable(true);
                return 0;
            }
        }

        return 1;
    }

    //when deal button is pushed do the below
    private void startDeal() {

        if (checkBetsDealEnabled() == 0){ //check once more if the bets are valid
            return;
        }

        //nothing should be able to be changed until the round is done
        namePlayerOne.setDisable(true);
        antePlayerOne.setDisable(true);
        namePlayerTwo.setDisable(true);
        antePlayerTwo.setDisable(true);
        pairPlusPlayerTwo.setDisable(true);
        pairPlusPlayerOne.setDisable(true);

        if (!isPlayerTwo){
            playerTwoPlay.setDisable(true);
            playerTwoFold.setDisable(true);
        }

        playerOnePress = false;
        playerOnePressPlay = false;
        playerOnePressFold = false;
        playerTwoPress = false;
        playerTwoPressPlay = false;
        playerTwoPressFold = false;


        //these things needs to happen every round regardless of # of players
        resetImagesFaceDown(); //resets all cards to be face down
        dealerHand = dealer.getHand(); //gets dealers hand
        gameCommentary.clear(); //clears text box


        //set up for Player ONE SOLELY
        dealer.dealPlayer(playerOne); //deals to the player
        playerOneHand = playerOne.getHand(); //gets the hand of player


        //converts PlayerOnes hands to strings to grab images
        String playerOneCardOne = "/" + playerOneHand.get(0).getSuit() + " " + playerOneHand.get(0).getValue() + ".png";
        String playerOneCardTwo = "/" + playerOneHand.get(1).getSuit() + " " + playerOneHand.get(1).getValue() + ".png";
        String playerOneCardThree = "/" + playerOneHand.get(2).getSuit() + " " + playerOneHand.get(2).getValue() + ".png";


        //revealed Cards for Player One
        Image playerOneRevealedOne = new Image(getClass().getResourceAsStream(playerOneCardOne));
        Image playerOneRevealedTwo = new Image(getClass().getResourceAsStream(playerOneCardTwo));
        Image playerOneRevealedThree = new Image(getClass().getResourceAsStream(playerOneCardThree));

        //set up for player Two if there is one
        dealer.dealPlayer(playerTwo); //deals to player
        playerTwoHand = playerTwo.getHand(); //gets hand of player


        //now doing the same thing but for PlayerTwo
        String playerTwoCardOne = "/" + playerTwoHand.get(0).getSuit() + " " + playerTwoHand.get(0).getValue() + ".png";
        String playerTwoCardTwo = "/" + playerTwoHand.get(1).getSuit() + " " + playerTwoHand.get(1).getValue() + ".png";
        String playerTwoCardThree = "/" +playerTwoHand.get(2).getSuit() + " " + playerTwoHand.get(2).getValue() + ".png";


        //revealed Cards for Player Two
        Image playerTwoRevealedOne = new Image(getClass().getResourceAsStream(playerTwoCardOne));
        Image playerTwoRevealedTwo = new Image(getClass().getResourceAsStream(playerTwoCardTwo));
        Image playerTwoRevealedThree = new Image(getClass().getResourceAsStream(playerTwoCardThree));


        //reveals cards sequentially
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event ->{
                    p1c1Image1.setImage(playerOneRevealedOne);
                    if (isPlayerTwo) {
                        p2c1Image1.setImage(playerTwoRevealedOne);
                    }
                }),
                new KeyFrame(Duration.seconds(2), event ->{
                    p1c1Image2.setImage(playerOneRevealedTwo);
                    if (isPlayerTwo) {
                        p2c2Image2.setImage(playerTwoRevealedTwo);
                    }
                }),
                new KeyFrame(Duration.seconds(3), event ->{
                    p1c1Image3.setImage(playerOneRevealedThree);
                    if (isPlayerTwo) {
                        p2c3Image3.setImage(playerTwoRevealedThree);
                    }
                }),
                new KeyFrame(Duration.seconds(3.5), event ->{
                    String handLabelP1 = getHandLabel(ThreeCardLogic.evalHand(playerOneHand));
                    handNamePlayerOne.setText(handLabelP1);

                    if (isPlayerTwo) {
                        String handLabelP2 = getHandLabel(ThreeCardLogic.evalHand(playerTwoHand));
                        handNamePlayerTwo.setText(handLabelP2);
                    }

                    //enable the play and fold butttons for players once the cards are revealed
                    playerOnePlay.setDisable(false);
                    playerOneFold.setDisable(false);
                    if (isPlayerTwo){
                        playerTwoPlay.setDisable(false);
                        playerTwoFold.setDisable(false);
                    }

                    setupListeners(); //call function which checks which button (play or fold) was pressed
                    dealGame.setDisable(true);
                })
        );


        timeline.play();
    }

    //sets the card images - a helper function for playerscards images
    private void setCardImage(ImageView imageView, String cardPic) {
        Image image = new Image(getClass().getResourceAsStream(cardPic));
        imageView.setImage(image);
    }


    //after players have decided to fold or play we reveal the dealers cards
    private void revealDealersCards() {
        dealerHand = dealer.getDeck();


        //converts dealers hand to strings to grab images from resources
        String dealerCardOne = "/" + dealerHand.get(0).getSuit() + " " + dealerHand.get(0).getValue() + ".png";
        String dealerCardTwo = "/" + dealerHand.get(1).getSuit() + " " + dealerHand.get(1).getValue() + ".png";
        String dealerCardThree = "/" + dealerHand.get(2).getSuit() + " " + dealerHand.get(2).getValue() + ".png";


        Image dealerReavealedOne = new Image(getClass().getResourceAsStream(dealerCardOne));
        Image dealerRevealedTwo = new Image(getClass().getResourceAsStream(dealerCardTwo));
        Image dealerRevealedThree = new Image(getClass().getResourceAsStream(dealerCardThree));


        //reveals dealers cards sequentially and then after reveal calls evaluate winner
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event ->{
                    dc1Image1.setImage(dealerReavealedOne);
                }),
                new KeyFrame(Duration.seconds(2), event ->{
                    dc2Image2.setImage(dealerRevealedTwo);
                }),
                new KeyFrame(Duration.seconds(3), event ->{
                    dc3Image3.setImage(dealerRevealedThree);


                    dealGame.setDisable(false);
                }),
                new KeyFrame(Duration.seconds(3.5), event -> evaluateWinner())
        );


        timeline.play();


    }

    //if both players are playing - this function checks to see whether both of them have pressed a button so it doesnt start dealing in the middle of a round
    private void bothPlayersReady() {

        if (playerOnePress && (!isPlayerTwo || playerTwoPress)) { //makes it so it works for both single and 2 player mode
            if (playerOnePressPlay) { //if player pressed play we need to now set their play ante as well
                playPlayerOne.setText(playerOne.getAnteBet() + "");
                playerOne.setPlayBet(playerOne.getAnteBet());
            }
            else if (playerOnePressFold) { //if pressed fold just subtract ante bet from curr winnings for now
                int p1Winnings = playerOne.getTotalWinnings();
                p1Winnings = p1Winnings - playerOne.getAnteBet();
                playerOne.setTotalWinnings(p1Winnings);
            }

            if (isPlayerTwo) { // if there is a second player playing
                if (playerTwoPressPlay) { //if player two pressed play then set play bet
                    playPlayerTwo.setText(playerTwo.getAnteBet() + "");
                    playerTwo.setPlayBet(playerTwo.getAnteBet());
                }
                else if (playerTwoPressFold) { // if pressed fold then subtract from player twos current winnings
                    int p2Winnings = playerTwo.getTotalWinnings();
                    p2Winnings = p2Winnings - playerTwo.getAnteBet();
                    playerTwo.setTotalWinnings(p2Winnings);
                }
            }

            revealDealersCards();
        }
    }

    //evaluates the pair plus bet whether they won it or lost it and changes winnings accordingly
    private int evaluatePairPlusBet(Player player, int playerPairPlusResult, int winnings, String playerName) {
        if (playerPairPlusResult != -2) { //if playerPairPlusResult is -2 that means they did not make a bet
            if (playerPairPlusResult != -1) {
                winnings = winnings + playerPairPlusResult;
                gameCommentary.appendText(" " + playerName + " won pair plus wager :D +" + playerPairPlusResult);
            } else {
                winnings = winnings - player.getPairPlusBet();
                gameCommentary.appendText(" " + playerName + " lost pair plus wager :( -" + player.getPairPlusBet());
            }
        }
        return winnings;
    }

    private void evaluateWinner() {

        int dealHandVal = ThreeCardLogic.evalHand(dealerHand);
        if (dealHandVal == 0) {
            int highestCard = ThreeCardLogic.getHighest(dealerHand);


            if (highestCard < 12) {
                gameCommentary.setText("Dealer does not have at least Queen high; ante wager is pushed");


                antePlayerOne.setEditable(false);
                pairPlusPlayerOne.setDisable(false);
                pairPlusPlayerOne.setEditable(true);


                if (isPlayerTwo){
                    antePlayerTwo.setEditable(false);
                    pairPlusPlayerTwo.setDisable(false); //they should be able to change their PP bet right?
                    pairPlusPlayerOne.setEditable(true);
                }
                if (ifPairPlusBetMadePlayerOne() != 2){
                    int p1Winnings = evaluatePairPlusBet(playerOne, ifPairPlusBetMadePlayerOne(), playerOne.getTotalWinnings(), "Player One");
                    playerOne.setTotalWinnings(p1Winnings);
                    amtWinningsPlayerOne.setText(playerOne.getTotalWinnings() + "");
                }
                if (isPlayerTwo) {
                    int p2Winnings = evaluatePairPlusBet(playerTwo, ifPairPlusBetMadePlayerTwo(), playerTwo.getTotalWinnings(), "Player Two");
                    playerTwo.setTotalWinnings(p2Winnings);
                    amtWinningsPlayerTwo.setText(playerTwo.getTotalWinnings() + "");
                }
                return;
            }
        }

        int winnerP1 = ThreeCardLogic.compareHands(dealerHand, playerOneHand);
        int winningsP1 = playerOne.getTotalWinnings();
        int winningsP2 = playerTwo.getTotalWinnings();


        if (playerOnePressPlay && playerTwoPressPlay){
            int winnerP2 = ThreeCardLogic.compareHands(dealerHand, playerTwoHand);

            if (winnerP1 == 2 && winnerP2 == 2){
                winningsP1 = winningsP1 + ((playerOne.getAnteBet() + playerOne.getPlayBet()) * 2);
                winningsP2 = winningsP2 + ((playerTwo.getAnteBet() + playerTwo.getPlayBet()) * 2);

                gameCommentary.setText("Both players won against the dealer! :D");
            }
            else if (winnerP1 == 1 && winnerP2 == 2){
                gameCommentary.setText("Player 1 lost against dealer. Congrats to Player 2!");
                winningsP1 = winningsP1 - (playerOne.getAnteBet() + playerOne.getPlayBet());
                winningsP2 = winningsP2 + ((playerTwo.getAnteBet() + playerTwo.getPlayBet()) * 2);
            }
            else if (winnerP1 == 2 && winnerP2 == 1){
                gameCommentary.setText("Player 2 lost against dealer. Congrats to Player 1");
                winningsP1 = winningsP1 + ((playerOne.getAnteBet() + playerOne.getPlayBet()) * 2);
                winningsP2 = winningsP2 - (playerTwo.getAnteBet() + playerTwo.getPlayBet());
            }
            else if (winnerP1 == 1 && winnerP2 == 1){
                gameCommentary.setText("Both player lost against the dealer! >:)");
                winningsP1 = winningsP1 - (playerOne.getAnteBet() + playerOne.getAnteBet());
                winningsP2 = winningsP2 - (playerTwo.getAnteBet() + playerTwo.getAnteBet());
            }
            else{
                gameCommentary.setText("This game is a tie! :O");
            }
        }
        else if ((playerOnePressPlay || playerTwoPressPlay) && (playerOnePressFold || playerTwoPressFold)){
            int p2Hand = ThreeCardLogic.evalHand(playerTwoHand);
            int winnerP2 = ThreeCardLogic.compareHands(dealerHand, playerTwoHand);


            if (winnerP1 == 2 || winnerP2 == 2){
                if (winnerP1 == 2){
                    winningsP1 = winningsP1 + ((playerOne.getAnteBet() + playerOne.getPlayBet()) * 2);
                }
                else if (winnerP2 == 2){
                    winningsP2 = winningsP2 + ((playerTwo.getAnteBet() + playerTwo.getPlayBet()) * 2);
                }
                gameCommentary.setText("Player won against dealer! :D");
            }
            else if (winnerP1 == 1 || winnerP2 == 1){
                if (winnerP1 == 1){
                    winningsP1 = winningsP1 - (playerOne.getAnteBet() + playerOne.getPlayBet());
                }
                else if (winnerP2 == 1){
                    winningsP2 = winningsP2 - (playerTwo.getAnteBet() + playerTwo.getPlayBet());
                }
                gameCommentary.setText("Dealer won against player! >:)");
            }
            else {
                gameCommentary.setText("This game is a tie! :O");
            }
        }
        else if (playerOnePressPlay && !isPlayerTwo){
            if (winnerP1 == 2){
                winningsP1 = winningsP1 + ((playerOne.getAnteBet() + playerOne.getPlayBet()) * 2);
                gameCommentary.setText("Player won against dealer!");
            }
            else if (winnerP1 == 1){
                winningsP1 = winningsP1 - (playerOne.getAnteBet() + playerOne.getPlayBet());
                gameCommentary.setText("Player lost against dealer");
            }
            else{
                gameCommentary.setText("This is a draw!");
            }
        }

        int playerOnePairPlusMade = ifPairPlusBetMadePlayerOne();
        int playerTwoPairPlusMade = ifPairPlusBetMadePlayerTwo();

//        if (playerOnePairPlusMade != -2){
//            if (playerOnePairPlusMade != -1){
//                winningsP1 = winningsP1 + playerOnePairPlusMade;
//                gameCommentary.appendText(" Player One won pair plus wager :D +" + playerOnePairPlusMade);
//            }
//            else{
//                winningsP1 = winningsP1 - playerOne.getPairPlusBet();
//                gameCommentary.appendText(" Player One lost pair plus wager :( -" + playerOne.getPairPlusBet());
//            }
//        }
//
//        if (playerTwoPairPlusMade != -2){
//            if (playerTwoPairPlusMade != -1){
//                winningsP2 = winningsP2 + playerTwoPairPlusMade;
//                gameCommentary.appendText(" Player Two won pair plus wager +" + playerTwoPairPlusMade);
//            }
//            else {
//                winningsP2 = winningsP2 - playerTwo.getPairPlusBet();
//                gameCommentary.appendText(" Player Two lost pair plus wager :( -" + playerTwo.getPairPlusBet());
//            }
//        }

        winningsP1 = evaluatePairPlusBet(playerOne, ifPairPlusBetMadePlayerOne(), winningsP1, "Player One");
        if (isPlayerTwo) {
            winningsP2 = evaluatePairPlusBet(playerTwo, ifPairPlusBetMadePlayerTwo(), winningsP2, "Player Two");
        }

        playerOne.setTotalWinnings(winningsP1);
        amtWinningsPlayerOne.setText(playerOne.getTotalWinnings() + "");
        antePlayerOne.setEditable(true);
        antePlayerOne.setDisable(false);
        pairPlusPlayerOne.setDisable(false);

        if (isPlayerTwo){
            antePlayerTwo.setEditable(true);
            antePlayerTwo.setDisable(false);
            playerTwo.setTotalWinnings(winningsP2);
            amtWinningsPlayerTwo.setText(playerTwo.getTotalWinnings() + "");
            pairPlusPlayerTwo.setDisable(false);
        }
    }

    public int checkPairPlus(){
        String pairPlus = pairPlusPlayerOne.getText();
        int pairPlusNum = Integer.parseInt(pairPlus);

        return checkBetValid(pairPlusNum);
    }

    //helper function to determine winnings for pair plus bet for playerOne
    public int ifPairPlusBetMadePlayerOne(){
        int playerPP = ThreeCardLogic.evalHand(playerOneHand);

        if (pairPlusPlayerOne.getText().isEmpty()){
            return -2;
        }

        if (playerPP > 0){
            return ThreeCardLogic.evalPPWinnings(playerOneHand, playerOne.getPairPlusBet());
        }
        else {
            return -1;
        }
    }

    public int ifPairPlusBetMadePlayerTwo(){
        int playerPP = ThreeCardLogic.evalHand(playerTwoHand);

        if (pairPlusPlayerTwo.getText().isEmpty()){
            return -2;
        }

        if (playerPP > 0){
            return ThreeCardLogic.evalPPWinnings(playerTwoHand, playerTwo.getPairPlusBet());
        }
        else {
            return -1;
        }
    }

    public void setIsPlayerTwo(boolean isPlayerTwo) {
        this.isPlayerTwo = isPlayerTwo;
        namePlayerTwo.setEditable(isPlayerTwo);
    }

}
