package components;

import gamelogic.Dealer;
import gamelogic.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;

public class OptionsMenuController {

    private Player playerOne;
    private Player playerTwo;
    private Dealer theDealer;
    private Stage primaryStage;

    @FXML private BorderPane optionsRoot;
    @FXML private Label optionsLabel;
    @FXML private Button freshStartButton;
    @FXML private Button newLookButton;
    @FXML private Button winningHandsButton;
    @FXML private Button exitButton;

    // Method to initialize data in the controller
    public void initializeData(Player playerOne, Player playerTwo, Dealer theDealer, Stage primaryStage) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.theDealer = theDealer;
        this.primaryStage = primaryStage;
    }

    @FXML
    private void handleFreshStart() {
        try {
            // Load the FXML file for StartNewGame
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartNewGame.fxml"));
            Parent root = loader.load();

            // Retrieve the controller and call initializeGame directly
            StartNewGameController controller = loader.getController();
            controller.initializeGame(playerOne, playerTwo, theDealer,primaryStage);

            // Set up the scene and show it on the primaryStage
            Scene scene = new Scene(root);

            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // Event handler for new look button
    @FXML
    private void handleNewLook() {
        NewLook.apply(primaryStage.getScene());
    }

    // Event handler for winning hands button
    @FXML
    private void handleWinningHands() {
        try {
            Stage winningHandsStage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/WinningHandsScreen.fxml"));
            Parent root = loader.load();

            // Assuming WinningHandsScreenController exists and handles Winning Hands screen logic
            WinningHandsScreenController controller = loader.getController();
//            controller.initializeData();  // If needed, adjust this as per controller requirements

            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            winningHandsStage.initModality(Modality.APPLICATION_MODAL);
            winningHandsStage.setTitle("Winning Hands");
            winningHandsStage.setScene(scene);
            winningHandsStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleExit() {
        try {

            // Load FXML for ExitScreen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExitScreen.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the optionsStage to it
            ExitScreenController controller = loader.getController();
            controller.initializeData((Stage) exitButton.getScene().getWindow());  // Pass the options stage

            // Setup and display the exit screen
            Stage exitStage = new Stage();
            exitStage.initModality(Modality.APPLICATION_MODAL);
            exitStage.initOwner((Stage) exitButton.getScene().getWindow());  // Set optionsStage as owner
            Scene exitScene = new Scene(root);
            exitScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
            exitStage.setScene(exitScene);
            exitStage.setTitle("Exit");
            exitStage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
