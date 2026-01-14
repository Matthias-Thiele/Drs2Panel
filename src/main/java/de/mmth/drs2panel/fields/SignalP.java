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
public class SignalP extends BaseField {

  private final String name;
  private final boolean hasErsatz;
  private final boolean hasDrop;
  
  public SignalP(String name, boolean hasErsatz, boolean hasDrop) {
    this.name = name;
    this.hasErsatz = hasErsatz;
    this.hasDrop = hasDrop;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1000, 13);
    });    
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    int leftFahrt = 67;
    int leftHalt = 46;
    int leftRangier = 20;
    int rangierTop = 59;
    if (hasErsatz) {
      leftFahrt = 107;
      leftHalt = 85;
      leftRangier = 35;
      gc.setFill(getErsatz());
      gc.beginPath();
      gc.moveTo(leftHalt - 18, rangierTop);
      gc.lineTo(leftHalt - 10 , rangierTop + 6);
      gc.lineTo(leftHalt - 18, rangierTop + 12);
      gc.fill();
    }
    gc.setFill(getFahrt());
    gc.fillOval(leftFahrt, 59, 11, 11);
    gc.setFill(getHalt());
    gc.fillOval(leftHalt, 59, 11, 11);
    gc.setFill(getRangier());
    gc.beginPath();
    gc.moveTo(leftRangier, rangierTop);
    gc.lineTo(leftRangier + 3, rangierTop);
    gc.lineTo(leftRangier + 10, rangierTop + 10);
    gc.lineTo(leftRangier + 7, rangierTop + 10);
    gc.fill();
  }
  
  private void drawBackground(GraphicsContext gc) {
    var siebtel = Presets.FIELD_HEIGHT / 7;
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, name, Presets.FIELD_WIDTH / 2 -15, siebtel - 4, 30, siebtel + 5, 7);
    if (hasDrop) {
      gc.beginPath();
      gc.moveTo(0, Presets.MIDDLE_BAR_START);
      gc.lineTo(Presets.FIELD_WIDTH / 2 + 6, Presets.MIDDLE_BAR_START);
      gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT - 10);
      gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH - 10, Presets.FIELD_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.lineTo(0, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.fill();
    } else {
      gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    }
    
    var baseX = 5;
    var baseY = 63;
    
    if (hasErsatz) {
      drawShield(gc, 55, 55, 50, 19);
      baseX = 20;
    } else {
      drawShield(gc, 40, 55, 25, 19);
    }
    
    gc.beginPath();
    gc.moveTo(baseX, baseY - 3);
    gc.lineTo(baseX + 3, baseY - 3);
    gc.lineTo(baseX + 3, baseY);
    gc.lineTo(baseX + 8, baseY);
    gc.lineTo(baseX + 16, baseY - 8);
    gc.lineTo(baseX + 24, baseY - 8);
    gc.lineTo(baseX + 32, baseY);
    gc.lineTo(baseX + 40, baseY);
    gc.lineTo(baseX + 40, baseY + 3);
    gc.lineTo(baseX + 32, baseY + 3);
    gc.lineTo(baseX + 24, baseY + 11);
    gc.lineTo(baseX + 16, baseY + 11);
    gc.lineTo(baseX + 8, baseY + 3);
    gc.lineTo(baseX + 3, baseY + 3);
    gc.lineTo(baseX + 3, baseY + 6);
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
