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
public class Weiche extends BaseField {

  private final boolean toLeft;
  private final boolean toBottom;
  private GraphicsContext gc;
  private final String name;
  
  public Weiche(String name, boolean toLeft, boolean toBottom) {
    super();
    this.name = name;
    this.toLeft = toLeft;
    this.toBottom = toBottom;
  }
  
  @Override
  public void update() {
    super.update();
    
    gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    //gc.fillText(name, toLeft ? Presets.FIELD_WIDTH / 5 * 3 : Presets.LAMP_MARGIN, Presets.FIELD_HEIGHT / 5);
    var left = toLeft ? Presets.FIELD_WIDTH / 5 * 4 : Presets.LAMP_MARGIN;
    var offset = (name.length() == 1) ? 10 : 4;
    drawBoxedText(gc, name,  left, Presets.FIELD_HEIGHT / 12, Presets.FIELD_WIDTH / 5, Presets.FIELD_HEIGHT / 5, offset);
    if (toLeft) {
      if (toBottom) {
        drawLeftBottom();
      } else {
        drawLeftTop();
      }
    } else {
      if (toBottom) {
        drawRightBottom();
      } else {
        drawRightTop();
      }
    }
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
  }
  
  private void drawRightTop() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START);
    gc.lineTo(Presets.FIELD_WIDTH - 10, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 10);
    gc.lineTo(Presets.FIELD_WIDTH / 2 + 30, Presets.MIDDLE_BAR_START);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.FIELD_WIDTH / 5 * 3 + Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(-29);
    gc.fillRect(65.0, 64.5, 36.0, 10.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawLeftTop() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2 - 30, Presets.MIDDLE_BAR_START);
    gc.lineTo(0, 10);
    gc.lineTo(0, 0);
    gc.lineTo(10, 0);
    gc.lineTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(-151);
    gc.fillRect(-53.0, -6.5, 36.0, 10.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawRightBottom() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH - 10, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT - 10);
    gc.lineTo(Presets.FIELD_WIDTH / 2 + 30, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.FIELD_WIDTH / 5 * 3 + Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(29);
    gc.fillRect(104.0, -4.5, 36.0, 10.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawLeftBottom() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(10, Presets.FIELD_HEIGHT);
    gc.lineTo(0, Presets.FIELD_HEIGHT);
    gc.lineTo(0, Presets.FIELD_HEIGHT - 10);
    gc.lineTo(Presets.FIELD_WIDTH / 2 - 30, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(151);
    gc.fillRect(-15.0, -73.5, 36.0, 10.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private Color getLampColor(boolean lineTrack) {
    if (!lineTrack) {
      return Color.LIGHTYELLOW;
    } else {
      return Presets.DARK_LAMP;
    }
  }
}
