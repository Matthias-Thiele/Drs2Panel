package de.mmth.drs2panel;

import de.mmth.drs2panel.fields.FieldGrid;
import de.mmth.drs2panel.fields.IOGrid;
import de.mmth.drs2panel.io.Uart;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        var uart = new Uart();
        
        var grid = new FieldGrid(uart);
        var io = new IOGrid(uart);
        var box = new VBox();
        box.getChildren().addAll(grid, io);
        box.setSpacing(10);
        box.setPrefSize(2200, 700);
        
        var scene = new Scene(box, 2200, 700);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}