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
public class Blinklicht extends BaseField {
  private final int[] lampIds;
  private final boolean[] lampState = new boolean[4];
  
  public Blinklicht(int[] lampIds) {
    this.lampIds = lampIds;
  }
  
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
  
  @Override
  public void checkedUpdate() {
    if (updateState(lampState, lampIds)) {
      update();
    }
  }
  
}
