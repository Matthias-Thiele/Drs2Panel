/*
 * (c) 2026 by Matthias Thiele
 * DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Drs2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Basis für alle Anzeigefelder des Tischfelds.
 * 
 * @author matthias
 */
public class BaseField extends Pane {

  protected final Canvas canvas;
  protected boolean isPressed = false;
  protected Drs2 drs2;
  
  /**
   * Konstruktor stellt die Zeichenfläche für die Anzeigen zur Verfügung.
   * 
   * Der Rahmen ist für den 3D Effekt der Kacheln und wird auch für
   * die "key pressed" Anzeige verwendet.
   */
  public BaseField() {
    this.setStyle("-fx-border-color: black");
    canvas = new Canvas(Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
    getChildren().add(canvas);
  }
  
  /**
   * Zeichnet den Feldinhalt neu.
   * 
   * Das Basiselement stellt den grauen Hintergrund zu Verfügung.
   */
  public void update() {
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Presets.FIELD_BACKGROUND);
    gc.fillRect(0,0, Presets.FIELD_WIDTH, Presets.FIELD_HEIGHT);
  }
  
  /**
   * Stellt die Verbindung zur DRS 2 Simulation auf dem Raspberry her.
   * 
   * @param drs2 
   */
  public void setDrs2(Drs2 drs2) {
    this.drs2 = drs2;
  }
  
  /**
   * Prüft nach, ob sich die Daten verändert haben und ein
   * Neuzeichnen des Feldes benötigt wird.
   * 
   * Diese Methode wird von den individuellen Kacheln implementiert.
   * 
   */
  public void checkedUpdate(){};
  
  /**
   * Teilt dem Feld mit, ob es einen gedrücken Button enthält.
   * 
   * Bei Feldern mit zwei Buttons wird nicht unterschieden, ob der
   * linke oder rechte Button gedrückt wurde. 
   * 
   * @param setPressed 
   */
  public void setButtonPressed(boolean setPressed) {
    isPressed = setPressed;
    this.setStyle(isPressed ? "-fx-border-color: red" : "-fx-border-color: black");
  }
  
  /**
   * Zeichnet einen Text in einem kleinen Fenster.
   * 
   * Diese Texte werden oft (aber nicht immer) für die Feldnamen verwendet.
   * 
   * @param gc
   * @param text
   * @param left
   * @param top
   * @param width
   * @param height
   * @param leftOffset 
   */
  protected void drawBoxedText(GraphicsContext gc, String text, int left, int top, int width, int height, int leftOffset) {
    gc.setFill(Presets.TEXT_BACKGROUND);
    gc.fillRect(left, top, width, height);
    gc.setFill(Color.BLACK);
    gc.fillText(text, left + leftOffset, top + height - 3);
  }
  
  /**
   * Zeichnet das Schild für Signalanzeigen.
   * 
   * @param gc
   * @param left
   * @param top
   * @param width
   * @param height 
   */
  protected void drawShield(GraphicsContext gc, int left, int top, int width, int height) {
    gc.fillOval(left, top, height, height);
    gc.fillOval(left + width, top, height, height);
    gc.fillRect(left + height / 2, top, width, height);
  }
  
  /**
   * Ermittelt die Anzeigefarbe für Felder die Weiß und Rot anzeigen können.
   * 
   * Eine gleichzeitige Anzeige ist in der Praxis nicht möglich. Falls aufgrund
   * eines Fehlers beide Marker aktiv werden wird die rote Anzeige verwendet.
   * 
   * @param withTrain
   * @param inUse
   * @return 
   */
  protected Color getTrackColor(boolean withTrain, boolean inUse) {
    if (withTrain) {
      return Presets.RED_LAMP;
    } else if (inUse) {
      return Presets.WHITE_LAMP;
    } else {
      return Presets.DARK_LAMP;
    }
    
  }
  
  /**
   * Liest die Anzeigeeinstellungen aus der DRS 2 Simulation in die
   * lokale Anzeigeliste lampState[] ein.
   * 
   * Zum Zeichnen wird dann die lokale Liste ausgewertet.
   * 
   * @param lampState
   * @param lampIds
   * @return 
   */
  protected boolean updateState(boolean[] lampState, int[] lampIds) {
    boolean changed = false;
    for (var i = 0; i < lampIds.length; i++) {
      if (lampIds[i] < 0) continue;
      
      var actState = drs2.getLampState(lampIds[i]);
      changed |= lampState[i] != actState;
      lampState[i] = actState;
    }
    
    return changed;
  }
  
}
