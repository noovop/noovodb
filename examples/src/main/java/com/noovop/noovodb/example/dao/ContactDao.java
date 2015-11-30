package com.noovop.noovodb.example.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.noovop.noovodb.example.bean.Contact;
import com.noovop.noovodb.example.constant.SQLContant;
import com.noovop.noovodb.exception.ModelException;
import com.noovop.noovodb.sql.SQLQueryBuilder;

public class ContactDao extends AbstractDao<Contact>{

  /**
   * The implentation for contact bean. 
   * The aim of the method is to define how each bean will be getted wired from the database.
   * 
   */
  @Override
  public Contact getResult(ResultSet resultSet) throws ModelException {
    Contact contact = new Contact();

    try {
      contact.setContactId(resultSet.getLong(SQLContant.TABLE_CONTACT.COLUMN_ID));
      contact.setFirstName(resultSet.getString(SQLContant.TABLE_CONTACT.COLUMN_FIRSTNAME));
      contact.setLastName(resultSet.getString(SQLContant.TABLE_CONTACT.COLUMN_LASTNAME));
      contact.setPhone(resultSet.getString(SQLContant.TABLE_CONTACT.COLUMN_PHONE));
      contact.setEmail(resultSet.getString(SQLContant.TABLE_CONTACT.COLUMN_EMAIL));

    }catch (SQLException sqle){
      throw new ModelException(sqle.getMessage(), sqle);
    }
    return contact;
  }

  /**
   * Builder the Insert Query of bean creation to datatabas
   */
  @Override
  protected String buildCreateQuery(Contact objectBean) throws ModelException {
    objectBean.setContactId(Long.parseLong(String.valueOf(this.getSequenceNextValue(SQLContant.TABLE_CONTACT.SEQUENCE_NAME).toString())));
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_INSERT, objectBean.getContactId(), objectBean.getFirstName(), objectBean.getLastName(), objectBean.getPhone(), objectBean.getEmail());
  }

  @Override
  protected String buildReadQuery(Contact objectBean) throws ModelException {
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_SELECT, objectBean.getContactId());
  }

  @Override
  protected String buildReadAllQuery() throws ModelException {
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_SELECT_ALL);
  }

  @Override
  protected String buildUpdateQuery(Contact objectBean) throws ModelException {
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_UPDATE, objectBean.getFirstName(), objectBean.getLastName(), objectBean.getPhone(), objectBean.getEmail(), objectBean.getContactId());
  }

  @Override
  protected String buildDeleteQuery(Contact objectBean) throws ModelException {
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_DELETE, objectBean.getContactId());
  }

}
