/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */

package de.mmth.drs2panel.io;

import com.fazecast.jSerialComm.SerialPort;

/**
 *
 * @author matthias
 */
public class Uart {

  private String portName;
  private final SerialPort comPort;
  private final byte[] readBuffer = new byte[1];
 
  public Uart(String name) {
    portName = name;
    comPort = SerialPort.getCommPort(portName);
    System.out.println("Open Port " + portName);
    comPort.openPort();
    //comPort.setBaudRate(9600);
  }
  
  public void close() {
    comPort.closePort();
    System.out.println("Port closed.");
  }
  
  public void send(String text) {
    byte[] writeBuffer = text.getBytes();
    comPort.writeBytes(writeBuffer, writeBuffer.length);
  }
  
  public void sendBytes(byte[] values, int offset, int length) {
    comPort.writeBytes(values, length, offset);
  }
  
  public boolean check() {
    return comPort.bytesAvailable() > 0;
  }
  
  public byte readByte() {
    if (comPort.bytesAvailable() > 0) {
      comPort.readBytes(readBuffer, 1);
      return readBuffer[0];
    }
    
    return 0;
  }
}
