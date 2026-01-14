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
public class SignalF extends BaseField {
  public SignalF() {
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 2500, 12);
    });    
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    
    gc.setFill(getGleisColor());
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 3, Presets.FIELD_WIDTH / 2 - Presets.LAMP_MARGIN * 3, Presets.MIDDLE_BAR_HEIGHT - 6);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    drawSignalState(gc);
  }
  
  private void drawSignalState(GraphicsContext gc) {
    var top = Presets.FIELD_HEIGHT - 22;
    var right = Presets.FIELD_WIDTH - 24;
    gc.setFill(getColor1());
    gc.fillOval(right, top, 11, 11);
    
    gc.setFill(getColor2());
    gc.fillOval(right - 22, top, 11, 11);
    
    gc.setFill(getColor3());
    gc.fillOval(right - 43, top - 2 ,5, 5);
    gc.fillOval(right - 37, top + 6 ,5, 5);
    
    gc.setFill(getColor4());
    gc.fillOval(right - 74, top - 2 ,5, 5);
    gc.fillOval(right - 68, top + 6 ,5, 5);
    
    gc.setFill(getColor5());
    gc.fillOval(right - 90, top - 2 ,5, 5);
    gc.fillOval(right - 84, top + 6 ,5, 5);
    
    // Ersatzsignal
    top = 9;
    right = Presets.FIELD_WIDTH - 18;
    gc.setFill(getColorErsatz());
    gc.beginPath();
    gc.moveTo(right - 17, top + 8);
    gc.lineTo(right - 27, top);
    gc.lineTo(right - 27, top + 16);
    gc.fill();
    
    // ?
    gc.setFill(getColorMarker());
    gc.fillOval(right - 9, top + 2, 12, 12);

  }
  
  private void drawBackground(GraphicsContext gc) {
    var siebtel = Presets.FIELD_HEIGHT / 7;
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    drawBoxedText(gc, "F", Presets.FIELD_WIDTH / 2 -15, siebtel - 4, 30, siebtel + 5, 7);    
    
    // Hauptsignalfeld
    var top = Presets.FIELD_HEIGHT - 26;
    var main = Presets.FIELD_WIDTH / 7 * 4 + 10;
    
    drawShield(gc, main, top, 24, 19);
    
    var foot = main + 15;
    top = Presets.FIELD_HEIGHT - 12;
    gc.beginPath();
    gc.moveTo(foot, top - 7);
    gc.lineTo(foot - 36, top - 7);
    gc.lineTo(foot - 36, top - 10);
    gc.lineTo(foot - 40, top - 10);
    gc.lineTo(foot - 40, top);
    gc.lineTo(foot - 36, top);
    gc.lineTo(foot - 36, top - 4);
    gc.lineTo(foot, top - 4);
    gc.fill();
    
    gc.beginPath();
    top = Presets.FIELD_HEIGHT - 26;
    var right = Presets.FIELD_WIDTH / 2 + 17;
    gc.moveTo(right - 16, top + 8);
    gc.lineTo(right - 16, top);
    gc.lineTo(right - 8, top);
    gc.lineTo(right, top + 8);
    gc.lineTo(right, top + 17);
    gc.lineTo(right - 8, top + 17);
    gc.lineTo(right - 16, top + 9);
    gc.fill();
    
    // Vorsignalfeld
    right = 70;
    gc.beginPath();
    gc.moveTo(right - 52, top);
    gc.lineTo(right - 16, top);
    gc.lineTo(right - 10, top + 7);
    gc.lineTo(right - 10, top + 18);
    gc.lineTo(right - 46, top + 18);
    gc.lineTo(right - 52, top + 11);
    gc.lineTo(right - 58, top + 11);
    gc.lineTo(right - 58, top + 15);
    gc.lineTo(right - 61, top + 15);
    gc.lineTo(right - 61, top + 3);
    gc.lineTo(right - 58, top + 3);
    gc.lineTo(right - 58, top + 7);
    gc.lineTo(right - 52, top + 7);
    gc.fill();
    
    // Anzeigen
    top = 8;
    right = Presets.FIELD_WIDTH - 30;
    gc.fillOval(right - 20, top, 18, 18);
    gc.fillOval(right, top, 18, 18);
    
  }
  
  private Color getGleisColor() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getColor1() {
    return Color.GRAY;
  }
  
  private Color getColor2() {
    return Color.RED;
  }
  
  private Color getColor3() {
    return Color.GREEN;
  }
  
  private Color getColor4() {
    return Color.GREEN;
  }
  
  private Color getColor5() {
    return Color.LIGHTYELLOW;
  }
  
  private Color getColorErsatz() {
    return Color.GRAY;
  }
  
  private Color getColorMarker() {
    return Color.GRAY;
  }
}
