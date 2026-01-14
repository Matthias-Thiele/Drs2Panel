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
public class Schluesselweiche extends BaseField {

  private final String name;
  private final int textOffset;
  
  public Schluesselweiche(String name, int textOffset) {
    this.name = name;
    this.textOffset = textOffset;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 2500, 8);
    });
  }
  
  @Override
  public void update() {
    super.update();
    var gc = canvas.getGraphicsContext2D();
    var achtelw = Presets.FIELD_WIDTH / 8;
    var achtelh = Presets.FIELD_HEIGHT / 8;
    drawBoxedText(gc, name, achtelw, Presets.FIELD_HEIGHT / 7 * 5 + 4, Presets.FIELD_WIDTH / 4 * 3, Presets.FIELD_HEIGHT / 7 + 4, textOffset);
    drawBoxedText(gc, "SlFM", achtelw - 4, achtelh, 2 * achtelw, 2 * achtelh - 3, 2);
    drawBoxedText(gc, "SlÜM", 6 * achtelw - 8, achtelh, 2 * achtelw + 4, 2 * achtelh - 3, 2);
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    gc.setFill(getFreigabeColor());
    gc.fillOval(achtelw + 6, 4 * achtelh - 4, Presets.LAMP_DIAMETER, Presets.LAMP_DIAMETER);
    gc.setFill(getEntnommenColor());
    gc.fillOval(achtelw * 7 - 12, 4 * achtelh - 4, Presets.LAMP_DIAMETER, Presets.LAMP_DIAMETER);
  }
  
  private Color getFreigabeColor() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getEntnommenColor() {
    return Color.RED;
  }
}
