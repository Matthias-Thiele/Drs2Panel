/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.io;


/**
 *
 * @author matthias
 */
public class Drs2 {
  private final static int OUTPUT_BYTE_COUNT = 15;
  private final static int IO_START = 120;
  private static final int MAX_LAMPS = 136;
  private static final int MAX_SWITCHES = 72;
  private static final int BUFFER_SIZE = 32;
  private static final byte[] switchInverter = {(byte)0x80, (byte)0xff, (byte)0x7e, (byte)0x64, (byte)0x1f, (byte)0xff, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00, (byte)0x00};
  
  private final boolean[] lamps = new boolean[MAX_LAMPS];
  private final boolean[] switches = new boolean[MAX_SWITCHES];
  private boolean switchesDirty = false;
  private boolean lampsChanged = false;
  
  private final byte[] receiveBuffer = new byte[BUFFER_SIZE];
  private final byte[] transmitBuffer = new byte[BUFFER_SIZE];

  private final byte[] receiveIBuffer = new byte[BUFFER_SIZE];

  public enum CommandState {
    WAIT,
    OUT_STARTED,
    OUT_TERMINATED,
    IN_QUERY,
    IO_RUNNING,
    IO_DONE
  }
  
  private final Uart uartD;
  private final Uart uartIO;
  private CommandState actState = CommandState.WAIT;
  private int outCount = 0;
  private CommandState actIState = CommandState.WAIT;
  private int outICount = 0;
  
  public Drs2() {
    uartD = new Uart("/dev/ttyUSB2");
    uartIO = new Uart("/dev/ttyUSB3");
  }
  
  public void tick(long now) {
    tickD(now);
    tickIO(now);
  }
  
  public void tickD(long now) {
    if (uartD.check()) {
      byte next = uartD.readByte();
      switch (actState) {
        case WAIT:
        switch (next) {
          case 'C':
            actState = CommandState.OUT_STARTED;
            outCount = 0;
            break;
          case 'Q':
            sendInputs();
            break;
          default:
            break;
        }
          break;

          
        case OUT_STARTED:
          receiveBuffer[outCount++] = next;
          if (outCount == OUTPUT_BYTE_COUNT) {
            actState = CommandState.OUT_TERMINATED;
          }
          break;
        
        case OUT_TERMINATED:
          if (next == 'X') {
            fillLamps();
          } else {
            System.out.println("Invalid termination");
          }
          actState = CommandState.WAIT;
          break;
      }
    }
  }
  
  public void tickIO(long now) {
    if (uartIO.check()) {
      byte next = uartIO.readByte();
      switch (actIState) {
        case WAIT:
          if (next == 'X') {
            actIState = CommandState.IO_RUNNING;
            outICount = 0;
          }
          break;

        case IO_RUNNING:
          if (next == 'y') {
            for (var i = 0; i < outICount; i++) {
              byte c = receiveIBuffer[i];
              boolean isSet = c >= 'a';
              int pos = (c - 'A') & 0xf;
              lamps[IO_START + pos] = isSet;
            }
            
            lampsChanged = true;
            actIState = CommandState.WAIT;
          } else {
            receiveIBuffer[outICount++] = next;
          }
          break;
      }
    }
  }
  
  private void fillLamps() {
    var lampPos = 0;
    for (var i = 0; i < OUTPUT_BYTE_COUNT; i++) {
      byte val = receiveBuffer[i];
      if (lampPos == 96) {
        lampPos += 8;
      }
      
      for (var j = 0; j < 8; j++) {
        lamps[lampPos++] = (val & 1) == 1;
        val >>= 1;
      }
    }
    
    lampsChanged = true;
  }
  
  private void fillTransmitBufferByte(int bufferPos, int bitPos) {
    byte value = 0;
    byte mask = 1;
    for (var i = 0; i < 8; i++) {
      if (switches[bitPos + i]) {
        value |= mask;
      }
      
      mask <<= 1;
    }
    
    transmitBuffer[bufferPos] = value;
  }
  
  private void fillTransmitBufferD() {
    transmitBuffer[0] = 'B';
    for (int i = 0, bit = 0; i < 6; i++, bit += 8) {
      if (bit == 32) bit += 8; // altes Statusbyte überspringen
      fillTransmitBufferByte(i + 1, bit);
      transmitBuffer[i + 1]  ^= switchInverter[i];
    }
    transmitBuffer[7] = 'X';
  }
  
  private void fillTransmitBufferIO() {
    fillTransmitBufferByte(0, 56);
    fillTransmitBufferByte(1, 64);
  }
  
  private void sendInputs() {
    fillTransmitBufferD();
    uartD.sendBytes(transmitBuffer, 0, 8);

    fillTransmitBufferIO();
    uartIO.send("Z5");
    int ioVal = (transmitBuffer[0] & 0xff) + ((transmitBuffer[1] & 0xff) << 8) + 0x50000;
    uartIO.send(Integer.toHexString(ioVal));
    uartIO.send("T");
  }
  
  public void checkSend() {
    if (switchesDirty) {
      System.out.println("Send switch state.");
      switchesDirty = false;
      sendInputs();
    }
  }
  
  public boolean checkReceived() {
    boolean result = lampsChanged;
    lampsChanged = false;
    if (result) {
      System.out.println("Lamps changed.");
    }
    return result;
  }
  
  public void setSwitch(int id, boolean newValue) {
    switchesDirty |= switches[id] != newValue;
    switches[id] = newValue;
  }
  
  public boolean getLampState(int id) {
    return lamps[id];
  }
}
