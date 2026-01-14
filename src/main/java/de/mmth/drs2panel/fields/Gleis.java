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
public class Gleis extends BaseField {

  private final String name;
  
  public Gleis(String name) {
    super();
    this.name = name;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1000, 7);
    });
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText(name, Presets.FIELD_WIDTH / 5 * 2 , Presets.FIELD_HEIGHT / 10 * 9);
    
    gc.setFill(getGleisColor());
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH - Presets.LAMP_MARGIN * 2, Presets.MIDDLE_BAR_HEIGHT - 6);
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
  }
  
  private Color getGleisColor() {
    return Color.RED;
  }
}
