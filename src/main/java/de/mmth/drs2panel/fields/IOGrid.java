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
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 *
 * @author matthias
 */
public class IOGrid extends GridPane {
  private int nextButtonCol = 0;
  private int nextLabelCol = 0;
  private boolean lastWecker = false;
  
  private final Drs2 drs2;
  private final List<Label> lampList = new ArrayList<>();
  private Button vorblockAH;
  private Button rückblockAH;
  
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
        // Wecker
        var actWecker = drs2.getLampState(Const.Wecker);
        if (actWecker != lastWecker) {
          lastWecker = actWecker;
          if (actWecker) {
            Media media = new Media(this.getClass().getResource("bell.mp3").toExternalForm());
            MediaPlayer mp3Player = new MediaPlayer(media);
            mp3Player.play();
          }
        }
        for (var lamp: lampList) {
          var state = (LabelState)lamp.getUserData();
          var lampState = drs2.getLampState(state.ioId);
          if (state.isActive != lampState) {
            System.out.println("Lamp state " + state.ioId + " changed to " + state.isActive);
            state.isActive = lampState;
            lamp.setStyle(lampState ? "-fx-background-color: red": "-fx-background-color: white");
            
            if (lampState) {
              if (state.assigned == vorblockAH) {
                processButton(vorblockAH, ButtonAction.SET);
              } else if (state.assigned == rückblockAH) {
                processButton(rückblockAH, ButtonAction.RESET);
              } else if (state.assigned != null) {
                processButton(state.assigned, ButtonAction.INVERT);
              }
            }
          }
        }
      }
      
    }.start();
  }
  
  private void addButtons() {
    var label = new Label("Eingaben");
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    this.add(label, nextButtonCol++, 0);
    addInput("TA", Const.TA, -1, false);
    addInput("Fa A", Const.SCHLUESSEL_A, -1, false);
    addInput("Fa F", Const.SCHLUESSEL_F, -1, false);
    addInput("SW I", Const.WSCHLUESSEL1, Const.SlFT1Relais, false);
    addInput("SW IV", Const.WSCHLUESSEL4, Const.SlFT4Relais, false);
    vorblockAH = addInput("Nach AH", Const.BLOCK_AH_OUT, -1, true);
    rückblockAH = addInput("Von AH", Const.BLOCK_AH_IN, - 1, true);
  }
  
  private void addLamps() {
    var label = new Label("Ausgaben");
    this.add(label, nextLabelCol++, 1);
    
    this.addLamp(" SW I", Const.SlFT1Relais, null);
    this.addLamp(" SW IV", Const.SlFT4Relais, null);
    this.addLamp(" VB nach AH", Const.StreckeNachAH, vorblockAH);
    this.addLamp(" RB von AH", Const.StreckeVonAH, rückblockAH);
  }
  
  private void addLamp(String name, int ioId, Button assignedOperation) {
    var label = new Label(name);
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    label.setPrefHeight(Presets.MIDDLE_BAR_HEIGHT);
    
    label.setStyle("-fx-background-color: white");
    var ls = new LabelState(false, ioId, assignedOperation);
    label.setUserData(ls);
    this.add(label, nextLabelCol++, 1);
    lampList.add(label);
  }
  
  private Button addInput(String name, int ioId, int checkId, boolean isBlock) {
    var bt = new Button(name);
    var state = new ButtonState();
    state.isPressed = false;
    state.isBlockButton = isBlock;
    state.ioId = ioId;
    state.checkId = checkId;
    bt.setUserData(state);
    bt.setPrefWidth(Presets.FIELD_WIDTH - 10);
    if (isBlock) {
      bt.setStyle("-fx-background-color: white");
    }
    
    bt.setOnAction(e -> {
      ButtonState s = (ButtonState) bt.getUserData();
      if (!s.isPressed && s.checkId >= 0) {
        if (!drs2.getLampState(s.checkId)) {
          System.out.println("Nicht freigegeben: " + s.ioId + ", check: " + s.checkId);
          return;
        }
      }
      
      processButton(bt, ButtonAction.INVERT);
    });
    this.add(bt, nextButtonCol++, 0);
    
    return bt;
  }
  
  private void processButton(Button bt, ButtonAction action) {
    ButtonState s = (ButtonState) bt.getUserData();
    switch (action) {
      case SET: s.isPressed = true; break;
      case RESET: s.isPressed = false; break;
      case INVERT: s.isPressed = !s.isPressed; break;
    }
    
    if (s.isBlockButton) {
      bt.setStyle(s.isPressed ? "-fx-background-color: red": "-fx-background-color: white");
    } else {
      bt.setStyle(s.isPressed ? "-fx-background-color: lightgreen": "");
    }
    drs2.setSwitch(s.ioId, s.isPressed);
  }
  
  enum ButtonAction {
    SET,
    RESET,
    INVERT
  }
  class ButtonState {
    boolean isPressed;
    boolean isBlockButton;
    int ioId;
    int checkId;
  }
  
  class LabelState {
    LabelState(boolean isActive, int ioId, Button assigned) {
      this.isActive = isActive;
      this.ioId = ioId;
      this.assigned = assigned;
    }
    
    boolean isActive;
    int ioId;
    Button assigned;
  }
}
