import components.*;
import gamelogic.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class JavaFXTemplate extends Application {
//	private Font customFont = Font.loadFont(getClass().getResourceAsStream("/DotGothic16-Regular.ttf"), 20);
	Player playerOne;
	Player playerTwo;
	Dealer theDealer;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Font.loadFont(getClass().getResourceAsStream("/DotGothic16-Regular.ttf"), 20);
		primaryStage.setTitle("Three Card Poker");
		theDealer = new Dealer();
		playerOne = new Player();
		playerTwo = new Player();

		// Load WelcomeScreen FXML
		FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/fxml/WelcomeScene.fxml"));
		Parent welcomeRoot = welcomeLoader.load();
		Scene welcomeScene = new Scene(welcomeRoot);
		welcomeScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());



		// Load Game Scene FXML and manually set the controller
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/StartNewGame.fxml"));
//		StartNewGameController gameController = new StartNewGameController(theDealer, playerOne, playerTwo, primaryStage);
//		gameLoader.setController(gameController);  // Manually set the controller
		Parent gameRoot = gameLoader.load();
		Scene gameScene = new Scene(gameRoot);
		gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

		// Get the controller and initialize it with primary stage and game scene
		WelcomeScreenController welcomeController = welcomeLoader.getController();
		welcomeController.init(primaryStage, gameScene);

		// Set the initial scene to welcome screen
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
	}
}
