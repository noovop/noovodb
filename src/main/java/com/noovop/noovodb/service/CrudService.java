package com.noovop.noovodb.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.noovop.noovodb.exception.ModelException;
import com.noovop.noovodb.manager.DbManager;

/**
 * Implement this interface to manipulate data of a specific object. It defines CRUD
 * (Create/Update/Update/Delete) operations.
 * 
 * @author Noovop
 * @version 1.0
 */
public abstract class CrudService<T> implements ResultSetService<T> {

  /**
   * The label for an insert connection
   */
  final static String STRING_LABEL_CREATE = "labelCreate";

  /**
   * The label for a read connection
   */
  final static String STRING_LABEL_READ = "labelRead";

  /**
   * The label for a read all connection
   */
  final static String STRING_LABEL_READ_ALL = "labelReadAll";

  /**
   * The label for an update connection
   */
  final static String STRING_LABEL_UPDATE = "labelUpdate";

  /**
   * The label for a delete connection
   */
  final static String STRING_LABEL_DELETE = "labelDelete";

  /**
   * The label for getting a sequence next value connection
   */
  final static String STRING_LABEL_SEQUENCE = "labelSequence";

  /**
   * SQL connection object
   */
  protected Connection connection;

  /**
   * Initialization : opens a new connection to a database
   * 
   * @param label the connection label
   */
  protected abstract void setUp(String label) throws ModelException;

  /**
   * Builds a create query
   * 
   * @param objectBean
   * @return
   * @throws ModelException
   */
  protected abstract String buildCreateQuery(T objectBean) throws ModelException;

  /**
   * Builds a read query
   * 
   * @param objectBean
   * @return
   */
  protected abstract String buildReadQuery(T objectBean) throws ModelException;

  /**
   * Builds a read all query
   * 
   * @return
   */
  protected abstract String buildReadAllQuery() throws ModelException;

  /**
   * Builds an update query
   * 
   * @param objectBean
   * @return
   */
  protected abstract String buildUpdateQuery(T objectBean) throws ModelException;

  /**
   * Builds a delete query
   * 
   * @param objectBean
   * @return
   */
  protected abstract String buildDeleteQuery(T objectBean) throws ModelException;

  /**
   * Gets the sequence next value.
   * 
   * @param sequenceName the sequence name.
   * @return
   * @throws ModelException
   */
  protected Object getSequenceNextValue(String sequenceName) throws ModelException {

    Object result = null;

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_SEQUENCE);

    /** - 2. Executes the statement **/
    result = DbManager.getSequenceValue(connection, sequenceName);

    /** - 3. Closes connection **/
    // DbManager.closeConnection(connection, STRING_LABEL_SEQUENCE);

    return result;
  }

  /**
   * Executes the given selection SQL query and parse the result into list
   * 
   * @param query the query to execute
   * @return
   * @throws ModelException
   */
  protected List<T> select(String query) throws ModelException {

    List<T> results = new ArrayList<T>();

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_READ_ALL);

    /** - 2. Executes the statement **/
    ResultSet resultSet = DbManager.select(connection, query);

    if (resultSet != null) {

      try {
        while (resultSet.next()) {
          results.add(getResult(resultSet));
        }
      } catch (SQLException e) {
        throw new ModelException(e);
      }
    }

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_READ_ALL);

    return results;
  }

  /**
   * Executes the given update SQL query.
   * 
   * @param query the SQL update query to execute
   * @throws ModelException
   */
  protected void update(String query) throws ModelException {

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_UPDATE);

    /** - 2. Executes the statement **/
    DbManager.update(connection, query);

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_UPDATE);
  }

  /**
   * Inserts into a table of a database.
   * 
   * @param objectBean
   * @throws ModelException
   */
  public Object create(T objectBean) throws ModelException {

    Object result = null;

    /** - 1. Open a connection to the database. **/
    setUp(STRING_LABEL_CREATE);

    /** - 2. Execute the statement **/
    String stringQuery = buildCreateQuery(objectBean);
    result = DbManager.updateAndReturnsGeneratedKey(connection, stringQuery);

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_CREATE);

    return result;
  }

  /**
   * Reads a row from a table of a database.
   * 
   * @param objectBean
   * @return
   * @throws ModelException
   */
  public T read(T objectBean) throws ModelException {

    T result = null;

    /** - 1. Open a connection to the database. **/
    setUp(STRING_LABEL_READ);

    /** - 2. Execute the statement **/
    String stringQuery = buildReadQuery(objectBean);
    ResultSet resultSet = DbManager.select(connection, stringQuery);

    try {

      if (resultSet != null && resultSet.next()) {
        result = getResult(resultSet);
      }

    } catch (SQLException e) {
      throw new ModelException(e);
    }

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_READ);

    return result;
  }

  /**
   * Reads a list of row from a table of a database.
   * 
   * @return
   * @throws ModelException
   */
  public List<T> readAll() throws ModelException {

    List<T> results = new ArrayList<T>();

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_READ_ALL);

    /** - 2. Executes the statement **/
    String stringQuery = buildReadAllQuery();
    ResultSet resultSet = DbManager.select(connection, stringQuery);

    if (resultSet != null) {

      try {
        while (resultSet.next()) {
          results.add(getResult(resultSet));
        }
      } catch (SQLException e) {
        throw new ModelException(e);
      }

    }

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_READ_ALL);

    return results;
  }

  /**
   * Updates a row from a table of a database.
   * 
   * @param objectBean
   * @throws ModelException
   */
  public void update(T objectBean) throws ModelException {

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_UPDATE);

    /** - 2. Executes the statement **/
    String stringQuery = buildUpdateQuery(objectBean);
    DbManager.update(connection, stringQuery);

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_UPDATE);

  }

  /**
   * Deletes a row from a table of a database.
   * 
   * @param objectBean
   * @throws ModelException
   */
  public void delete(T objectBean) throws ModelException {

    /** - 1. Opens a connection to the database. **/
    setUp(STRING_LABEL_DELETE);

    /** - 2. Executes the statement **/
    String stringQuery = buildDeleteQuery(objectBean);
    DbManager.update(connection, stringQuery);

    /** - 3. Closes connection **/
    DbManager.closeConnection(connection, STRING_LABEL_DELETE);

  }
}
