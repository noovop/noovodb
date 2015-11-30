package com.noovop.noovodb.service;

import java.sql.ResultSet;

import com.noovop.noovodb.exception.ModelException;

/**
 * Implement this interface to parse the result contained in the object ResulSet a specific object.
 * 
 * @author Noovop
 * @version 1.0
 */ 
public interface ResultSetService<T> {

  /**
   * Parses the given result set an creates a new object of instance of the generic class T.
   * 
   * @param resultSet the result set to parse
   * @return
   * @throws ModelException
   */
  T getResult(ResultSet resultSet) throws ModelException;
}
