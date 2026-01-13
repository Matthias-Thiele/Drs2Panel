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
public class Melder extends BaseField {

  private final String name;
  private final String leftView;
  private final String rightView;
  private final Color leftButtonColor;
  private final Color rightButtonColor;
  
  public Melder(String name, String leftView, String rightView, Color leftButton, Color rightButton) {
    this.name = name;
    this.leftView = leftView;
    this.rightView = rightView;
    this.leftButtonColor = leftButton;
    this.rightButtonColor = rightButton;
  }
  
  @Override
  public void update() {
    super.update();
    
    var achtel = Presets.FIELD_WIDTH / 8;
    
    var gc = canvas.getGraphicsContext2D();
    if (!name.isEmpty()) {
      gc.setFill(Color.BLACK);
      gc.fillText(name, 2 * achtel + 3, Presets.FIELD_HEIGHT / 5 * 4 + 5);
    }
    
    if (!leftView.isEmpty()) {
      gc.setFill(Color.LIGHTYELLOW);
      gc.fillRect(achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.YELLOW);
      gc.fillText(leftView, achtel, 2 * achtel);
    }
    
    if (leftButtonColor != null) {
      gc.setFill(leftButtonColor);
      gc.fillOval(achtel - 11, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
    
    if (!rightView.isEmpty()) {
      gc.setFill(Color.LIGHTYELLOW);
      gc.fillRect(Presets.FIELD_WIDTH - 3 * achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.YELLOW);
      gc.fillText(rightView, Presets.FIELD_WIDTH - 3 * achtel, 2 * achtel);
    }
    
    if (rightButtonColor != null) {
      gc.setFill(rightButtonColor);
      gc.fillOval(Presets.FIELD_WIDTH - 2 * achtel + 3, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
  }
}
