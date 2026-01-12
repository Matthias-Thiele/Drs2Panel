/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;

/**
 * Grid zur Aufnahme der Tischfelder.
 * 
 * @author matthias
 */
public class FieldGrid extends GridPane {
  private final static int[] LINE_FIELD_LIST = {5, 1, 6, 1, 1, 2, 5, 2, 6,2, 7, 2, 9, 2, 12, 2, 2, 3, 3, 3, 4, 3, 7, 3, 10, 3, 11, 3};
  
  private final List<BaseField> allFields = new ArrayList<>();
  private final boolean[] isSet = new boolean[Presets.GRID_WIDTH * Presets.GRID_HEIGHT];
  
  public FieldGrid() {
    this.setStyle("-fx-border-color: green");
    this.setHgap(1);
    this.setVgap(1);
    populateGrid();
    update();
  }
  
  /**
   * Aktualisiert alle Felder des Tischfeldes.
   */
  public final void update() {
    for (var f: allFields) {
      f.update();
    }
  }
  
  /**
   * Füllt das Tischfeld mit den einzelnen Feldern.
   */
  private void populateGrid() {
    addStreckenblock();
    addLineFields();
    addWeichen();
    addBahnhofsgleise();
    for (var col = 0; col < Presets.GRID_WIDTH; col++) {
      for (var row = 0; row < Presets.GRID_HEIGHT; row++) {
        if (!isSet[row * Presets.GRID_WIDTH + col]) {
          var field = new BaseField();
          addField(field, col, row);
        }
      }
    }
  }
  
  private void addWeichen() {
    var w20 = new Weiche("W20", false, false);
    addField(w20, 1, 3);
    var w19 = new Weiche("W19", true, true);
    addField(w19, 2, 2);
    var w18 = new Weiche("W18", false, false);
    addField(w18, 3, 2);
    var w5 = new Weiche("W5", true, false);
    addField(w5, 10, 2);
    var w4 = new Weiche("W4", false, true);
    addField(w4, 11, 2);
    var w3 = new Weiche("W3", true, false);
    addField(w3, 12, 3);
  }
  
  private void addBahnhofsgleise() {
    var g1 = new Gleis("1");
    addField(g1, 8, 3);
    var g2 = new Gleis("2");
    addField(g2, 8, 2);
    var g3 = new Gleis("3");
    addField(g3, 8, 1);
  }
  
  private void addStreckenblock() {
    var blockOutAH = new StreckeOut("AH", true);
    addField(blockOutAH, 0, 2);
    
    var blockOutWB = new StreckeOut("WB", false);
    addField(blockOutWB, 14, 3);
  }
  
  private void addLineFields() {
    for (var i = 0; i < LINE_FIELD_LIST.length; i += 2) {
      var lf = new LineField();
      addField(lf, LINE_FIELD_LIST[i], LINE_FIELD_LIST[i + 1]);
    }
  }
  /**
   * Fügt ein Feld in das Tischfeld ein und markiert die Position als besetzt.
   * 
   * @param field
   * @param column
   * @param row 
   */
  private void addField(BaseField field, int column, int row) {
    this.add(field, column, row);
    allFields.add(field);
    isSet[row * Presets.GRID_WIDTH + column] = true;
  }
}
