/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Const;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Klasse zur Anzeige des Tastenabschalters.
 * 
 * @author matthias
 */
public class Tastenabschalter extends BaseField {
  private final FieldType type;
  private boolean taOff;
  
  public Tastenabschalter(FieldType type) {
    this.type = type;
  }
  
  /**
   * Zeichnet das Feld neu.
   * 
   * Das TA Feld besteht aus zwei Teilfeldern, im
   * Original ist es ein einzelnes großes Feld.
   */
  @Override
  public void update() {
    super.update();
    var gc = canvas.getGraphicsContext2D();
    switch (type) {
      case TASTENANSCHALTER_TOP:
        drawTAtop(gc);
        break;
        
      case TASTENANSCHALTER_BOTTOM:
        drawTAbottom(gc);
        break;
    }
  }
  
  /**
   * Zeichnet die obere Hälfte.
   * @param gc 
   */
  private void drawTAtop(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    var mid = Presets.FIELD_WIDTH / 2;
    gc.fillOval(mid - 15, Presets.FIELD_HEIGHT - 30, 30, 30);
    gc.beginPath();
    gc.moveTo(mid - 9, Presets.FIELD_HEIGHT - 15);
    gc.lineTo(mid - 10, Presets.FIELD_HEIGHT);
    gc.lineTo(mid + 10, Presets.FIELD_HEIGHT);
    gc.lineTo(mid + 9, Presets.FIELD_HEIGHT - 15);
    gc.fill();
    
    if (taOff) {
      gc.setFill(Color.GRAY);
      gc.fillOval(mid - 10, Presets.FIELD_HEIGHT - 25, 20, 20);
      gc.beginPath();
      gc.moveTo(mid - 4, Presets.FIELD_HEIGHT - 12);
      gc.lineTo(mid - 5, Presets.FIELD_HEIGHT);
      gc.lineTo(mid + 5, Presets.FIELD_HEIGHT);
      gc.lineTo(mid + 4, Presets.FIELD_HEIGHT - 12);
      gc.fill();
      
      gc.setFill(Color.DARKGREY);
      gc.fillOval(mid - 25, Presets.FIELD_HEIGHT - 22, 50, 16);
    }
  }
  
  /**
   * Zeichnet die untere Hälfte.
   * 
   * @param gc 
   */
  private void drawTAbottom(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    var mid = Presets.FIELD_WIDTH / 2;
    gc.beginPath();
    gc.moveTo(mid - 10, 0);
    gc.lineTo(mid - 13, 30);
    gc.lineTo(mid + 13, 30);
    gc.lineTo(mid + 10, 0);
    gc.fill();
    
    if (taOff) {
      gc.setFill(Color.GRAY);
      gc.beginPath();
      gc.moveTo(mid - 5, 0);
      gc.lineTo(mid - 7, 26);
      gc.lineTo(mid + 7, 26);
      gc.lineTo(mid + 5, 0);
      gc.fill();
    }
  }

  /**
   * Prüft nach, ob sich der Tastenabschalter geändert
   * hat und löst bei Bedarf ein Neuzeichnen aus.
   */
  @Override
  public void checkedUpdate() {
    boolean tas = !drs2.getSwitch(Const.TA);
    
    if (tas != this.taOff) {
      this.taOff = tas;
      update();
    }
  }
  
}
