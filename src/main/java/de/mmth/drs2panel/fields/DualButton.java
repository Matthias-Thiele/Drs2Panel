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
public class DualButton extends BaseField {

  private final String leftName;
  private final String rightName;
  private final Color leftColor;
  private final Color rightColor;
  
  public DualButton(String leftName, Color leftColor, String rightName, Color rightColor) {
    this.leftName = leftName;
    this.rightName = rightName;
    this.leftColor = leftColor;
    this.rightColor = rightColor;
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillText(leftName, Presets.FIELD_WIDTH / 5 - 15, Presets.FIELD_HEIGHT / 5 * 4 + 5);
    gc.fillText(rightName, Presets.FIELD_WIDTH / 5 * 3 + 8, Presets.FIELD_HEIGHT / 5 * 4 + 5);
    
    gc.setFill(leftColor);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 5 - 5, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    gc.setFill(rightColor);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 5 * 4, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
  }
}
