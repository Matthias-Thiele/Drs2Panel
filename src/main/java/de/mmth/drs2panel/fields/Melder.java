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
  
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[2];
  
  public Melder(String name, String leftView, String rightView, int leftId, int rightId, Color leftButton, Color rightButton, int[] lampIds) {
    this.name = name;
    this.leftView = leftView;
    this.rightView = rightView;
    this.leftButtonColor = leftButton;
    this.rightButtonColor = rightButton;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      boolean left = ev.getX() < Presets.FIELD_WIDTH / 2;
      ButtonHandler.add(this, 500, left ? leftId : rightId);
    });
  }
  
  @Override
  public void update() {
    super.update();
    
    var achtel = Presets.FIELD_WIDTH / 8;
    
    var gc = canvas.getGraphicsContext2D();
    if (!name.isEmpty()) {
      gc.setFill(Color.BLACK);
      drawBoxedText(gc, name,  2 * achtel, Presets.FIELD_HEIGHT / 5 * 3 + 5, Presets.FIELD_WIDTH / 2, Presets.FIELD_HEIGHT / 5, 3);
    }
    
    if (!leftView.isEmpty()) {
      gc.setFill(lampState[0] ? Color.LIGHTYELLOW : Color.GRAY);
      gc.fillRect(achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.BLACK);
      gc.fillText(leftView, achtel, 2 * achtel);
    }
    
    if (leftButtonColor != null) {
      gc.setFill(leftButtonColor);
      gc.fillOval(achtel - 11, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
    
    if (!rightView.isEmpty()) {
      gc.setFill(lampState[1] ? Color.LIGHTYELLOW : Color.GRAY);
      gc.fillRect(Presets.FIELD_WIDTH - 3 * achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.BLACK);
      gc.fillText(rightView, Presets.FIELD_WIDTH - 3 * achtel, 2 * achtel);
    }
    
    if (rightButtonColor != null) {
      gc.setFill(rightButtonColor);
      gc.fillOval(Presets.FIELD_WIDTH - 2 * achtel + 3, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
  }
  
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
}
