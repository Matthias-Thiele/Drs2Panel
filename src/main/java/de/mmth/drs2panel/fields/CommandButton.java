/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Drs2;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class CommandButton extends HBox {
  private final static int SET_TIME = 60;
  private final Drs2 drs;
  private final Pane lamp;
  private int resetCount = 0;
  private final int buttonId;
  private final int ledId;
  private boolean actLedState = false;
  private final String activeColor, passiveColor;
  private final Button button;
  
  public CommandButton(Drs2 drs, String name, int buttonId, int ledId, Color buttonColor) {
    this.drs = drs;
    this.buttonId = buttonId;
    this.ledId = ledId;
    this.activeColor = "#" + buttonColor.toString().substring(2);
    this.passiveColor = "#" + buttonColor.darker().desaturate().toString().substring(2);
    this.setSpacing(5);
    
    button = new Button(name);
    button.setStyle("-fx-background-color: " + passiveColor);
    button.setOnAction(ev -> {
      drs.setSwitch(buttonId, true);
      button.setStyle("-fx-background-color: " + activeColor);
      resetCount = SET_TIME;
    });
    
    lamp = new Pane();
    lamp.setPrefSize(25, 25);
    lamp.setStyle(actLedState ? ("-fx-background-color:" + activeColor) : ("-fx-background-color: gray"));
    
    this.getChildren().addAll(button, lamp);
  }
  
  public void tick() {
    if (resetCount > 0) {
      resetCount--;
      if (resetCount == 0) {
        drs.setSwitch(buttonId, false);
        this.button.setStyle("-fx-background-color: " + passiveColor);
      }
    }
    
    if (drs.getLampState(ledId) != actLedState) {
      actLedState = !actLedState;
      lamp.setStyle(actLedState ? ("-fx-background-color:" + activeColor) : ("-fx-background-color: gray"));
    }
  }
}
