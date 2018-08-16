package com.pandakings.utilities.enumclass;

public enum ExpirationStatus {
  ON_GOING("On-going"),
  SOON("Soon"),
  EXPIRED("Expired");
  
  private final String description;
  
  ExpirationStatus(String preDescription) {
    description = preDescription;
  }
  
  public String description() {
    return description;
  }
  
  /**
   * Get the expiration status by index.
   * @param description The index.
   * @return ExpirationStatus.
   */
  public static ExpirationStatus getExpirationStatus(String description) {
    if (description.equalsIgnoreCase("On-going")) {
      return ON_GOING;
    } else if (description.equalsIgnoreCase("Soon")) {
      return SOON;
    } else if (description.equalsIgnoreCase("Expired")) {
      return EXPIRED;
    } else {
      return null;
    }
  }
}
