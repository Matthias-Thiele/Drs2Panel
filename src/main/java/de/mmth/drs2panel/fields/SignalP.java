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
public class SignalP extends BaseField {

  private final String name;
  private final boolean hasErsatz;
  private final boolean hasDrop;
  
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[4];
  
  /**
   * Konstruktor mit der Tastennummer und den Lampen-Ids.
   * @param name
   * @param id
   * @param hasErsatz
   * @param hasDrop
   * @param lampIds 
   */
  public SignalP(String name, int id, boolean hasErsatz, boolean hasDrop, int[] lampIds) {
    this.name = name;
    this.hasErsatz = hasErsatz;
    this.hasDrop = hasDrop;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1000, id);
    });    
  }
  
  /**
   * Löst ein Neuzeichnen der Anzeige aus.
   */
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    int leftFahrt = 67;
    int leftHalt = 46;
    int leftRangier = 20;
    int rangierTop = 59;
    int ersatzX = 134;
    int ersatzY = 14;
    
    if (hasErsatz) {
      leftFahrt = 107;
      leftHalt = 85;
      leftRangier = 35;
      ersatzX = leftHalt - 1;
      ersatzY = rangierTop;
    }
    
    gc.setFill(getErsatz());
    gc.beginPath();
    gc.moveTo(ersatzX - 18, ersatzY);
    gc.lineTo(ersatzX - 10 , ersatzY + 6);
    gc.lineTo(ersatzX - 18, ersatzY + 12);
    gc.fill();

    gc.setFill(getFahrt());
    gc.fillOval(leftFahrt, 59, 11, 11);
    gc.setFill(getHalt());
    gc.fillOval(leftHalt, 59, 11, 11);
    gc.setFill(getRangier());
    gc.beginPath();
    gc.moveTo(leftRangier, rangierTop);
    gc.lineTo(leftRangier + 3, rangierTop);
    gc.lineTo(leftRangier + 10, rangierTop + 10);
    gc.lineTo(leftRangier + 7, rangierTop + 10);
    gc.fill();
  }
  
  /**
   * Zeichnet den Hintergrund mit stilisierten Signalmast.
   * 
   * @param gc 
   */
  private void drawBackground(GraphicsContext gc) {
    var siebtel = Presets.FIELD_HEIGHT / 7;
    gc.setFill(Color.BLACK);
    drawBoxedText(gc, name, Presets.FIELD_WIDTH / 2 -15, siebtel - 4, 30, siebtel + 5, 7);
    if (hasDrop) {
      gc.beginPath();
      gc.moveTo(0, Presets.MIDDLE_BAR_START);
      gc.lineTo(Presets.FIELD_WIDTH / 2 + 6, Presets.MIDDLE_BAR_START);
      gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT - 7);
      gc.lineTo(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH - 13, Presets.FIELD_HEIGHT);
      gc.lineTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.lineTo(0, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
      gc.fill();
    } else {
      gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    }
    
    var baseX = 5;
    var baseY = 63;
    
    if (hasErsatz) {
      drawShield(gc, 55, 55, 50, 19);
      baseX = 20;
    } else {
      drawShield(gc, 40, 55, 25, 19);
      gc.fillOval(110, 10, 19, 19);
    }
    
    gc.beginPath();
    gc.moveTo(baseX, baseY - 3);
    gc.lineTo(baseX + 3, baseY - 3);
    gc.lineTo(baseX + 3, baseY);
    gc.lineTo(baseX + 8, baseY);
    gc.lineTo(baseX + 16, baseY - 8);
    gc.lineTo(baseX + 24, baseY - 8);
    gc.lineTo(baseX + 32, baseY);
    gc.lineTo(baseX + 40, baseY);
    gc.lineTo(baseX + 40, baseY + 3);
    gc.lineTo(baseX + 32, baseY + 3);
    gc.lineTo(baseX + 24, baseY + 11);
    gc.lineTo(baseX + 16, baseY + 11);
    gc.lineTo(baseX + 8, baseY + 3);
    gc.lineTo(baseX + 3, baseY + 3);
    gc.lineTo(baseX + 3, baseY + 6);
    gc.lineTo(baseX, baseY + 6);
    gc.fill();
  }
  
  /**
   * Ermittelt die Farbe der Haltanzeige.
   * @return 
   */
  private Color getHalt() {
    return (lampState[1]) ? Presets.RED_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Ermittelt die Farbe der Fahrtanzeige.
   * @return 
   */
  private Color getFahrt() {
    return (lampState[0]) ? Presets.GREEN_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Ermittelt die Farbe für die Anzeige des Ersatzsignals.
   * @return 
   */
  private Color getErsatz() {
    return (lampState[3]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
  }
  
  /**
   * Ermittelt die Farbe für die Anzeige des Rangiersignals.
   * @return 
   */
  private Color getRangier() {
    return (lampState[2]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
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
