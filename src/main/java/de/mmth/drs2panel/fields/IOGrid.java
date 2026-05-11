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
 * Diese Klasse enthält die Elemente die die beiden
 * externen Relais/ Optokoppler Platinen enthalten.
 * 
 * Die Karten haben eine eigene UART Verbindung, ihre
 * Daten werden aber mit in das Tasten/ Lampen Array
 * eingetragen.
 * 
 * @author matthias
 */
public class IOGrid extends GridPane {
  private static final int TA_CHANGED = -2;
  private static MediaPlayer mp3Player;
  private int nextButtonCol = 0;
  private int nextLabelCol = 0;
  private boolean lastWecker = false;
  
  private final Drs2 drs2;
  private final List<Label> lampList = new ArrayList<>();
  private final List<Button> buttonList = new ArrayList<>();
  
  private Button vorblockAH;
  private Button rückblockAH;
  
  /**
   * Konstruktor erzeugt die Anzeigen und Schalter für die
   * Simulation der Relais und Optokoppler.
   * 
   * @param drs2 
   */
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
        // Wecker - spielt eine MP3 Datei ab
        var actWecker = drs2.getLampState(Const.Wecker);
        if (actWecker != lastWecker) {
          lastWecker = actWecker;
          if (actWecker) {
            Media media = new Media(this.getClass().getResource("bell.mp3").toExternalForm());
            mp3Player = new MediaPlayer(media);
            mp3Player.play();
          }
        }
        
        // Aktualisiert die Anzeigelampen.
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
        
        // Prüft die Taster
        for (var button: buttonList) {
          ButtonState s = (ButtonState) button.getUserData();
          if (s.isAutoRelease && s.releaseCount > 0) {
            s.releaseCount--;
            if (s.releaseCount == 0) {
              processButton(button, ButtonAction.RESET);
            }
          }
        }
      }
      
    }.start();
  }
  
  /**
   * Erzeugt die Buttons welche die Optokoppler-Eingänge
   * simulieren.
   */
  private void addButtons() {
    var label = new Label("Eingaben");
    label.setPrefWidth(Presets.FIELD_WIDTH - 10);
    this.add(label, nextButtonCol++, 0);
    addInput("TA", Const.TA, TA_CHANGED, false, false);
    addInput("Fa A", Const.SCHLUESSEL_A, -1, false, true);
    addInput("Fa F", Const.SCHLUESSEL_F, -1, false, true);
    addInput("SW I", Const.WSCHLUESSEL1, Const.SlFT1Relais, false, true);
    addInput("SW IV", Const.WSCHLUESSEL4, Const.SlFT4Relais, false, true);
    addInput("ÜM", Const.WHSPERRE_ZURÜCK, -1, false, false);
    vorblockAH = addInput("Nach AH", Const.BLOCK_AH_OUT, -1, true, false);
    rückblockAH = addInput("Von AH", Const.BLOCK_AH_IN, - 1, true, false);
    addInput("Pause", Const.PAUSE, -1, false, false);
    addInput("Faden", Const.HAUPTFADEN_DEFEKT, -1, false, false);
    addInput("LS P1 <-> P3", Const.SWITCH_LS, -1, false, true);
  }
  
  /**
   * Erzeugt die Anzeigen welche die Relaisausgänge simulieren.
   */
  private void addLamps() {
    var label = new Label("Ausgaben");
    this.add(label, nextLabelCol++, 1);
    
    this.addLamp(" SW I", Const.SlFT1Relais, null);
    this.addLamp(" SW IV", Const.SlFT4Relais, null);
    this.addLamp(" VB nach AH", Const.StreckeNachAH, vorblockAH);
    this.addLamp(" RB von AH", Const.StreckeVonAH, rückblockAH);
    this.addLamp(" LS HP1", Const.LS_HP1, null);
    this.addLamp(" LS HP2", Const.LS_HP2, null);
    this.addLamp(" LS ZS1", Const.LS_ZS1, null);
    this.addLamp(" LS SH1", Const.LS_SH1, null);
    this.addLamp(" RH1 Def.", Const.LS_RH1_DEFEKT, null);
    this.addLamp(" RN1 Def.", Const.LS_RN1_DEFEKT, null);
    this.addLamp(" RT2 Def.", Const.LS_RT2_DEFEKT, null);
    this.addLamp(" FD Def.", Const.LS_FD_DEFEKT, null);
  }
  
  /**
   * Erzeugt eine Lampenanzeige.
   * @param name
   * @param ioId
   * @param assignedOperation 
   */
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
  
  /**
   * Erzeugt einen Taster.
   * 
   * Ein Taster kann eine externe Abhängigkeit zu einer Lampenanzeige
   * besitzen. So kann eine Schlüsselentnahme nur erzeugt werden, wenn
   * es eine Freigabe für diesen Weichenschlüssel gibt.
   * 
   * @param name
   * @param ioId
   * @param checkId
   * @param isBlock
   * @return 
   */
  private Button addInput(String name, int ioId, int checkId, boolean isBlock, boolean isAutoRelease) {
    var bt = new Button(name);
    var state = new ButtonState();
    state.isPressed = isBlock;
    state.isBlockButton = isBlock;
    state.ioId = ioId;
    state.checkId = checkId;
    state.isAutoRelease = isAutoRelease;
    state.releaseCount = 0;
    
    bt.setUserData(state);
    bt.setPrefWidth(Presets.FIELD_WIDTH - 10);
    if (isBlock) {
      bt.setStyle("-fx-background-color: white");
      drs2.setSwitch(ioId, true);
    }
    
    bt.setOnAction(e -> {
      ButtonState s = (ButtonState) bt.getUserData();
      if (!s.isPressed && s.checkId >= 0) {
        if (!drs2.getLampState(s.checkId)) {
          System.out.println("Nicht freigegeben: " + s.ioId + ", check: " + s.checkId);
          return;
        }
      }
      
      processButton(bt, isAutoRelease ? ButtonAction.SET : ButtonAction.INVERT);
      if (checkId == TA_CHANGED) {
        // Tastenabschalter betätigt.
        drs2.setLampChanged();
      }
    });
    this.add(bt, nextButtonCol++, 0);
    
    buttonList.add(bt);
    return bt;
  }
  
  /**
   * Taster/ Schalter wurde gedrückt.
   * 
   * Es gibt Taster, die ein Setzen, Rücksetzen oder Invertieren
   * auslösen. Der Zustand wird jeweils über eine Farbänderung
   * angezeigt.
   * 
   * @param bt
   * @param action 
   */
  private void processButton(Button bt, ButtonAction action) {
    ButtonState s = (ButtonState) bt.getUserData();
    switch (action) {
      case SET: 
        s.isPressed = true; 
        if (s.isAutoRelease) {
          s.releaseCount = 30;
        }
        break;
      case RESET: s.isPressed = false; break;
      case INVERT: s.isPressed = !s.isPressed; break;
    }
    
    if (s.isBlockButton) {
      bt.setStyle(s.isPressed ? "-fx-background-color: red": "-fx-background-color: white");
      drs2.setSwitch(s.ioId, !s.isPressed);
    } else {
      bt.setStyle(s.isPressed ? "-fx-background-color: lightgreen": "");
      drs2.setSwitch(s.ioId, s.isPressed);
    }
  }
  
  enum ButtonAction {
    SET,
    RESET,
    INVERT
  }
  
  class ButtonState {
    boolean isPressed;
    boolean isBlockButton;
    boolean isAutoRelease;
    int ioId;
    int checkId;
    int releaseCount;
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
