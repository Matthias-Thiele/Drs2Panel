/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import de.mmth.drs2panel.io.Drs2;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.AnimationTimer;

/**
 *
 * @author matthias
 */
public class ButtonHandler {
  private final static List<ButtonEvent> pendingEvents = new ArrayList<>();
  public static Drs2 drs2;
  
  public static void add(BaseField source, int delay, int buttonId) {
    System.out.println("Button " + buttonId + " pressed at " + System.nanoTime());
    source.setButtonPressed(true);
    drs2.setSwitch(buttonId, true);
    pendingEvents.add(new ButtonEvent(source, System.nanoTime() + (delay * 1000000l), buttonId));
  }
  
  static {
    new AnimationTimer() {
      @Override
      public void handle(long now) {
        ButtonEvent forDeletion = null;
        for (var ev: pendingEvents) {
          if (ev.expire() < now) {
            System.out.println("Button " + ev.buttonId() + " released at " + now);
            forDeletion = ev;
            break;
          }
        }
        
        if (forDeletion != null) {
          boolean keepTriggered = false;
          for (var ev: pendingEvents) {
            if (ev != forDeletion) {
              if (ev.buttonId() == forDeletion.buttonId()) {
                // Erneut betätigt
                keepTriggered = true;
              }
            }
          }
          
          if (!keepTriggered) {
            forDeletion.source().setButtonPressed(false);
            drs2.setSwitch(forDeletion.buttonId(), false);
          }
          
          pendingEvents.remove(forDeletion);
        }
      }
    }.start();
  }
}

record ButtonEvent(BaseField source, long expire, int buttonId) {
  
}