package com.noovop.noovodb.sql;

import java.util.Date;


/**
 * This class defines SQL methods.
 * 
 * @author Noovop
 * @version 1.0
 */
public class SQLQueryBuilder {

  /**
   * Completes the given SQL query
   * 
   * @param query the SQL query
   * @param params the list of query parameters value
   * @return the SQL Statement as a String
   */
  public static String completeQuery(String query, Object... params) {

    String newQuery = query.replace('?', '#');

    for (Object object : params) {

      if (object instanceof Integer || object instanceof Long || object instanceof Double) {

        newQuery = newQuery.replaceFirst("#", String.valueOf(object));

      } else if (object instanceof String) {

        newQuery = newQuery.replaceFirst("#", "'" + object + "'");

      } else if (object instanceof Date) {

        Date date = (Date) object;
        newQuery = newQuery.replaceFirst("#", "'" + date + "'");

      } else if (object instanceof Boolean) {

        Boolean bool = (Boolean) object;
        newQuery = newQuery.replaceFirst("#", bool.toString());
      }
    }

    return newQuery;
  }
}
