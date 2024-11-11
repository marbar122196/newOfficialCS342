package components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WelcomeScreenController {

    @FXML private Label intro;
    @FXML private Label authors;
    @FXML private Button startButton;
    @FXML private Button exitButton;

    private Runnable onStartGame;

    private Scene gameScene;

    public void init(Stage primaryStage, Scene gameScene) {
        this.primaryStage = primaryStage;
        this.gameScene = gameScene;

        // Set the action for the start button to switch to the game scene
        startButton.setOnAction(e -> primaryStage.setScene(gameScene));

        // Set the action for the exit button to close the application
        exitButton.setOnAction(e -> primaryStage.close());
    }
}
