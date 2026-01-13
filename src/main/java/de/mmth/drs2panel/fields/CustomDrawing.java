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
public class CustomDrawing extends BaseField {

  private final FieldType type;
  
  public CustomDrawing(FieldType type) {
    this.type = type;
  }
  
  @Override
  public void update() {
    super.update();
    var gc = canvas.getGraphicsContext2D();
    switch (type) {
      case SCHLUESSELWEICHE1A:
        drawSW1A(gc);
        break;
        
      case SCHLUESSELWEICHE1B:
        drawSW1B(gc);
        break;
        
      case SCHLUESSELWEICHE1C:
        drawSW1C(gc);
        break;
        
      
    }
  }
  
  private void drawSW1A(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText("[1]", Presets.FIELD_WIDTH / 2 - 10, Presets.FIELD_HEIGHT / 3);
    
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 3 - 11, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(0, Presets.FIELD_HEIGHT - 7);
    gc.lineTo(0, Presets.FIELD_HEIGHT);
    gc.lineTo(8, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH / 3 + 5, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
  }
  
  private void drawSW1B(GraphicsContext gc) {
    var lowerLine = Presets.FIELD_HEIGHT / 7 * 6;
    var midLine = Presets.FIELD_HEIGHT/ 7 * 4;
    
    gc.setFill(Color.BLACK);
    gc.fillText("[I]", Presets.FIELD_WIDTH / 5 * 4, Presets.FIELD_HEIGHT / 5 * 4);
    gc.fillText("2", Presets.FIELD_WIDTH / 2 - 10, Presets.FIELD_HEIGHT / 3);
    
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH - 7, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 0);
    gc.lineTo(Presets.FIELD_WIDTH, 7);
    gc.lineTo(Presets.FIELD_WIDTH / 2, lowerLine);
    gc.lineTo(0, lowerLine);
    gc.lineTo(0, lowerLine - 10);
    gc.lineTo(Presets.FIELD_WIDTH / 2 - 7, lowerLine - 10);
    gc.fill();
    
    gc.beginPath();
    gc.moveTo(0, midLine);
    gc.lineTo(Presets.FIELD_WIDTH / 7 * 6, midLine);
    gc.lineTo(Presets.FIELD_WIDTH / 7 * 6 + 5, midLine - 5);
    gc.lineTo(Presets.FIELD_WIDTH / 7 * 6, midLine - 10);
    gc.lineTo(0, midLine - 10);
    gc.fill();
  }
  
  private void drawSW1C(GraphicsContext gc) {
    var midLine = Presets.FIELD_HEIGHT/ 7 * 4;
    
    gc.setFill(Color.WHITE);
    gc.fillRect(Presets.FIELD_WIDTH / 5 * 2 - 5, Presets.FIELD_HEIGHT / 5 * 3 + 2, Presets.FIELD_WIDTH / 5, Presets.FIELD_HEIGHT / 5);
    
    gc.setFill(Color.BLACK);
    gc.fillText("4", Presets.FIELD_WIDTH / 2 - 10, Presets.FIELD_HEIGHT / 5 * 4);
    gc.fillRect(0, midLine - 10, Presets.FIELD_WIDTH, 10);
  }
}
