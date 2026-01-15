/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class Gleis extends BaseField {
  private final static int LampWhite = 0;
  private final static int LampRed = 1;
  
  private final String name;
  private final int ioId;
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[2];
  
  public Gleis(String name, int id, int[] lampIds) {
    super();
    this.name = name;
    this.ioId = id;
    this.lampIds = lampIds;
    this.setOnMouseClicked(ev -> {
      ButtonHandler.add(this, 1000, ioId);
    });
  }
  
  @Override
  public void update() {
    super.update();
    
    var gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText(name, Presets.FIELD_WIDTH / 5 * 2 , Presets.FIELD_HEIGHT / 10 * 9);
    
    gc.setFill(getGleisColor());
    gc.fillRect(Presets.LAMP_MARGIN, Presets.MIDDLE_BAR_START + 4, Presets.FIELD_WIDTH - Presets.LAMP_MARGIN * 2, Presets.MIDDLE_BAR_HEIGHT / 2);
    gc.setFill(Color.GRAY);
    gc.fillOval((Presets.FIELD_WIDTH - Presets.BUTTON_DIAMETER) / 2, (Presets.FIELD_HEIGHT - Presets.BUTTON_DIAMETER) / 2, Presets.BUTTON_DIAMETER, Presets.BUTTON_DIAMETER);
    
  }
  
  private Color getGleisColor() {
    if (lampState[LampRed]) {
      return Presets.RED_LAMP;
    } else if (lampState[LampWhite]) {
      return Color.LIGHTYELLOW;
    } else {
      return Presets.DARK_LAMP;
    }
  }
  
  @Override
  public void checkedUpdate() {
    boolean wh = drs2.getLampState(lampIds[LampWhite]);
    boolean rd = drs2.getLampState(lampIds[LampRed]);
    
    if (wh != lampState[LampWhite] || rd != lampState[LampRed]) {
      lampState[LampWhite] = wh;
      lampState[LampRed] = rd;
      update();
    }
  }
}
