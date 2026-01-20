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
 * Diese Klasse erzeugt ein verzögertes "Button up"
 * Event nach einem Mausklick.
 * 
 * Da beim DRS 2 fast alle Aktionen die gleichzeitige Betätigung
 * von zwei Tasten erfordern und das mit der Maus nicht möglich
 * ist, wird nach einem Klick die Taste gedrückt gehalten und
 * geht erst nach einer einstellbaren Zeit wieder auf "nicht
 * gedrückt" zurück.
 * 
 * Beim Mausklick wird die Taste als gedrückt markiert und ein
 * Event in eine Liste eingetragen welches nach der eingestellten
 * Zeit die Taste als losgelassen meldet.
 * 
 * @author matthias
 */
public class ButtonHandler {
  private final static List<ButtonEvent> pendingEvents = new ArrayList<>();
  public static Drs2 drs2;
  
  /**
   * Fügt einen Tastendruck in die Warteliste ein.
   * 
   * @param source
   * @param delay
   * @param buttonId 
   */
  public static void add(BaseField source, int delay, int buttonId) {
    System.out.println("Button " + buttonId + " pressed at " + System.nanoTime());
    source.setButtonPressed(true);
    drs2.setSwitch(buttonId, true);
    pendingEvents.add(new ButtonEvent(source, System.nanoTime() + (delay * 1000000l), buttonId));
  }
  
  /**
   * Der Timer durchläuft die Liste aller aktiven Tastendrücke und
   * prüft, ob abgelaufene dabei sind.
   * 
   * Dafür wird ein AnimationTimer verwendet der 60 mal pro Sekunde
   * ausgeführt wird. Da die Liste immer nur gar keine oder wenige
   * Einträge enthält, lohnt es sich nicht, hier längere Intervalle
   * zu erzeugen.
   */
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

/**
 * Datenfeld für die Informationen eines Tastenevents.
 * 
 * Die source enthält das Anzeigefeld - es verändert sich
 * jeweils mit Taste gedrückt und Taste losgelassen.
 * 
 * Das expire Feld enthält den Zeitpunkt an dem das
 * Loslassen Ereignis ausgeführt werden soll (in nanoTime).
 */
record ButtonEvent(BaseField source, long expire, int buttonId) {
  
}