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
        // Set up any default values, placeholders, or initial UI state here.
        gameCommentary.setText("Hello! Please enter a name and ante wager to start. Min bet: $5, Max bet: $25.");
        initializeGame(playerOne, playerTwo, dealer,primaryStage);

        optionsButton.setOnAction(event -> showOptionsMenu());
        rulesButton.setOnAction(event -> showRulesScreen());

        namePlayerOne.setOnAction(event -> {
            String nameP1 = namePlayerOne.getText();
            namePlayerOne.setEditable(false);
            namePlayerOne.setText(nameP1);
            namePlayerTwo.setEditable(true);
            enableDeal();
        });

        antePlayerOne.setOnAction(event -> {
            if (enableDeal() == 1){
                antePlayerOne.setEditable(false);
                setupListeners();
            }
        });

        pairPlusPlayerOne.setOnAction(event ->{
            if(checkPairPlus() == 0){
                gameCommentary.setText("Player 1's pair plus bet is valid");
                pairPlusPlayerOne.setEditable(false);
                playerOne.setPairPlusBet(Integer.parseInt(pairPlusPlayerOne.getText()));
            }
            else{
                dealGame.setDisable(true);
                gameCommentary.setText("Pair plus bet is invalid");
            }
        });

        // Set listener on namePlayerTwo to activate two-player mode if it has been filled
        namePlayerTwo.setOnAction(event -> {
            String nameP2 = namePlayerTwo.getText();
            isPlayerTwo = true;
            namePlayerTwo.setEditable(false);
            namePlayerTwo.setText(nameP2);
            enableDeal();
        });

        antePlayerTwo.setOnAction(event -> {
            if (enableDeal() == 1){
                antePlayerTwo.setEditable(false);
                setupListeners();
            }
        });

        pairPlusPlayerTwo.setOnAction(event ->{
            if(checkPairPlus() == 0){
                gameCommentary.setText("Player Twos pair plus bet is valid!");
                pairPlusPlayerTwo.setEditable(false);
                playerTwo.setPairPlusBet(Integer.parseInt(pairPlusPlayerTwo.getText()));
            }
            else{
                dealGame.setDisable(true);
                gameCommentary.setText("Pair plus bet is invalid");
            }
        });

    }

    @FXML
    private void showOptionsMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/OptionsMenu.fxml"));
            Parent root = loader.load();

            OptionsMenuController controller = loader.getController();
            controller.initializeData(playerOne, playerTwo, dealer, primaryStage);

            Scene optionsScene = new Scene(root);
            Stage optionsStage = new Stage();
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

        populateDiffHands();
        resetImagesFaceDown();
//        setupListeners();

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

    private void setCardImage(ImageView imageView, String cardPic) {
        Image image = new Image(getClass().getResourceAsStream(cardPic));
        imageView.setImage(image);
    }

    private void setupListeners() {

//        dealGame.setOnAction(event -> startDeal());
        // Add action listeners for buttons and text fields

        dealGame.setOnAction(event -> startDeal()); // Start the deal sequence

        playerOnePlay.setOnAction(event -> {
            playerOnePlay.setDisable(true);
            playerOneFold.setDisable(true);
            playerOnePressPlay = true;
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

        System.out.println("SHOULD BE CALLLLEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDddd");
        String nameP1 = namePlayerOne.getText();
        String anteTextP1 = antePlayerOne.getText();

        boolean hasNameP1 = !nameP1.isEmpty();
        int anteP1 = Integer.parseInt(anteTextP1);
        int p1BetStatus = checkBetValid(anteP1);

        System.out.println("BLAHBLAHBLAH");

        // If there are two players, retrieve and validate Player 2's name and ante
        if (isPlayerTwo) {
            String nameP2 = namePlayerTwo.getText();
            String anteTextP2 = antePlayerTwo.getText();
            boolean hasNameP2 = !nameP2.isEmpty();

            // Check both players have entered their names
            if (hasNameP1 && hasNameP2) {
                int anteP2 = Integer.parseInt(anteTextP2);
                int p2BetStatus = checkBetValid(anteP2);

                // Determine the status of both players' bets
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
                    // Both bets are valid
                    gameCommentary.setText("Bets look good!");
                    playerOne.setAnteBet(anteP1);
                    playerTwo.setAnteBet(anteP2);
                    dealGame.setDisable(false);
                    System.out.println("helooasoansfoanoif");
                    return 1;
                }
            }
        }

        // If only Player 1 is playing or Player 2 is not yet enabled
        if (p1BetStatus == -1) {
            gameCommentary.setText("Bet is invalid, please try again.");
            return 0;
        } else {
            gameCommentary.setText("Looks great, let's get started!");
            playerOne.setAnteBet(anteP1);
            dealGame.setDisable(false);
            return 1;
        }
    }


    private void startDeal() {

        namePlayerOne.setDisable(true);
        antePlayerOne.setDisable(true);
        namePlayerTwo.setDisable(true);
        antePlayerTwo.setDisable(true);

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


        //Converts PlayerOnes hands to strings to grab images
        String playerOneCardOne = "/" + playerOneHand.get(0).getSuit() + " " + playerOneHand.get(0).getValue() + ".png";
        String playerOneCardTwo = "/" + playerOneHand.get(1).getSuit() + " " + playerOneHand.get(1).getValue() + ".png";
        String playerOneCardThree = "/" + playerOneHand.get(2).getSuit() + " " + playerOneHand.get(2).getValue() + ".png";


        //Revealed Cards for Player One
        Image playerOneRevealedOne = new Image(getClass().getResourceAsStream(playerOneCardOne));
        Image playerOneRevealedTwo = new Image(getClass().getResourceAsStream(playerOneCardTwo));
        Image playerOneRevealedThree = new Image(getClass().getResourceAsStream(playerOneCardThree));

        //set up for player Two if there is one
        dealer.dealPlayer(playerTwo); //deals to player
        playerTwoHand = playerTwo.getHand(); //gets hand of player


        //Now doing the same thing but for PlayerTwo
        String playerTwoCardOne = "/" + playerTwoHand.get(0).getSuit() + " " + playerTwoHand.get(0).getValue() + ".png";
        String playerTwoCardTwo = "/" + playerTwoHand.get(1).getSuit() + " " + playerTwoHand.get(1).getValue() + ".png";
        String playerTwoCardThree = "/" +playerTwoHand.get(2).getSuit() + " " + playerTwoHand.get(2).getValue() + ".png";


        //Revealed Cards for Player Two
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

                    playerOnePlay.setDisable(false);
                    playerOneFold.setDisable(false);
                    if (isPlayerTwo){
                        playerTwoPlay.setDisable(false);
                        playerTwoFold.setDisable(false);
                    }

                    setupListeners();
                    dealGame.setDisable(true);
                })
        );


        timeline.play();
    }

    private void revealPlayerCards(ArrayList<Card> hand, ImageView card1, ImageView card2, ImageView card3) {
        setCardImage(card1, "/" + hand.get(0).getSuit() + " " + hand.get(0).getValue() + ".png");
        setCardImage(card2, "/" + hand.get(1).getSuit() + " " + hand.get(1).getValue() + ".png");
        setCardImage(card3, "/" + hand.get(2).getSuit() + " " + hand.get(2).getValue() + ".png");

    }

    private void revealDealersCards() {
        revealPlayerCards(dealerHand, dc1Image1, dc2Image2, dc3Image3);
    }

    private void bothPlayersReady() {

        if (playerOnePress && (!isPlayerTwo || playerTwoPress)) { //makes it so it works for both single and 2 player mode
            System.out.println("DID WE GET HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            if (playerOnePressPlay) {
                System.out.println("Should also be here");
                playPlayerOne.setText(playerOne.getAnteBet() + "");
                playerOne.setPlayBet(playerOne.getAnteBet());
            }
            else if (playerOnePressFold) {
                int p1Winnings = playerOne.getTotalWinnings();
                p1Winnings = p1Winnings - playerOne.getAnteBet();
                playerOne.setTotalWinnings(p1Winnings);
            }

            if (isPlayerTwo) {
                if (playerTwoPressPlay) {
                    System.out.println("and here also be here");
                    playPlayerTwo.setText(playerTwo.getAnteBet() + "");
                    playerTwo.setPlayBet(playerTwo.getAnteBet());
                }
                else if (playerTwoPressFold) {
                    int p2Winnings = playerTwo.getTotalWinnings();
                    p2Winnings = p2Winnings - playerTwo.getAnteBet();
                    playerTwo.setTotalWinnings(p2Winnings);
                }
            }

            System.out.println("and finally here");
            revealDealersCards();
        }
    }

    private void evaluateWinner() {

        int dealHandVal = ThreeCardLogic.evalHand(dealerHand);
        if (dealHandVal == 0) {
            int highestCard = ThreeCardLogic.getHighest(dealerHand);


            if (highestCard < 12) {
                gameCommentary.setText("Dealer does not have at least Queen high; ante wager is pushed");
                antePlayerOne.setEditable(false);

                if (isPlayerTwo){
                    antePlayerTwo.setEditable(false);
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
                winningsP1 = winningsP1 + (playerOne.getAnteBet() * 4);
                winningsP2 = winningsP2 + (playerTwo.getAnteBet() * 4);

                gameCommentary.setText("Both players won against the dealer! :D");
            }
            else if (winnerP1 == 1 && winnerP2 == 2){
                gameCommentary.setText("Player 1 lost against dealer. Congrats to Player 2!");
                winningsP1 = winningsP1 - (playerOne.getAnteBet() * playerOne.getPlayBet());
                winningsP2 = winningsP2 + (playerTwo.getAnteBet() * 4);
            }
            else if (winnerP1 == 2 && winnerP2 == 1){
                gameCommentary.setText("Player 2 lost against dealer. Congrats to Player 1");
                winningsP1 = winningsP1 + (playerOne.getAnteBet() * 4);
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
                    winningsP1 = winningsP1 + (playerOne.getAnteBet() * 4);
                }
                else if (winnerP2 == 2){
                    winningsP2 = winningsP2 + (playerTwo.getAnteBet() * 4);
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
                winningsP1 = winningsP1 + (playerOne.getAnteBet() * 4);
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

        if (playerOnePairPlusMade!= -2){
            if (playerOnePairPlusMade != -1){
                winningsP1 = winningsP1 + playerOnePairPlusMade;
                gameCommentary.appendText(" Player One won pair plus wager :D +" + playerOnePairPlusMade);
            }
            else{
                winningsP1 = winningsP1 - playerOne.getPairPlusBet();
                gameCommentary.appendText(" Player One lost pair plus wager :( -" + playerOne.getPairPlusBet());
            }
        }

        if (playerTwoPairPlusMade != -2){
            if (playerTwoPairPlusMade != -1){
                winningsP2 = winningsP2 + playerTwoPairPlusMade;
                gameCommentary.appendText(" Player Two won pair plus wager +" + playerTwoPairPlusMade);
            }
            else {
                winningsP2 = winningsP2 - playerTwo.getPairPlusBet();
                gameCommentary.appendText(" Player Two lost pair plus wager :( -" + playerTwo.getPairPlusBet());
            }
        }

        playerOne.setTotalWinnings(winningsP1);
        amtWinningsPlayerOne.setText(playerOne.getTotalWinnings() + "");
        antePlayerOne.setEditable(true);
        antePlayerOne.setDisable(false);

        if (isPlayerTwo){
            antePlayerTwo.setEditable(true);
            antePlayerTwo.setDisable(false);
            playerTwo.setTotalWinnings(winningsP2);
            amtWinningsPlayerTwo.setText(playerTwo.getTotalWinnings() + "");
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
