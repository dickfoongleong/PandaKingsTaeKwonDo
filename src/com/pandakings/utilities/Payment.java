package com.pandakings.utilities;

import com.pandakings.utilities.enumclass.PaymentStatus;

/**
 * Payment instance class.
 * @author Dickfoong
 *
 */
public class Payment {
  /**
   * Student whom this payment belongs.
   */
  private int studentId;
  
  /**
   * Total amount to pay.
   */
  private int amountTotal;
  
  /**
   * Left amount to pay.
   */
  private int amountLeft;
  
  /**
   * Message of this payment.
   */
  private String message;
  
  /**
   * Status of this payment.
   */
  private PaymentStatus status;
  
  /**
   * Empty constructor.
   */
  public Payment() {
    
  }
  
  /**
   * Instance constructor with full details given. 
   * @param studentId
   *    Id of the student.
   * @param amountTotal
   *    Total amount of this payment.
   * @param amountLeft
   *    Amount left of this payment.
   * @param message
   *    Message of this payment.
   * @param status
   *    Status if this payment.
   */
  public Payment(int studentId, int amountTotal, int amountLeft, 
      String message, PaymentStatus status) {
    this.studentId = studentId;
    this.amountTotal = amountTotal;
    this.amountLeft = amountLeft;
    this.message = message;
    this.status = status;
  }
  
  /**
   * Get the id of the student.
   * @return studentId.
   */
  public int getStudetnId() {
    return studentId;
  }
  
  /**
   * Get the total amount of this payment.
   * @return amountTotal;
   */
  public int getAmountTotal() {
    return amountTotal;
  }
  
  /**
   * Get the amount left for this payment.
   * @return amountLeft.
   */
  public int getAmountLeft() {
    return amountLeft;
  }
  
  /**
   * Get the message of this payment.
   * @return message.
   */
  public String getMessage() {
    return message;
  }
  
  /**
   * Get the status of this payment.
   * @return status.
   */
  public PaymentStatus getPaymentStatus() {
    return status;
  }
  
  /**
   * Change the amount left.
   * @param amount New amount left of the payment.
   */
  public void setAmountLeft(int amount) {
    this.amountLeft = amount;
  }
  
  /**
   * Change the message.
   * @param message New message of the payment
   */
  public void setMessage(String message) {
    this.message = message;
  }
  
  /**
   * Change the status.
   * @param status New status of the payment.
   */
  public void setPaymentStatus(PaymentStatus status) {
    this. status = status;
  }
  
  /**
   * Get the full information about this payment.
   */
  public String toString() {
    return studentId + "," + amountTotal + "," + amountLeft + ","
        + message + "," + status.description();
  }
}
