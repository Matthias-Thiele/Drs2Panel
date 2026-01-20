/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 * Diese Klasse enthält ein Tischfeld mit zwei Tastern.
 * 
 * @author matthias
 */
public class DualButton extends BaseField {

  private final String leftName;
  private final String rightName;
  private final Color leftColor;
  private final Color rightColor;
  
  /**
   * Konstruktor mit den Namen, Farben und Nummern der beiden Taster.
   * 
   * Ein Tastendruck führt dazu, dass in der Schalterliste ein
   * Drücken und wieder Loslassen erzeugt wird.
   * 
   * @param leftName
   * @param leftId
   * @param leftColor
   * @param rightName
   * @param rightId
   * @param rightColor 
   */
  public DualButton(String leftName, int leftId, Color leftColor, String rightName, int rightId, Color rightColor) {
    this.leftName = leftName;
    this.rightName = rightName;
    this.leftColor = leftColor;
    this.rightColor = rightColor;
    this.setOnMouseClicked(ev -> {
      boolean left = ev.getX() < Presets.FIELD_WIDTH / 2;
      ButtonHandler.add(this, 2500, left ? leftId : rightId);
    });
  }
  
  /**
   * Zeichnet den Feldinhalt neu.
   * 
   * Es gibt keine checkUpdate Funktion da sich der Tasterzustand nicht durch
   * ein externes Ereignis ändern kann.
   */
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, leftName, Presets.FIELD_WIDTH / 5 - 15, Presets.FIELD_HEIGHT / 5 * 3 + 9, Presets.FIELD_WIDTH /3, Presets.FIELD_HEIGHT / 5, 8);
    drawBoxedText(gc, rightName, Presets.FIELD_WIDTH / 5 * 3 - 4, Presets.FIELD_HEIGHT / 5 * 3 + 9, Presets.FIELD_WIDTH /3, Presets.FIELD_HEIGHT / 5, 8);
    
    gc.setFill(leftColor);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 5 - 5, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    gc.setFill(rightColor);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 5 * 4, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
  }
}
