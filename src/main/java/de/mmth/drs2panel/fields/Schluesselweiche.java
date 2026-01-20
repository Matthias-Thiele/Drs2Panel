/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 * Diese Klasse zeichnet das Bedienfeld einer Schlüsselweiche.
 * 
 * @author matthias
 */
public class Schluesselweiche extends BaseField {
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[9];
  

  private final String name;
  private final int textOffset;
  private final int ioId;
  
  /**
   * Konstruktor mit Name, Tastennummer und Lampenanzeige-Ids.
   * @param name
   * @param id
   * @param textOffset
   * @param lampIds 
   */
  public Schluesselweiche(String name, int id, int textOffset, int[] lampIds) {
    this.name = name;
    this.ioId = id;
    this.textOffset = textOffset;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 2500, ioId);
    });
  }
  
  /**
   * Zeichnet den Feldinhalt neu.
   */
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
  
  /**
   * Ermittelt aus den Lampendaten die Farbe für die Freigabeanzeige.
   * @return 
   */
  private Color getFreigabeColor() {
    return (lampState[0]) ? Presets.RED_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Ermittelt aus den Lampendaten die Farbe für die Entnahmeanzeige.
   * @return 
   */
  private Color getEntnommenColor() {
    return (lampState[1]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Prüft nach, ob sich die Lampenzustände geändert haben und löst
   * bei Bedarf ein Neuzeichnen aus.
   */
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
  
}
