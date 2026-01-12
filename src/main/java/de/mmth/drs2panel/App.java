package de.mmth.drs2panel;

import de.mmth.drs2panel.fields.FieldGrid;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var grid = new FieldGrid();
        var scene = new Scene(new StackPane(grid), 2200, 600);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}