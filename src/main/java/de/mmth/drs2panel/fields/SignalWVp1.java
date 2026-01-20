/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

/**
 * Klasse zur Anzeige des Vorsignalwiederholers.
 * 
 * @author matthias
 */
public class SignalWVp1 extends BaseField {
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[2];

  /**
   * Konstruktor mit der Liste der Lampen-Ids.
   * 
   * @param lampIds 
   */
  public SignalWVp1(int[] lampIds) {
    this.lampIds = lampIds;
  }
  
  /**
   * Löst ein Neuzeichnen der Anzeige aus.
   */
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    drawSignalState(gc);
  }
  
  /**
   * Zeichnet die aktuellen Anzeigefarben des Signalzustands.
   * 
   * @param gc 
   */
  private void drawSignalState(GraphicsContext gc) {
    var top = Presets.FIELD_HEIGHT / 7 * 5;
    var left0 = Presets.FIELD_WIDTH / 6 * 4 - 4;
    var left1 = left0 - 20;
    
    gc.setFill(getHP1Color());
    gc.fillOval(left0, top + 4 ,5, 5);
    gc.fillOval(left0 + 4, top + 12 ,5, 5);
    
    gc.setFill(getHP0Color());
    gc.fillOval(left1, top + 4 ,5, 5);
    gc.fillOval(left1 + 4, top + 12 ,5, 5);
    
  }
  
  /**
   * Zeichnet den Hintergrund mit stilisierten Signalmast.
   * 
   * @param gc 
   */
  private void drawBackground(GraphicsContext gc) {
    var top = Presets.FIELD_HEIGHT / 7 * 5;
    var left = Presets.FIELD_WIDTH / 7 * 3;
    
    gc.setFill(Color.BLACK);
    
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText("WVp1", 6, top + 15);
    
    gc.beginPath();
    gc.moveTo(left, top);
    gc.lineTo(left + 40, top);
    gc.lineTo(left + 46, top + 6);
    gc.lineTo(left + 46, top + 20);
    gc.lineTo(left + 6, top + 20);
    gc.lineTo(left, top + 12);
    gc.lineTo(left - 5, top + 12);
    gc.lineTo(left - 5, top + 16);
    gc.lineTo(left - 8, top + 16);
    gc.lineTo(left - 8, top + 4);
    gc.lineTo(left - 5, top + 4);
    gc.lineTo(left - 5, top + 8);
    gc.lineTo(left, top + 8);
    gc.fill();
    
  }
  
  /**
   * Ermittelt die Farbe der Halt-Erwarten Anzeige.
   * @return 
   */
  private Color getHP0Color() {
   return (lampState[0]) ? Presets.YELLOW_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Ermittelt die Farbe der Fahrt-Erwarten Anzeige.
   * @return 
   */
  private Color getHP1Color() {
   return (lampState[1]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Prüft nach, ob sich ein Lampenzustand geändert hat und
   * löst bei Bedarf ein Neuzeichnen aus.
   */
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
}
