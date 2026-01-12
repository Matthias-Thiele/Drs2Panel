/*
 * (c) 2026 by Matthias Thiele
 * DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

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
}
