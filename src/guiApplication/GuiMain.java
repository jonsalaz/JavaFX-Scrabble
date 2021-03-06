/**
 * Author: Jonathan Salazar
 * Entry point for the javaFX GUI Scrabble program.
 */
package guiApplication;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GuiMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/scrabble.fxml"));
        primaryStage.setTitle("Scrabble!");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
