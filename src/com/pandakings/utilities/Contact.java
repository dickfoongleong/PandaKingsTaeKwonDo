package com.pandakings.utilities;

import com.pandakings.utilities.enumclass.ParentRelationship;

/**
 * Contact instance class.
 * @author Dickfoong
 *
 */
public class Contact {
  /**
   * ID of the student whom this contact belongs.
   */
  private int studentId;
  
  /**
   * Name of the contact.
   */
  private String name;
  
  /**
   * Phone number of the contact.
   */
  private String phone;
  
  /**
   * Relationship of this contact to the student. 
   */
  private ParentRelationship relationship;
  
  /**
   * Other specified relationship of this contact to the student.
   */
  private String otherRelationship;
 
  /**
   * Empty instance constructor.
   */
  public Contact() {
    
  }
  
  /**
   * Instance constructor with known relationship.
   * @param studentId
   *    Id of the student.
   * @param name Name of this contact.
   * @param phone Phone of this contact.
   * @param relationship Relationship of this contact.
   */
  public Contact(int studentId, String name, String phone, ParentRelationship relationship) {
    this.studentId = studentId;
    this.name = name;
    this.phone = phone;
    this.relationship = relationship;
    this.otherRelationship = "";
  }
  
  /**
   * Instance constructor with other relationship.
   * @param studentId
   *    Id of the student.
   * @param name Name of this contact.
   * @param phone Phone of this contact.
   * @param relationship Relationship of this contact.
   * @param otherRelationship Other specified relationship.
   */
  public Contact(int studentId, String name, String phone, ParentRelationship relationship,
      String otherRelationship) {
    this.studentId = studentId;
    this.name = name;
    this.phone = phone;
    this.relationship = relationship;
    this.otherRelationship = otherRelationship;
  }

  /**
   * Set the new phone number for this contact.
   * @param newPhone New phone number.
   */
  public void setPhone(String newPhone) {
    this.phone = newPhone;
  }
  
  /**
   * Get the id of the student.
   * @return studentId.
   */
  public int getStudentId() {
    return studentId;
  }
  
  /**
   * Get the name of this contact.
   * @return name.
   */
  public String getName() {
    return name;
  }
  
  /**
   * Get the phone number of this contact.
   * @return phone.
   */
  public String getPhone() {
    return phone;
  }
  
  /**
   * Get the relationship of this contact.
   * @return relationship.
   */
  public ParentRelationship getRelationship() {
    return relationship;
  }
  
  /**
   * Get the specified other relationship of this contact.
   * @return otherRelationship.
   */
  public String getOtherRelationship() {
    return otherRelationship;
  }
  
  /**
   * Detail information about this contact to the student.
   */
  public String toString() {
    if (relationship != ParentRelationship.OTHER) {
      return studentId + "," + name + "," + phone + "," + relationship.description();
    } else {
      return studentId + "," + name + "," + phone + "," 
          + relationship.description() + "," + otherRelationship;
    }
    
  }
}
