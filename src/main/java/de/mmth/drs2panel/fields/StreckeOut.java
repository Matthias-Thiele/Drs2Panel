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
public class StreckeOut extends BaseField {

  private final boolean toLeft;
  private final String name;
  
  public StreckeOut(String name, boolean toLeft) {
    super();
    this.name = name;
    this.toLeft = toLeft;
  }
  
  @Override
  public void update() {
    super.update();
    int startX;
    int startY;
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    
    if (toLeft) {
      startX = Presets.FIELD_WIDTH / 5 * 4;
      startY = Presets.FIELD_HEIGHT / 5;
    } else {
      startX = Presets.LAMP_MARGIN;
      startY = Presets.FIELD_HEIGHT / 10 * 9;
    }
    gc.fillText(name, startX, startY);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    gc.setFill(getStreckenFarbe());
    if (toLeft) {
      startX = Presets.FIELD_WIDTH - Presets.LAMP_MARGIN - Presets.LAMP_WIDTH;
    } else {
      startX = Presets.LAMP_MARGIN;
    }
    gc.fillRect(startX, Presets.MIDDLE_BAR_START + 3, Presets.LAMP_WIDTH, Presets.LAMP_DIAMETER);
    
    gc.setFill(getWHSperre());
    if (toLeft) {
      startY = Presets.FIELD_HEIGHT / 5 - Presets.LAMP_DIAMETER + 3;
    } else {
      startY = Presets.FIELD_HEIGHT / 5 * 4;
    }
    gc.fillOval((Presets.FIELD_WIDTH - Presets.LAMP_DIAMETER) / 2, startY, Presets.LAMP_DIAMETER, Presets.LAMP_DIAMETER);
    
    if (toLeft) {
      startX = Presets.FIELD_WIDTH / 5 * 4;
      startY = Presets.FIELD_HEIGHT / 5 * 3 + Presets.LAMP_DIAMETER;
    } else {
      startX = Presets.LAMP_MARGIN;
      startY = Presets.FIELD_HEIGHT / 5;
    }
    gc.setFill(getMelder());
    gc.fillOval(startX, startY, Presets.LAMP_DIAMETER, Presets.LAMP_DIAMETER);
    
    gc.setFill(getBlockFarbe());
    gc.beginPath();
    if (toLeft) {
      var top = Presets.FIELD_HEIGHT / 10;
      var right = Presets.FIELD_WIDTH / 2 - Presets.LAMP_MARGIN;
      var bottom = top + Presets.MIDDLE_BAR_HEIGHT - 4;
      
      gc.moveTo(right, top);
      gc.lineTo(Presets.LAMP_MARGIN + 8, top);
      gc.lineTo(Presets.LAMP_MARGIN, (top + bottom) / 2);
      gc.lineTo(Presets.LAMP_MARGIN + 8, bottom);
      gc.lineTo(right, bottom);
      gc.fill();
    } else {
      var top = Presets.FIELD_HEIGHT / 5 * 4;
      var left = Presets.FIELD_WIDTH / 2 + Presets.LAMP_MARGIN;
      var bottom = top + Presets.MIDDLE_BAR_HEIGHT - 4;
      
      gc.moveTo(left, top);
      gc.lineTo(Presets.FIELD_WIDTH - Presets.LAMP_MARGIN * 2, top);
      gc.lineTo(Presets.FIELD_WIDTH - Presets.LAMP_MARGIN, (top + bottom) / 2);
      gc.lineTo(Presets.FIELD_WIDTH - Presets.LAMP_MARGIN * 2, bottom);
      gc.lineTo(left, bottom);
      gc.fill();
    }
  }
  
  private Color getStreckenFarbe() {
    return Color.RED;
  }
  
  private Color getMelder() {
    return Color.BLUE;
  }
  
  private Color getBlockFarbe() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getWHSperre() {
    return Color.LIGHTYELLOW;
  }
}
