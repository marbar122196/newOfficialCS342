package components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class RulesScreenController {

    @FXML
    private Label rulesText;  // Link to the Label in FXML

    @FXML
    private void initialize() { //Rules that are shown
        rulesText.setText("Ante Wager: Players place an ante wager ($5 - $25).\n\n" +
                "Optional Pair Plus Bet: Players can also make a Pair Plus wager ($5 - $25), paying\n" +
                "based solely on their hand if it has at least a pair of 2’s, regardless of the dealer's hand.\n\n" +
                "Dealing Cards: Each player and the dealer receive three cards; players’ cards are face\n" +
                "up, the dealer’s face down.\n\n" +
                "Play or Fold:\n" +
                "• Fold: Player loses both ante and Pair Plus wagers (if made).\n" +
                "• Play: Player places a play wager equal to the ante.\n\n" +
                "Dealer's Hand:\n" +
                "• Below Queen High: Play wager is returned, and ante is pushed.\n" +
                "• Queen High or Better: Dealer’s hand is compared to the player’s hand:\n" +
                "  - Dealer Wins: Player loses both ante and play wagers.\n" +
                "  - Player Wins: Player receives 1 to 1 payout on both wagers.\n\n" +
                "  - PLEASE input player 1's information before attempting to input player 2! And make sure to press enter :)");
    }

    @FXML
    private void closeWindow() {
        Stage stage = (Stage) rulesText.getScene().getWindow();
        stage.close();
    }
}
