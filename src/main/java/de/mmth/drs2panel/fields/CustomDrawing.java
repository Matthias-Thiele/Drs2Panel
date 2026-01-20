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
        
      case SCHLUESSELWEICHE3A:
        drawSW3A(gc);
        break;
        
      case SCHLUESSELWEICHE3B:
        drawSW3B(gc);
        break;
        
      case SCHLUESSELWEICHE3C:
        drawSW3C(gc);
        break;
        
      case SCHLUESSELWEICHE4A:
        drawSW4A(gc);
        break;
        
      case SCHLUESSELWEICHE4B:
        drawSW4B(gc);
        break;
        
      case TASTENKAPPEN:
        drawKappen(gc);
        break;
    }
  }
  
  
  private void drawKappen(GraphicsContext gc) {
    var top = Presets.FIELD_HEIGHT / 10;
    var step = Presets.FIELD_WIDTH / 9;
    var bigRadius = 30;
    var smallRadius = 18;
    gc.setFill(Color.RED);
    gc.fillOval(step, top, bigRadius, bigRadius);
    gc.fillOval(4 * step, top, bigRadius, bigRadius);
    gc.setFill(Color.NAVY);
    gc.fillOval(step, 5 * top, bigRadius, bigRadius);
    gc.fillOval(4 * step, 5 * top, bigRadius, bigRadius);
    gc.setFill(Color.GRAY);
    gc.fillOval(7 * step + 4, top + 6, smallRadius, smallRadius);
    gc.fillOval(7 * step + 4, 5 * top + 6, smallRadius, smallRadius);
  }
  
  private void drawSW4A(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText("[IV]", Presets.FIELD_WIDTH / 2, Presets.FIELD_HEIGHT / 8 + 4);
    gc.fillText("10", Presets.FIELD_WIDTH / 2, Presets.FIELD_HEIGHT / 4 * 3 + 4);
    
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 2, Presets.MIDDLE_BAR_START);
    gc.lineTo(Presets.FIELD_WIDTH / 4, 2 + Presets.MIDDLE_BAR_HEIGHT / 2);
    gc.lineTo(Presets.FIELD_WIDTH / 3, 2 + Presets.MIDDLE_BAR_HEIGHT / 2);
    gc.lineTo(Presets.FIELD_WIDTH / 3 + 4, 2 + Presets.MIDDLE_BAR_HEIGHT / 4);
    gc.lineTo(Presets.FIELD_WIDTH / 3, 2);
    gc.lineTo(0, 2);
    gc.lineTo(0, 2 + Presets.MIDDLE_BAR_HEIGHT / 2);
    gc.lineTo(Presets.FIELD_WIDTH / 3 - 25, 2 + Presets.MIDDLE_BAR_HEIGHT / 2);
    gc.lineTo(Presets.FIELD_WIDTH / 2 - 14, Presets.MIDDLE_BAR_START);
    gc.fill();
  }

  private void drawSW4B(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillRect(0, 2, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT / 2);
  }

  private void drawSW3A(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillRect(0, Presets.MIDDLE_BAR_START, Presets.FIELD_WIDTH, Presets.MIDDLE_BAR_HEIGHT);
    gc.fillText("[13]", Presets.FIELD_WIDTH / 2 - 10, Presets.FIELD_HEIGHT / 3);
    
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 3 + 14, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH / 6 * 4, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH / 6 * 4 + 12, Presets.FIELD_HEIGHT);
    gc.lineTo(Presets.FIELD_WIDTH / 3 + 26, Presets.MIDDLE_BAR_START + Presets.MIDDLE_BAR_HEIGHT);
    gc.fill();
  }

  private void drawSW3B(GraphicsContext gc) {
    gc.setFill(Color.BLACK);
    gc.fillText("[III]", Presets.FIELD_WIDTH / 2, Presets.FIELD_HEIGHT / 5 * 2 + 4);
    gc.fillText("12", Presets.FIELD_WIDTH / 7 * 5, Presets.FIELD_HEIGHT / 5 * 4);
    
    var midLine = Presets.FIELD_HEIGHT/ 7 * 4;
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH / 6 * 4 + 3, 0);
    gc.lineTo(Presets.FIELD_WIDTH, midLine);
    gc.lineTo(Presets.FIELD_WIDTH, midLine - 11);
    gc.lineTo(Presets.FIELD_WIDTH / 6 * 4 + 14, 0);
    gc.fill();
    
    midLine = Presets.FIELD_HEIGHT / 7;
    gc.beginPath();
    gc.moveTo(Presets.FIELD_WIDTH - 25, midLine);
    gc.lineTo(Presets.FIELD_WIDTH - 50, midLine);
    gc.lineTo(Presets.FIELD_WIDTH - 55, midLine + 5);
    gc.lineTo(Presets.FIELD_WIDTH - 50, midLine + 10);
    gc.lineTo(Presets.FIELD_WIDTH - 20, midLine + 10);
    gc.fill();
  }
  
  private void drawSW3C(GraphicsContext gc) {
    var midLine = Presets.FIELD_HEIGHT/ 7 * 4;
    
    gc.setFill(Color.BLACK);
    gc.fillText("6", Presets.FIELD_WIDTH / 2 - 10, Presets.FIELD_HEIGHT / 5 * 2 - 4);
    gc.fillRect(0, midLine - 10, Presets.FIELD_WIDTH, 10);
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
