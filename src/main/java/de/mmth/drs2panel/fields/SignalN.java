/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class SignalN extends BaseField {
  private final String name;
  private final boolean hasDrop;
  
  public SignalN(String name, boolean hasDrop) {
    this.name = name;
    this.hasDrop = hasDrop;
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    int leftFahrt = 21;
    int leftHalt = 42;
    int leftRangier = 95;
    int rangierTop = 9;
    gc.setFill(getErsatz());
    gc.beginPath();
    gc.moveTo(leftHalt + 31, rangierTop);
    gc.lineTo(leftHalt + 23 , rangierTop + 6);
    gc.lineTo(leftHalt + 31, rangierTop + 12);
    gc.fill();
    gc.setFill(getFahrt());
    gc.fillOval(leftFahrt, rangierTop, 11, 11);
    gc.setFill(getHalt());
    gc.fillOval(leftHalt, rangierTop, 11, 11);
    gc.setFill(getRangier());
    gc.beginPath();
    gc.moveTo(leftRangier, rangierTop + 1);
    gc.lineTo(leftRangier + 3, rangierTop + 1);
    gc.lineTo(leftRangier + 10, rangierTop + 10);
    gc.lineTo(leftRangier + 7, rangierTop + 10);
    gc.fill();
  }
  
  private void drawBackground(GraphicsContext gc) {
    var siebtel = Presets.FIELD_HEIGHT / 7;
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, name, Presets.FIELD_WIDTH / 2 -15, 6 *siebtel - 6, 30, siebtel + 5, 7);
    if (hasDrop) {
      gc.beginPath();
      gc.moveTo(Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_START);
      gc.lineTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START);
      gc.lineTo(0, Presets.FIELD_HEIGHT - 10);
      gc.lineTo(0, Presets.FIELD_HEIGHT);
      gc.lineTo(10, Presets.FIELD_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.fill();
    } else {
      gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    }
    
    var baseX = 120;
    var baseY = 13;
    
    drawShield(gc, 15, 5, 50, 19);
    
    gc.beginPath();
    gc.moveTo(baseX, baseY - 3);
    gc.lineTo(baseX - 3, baseY - 3);
    gc.lineTo(baseX - 3, baseY);
    gc.lineTo(baseX - 8, baseY);
    gc.lineTo(baseX - 16, baseY - 8);
    gc.lineTo(baseX - 24, baseY - 8);
    gc.lineTo(baseX - 32, baseY);
    gc.lineTo(baseX - 40, baseY);
    gc.lineTo(baseX - 40, baseY + 3);
    gc.lineTo(baseX - 32, baseY + 3);
    gc.lineTo(baseX - 24, baseY + 11);
    gc.lineTo(baseX - 16, baseY + 11);
    gc.lineTo(baseX - 8, baseY + 3);
    gc.lineTo(baseX - 3, baseY + 3);
    gc.lineTo(baseX - 3, baseY + 6);
    gc.lineTo(baseX, baseY + 6);
    gc.fill();
  }
  
  private Color getHalt() {
    return Color.RED;
  }
  
  private Color getFahrt() {
    return Color.GREEN;
  }
  
  private Color getErsatz() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getRangier() {
    return Color.LIGHTYELLOW;
  }
  
}
