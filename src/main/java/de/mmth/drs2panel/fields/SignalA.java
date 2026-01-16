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
public class SignalA extends BaseField {
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[9];
  
  public SignalA(int id, int[] lampIds) {
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 2500, id);
    });    
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    drawBackground(gc);
    
    gc.setFill(getGleisColor());
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH / 2 - Presets.LAMP_MARGIN * 3, Presets.MIDDLE_BAR_HEIGHT / 2);
    
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
    drawSignalState(gc);
  }
  
  private void drawSignalState(GraphicsContext gc) {
    gc.setFill(getColor1());
    gc.fillOval(18, 13, 11, 11);
    
    gc.setFill(getColor2());
    gc.fillOval(40, 13, 11, 11);
    
    gc.setFill(getColor3());
    gc.fillOval(60, 12 ,5, 5);
    gc.fillOval(64, 19 ,5, 5);
    
    gc.setFill(getColor4());
    gc.fillOval(90, 12 ,5, 5);
    gc.fillOval(94, 19 ,5, 5);
    
    gc.setFill(getColor5());
    gc.fillOval(110, 12 ,5, 5);
    gc.fillOval(114, 19 ,5, 5);
    
    // Ersatzsignal
    gc.setFill(getColorErsatz());
    gc.beginPath();
    gc.moveTo(38, 64);
    gc.lineTo(48, 56);
    gc.lineTo(48, 72);
    gc.fill();
    
    // ?
    gc.setFill(getColorMarker());
    gc.fillOval(17, 58, 12, 12);

  }
  
  private void drawBackground(GraphicsContext gc) {
    var siebtel = Presets.FIELD_HEIGHT / 7;
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText("A", Presets.FIELD_WIDTH / 5 * 3, siebtel * 6);
    
    // Hauptsignalfeld
    drawShield(gc, 14, 9, 22, 19);
    
    gc.beginPath();
    gc.moveTo(40, 17);
    gc.lineTo(76, 17);
    gc.lineTo(76, 14);
    gc.lineTo(80, 14);
    gc.lineTo(80, 24);
    gc.lineTo(76, 24);
    gc.lineTo(76, 20);
    gc.lineTo(40, 20);
    gc.fill();
    
    gc.beginPath();
    gc.moveTo(58, 17);
    gc.lineTo(58, 9);
    gc.lineTo(66, 9);
    gc.lineTo(72, 17);
    gc.lineTo(72, 26);
    gc.lineTo(64, 26);
    gc.lineTo(58, 18);
    gc.fill();
    
    // Vorsignalfeld
    gc.beginPath();
    gc.moveTo(84, 9);
    gc.lineTo(120, 9);
    gc.lineTo(126, 17);
    gc.lineTo(132, 17);
    gc.lineTo(132, 13);
    gc.lineTo(136, 13);
    gc.lineTo(136, 24);
    gc.lineTo(132, 24);
    gc.lineTo(132, 20);
    gc.lineTo(126, 20);
    gc.lineTo(126, 28);
    gc.lineTo(90, 28);
    gc.lineTo(84, 21);
    gc.fill();
    
    // Anzeigen
    gc.fillOval(14, 55, 18, 18);
    gc.fillOval(36, 55, 18, 18);
    
  }
  
  private Color getGleisColor() {
    return getTrackColor(lampState[4], lampState[5]);
  }
  
  private Color getColor1() {
    return (lampState[0]) ? Presets.GREEN_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColor2() {
    return (lampState[1]) ? Presets.RED_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColor3() {
    return (lampState[8]) ? Presets.GREEN_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColor4() {
    return (lampState[2]) ? Presets.GREEN_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColor5() {
    return (lampState[3]) ? Presets.YELLOW_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColorErsatz() {
    return (lampState[6]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
  }
  
  private Color getColorMarker() {
    return (lampState[7]) ? Presets.WHITE_LAMP : Presets.DARK_LAMP;
  }
  
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
  
}
