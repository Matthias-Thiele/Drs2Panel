/*
 * (c) 2026 by Matthias Thiele
 * DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Drs2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class BaseField extends Pane {

  protected final Canvas canvas;
  protected boolean isPressed = false;
  protected Drs2 drs2;
  
  public BaseField() {
    this.setStyle("-fx-border-color: black");
    canvas = new Canvas(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
    getChildren().add(canvas);
  }
  
  public void update() {
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Presets.FIELD_BACKGROUND);
    gc.fillRect(0,0, Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
  }
  
  public void setDrs2(Drs2 drs2) {
    this.drs2 = drs2;
  }
  
  public void checkedUpdate(){};
  
  public void setButtonPressed(boolean setPressed) {
    isPressed = setPressed;
    this.setStyle(isPressed ? "-fx-border-color: red" : "-fx-border-color: black");
  }
  
  protected void drawBoxedText(GraphicsContext gc, String text, int left, int top, int width, int height, int leftOffset) {
    gc.setFill(Presets.TEXT_BACKGROUND);
    gc.fillRect(left, top, width, height);
    gc.setFill(Color.BLACK);
    gc.fillText(text, left + leftOffset, top + height - 3);
  }
  
  protected void drawShield(GraphicsContext gc, int left, int top, int width, int height) {
    gc.fillOval(left, top, height, height);
    gc.fillOval(left + width, top, height, height);
    gc.fillRect(left + height / 2, top, width, height);
  }
  
  protected Color getTrackColor(boolean withTrain, boolean inUse) {
    if (withTrain) {
      return Presets.RED_LAMP;
    } else if (inUse) {
      return Presets.WHITE_LAMP;
    } else {
      return Presets.DARK_LAMP;
    }
    
  }
}
