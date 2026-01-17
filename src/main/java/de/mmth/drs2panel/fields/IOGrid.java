/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Const;
import de.mmth.drs2panel.io.Drs2;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author matthias
 */
public class IOGrid extends GridPane {
  private int nextButtonCol = 0;
  private int nextLabelCol = 0;
  
  private final Drs2 drs2;
  private final List<Label> lampList = new ArrayList<>();
  
  public IOGrid(Drs2 drs2) {
    this.drs2 = drs2;
    this.setStyle("-fx-border-color: green; -fx-padding: 10px");
    this.setHgap(10);
    this.setVgap(10);
    addButtons();
    addLamps();
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        for (var lamp: lampList) {
          var state = (LabelState)lamp.getUserData();
          var lampState = drs2.getLampState(state.ioId);
          if (state.isActive != lampState) {
            state.isActive = lampState;
            lamp.setStyle(lampState ? "-fx-background-color: red": "-fx-background-color: white");
          }
        }
      }
      
    }.start();
  }
  
  private void addButtons() {
    var label = new Label("Eingaben");
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    this.add(label, nextButtonCol++, 0);
    addInput("Fa A", Const.SCHLUESSEL_A, -1, false);
    addInput("Fa F", Const.SCHLUESSEL_F, -1, false);
    addInput("SW I", Const.WSCHLUESSEL1, Const.SlFT1Relais, false);
    addInput("SW IV", Const.WSCHLUESSEL4, Const.SlFT4Relais, false);
    addInput("Nach AH", Const.BLOCK_AH_OUT, -1, true);
    addInput("Von AH", Const.BLOCK_AH_IN, - 1, true);
  }
  
  private void addLamps() {
    var label = new Label("Ausgaben");
    this.add(label, nextLabelCol++, 1);
    
    this.addLamp(" VB nach AH", Const.BlockMOut);
    this.addLamp(" RB von AH", Const.BlockMIn);
  }
  
  private void addLamp(String name, int ioId) {
    var label = new Label(name);
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    label.setPrefHeight(Presets.MIDDLE_BAR_HEIGHT);
    
    label.setStyle("-fx-background-color: white");
    var ls = new LabelState(false, ioId);
    label.setUserData(ls);
    this.add(label, nextLabelCol++, 1);
  }
  
  private void addInput(String name, int ioId, int checkId, boolean isBlock) {
    var bt = new Button(name);
    var state = new ButtonState();
    state.isPressed = false;
    state.ioId = ioId;
    state.checkId = checkId;
    bt.setUserData(state);
    bt.setPrefWidth(Presets.FIELD_WIDTH - 10);
    if (isBlock) {
      bt.setStyle("-fx-background-color: white");
    }
    
    bt.setOnAction(e -> {
      ButtonState s = (ButtonState) bt.getUserData();
      if (s.checkId >= 0) {
        if (!drs2.getLampState(s.checkId)) {
          System.out.println("Nicht freigegeben: " + s.ioId + ", check: " + s.checkId);
          return;
        }
      }
      
      s.isPressed = !s.isPressed;
      if (isBlock) {
        bt.setStyle(s.isPressed ? "-fx-background-color: red": "-fx-background-color: white");
      } else {
        bt.setStyle(s.isPressed ? "-fx-background-color: lightgreen": "");
      }
      drs2.setSwitch(s.ioId, s.isPressed);
    });
    this.add(bt, nextButtonCol++, 0);
  }
  
  class ButtonState {
    boolean isPressed;
    int ioId;
    int checkId;
  }
  
  class LabelState {
    LabelState(boolean isActive, int ioId) {
      this.isActive = isActive;
      this.ioId = ioId;
    }
    
    boolean isActive;
    int ioId;
  }
}
