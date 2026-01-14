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
public class StreckeIn extends BaseField {

  private final String name;
  private final boolean withStrecke;
  private final boolean toLeft;
  
  public StreckeIn(String name, boolean toLeft, boolean withStrecke) {
    super();
    this.name = name;
    this.toLeft = toLeft;
    this.withStrecke = withStrecke;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1500, 41);
    });    
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    
    gc.setFill(Color.BLACK);
    if (withStrecke) {
      gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    }
    var startX = toLeft ? (Presets.FIELD_WIDTH / 6 * 5) : Presets.LAMP_MARGIN;
    var startY = Presets.FIELD_HEIGHT / 5;
    gc.fillText(name, startX, startY);
    
    gc.setFill(getBlockColor());
    startX = toLeft ? (Presets.LAMP_MARGIN) : (Presets.FIELD_WIDTH / 5 * 3 + 10);
    startY = (withStrecke ? Presets.FIELD_HEIGHT / 10 : Presets.MIDDLE_BAR_START) + 3;
    gc.beginPath();
    gc.moveTo(startX, startY);
    if (toLeft) {
      gc.lineTo(startX + Presets.LAMP_WIDTH, startY);
      gc.lineTo(startX + Presets.LAMP_WIDTH + 10, (startY + Presets.LAMP_DIAMETER / 2));
      gc.lineTo(startX + Presets.LAMP_WIDTH, startY + Presets.LAMP_DIAMETER);
      gc.lineTo(startX, startY + Presets.LAMP_DIAMETER);
    } else {
      gc.lineTo(startX + Presets.LAMP_WIDTH, startY);
      gc.lineTo(startX + Presets.LAMP_WIDTH, startY + Presets.LAMP_DIAMETER);
      gc.lineTo(startX, startY + Presets.LAMP_DIAMETER);
      gc.lineTo(startX - 10, startY + Presets.LAMP_DIAMETER / 2);
    }
    gc.fill();
    //gc.fillRect(startX, startY + 3, Presets.LAMP_WIDTH, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    startX = toLeft ? (Presets.FIELD_WIDTH / 5 * 4 - 5) : (Presets.FIELD_WIDTH / 5 + 6);
    startY -= 3;
    gc.setFill(Color.GRAY);
    gc.fillOval(startX, startY - 3, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    gc.setFill(getMelderColor());
    startX = toLeft ? (Presets.FIELD_WIDTH / 5 * 2 + 5): (Presets.FIELD_WIDTH/ 2);
    gc.fillOval(startX, startY + 3, Presets.LAMP_DIAMETER, Presets.LAMP_DIAMETER);
    
  }
  
  private Color getBlockColor() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getMelderColor() {
    return Color.LIGHTYELLOW;
  }
}
