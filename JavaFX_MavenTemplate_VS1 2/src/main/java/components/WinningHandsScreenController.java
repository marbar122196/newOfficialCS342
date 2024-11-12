package components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class WinningHandsScreenController {

    @FXML
    private Label winningHandsText;  // Link to the Label in FXML

    @FXML
    private void initialize() {//Winning hands that are shown
        winningHandsText.setText(
                "Straight Flush: 40 to 1\n\n" +
                        "Three of a Kind: 30 to 1\n\n" +
                        "Straight: 6 to 1\n\n" +
                        "Flush: 3 to 1\n\n" +
                        "Pair: 1 to 1"
        );
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) winningHandsText.getScene().getWindow();
        stage.close();
    }
}
