/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Const;
import de.mmth.drs2panel.io.Drs2;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class Lichtsignal extends Pane {
  private static final int LS_WIDTH = 90;
  private static final int LS_HEIGHT = 90;
  
  private Drs2 drs2;
  private final Canvas canvas;
  private boolean isHP1;
  private boolean isHP2;
  private boolean isSH1;
  private boolean isZS1;
  
  public Lichtsignal(Drs2 drs2) {
    this.drs2 = drs2;

    canvas = new Canvas(LS_WIDTH, LS_HEIGHT);
    getChildren().add(canvas);
    update(true);
  }
  
  public void tick() {
    update(false);
  }
  
  private void update(boolean updateAlways) {
    if (updateLocalState() || updateAlways) {
      var gc = canvas.getGraphicsContext2D();

      drawBackground(gc);
      drawErsatzState(gc);
      drawSignalState(gc);
    }
  }  
    
  private void drawErsatzState(GraphicsContext gc) {
    Color lampColor = drs2.getLampState(Const.LS_ZS1) ? Color.WHITE : Color.DARKGREY;
    gc.setFill(lampColor);
    gc.fillOval(15, 8, 9, 9);
    gc.fillOval(7, 20, 9, 9);
    gc.fillOval(23, 20, 9, 9);
  }
  
  private boolean updateLocalState() {
    boolean lisHP1 = drs2.getLampState(Const.LS_HP1);
    boolean lisHP2 = drs2.getLampState(Const.LS_HP2);
    boolean lisSH1 = drs2.getLampState(Const.LS_SH1);
    boolean lisZS1 = drs2.getLampState(Const.LS_ZS1);
    
    if (isHP1 != lisHP1 || isHP2 != lisHP2 || isSH1 != lisSH1 || isZS1 != lisZS1) {
      isHP1 = lisHP1;
      isHP2 = lisHP2;
      isSH1 = lisSH1;
      isZS1 = lisZS1;
      return true;
    } else {
      return false;
    }
  }
  
  private void drawSignalState(GraphicsContext gc) {
    
    Color greenLamp = (isHP1 || isHP2) ? Presets.GREEN_LAMP : Presets.DARK_LAMP;
    gc.setFill(greenLamp);
    gc.fillOval(54, 10, 9, 9);
    
    Color yellowLamp = isHP2 ? Presets.YELLOW_LAMP : Presets.DARK_LAMP;
    gc.setFill(yellowLamp);
    gc.fillOval(54, 55, 9, 9);
    
    Color redLamp = (!isHP1 && !isHP2 && !isSH1 && !isZS1) ? Presets.RED_LAMP : Presets.DARK_LAMP;
    gc.setFill(redLamp);
    gc.fillOval(54, 24, 9, 9);
    gc.fillOval(70, 24, 9, 9);
    
    Color whiteLamp = isSH1 ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
    gc.setFill(whiteLamp);
    gc.fillOval(71, 35, 7, 7);
    gc.fillOval(55, 44, 7, 7);
  }
  
  private void drawBackground(GraphicsContext gc) {
    gc.setFill(Presets.FIELD_BACKGROUND);
    gc.fillRect(0,0, LS_WIDTH, LS_HEIGHT);
    
    // Ersatzsignalschirm
    gc.setFill(Color.BLACK);
    gc.beginPath();
    gc.moveTo(14, 4);
    gc.lineTo(26, 4);
    gc.lineTo(36, 14);
    gc.lineTo(36, 34);
    gc.lineTo(4, 34);
    gc.lineTo(4, 14);
    gc.fill();
    
    // Hauptsignalschirm
    gc.beginPath();
    gc.moveTo(56, 4);
    gc.lineTo(76, 4);
    gc.lineTo(82, 10);
    gc.lineTo(82, 70);
    gc.lineTo(50, 70);
    gc.lineTo(50, 10);
    gc.fill();
  }
}
