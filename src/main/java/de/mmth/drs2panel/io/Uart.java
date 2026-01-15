/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.io;

import javafx.animation.AnimationTimer;

/**
 *
 * @author matthias
 */
public class Uart {
  private static final int MAX_LAMPS = 128;
  private static final int MAX_SWITCHES = 64;
  private static final int BUFFER_SIZE = 32;
  
  private boolean[] lamps = new boolean[MAX_LAMPS];
  private boolean[] switches = new boolean[MAX_SWITCHES];
  private boolean switchesDirty = false;
  
  private byte[] receiveBuffer = new byte[BUFFER_SIZE];
  private byte[] transmitBuffer = new byte[BUFFER_SIZE];
  
  public Uart() {
  }
  
  public void checkSend() {
    if (switchesDirty) {
      System.out.println("Send switch state.");
      switchesDirty = false;
    }
  }
  
  public void setSwitch(int id, boolean newValue) {
    switchesDirty |= switches[id] != newValue;
    switches[id] = newValue;
  }
  
  public boolean getLampState(int id) {
    return lamps[id];
  }
}
