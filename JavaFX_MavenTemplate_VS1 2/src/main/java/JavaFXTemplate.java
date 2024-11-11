import components.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.text.Font;

import java.io.IOException;
import java.util.HashMap;

public class JavaFXTemplate extends Application {
	private HashMap<String, Scene> sceneMap = new HashMap<>();
	private Font customFont = Font.loadFont(getClass().getResourceAsStream("/DotGothic16-Regular.ttf"), 20);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		primaryStage.setTitle("Three Card Poker");

		// Load WelcomeScreen FXML and set up
		FXMLLoader welcomeLoader = new FXMLLoader(getClass().getResource("/fxml/WelcomeScene.fxml"));
		Parent welcomeRoot = welcomeLoader.load();
		Scene welcomeScene = new Scene(welcomeRoot);

		FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("/fxml/StartNewGame.fxml"));
		Parent gameRoot = gameLoader.load();
		Scene gameScene = new Scene(gameRoot);

		// Initialize the WelcomeScreenController with the primary stage and game scene
		welcomeController.init(primaryStage, gameScene);
		// Initialize controller and set up actions
		WelcomeScreenController welcomeController = welcomeLoader.getController();
		welcomeController.init(() -> primaryStage.setScene(sceneMap.get("game")));

		welcomeController.init(primaryStage, gameScene);

		// Display the welcome screen
		primaryStage.setScene(welcomeScene);
		primaryStage.show();

		// Store scenes for future use if needed
		sceneMap.put("welcomeScreen", welcomeScene);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
