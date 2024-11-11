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
                setupListeners();;
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
                setupListeners();;
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
        namePlayerOne.setOnAction(event -> {
            playerOnePlay.setDisable(true);
            playerOneFold.setDisable(true);
            playerOnePress = true;
            playerOnePressPlay = true;
            bothPlayersReady();
        });

        dealGame.setOnAction(event -> startDeal()); // Start the deal sequence

        playerOnePlay.setOnAction(event -> {
            playerOnePlay.setDisable(true);
            playerOneFold.setDisable(true);
            playerOnePress = true;
            playerTwoPressPlay = true;
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
        resetImagesFaceDown();
        dealerHand = dealer.getHand();
        dealer.dealPlayer(playerOne);
        playerOneHand = playerOne.getHand();

        if (isPlayerTwo) {
            dealer.dealPlayer(playerTwo);
            playerTwoHand = playerTwo.getHand();
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> revealPlayerCards(playerOneHand, p1c1Image1, p1c1Image2, p1c1Image3)),
                new KeyFrame(Duration.seconds(2), e -> revealPlayerCards(playerTwoHand, p2c1Image1, p2c2Image2, p2c3Image3)),
                new KeyFrame(Duration.seconds(3), e -> revealDealersCards())
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
            if (playerOnePressPlay) {
                playerOnePlay.setText(playerOne.getAnteBet() + "");
                playerOne.setPlayBet(playerOne.getAnteBet());
            }
            else if (playerOnePressFold) {
                int p1Winnings = playerOne.getTotalWinnings();
                p1Winnings = p1Winnings - playerOne.getAnteBet();
                playerOne.setTotalWinnings(p1Winnings);
            }

            if (isPlayerTwo) {
                if (playerTwoPressPlay) {
                    playerTwoPlay.setText(playerTwo.getAnteBet() + "");
                    playerTwo.setPlayBet(playerTwo.getAnteBet());
                }
                else if (playerTwoPressFold) {
                    int p2Winnings = playerTwo.getTotalWinnings();
                    p2Winnings = p2Winnings - playerTwo.getAnteBet();
                    playerTwo.setTotalWinnings(p2Winnings);
                }
            }

            revealDealersCards();
        }
    }

    private void evaluateWinner() {
        // Placeholder logic for evaluating winners, can expand based on your existing logic
        // E.g., comparing hands, updating `gameCommentary`, etc.
    }

    public void setIsPlayerTwo(boolean isPlayerTwo) {
        this.isPlayerTwo = isPlayerTwo;
        namePlayerTwo.setEditable(isPlayerTwo);
    }

}
