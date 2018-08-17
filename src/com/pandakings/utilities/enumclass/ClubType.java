package com.pandakings.utilities.enumclass;

public enum ClubType {
  NORMAL("Normal"),
  BLACK_BELT_CLUB("BBC"),
  MASTER_CLUB("Master-Club");

  private final String description;
  
  ClubType(String preDescription) {
    description = preDescription;
  }
  
  public String description() {
    return description;
  }
  
  /**
   * Get the club type by description.
   * @param description of the type.
   * @return ClubStatus.
   */
  public static ClubType getClubType(String description) {
    if (description.equalsIgnoreCase("Normal")) {
      return NORMAL;
    } else if (description.equalsIgnoreCase("BBC")) {
      return BLACK_BELT_CLUB;
    } else if (description.equalsIgnoreCase("Master-Club")) {
      return MASTER_CLUB;
    } else {
      return null;
    }
  }
}
