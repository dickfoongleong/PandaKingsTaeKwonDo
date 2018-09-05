package com.pandakings.utilities;

import com.pandakings.utilities.enumclass.Belt;
import com.pandakings.utilities.enumclass.Club;
import com.pandakings.utilities.enumclass.ExpirationStatus;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Student instance class.
 * 
 * @author Dickfoong
 *
 */
public class Student {
  /**
   * Student's id.
   */
  private int id;
 
  /**
   * Student's name.
   */
  private String name;
  
  /**
   * Student's current belt level.
   */
  private Belt currentBelt;
  
  /**
   * Student's parents.
   */
  private List<Contact> contactList;
  
  /**
   * Student's club type.
   */
  private Club club;

  /**
   * Student's payment records.
   */
  private List<Payment> paymentRecord;

  /**
   * Student's expired status.
   */
  private ExpirationStatus expiredState;
  
  /**
   * Student's birthday.
   */
  private Date birthday;
  
  /**
   * Student's start date.
   */
  private Date startDate;
  
  /**
   * Student's expired date.
   */
  private Date expiredDate;
  
  /**
   * Empty instance constructor.
   */
  public Student() {
    
  }
  
  /**
   * Instance constructor with full informations.
   * 
   * @param id
   *    Student's id.
   * @param name 
   *    Student's name.
   * @param currentBelt 
   *    Student's current belt level.
   * @param contactList 
   *    Student's contact list.
   * @param club
   *    Student's club type.
   * @param paymentRecord
   *    Record of every payment made by this student.
   * @param expiredState
   *    Student's expired status.
   * @param birthday
   *    Student's birthday.
   * @param startDate
   *    Student's start date.
   * @param expiredDate
   *    Student's expired date.
   */
  public Student(int id, String name,Belt currentBelt, List<Contact> contactList, 
      Club club, List<Payment> paymentRecord, ExpirationStatus expiredState, 
      Date birthday, Date startDate, Date expiredDate) {
    this.id = id;
    this.name = name;
    this.currentBelt = currentBelt;
    this.contactList = contactList;
    this.club = club;
    this.paymentRecord = paymentRecord;
    this.expiredState = expiredState;
    this.birthday = birthday;
    this.startDate = startDate;
    this.expiredDate = expiredDate;
  }
  
  /**
   * Instance constructor with default belt level (White belt).
   * @param id
   *    Student's id.
   * @param name
   *    Student's name.
   * @param contactList
   *    Student's contact list.
   * @param club
   *    Student's club type.
   * @param paymentRecord
   *    Record of every payment made by this student.
   * @param expiredState
   *    Student's expired status.
   * @param birthday
   *    Student's birthday.
   * @param startDate
   *    Student's start date.
   * @param expiredDate
   *    Student's expired date.
   */
  public Student(int id, String name, List<Contact> contactList,
      Club club, List<Payment> paymentRecord, ExpirationStatus expiredState,
      Date birthday, Date startDate, Date expiredDate) {
    this.id = id;
    this.name = name;
    this.currentBelt = Belt.WHITE;
    this.contactList = contactList;
    this.club = club;
    this.paymentRecord = paymentRecord;
    this.expiredState = expiredState;
    this.birthday = birthday;
    this.startDate = startDate;
    this.expiredDate = expiredDate;
  }
  
  /**
   * Instance constructor with no contact list.
   * @param id
   *    Student's id.
   * @param name
   *    Student's name.
   * @param currentBelt
   *    Student's currentBelt.
   * @param club
   *    Student's club type.
   * @param paymentRecord
   *    Record of every payment made by this student.
   * @param expiredState
   *    Student's expired status.
   * @param birthday
   *    Student's birthday
   * @param startDate
   *    Student's start date.
   * @param expiredDate
   *    Student's expired date.
   */
  public Student(int id, String name, Belt currentBelt, Club club,
      List<Payment> paymentRecord, ExpirationStatus expiredState,
      Date birthday, Date startDate, Date expiredDate) {
    this.id = id;
    this.name = name;
    this.currentBelt = currentBelt;
    this.contactList = new ArrayList<Contact>();
    this.club = club;
    this.paymentRecord = paymentRecord;
    this.expiredState = expiredState;
    this.birthday = birthday;
    this.startDate = startDate;
    this.expiredDate = expiredDate;
  }
  
  /**
   * Add a new contact to the contact list.
   * @param newContact The new contact.
   */
  public void addContact(Contact newContact) {
    contactList.add(newContact);
  }
  
  /**
   * Remove a contact from the contact list.
   * @param contactToDelete The contact to delete.
   */
  public void removeContact(Contact contactToDelete) {
    contactList.remove(contactToDelete);
  }
  
  /**
   * Get the student's id.
   */
  public int getId() {
    return id;
  }
  
  /**
   * Get the student's name.
   * @return name.
   */
  public String getName() {
    return name;
  }

  /**
   * Get the student's current belt level.
   * @return currentBelt.
   */
  public Belt getCurrentBelt() {
    return currentBelt;
  }

  /**
   * Get the student's contact list.
   * @return contactList.
   */
  public List<Contact> getContactList() {
    return contactList;
  }
  
  /**
   * Get the student's club type.
   * @return club.
   */
  public Club getClub() {
    return club;
  }

  /**
   * Get the payment record of this student.
   * @return paymentRecord.
   */
  public List<Payment> getPaymentRecord() {
    return paymentRecord;
  }

  /**
   * Get the student's birthday.
   * @return birthday.
   */
  public Date getBirthday() {
    return birthday;
  }
  
  /**
   * Get the student's expired status.
   * @return expiredState.
   */
  public ExpirationStatus getExpiredState() {
    return expiredState;
  }

  /**
   * Get the student's start date.
   * @return startDate.
   */
  public Date getStartDate() {
    return startDate;
  }

  /**
   * Get the student's expired date.
   * @return expiredDate.
   */
  public Date getExpiredDate() {
    return expiredDate;
  }

  /**
   * Set the student's current belt level.
   * @param newBelt The new belt level.
   */
  public void setCurrentBelt(Belt newBelt) {
    this.currentBelt = newBelt;
  }

  /**
   * Set the student's club type.
   * @param club of the student.
   */
  public void setClub(Club club) {
    this.club = club;
  }
  
  /**
   * Add the new payment.
   * @param newPayment New payment.
   */
  public void addPayment(Payment newPayment) {
    paymentRecord.add(newPayment);
  }

  /**
   * Set the student's expired status.
   * Private method and auto update, no calls needed.
   */
  private void setExpiredState() {
    Date today = Calendar.getInstance().getTime();
    long timeDiff = expiredDate.getTime() - today.getTime();
    
    if (timeDiff <= 0) {
      expiredState = ExpirationStatus.EXPIRED;
    } else {
      long dayDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
      if (dayDiff <= 14) {
        expiredState = ExpirationStatus.SOON;
      } else {
        expiredState = ExpirationStatus.ON_GOING;
      }
    }
  }

  /**
   * Set the student's new expired date.
   * @param expiredDate The new expired date.
   */
  public void setExpiredDate(Date expiredDate) {
    this.expiredDate = expiredDate;
    setExpiredState();
  }
  
  public String toString() {
    return id + "," + name + "," + currentBelt + "," + club + ","
        + expiredState + "," + birthday + "," + startDate + "," + expiredDate;
  }
}
