/*
 * (c) 2026 by Matthias Thiele
 * DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

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
  
}
