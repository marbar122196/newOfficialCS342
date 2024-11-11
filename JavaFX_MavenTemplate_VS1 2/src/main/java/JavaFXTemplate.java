import components.*;
import gamelogic.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;



public class JavaFXTemplate extends Application {
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

		// Load Game Scene FXML and set the controller
		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/StartNewGame.fxml"));
		Parent gameRoot = gameLoader.load();
		Scene gameScene = new Scene(gameRoot);
		gameScene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());

		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((screenBounds.getWidth() - 1500) / 2); // Adjust 1500 to match the width of your window
		primaryStage.setY((screenBounds.getHeight() - 800) / 2);

		// Initialize the game controller with the primary stage and game data
		StartNewGameController gameController = gameLoader.getController();
		gameController.initializeGame(playerOne, playerTwo, theDealer, primaryStage);

		// Initialize the welcome screen controller with primary stage and game scene
		WelcomeScreenController welcomeController = welcomeLoader.getController();
		welcomeController.init(primaryStage, gameScene);

		// Set the initial scene to welcome screen and show the stage
		primaryStage.setScene(welcomeScene);
		primaryStage.show();
	}
}
