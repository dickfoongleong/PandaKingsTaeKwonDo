package com.pandakings.utilities.enumclass;

public enum PaymentStatus {
  COMPLETED("Completed"),
  INCOMPLETE("Incomplete");
  
  private final String description;
  
  PaymentStatus(String preDescription) {
    description = preDescription;
  }
  
  public String description() {
    return description;
  }
  
  /**
   * Get the payment status by index.
   * @param description The index.
   * @return PaymentStatus.
   */
  public static PaymentStatus getPaymentStatus(String description) {
    if (description.equalsIgnoreCase("Completed")) {
      return COMPLETED;
    } else if (description.equalsIgnoreCase("Incomplete")) {
      return INCOMPLETE;
    } else {
      return null;
    }
  }
}
