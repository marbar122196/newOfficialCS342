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

public class StartNewGameController {
    private Dealer dealer;
    private Player playerOne;
    private Player playerTwo;
    private Stage primaryStage;  // Primary stage for the main game

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

//    public StartNewGameController(Dealer dealer, Player playerOne, Player playerTwo, Stage primaryStage) {
//        this.dealer = dealer;
//        this.playerOne = playerOne;
//        this.playerTwo = playerTwo;
//        this.primaryStage = primaryStage;
//    }

    public void initialize() {
        // Set up any default values, placeholders, or initial UI state here.
        gameCommentary.setText("Hello! Please enter a name and ante wager to start. Min bet: $5, Max bet: $25.");

//        optionsButton.setOnAction(event -> showOptionsMenu());
        rulesButton.setOnAction(event -> showRulesScreen());
    }

//    private void showOptionsMenu() {
//        OptionsMenu optionsMenu = new OptionsMenu(playerOne, playerTwo, dealer, primaryStage);
//        optionsMenu.show(primaryStage);
//    }
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
}
