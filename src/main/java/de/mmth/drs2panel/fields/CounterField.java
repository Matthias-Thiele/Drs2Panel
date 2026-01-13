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
public class CounterField extends BaseField {

  private final String name;
  private final Color buttonColor;
  private int counterValue = 1234;
  
  public CounterField(String name, Color buttonColor) {
    this.name = name;
    this.buttonColor = buttonColor;
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    
    gc.setFill(Color.BLACK);
    gc.fillRect(Presets.FIELD_WIDTH / 2 - 7, Presets.FIELD_HEIGHT / 5 * 2 + 1, Presets.FIELD_WIDTH / 5 * 2, Presets.MIDDLE_BAR_HEIGHT + 2);
    gc.setFill(Color.WHITE);
    gc.fillText(Integer.toString(counterValue), Presets.FIELD_WIDTH / 2 + 8, Presets.FIELD_HEIGHT / 5 * 3);
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, name,Presets.FIELD_WIDTH / 2 - 7, Presets.FIELD_HEIGHT / 5 * 4 - 5, Presets.FIELD_WIDTH / 5 * 2, Presets.FIELD_HEIGHT / 5, 5);
    
    if (buttonColor != null) {
      gc.setFill(buttonColor);
      gc.fillOval(2 * Presets.LAMP_MARGIN, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
  }
}
