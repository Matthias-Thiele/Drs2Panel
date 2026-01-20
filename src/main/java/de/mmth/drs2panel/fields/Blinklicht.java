/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 * Anzeigefeld Blinklicht Störung.
 * 
 * Das DRS 2 hat einen zentralen Blinklicht-Generator. Da das
 * Blinken wichtige Informationen trägt (z.B. beim Umschalten
 * einer Weiche), darf eine Störung nicht dazu führen, dass
 * die Anzeige zufällig dauerhaft an oder aus ist. Bei einem 
 * gestörten Generator wird die Anzeige auf dauerhaft an
 * umgeschaltet und die Leuchtanzeige weist darauf hin, dass
 * das Blinklicht ausgefallen ist.
 * 
 * @author matthias
 */
public class Blinklicht extends BaseField {
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[4];
  
  /**
   * Konstruktor mit der liste der Lampen-Ids.
   * 
   * In diesem Fall nur eine Id mit der Nummer der
   * Lampe in der Störungsanzeige.
   * 
   * @param lampIds 
   */
  public Blinklicht(int[] lampIds) {
    this.lampIds = lampIds;
  }
  
  /**
   * Zeichnet den Feldinhalt neu.
   * 
   * Diese Funktion wird aufgerufen, wenn sich der
   * Zustand der Lampe ändert.
   */
  @Override
  public void update() {
    super.update();
    
    var fuenftelw = Presets.FIELD_WIDTH / 5;
    var achtelh = Presets.FIELD_HEIGHT / 8;
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(lampState[0] ? Color.LIGHTYELLOW : Color.GRAY);
    gc.fillRect(fuenftelw, achtelh, Presets.FIELD_WIDTH - 2 * fuenftelw, Presets.FIELD_HEIGHT - 2 * achtelh);
    
    gc.setFill(Color.BLACK);
    gc.fillText("Blinklicht", 1.5 * fuenftelw, 3 * achtelh);
    gc.fillText(" Störung", 1.5 * fuenftelw, 5 * achtelh);
  }
  
  /**
   * Prüft nach, ob sich der Lampenzustand geändert hat
   * und ruft bei Bedarf ein Neuzeichnen auf.
   */
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
  
}
