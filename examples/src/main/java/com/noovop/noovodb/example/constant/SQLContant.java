package com.noovop.noovodb.example.constant;

/** 
 * All SQL Constants are in this interface
 * @author Noovop
 * @version 1.0
 */
public interface SQLContant {

  /**
   * The contacts for the contact table
   * @author Noovop
   * @version 1.0
   */
  public interface TABLE_CONTACT {

    public final static String TABLE_NAME           = "CONTACT";
    public final static String SEQUENCE_NAME        = "CONTACT_SEQ";
    public final static String COLUMN_ID            = "ID";
    public final static String COLUMN_FIRSTNAME     = "FIRSTNAME";
    public final static String COLUMN_LASTNAME      = "LASTNAME";
    public final static String COLUMN_PHONE         = "PHONE";
    public final static String COLUMN_EMAIL         = "EMAIL";
    public final static String SQL_STATEMENT_INSERT     = "INSERT INTO CONTACT VALUES(?, ?, ?, ?, ?)";
    public final static String SQL_STATEMENT_SELECT     = "SELECT * FROM CONTACT WHERE ID = ?";
    public final static String SQL_STATEMENT_SELECT_ALL = "SELECT * FROM CONTACT";
    public final static String SQL_STATEMENT_UPDATE     = "UPDATE CONTACT SET FIRSTNAME = ?, LASTNAME = PHONE, EMAIL = ? WHERE ID = ?";
    public final static String SQL_STATEMENT_DELETE     = "DELETE FROM CONTACT WHERE ID = ?";
  }
}
