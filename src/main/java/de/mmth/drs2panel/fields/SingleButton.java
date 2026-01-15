/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class SingleButton extends BaseField {

  private final String name;
  private final Color color;
  private final int ioId;
  public SingleButton(String name, int id, Color color) {
    this.name = name;
    this.ioId = id;
    this.color = color;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 2500, ioId);
    });
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, name, Presets.FIELD_WIDTH / 5 * 2 - 8, Presets.FIELD_HEIGHT / 5 * 3 + 9, Presets.FIELD_WIDTH /3, Presets.FIELD_HEIGHT / 5, 8);
    
    gc.setFill(color);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 5 * 2 + 10, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
  }
  
}
