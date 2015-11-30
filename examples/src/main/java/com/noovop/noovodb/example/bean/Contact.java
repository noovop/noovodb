package com.noovop.noovodb.example.bean;

/**
 * @author Noovop
 * @version 1.0
 */
public class Contact {

  /**
   * The property <strong>contactId</strong> of type {@link Long}
   */
  private Long      contactId;

  /**
   * The property <strong>firstName</strong> of type {@link String}
   */
  private String    firstName;

  /**
   * The property <strong>lastName</strong> of type {@link String}
   */
  private String    lastName;

  /**
   * The property <strong>phone</strong> of type {@link String}
   */
  private String    phone;

  /**
   * The property <strong>email</strong> of type {@link String}
   */
  private String    email;

  /**
   * Gets the value of the id property.
   * 
   * @return possible object is {@link Long}
   */
  public Long getContactId() {
    return contactId;
  }

  /**
   * Sets the value of the id property.
   * 
   * @param id the id property to set
   */
  public void setContactId(final Long contactId) {
    this.contactId = contactId;
  }

  /**
   * Gets the value of the firstName property.
   * 
   * @return possible object is {@link String}
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * Sets the value of the firstName property.
   * 
   * @param firstName the firstName property to set
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * Gets the value of the lastName property.
   * 
   * @return possible object is {@link String}
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Sets the value of the lastName property.
   * 
   * @param lastName the lastName property to set
   */
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * Gets the value of the phone property.
   * 
   * @return possible object is {@link String}
   */
  public String getPhone() {
    return phone;
  }

  /**
   * Sets the value of the phone property.
   * 
   * @param phone the phone property to set
   */
  public void setPhone(final String phone) {
    this.phone = phone;
  }

  /**
   * Gets the value of the email property.
   * 
   * @return possible object is {@link String}
   */
  public String getEmail() {
    return email;
  }

  /**
   * Sets the value of the email property.
   * 
   * @param email the email property to set
   */
  public void setEmail(final String email) {
    this.email = email;
  }

}
