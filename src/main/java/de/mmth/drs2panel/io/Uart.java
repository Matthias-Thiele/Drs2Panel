/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */

package de.mmth.drs2panel.io;

import com.fazecast.jSerialComm.SerialPort;

/**
 * Klasse zur Kommunikation mit der DRS 2 Simulation
 * über UARTs.
 * 
 * @author matthias
 */
public class Uart {

  private final String portName;
  private final SerialPort comPort;
  private final byte[] readBuffer = new byte[1];
 
  /**
   * Konstruktor mit Namen des Ports und öffnet die Verbindung.
   * @param name 
   */
  public Uart(String name) {
    portName = name;
    comPort = SerialPort.getCommPort(portName);
    System.out.println("Open Port " + portName);
    comPort.openPort();
    //comPort.setBaudRate(9600);
  }
  
  /**
   * Beendet die Verbindung.
   */
  public void close() {
    comPort.closePort();
    System.out.println("Port closed.");
  }
  
  /**
   * Sendet einen String (wie von der IO Karte).
   * @param text 
   */
  public void send(String text) {
    byte[] writeBuffer = text.getBytes();
    comPort.writeBytes(writeBuffer, writeBuffer.length);
  }
  
  /**
   * Sendet eine Byte-Folge (wie vom DRS 2 Stellpult).
   * @param values
   * @param offset
   * @param length 
   */
  public void sendBytes(byte[] values, int offset, int length) {
    comPort.writeBytes(values, length, offset);
  }
  
  /**
   * Meldet zurück, ob weitere Zeichen empfangen wurden.
   * @return 
   */
  public boolean check() {
    return comPort.bytesAvailable() > 0;
  }
  
  /**
   * Liest ein Zeichen von der DRS 2 Simulation ein.
   * @return 
   */
  public byte readByte() {
    if (comPort.bytesAvailable() > 0) {
      comPort.readBytes(readBuffer, 1);
      return readBuffer[0];
    }
    
    return 0;
  }
}
