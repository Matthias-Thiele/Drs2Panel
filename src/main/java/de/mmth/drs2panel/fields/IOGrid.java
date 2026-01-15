/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Const;
import de.mmth.drs2panel.io.Uart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author matthias
 */
public class IOGrid extends GridPane {
  private int nextButtonCol = 0;
  private final Uart drs2;
  
  public IOGrid(Uart drs2) {
    this.drs2 = drs2;
    this.setStyle("-fx-border-color: green; -fx-padding: 10px");
    this.setHgap(10);
    this.setVgap(10);
    addButtons();
    addLamps();
  }
  
  private void addButtons() {
    var label = new Label("Eingaben");
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    this.add(label, nextButtonCol++, 0);
    addInput("Fa A", Const.SCHLUESSEL_A);
    addInput("Fa F", Const.SCHLUESSEL_F);
  }
  
  private void addLamps() {
    var label = new Label("Ausgaben");
    this.add(label, 0, 1);
  }
  
  private void addInput(String name, int ioId) {
    var bt = new Button(name);
    var state = new ButtonState();
    state.isPressed = false;
    state.ioId = ioId;
    bt.setUserData(state);
    bt.setPrefWidth(Presets.FIELD_WIDTH / 2 - 5);
    bt.setOnAction(e -> {
      ButtonState s = (ButtonState) bt.getUserData();
      s.isPressed = !s.isPressed;
      bt.setStyle(s.isPressed ? "-fx-background-color: lightgreen": "");
      drs2.setSwitch(s.ioId, s.isPressed);
    });
    this.add(bt, nextButtonCol++, 0);
  }
  
  class ButtonState {
    boolean isPressed;
    int ioId;
  }
}
