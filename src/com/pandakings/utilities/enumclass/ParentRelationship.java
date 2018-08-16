package com.pandakings.utilities.enumclass;

public enum ParentRelationship {
  FATHER("Father"),
  MOTHER("Mother"),
  UNCLE("Uncle"),
  AUNT("Aunt"),
  OTHER("Other");

  private final String description;

  ParentRelationship(String preDescription) {
    description = preDescription;
  }

  public String description() {
    return description;
  }

  /**
   * Get the parent relationship by description.
   * @param description The description of the relationship.
   * @return ParentRelationship or null if non-existed.
   */
  public static ParentRelationship getParentRelationship(String description) {
    if (description.equalsIgnoreCase("Father")) {
      return FATHER;
    } else if (description.equalsIgnoreCase("Mother")) {
      return MOTHER;
    } else if (description.equalsIgnoreCase("Uncle")) {
      return UNCLE;
    } else if (description.equalsIgnoreCase("Aunt")) {
      return AUNT;
    } else if (description.equalsIgnoreCase("Other")) {
      return OTHER;
    } else {
      return null;
    }
  }
}
