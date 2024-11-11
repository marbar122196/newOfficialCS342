package components;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ExitScreenController {

    @FXML private Label exitScreenTitle;
    @FXML private Button yesButton;
    @FXML private Button noButton;

    private Stage optionsStage; // Reference to the options stage

    // Method to initialize optionsStage reference
    public void initializeData(Stage optionsStage) {
        this.optionsStage = optionsStage;
        System.out.println("initializeData called: optionsStage set");
    }


    // Event handler for Yes button
    @FXML
    private void handleYes() {
        if (optionsStage != null) {
            optionsStage.close();  // Close the main options stage
        }
        ((Stage) yesButton.getScene().getWindow()).close();  // Close the exit screen dialog
    }

    // Event handler for No button
    @FXML
    private void handleNo() {
        Platform.exit();  // Exit the application
    }
}
