/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * Grid zur Aufnahme der Tischfelder.
 * 
 * @author matthias
 */
public class FieldGrid extends GridPane {
  private final static int[] LINE_FIELD_LIST = {5, 1, 1, 2, 5, 2, 6,2, 7, 2, 9, 2, 12, 2, 2, 3, 3, 3, 4, 3, 7, 3, 10, 3, 11, 3};
  
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
    addSchluesselweichen();
    addBahnhofsgleise();
    addCounter();
    addButtons();
    addMelder();
    addSignale();
    addCustom();
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
    var w20 = new Weiche("20", false, false);
    addField(w20, 1, 3);
    var w19 = new Weiche("19", true, true);
    addField(w19, 2, 2);
    var w18 = new Weiche("18", false, false);
    addField(w18, 3, 2);
    var w5 = new Weiche("5", true, false);
    addField(w5, 10, 2);
    var w4 = new Weiche("4", false, true);
    addField(w4, 11, 2);
    var w3 = new Weiche("3", true, false);
    addField(w3, 12, 3);
  }
  
  private void addSchluesselweichen() {
    var slftIV = new Schluesselweiche("SlFT IV 13 III", 12);
    addField(slftIV, 2, 1);
    
    var slftIII = new Schluesselweiche("SlFT III 10 IV", 10);
    addField(slftIII, 3, 1);
    
    var slft1 = new Schluesselweiche("SlFT 1 I", 25);
    addField(slft1, 8, 0);
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
    var blockOutWB = new StreckeOut("WB", true);
    addField(blockOutWB, 0, 2);
    
    var blockOutAH = new StreckeOut("AH", false);
    addField(blockOutAH, 14, 3);
    
    var blockInWB = new StreckeIn("F", true, false);
    addField(blockInWB, 0, 4);
    
    var blockInAH = new StreckeIn("A", false, true);
    addField(blockInAH, 14, 2);
  }
  
  private void addCounter() {
    var ersgt = new CounterField("Ers GT", Color.RED);
    addField(ersgt, 3, 0);
    
    var blz = new CounterField("  BLZ", Color.GRAY);
    addField(blz, 7, 0);
    
    var fht = new CounterField("  FHT", Color.RED);
    addField(fht, 9, 0);
    
    var wht = new CounterField("  WHT", Color.NAVY);
    addField(wht, 10, 0);
    
    var azgrt = new CounterField("AZGRT", Color.RED);
    addField(azgrt, 11, 0);
    
    var ast = new CounterField("  AsT", Color.BLACK);
    addField(ast, 12, 0);
    
    var rbhgt = new CounterField("RbHGT", Color.BLACK);
    addField(rbhgt, 13, 1);
    
    var af = new CounterField("    Af", null);
    addField(af, 10, 1);
  }
  
  private void addButtons() {
    var wgtsgt = new DualButton("WGT", Color.NAVY, "SGT", Color.RED);
    addField(wgtsgt, 1, 0);
    
    var hagtslflt = new DualButton("HaGT", Color.RED, "SlFLT", Color.WHITE);
    addField(hagtslflt, 2, 0);
    
    var vbhth = new SingleButton("VbHT", Color.BLACK);
    addField(vbhth, 0, 1);
    
    var bgt = new SingleButton("BlGT", Color.BLACK);
    addField(bgt, 5, 0);
    
    var aslt = new SingleButton("AsLT", Color.WHITE);
    addField(aslt, 13, 0);
    
    var vbhtm = new SingleButton("VbHT", Color.BLACK);
    addField(vbhtm, 14, 4);
  }
  
  private void addMelder() {
    var wut = new Melder("S|WuT|W", "   S", "   W", Color.WHITE, Color.WHITE);
    addField(wut, 4, 0);
    
    var unkn = new Melder("", "  MJI", " MJII", null, null);
    addField(unkn, 12, 1);
    
    var tu = new Melder("", "  TÜ", "", null, null);
    addField(tu, 3, 4);
    
    var zsm = new Melder("", " ZSM", "", null, null);
    addField(zsm, 11, 1);
    
    var blk = new Blinklicht();
    addField(blk, 2, 4);
  }
  
  private void addSignale() {
    var sigA = new SignalA();
    addField(sigA, 13,2);
    
    var sigF = new SignalF();
    addField(sigF, 0,3);
    
    var sigN3 = new SignalN("N3", true);
    addField(sigN3, 4, 1);
    
    var sigN2 = new SignalN("N2", false);
    addField(sigN2, 4, 2);
    
    var sigP3 = new SignalP("P3", false, true);
    addField(sigP3, 9, 1);
    
    var sigP1 = new SignalP("P1", true, false);
    addField(sigP1, 9, 3);
    
    var wvP1 = new SignalWVp1();
    addField(wvP1, 6, 3);
  }
  
  private void addCustom() {
    var sw1A = new CustomDrawing(FieldType.SCHLUESSELWEICHE1A);
    addField(sw1A, 13, 3);
    
    var sw1B = new CustomDrawing(FieldType.SCHLUESSELWEICHE1B);
    addField(sw1B, 12, 4);
    
    var sw1C = new CustomDrawing(FieldType.SCHLUESSELWEICHE1C);
    addField(sw1C, 11, 4);
    
    var sw3A = new CustomDrawing(FieldType.SCHLUESSELWEICHE3A);
    addField(sw3A, 5, 3);
    
    var sw3B = new CustomDrawing(FieldType.SCHLUESSELWEICHE3B);
    addField(sw3B, 5, 4);
    
    var sw3C = new CustomDrawing(FieldType.SCHLUESSELWEICHE3C);
    addField(sw3C, 6, 4);
    
    var sw4A = new CustomDrawing(FieldType.SCHLUESSELWEICHE4A);
    addField(sw4A, 7, 1);
    
    var sw4B = new CustomDrawing(FieldType.SCHLUESSELWEICHE4B);
    addField(sw4B, 6, 1);
    
    var kappen = new CustomDrawing(FieldType.TASTENKAPPEN);
    addField(kappen, 0, 0);
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
