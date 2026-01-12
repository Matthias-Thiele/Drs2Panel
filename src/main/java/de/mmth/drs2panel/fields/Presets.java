/*
(c) 2026 by Matthias Thiele
DRS 2 Stellpult
 */
package de.mmth.drs2panel.fields;

import javafx.scene.paint.Color;

/**
 *
 * @author matthias
 */
public class Presets {
  public static final int GRID_WIDTH = 15;
  public static final int GRID_HEIGHT = 7;
  public static final int FIELD_WIDTH = 140;
  public static final int FIELD_HEIGHT = 80;
  public static final Color FIELD_BACKGROUND = Color.rgb(0xd0, 0xd0, 0xd0);
  public static final Color DARK_LAMP = Color.rgb(0x30, 0x30, 0x30);
  
  public static final int MIDDLE_BAR_HEIGHT = FIELD_HEIGHT / 5;
  public static final int MIDDLE_BAR_START = FIELD_HEIGHT / 5 * 2;
  public static final int BUTTON_DIAMETER = FIELD_HEIGHT / 10 * 3;
  
  public static final int LAMP_WIDTH = FIELD_WIDTH / 4;
  public static final int LAMP_MARGIN = FIELD_WIDTH / 14;
  public static final int LAMP_DIAMETER = MIDDLE_BAR_HEIGHT - 4;
}
