/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 * Zeichnet ein Feld mit zwei Anzeigelampen.
 * 
 * Nur das WuT Feld für Störmeldungen hat zwei
 * zusätzliche Tasten zur Abschaltung des Weckers.
 * Alle anderen Melder haben reine Anzeigefunktion.
 * 
 * @author matthias
 */
public class Melder extends BaseField {

  private final String name;
  private final String leftView;
  private final String rightView;
  private final Color leftButtonColor;
  private final Color rightButtonColor;
  
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[2];
  private final Color melderColor;
  
  /**
   * Konstruktor mit Namen, Tasterdaten und Lampen-Ids.
   * 
   * @param name
   * @param leftView
   * @param rightView
   * @param leftId
   * @param rightId
   * @param leftButton    null wenn kein linker Taster vorhanden ist
   * @param rightButton   null wenn kein rechter Taster vorhanden ist.
   * @param lampIds 
   */
  public Melder(String name, String leftView, String rightView, int leftId, int rightId, Color leftButton, Color rightButton, Color melderColor, int[] lampIds) {
    this.name = name;
    this.leftView = leftView;
    this.rightView = rightView;
    this.leftButtonColor = leftButton;
    this.rightButtonColor = rightButton;
    this.melderColor = melderColor;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      boolean left = ev.getX() < Presets.FIELD_WIDTH / 2;
      ButtonHandler.add(this, 500, left ? leftId : rightId);
    });
  }
  
  /**
   * Zeichnet den Feldinhalt neu.
   */
  @Override
  public void update() {
    super.update();
    
    var achtel = Presets.FIELD_WIDTH / 8;
    
    var gc = canvas.getGraphicsContext2D();
    if (!name.isEmpty()) {
      gc.setFill(Color.BLACK);
      drawBoxedText(gc, name,  2 * achtel, Presets.FIELD_HEIGHT / 5 * 3 + 5, Presets.FIELD_WIDTH / 2, Presets.FIELD_HEIGHT / 5, 3);
    }
    
    if (!leftView.isEmpty()) {
      gc.setFill(lampState[0] ? melderColor : Color.GRAY);
      gc.fillRect(achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.BLACK);
      gc.fillText(leftView, achtel, 2 * achtel);
    }
    
    if (leftButtonColor != null) {
      gc.setFill(leftButtonColor);
      gc.fillOval(achtel - 11, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
    
    if (!rightView.isEmpty()) {
      gc.setFill(lampState[1] ? melderColor : Color.GRAY);
      gc.fillRect(Presets.FIELD_WIDTH - 3 * achtel, achtel, 2 * achtel, 2 * achtel - 8);
      gc.setFill(Color.BLACK);
      gc.fillText(rightView, Presets.FIELD_WIDTH - 3 * achtel, 2 * achtel);
    }
    
    if (rightButtonColor != null) {
      gc.setFill(rightButtonColor);
      gc.fillOval(Presets.FIELD_WIDTH - 2 * achtel + 3, Presets.FIELD_HEIGHT / 5 * 4 - 12 , Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    }
  }
  
  /**
   * Prüft nach, ob sich der Zustand einer der Anzeigelampen
   * verändert hat und löst bei Bedarf ein Neuzeichnen aus.
   */
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
}
