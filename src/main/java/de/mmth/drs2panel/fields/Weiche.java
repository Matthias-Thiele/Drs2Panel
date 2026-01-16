/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Const;
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
  private final int ioId;
  
  private int[] lampIds;
  private final boolean[] lampState = new boolean[4];
  
  public Weiche(String name, int id, boolean toLeft, boolean toBottom, int[] lampIds) {
    super();
    this.name = name;
    this.ioId = id;
    this.toLeft = toLeft;
    this.toBottom = toBottom;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1500, ioId);
    });
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
    gc.moveTo(Presets.FIELD_WIDTH / 2 + 1, Presets.MIDDLE_BAR_START);
    gc.lineTo(Presets.FIELD_WIDTH - 13, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 7);
    gc.lineTo(Presets.FIELD_WIDTH / 2 + 29, Presets.MIDDLE_BAR_START);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.FIELD_WIDTH / 5 * 3 + Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT / 2);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(-31);
    gc.fillRect(65.0, 67.5, 36.0, 8); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawLeftTop() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2 - 30, Presets.MIDDLE_BAR_START);
    gc.lineTo(0, 7);
    gc.lineTo(0, 0);
    gc.lineTo(13, 0);
    gc.lineTo(Presets.FIELD_WIDTH / 2 - 3, Presets.MIDDLE_BAR_START);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT / 2);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(-149);
    gc.fillRect(-53.0, -4.0, 36.0, 8.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawRightBottom() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH - 13, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT - 7);
    gc.lineTo(Presets.FIELD_WIDTH / 2 + 27, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.FIELD_WIDTH / 5 * 3 + Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT / 2);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(30);
    gc.fillRect(104.0, -4, 36.0, 8.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private void drawLeftBottom() {
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(13, Presets.FIELD_HEIGHT);
    gc.lineTo(0, Presets.FIELD_HEIGHT);
    gc.lineTo(0, Presets.FIELD_HEIGHT - 7);
    gc.lineTo(Presets.FIELD_WIDTH / 2 - 27, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
    
    gc.setFill(getLampColor(true));
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH / 4, Presets.MIDDLE_BAR_HEIGHT / 2);
    
    gc.save();
    gc.setFill(getLampColor(false));
    gc.rotate(150);
    gc.fillRect(-15.0, -73.5, 36.0, 8.0); // ToDo Absolute Koordinaten entfernen
    gc.restore();
  }
  
  private Color getLampColor(boolean lineTrack) {
    int offset = lineTrack ? 1 : 0;
    if (lampState[offset + 0]) {
      return Presets.RED_LAMP;
    } else if (lampState[offset + 2]) {
      return Presets.WHITE_LAMP;
    } else {
      return Presets.DARK_LAMP;
    }
  }
  
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
  
}
