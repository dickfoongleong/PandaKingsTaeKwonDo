package com.pandakings.utilities.enumclass;

public enum Belt {
  WHITE(0),
  YELLOW(1),
  ORANGE(2),
  GREEN(3),
  BLUE(4),
  PURPLE(5),
  RED(6),
  BROWN(7),
  RED_BLACK(8),
  SEMI_BLACK(9),
  BLACK(10);
  
  private final int description;
  
  Belt(int preDescription) {
    description = preDescription;
  }
  
  public int description() {
    return description;
  }
  
  /**
   * Get the Belt by index.
   * @param description The index.
   * @return Belt
   */
  public static Belt getBelt(int description) {
    switch (description) {
      case 0:
        return WHITE;
      case 1:
        return YELLOW;
      case 2:
        return ORANGE;
      case 3:
        return GREEN;
      case 4:
        return BLUE;
      case 5:
        return PURPLE;
      case 6:
        return RED;
      case 7:
        return BROWN;
      case 8:
        return RED_BLACK;
      case 9:
        return SEMI_BLACK;
      case 10:
        return BLACK;
      default:
        return null;
    }
  }
}
