package com.noovop.noovodb.manager;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.DataSource;

import com.noovop.noovodb.exception.ModelException;

/**
 * This class contains all the utility functions database
 * connection/disconnection and for data manipulation.
 * 
 * @author Noovop
 * @version 1.0
 */
public class DbManager {

  final static String ERROR_DB_DRIVER_NOT_FOUND = "The specified driver is not found";
  final static String ERROR_DB_CONNECTION_FAILED = "Connection failed! Wrong username/password";
  final static String ERROR_DB_DISCONNECTION_FAILED = "An error occured while disconnecting";
  final static String ERROR_DB_NO_CONNECTION = "Not open connection was found";
  final static String ERROR_SQL_UPDATE_FAILED = "Update failed";
  final static String ERROR_SQL_SELECTION_FAILED = "Selection failed";
  final static String ERROR_SQL = "A SQL error occured";

  final static String SQL_QUERY_SELECT_NEXT_SEQUENCE_VALUE = "SELECT NEXTVAL('%s')";

  /**
   * Shared database connection
   */
  private static Connection dbConnection = null;

  /**
   * If true the transaction is rolled back, otherwise it is committed
   */
  private static boolean rollBack;

  /**
   * Completes the SQL query containing in to the preparedStatement.
   * 
   * @param preparedStatement
   *            the statement containing the query to execute.
   * @param params
   *            the list of the query parameters values.
   * @throws SQLException
   */
  private static void completeQuery(PreparedStatement preparedStatement, Object... params) throws SQLException {

    int index = 1;
    for (Object object : params) {

      /** Integer parameter value **/
      if (object instanceof Integer) {
        preparedStatement.setInt(index, (int) object);
      }

      /** String parameter value **/
      else if (object instanceof String) {
        preparedStatement.setString(index, (String) object);
      }

      /** Double parameter value **/
      else if (object instanceof Double) {
        preparedStatement.setDouble(index, (double) object);
      }

      /** Long parameter value **/
      else if (object instanceof Long) {
        preparedStatement.setDouble(index, (long) object);
      }

      /** Date parameter value **/
      else if (object instanceof Date) {
        Date date = (Date) object;
        preparedStatement.setDate(index, new java.sql.Date(date.getTime()));
      }

      /** Object parameter value otherwise (UUID, etc.) **/
      else {
        preparedStatement.setObject(index, object);
      }

      index++;
    }
  }

  /**
   * Gets the roll back value
   * 
   * @return the rollBack
   */
  public static boolean isRollBack() {
    return rollBack;
  }

  /**
   * Sets the roll back value.
   * 
   * @param rollBack
   *            the rollBack to set
   */
  public static void setRollBack(boolean rollBack) {
    DbManager.rollBack = rollBack;
  }

  /**
   * Opens a new connection to a database through the given data source.
   * 
   * @param dataSource
   *            the database data source
   * @param label
   *            the connection name
   * @return the {@link Connection}
   * @throws ModelException
   */
  public static Connection openConnection(DataSource dataSource, String label) throws ModelException {

    try {
      if (dbConnection == null || dbConnection.isClosed()) {
        dbConnection = dataSource.getConnection();
      }
    } catch (SQLException e) {
      throw new ModelException(ERROR_DB_CONNECTION_FAILED, e);
    }

    return dbConnection;
  }

  /**
   * Opens a new connection to a database.
   * 
   * @param driverName
   *            the SGBD driver name
   * @param url
   *            the database URL
   * @param label
   *            the connection name
   * @return
   * @throws ModelException
   */
  public static Connection openConnection(String driverName, String url, String label) throws ModelException {

    try {

      DriverManager.registerDriver((Driver) Class.forName(driverName).newInstance());

      if (dbConnection == null || dbConnection.isClosed()) {
        dbConnection = DriverManager.getConnection(url);
      }

    } catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
      throw new ModelException(ERROR_DB_CONNECTION_FAILED, e);
    }

    return dbConnection;
  }

  /**
   * Executes an update query
   * 
   * @param connection
   *            the database opened connection
   * @param query
   *            the update query to execute
   * @param params
   *            the list of the query parameters values.
   * @throws ModelException
   */
  public static void update(Connection connection, String query, Object... params) throws ModelException {

    /** Connection can not be null **/
    if (connection == null) {
      throw new ModelException(ERROR_DB_NO_CONNECTION);
    }

    try {

      /**
       * Adds the query into the preparedStatement and complete the
       * parameters.
       **/
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      completeQuery(preparedStatement, params);

      /** Executes the query **/
      preparedStatement.executeUpdate();

    } catch (SQLException e) {
      throw new ModelException(ERROR_SQL, e);
    }
  }

  /**
   * Executes an update query and returns the auto generated key.
   * 
   * @param connection
   *            the database opened connection
   * @param query
   *            the update query to execute
   * @param params
   *            the list of the query parameters values.
   * @return Object
   * @throws ModelException
   */
  public static Object updateAndReturnsGeneratedKey(Connection connection, String query, Object... params)
      throws ModelException {

    /** Contains the generated key, returned after update completed. **/
    Object result = null;

    /** Connection can not be null **/
    if (connection == null) {
      throw new ModelException(ERROR_DB_NO_CONNECTION);
    }

    try {

      /**
       * Adds the query into the preparedStatement and complete the
       * parameters.
       **/
      PreparedStatement preparedStatement = connection.prepareStatement(query,
          PreparedStatement.RETURN_GENERATED_KEYS);
      completeQuery(preparedStatement, params);

      /** Executes the query **/
      preparedStatement.executeUpdate();

      /** Gets the generated key. **/
      if (preparedStatement.getGeneratedKeys().next()) {
        result = preparedStatement.getGeneratedKeys().getObject(1);
      }

    } catch (SQLException e) {
      throw new ModelException(ERROR_SQL, e);
    }
    return result;
  }

  /**
   * Executes a select query and returns a ResulSet.
   * 
   * @param connection
   *            the database opened connection
   * @param query
   *            the update query to execute
   * @param params
   *            the list of the query parameters values.
   * @return ResultSet
   * @throws ModelException
   */
  public static ResultSet select(Connection connection, String query, Object... params) throws ModelException {

    /** Contains the result of the selection. **/
    ResultSet result = null;

    /** Connection can not be null **/
    if (connection == null) {
      throw new ModelException(ERROR_DB_NO_CONNECTION);
    }

    try {

      /**
       * Adds the query into the preparedStatement and complete the
       * parameters.
       **/
      PreparedStatement preparedStatement = connection.prepareStatement(query);
      completeQuery(preparedStatement, params);

      /** Executes the query **/
      result = preparedStatement.executeQuery();

    } catch (SQLException e) {
      throw new ModelException(ERROR_SQL, e);
    }

    return result;
  }

  /**
   * Gets the given sequence next value
   * 
   * @param sequenceName
   *            the sequence name
   * @return Object
   * @throws ModelException
   */
  public static Object getSequenceValue(Connection connection, String sequenceName) throws ModelException {

    /** Contains the next value of the sequence **/
    Object result = null;

    /** Executes the statement **/
    String query = String.format(SQL_QUERY_SELECT_NEXT_SEQUENCE_VALUE, sequenceName);
    ResultSet resultSet = select(connection, query);

    try {
      if (resultSet != null && resultSet.next()) {
        result = resultSet.getObject(1);
      }
    } catch (SQLException e) {
      throw new ModelException(ERROR_SQL, e);
    }
    return result;
  }

  /**
   * Closes an opened connection.
   * 
   * @param connection
   *            the connection to close
   * @param label
   *            the connection label
   * @throws ModelException
   */
  public static void closeConnection(Connection connection, String label) throws ModelException {

    try {
      if (connection != null) {
        if (isRollBack()) {
          connection.rollback();
        }
        connection.close();
      }
    } catch (SQLException e) {
      throw new ModelException(e);
    }
  }

  /**
   * Closes an opened connection and statement.
   * 
   * @param connection
   *            the connection to close
   * @param label
   *            the connection label
   * @param statement
   *            the statement to close
   * @throws ModelException
   */
  public static void closeConnection(Connection connection, String label, PreparedStatement statement)
      throws ModelException {

    try {

      /** Closing of the statement **/
      if (statement != null) {
        statement.close();
      }

      /** Closing of the connection **/
      if (connection != null) {
        if (isRollBack()) {
          connection.rollback();
        }
        connection.close();
      }

    } catch (SQLException e) {
      throw new ModelException(ERROR_DB_DISCONNECTION_FAILED);
    }
  }

  /**
   * Closes an opened connection and resultSet.
   * 
   * @param connection
   *            the connection to close
   * @param label
   *            the connection label
   * @param resultSet
   *            the resultSet to close
   * @throws ModelException
   */
  public static void closeConnection(Connection connection, String label, ResultSet resultSet) throws ModelException {

    try {

      /** Closing of the resultSet **/
      if (resultSet != null) {
        resultSet.close();
      }

      /** Closing of the connection **/
      if (connection != null) {
        if (isRollBack()) {
          connection.rollback();
        }
        connection.close();
      }

    } catch (SQLException e) {
      throw new ModelException(ERROR_DB_DISCONNECTION_FAILED);
    }
  }

  /**
   * Closes an opened connection and resultSet.
   * 
   * @param connection
   *            the connection to close
   * @param label
   *            the connection label
   * @param statement
   *            the statement to close
   * @param resultSet
   *            the resultSet to close
   * @throws ModelException
   */
  public static void closeConnection(Connection connection, String label, PreparedStatement statement,
      ResultSet resultSet) throws ModelException {

    try {

      /** Closing of the resultSet **/
      if (resultSet != null) {
        resultSet.close();
      }

      /** Closing of the statement **/
      if (statement != null) {
        statement.close();
      }

      /** Closing of the connection **/
      if (connection != null) {
        if (isRollBack()) {
          connection.rollback();
        }
        connection.close();
      }

    } catch (SQLException e) {
      throw new ModelException(ERROR_DB_DISCONNECTION_FAILED);
    }
  }
}
