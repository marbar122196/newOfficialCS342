package components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class WelcomeScreenController {

    @FXML private Label intro;
    @FXML private Label authors;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    private Stage primaryStage;
    private Scene gameScene;

    // Initialize the controller with the primary stage and game scene
    public void init(Stage primaryStage, Scene gameScene) {
        this.primaryStage = primaryStage;
        this.gameScene = gameScene;
    }

    // Handler for the start button to switch to the game scene
    @FXML
    private void handleStartButton() {
        primaryStage.setScene(gameScene);
    }

    // Handler for the exit button to close the application
    @FXML
    private void handleExitButton() {
        primaryStage.close();
    }
}
