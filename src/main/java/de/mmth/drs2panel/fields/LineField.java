/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 * Zeichnet einen gerade Gleisabschnitt ohne Anzeige-
 * oder Tastfunktion.
 * 
 * @author matthias
 */
public class LineField extends BaseField {
  @Override
  public void update() {
    super.update();
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
  }
}
