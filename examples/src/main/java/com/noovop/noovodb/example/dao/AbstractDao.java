package com.noovop.noovodb.example.dao;

import com.noovop.noovodb.example.constant.Constant;
import com.noovop.noovodb.exception.ModelException;
import com.noovop.noovodb.manager.DbManager;
import com.noovop.noovodb.service.CrudService;

/**
 * This abstract class implements the setUp method to connect to the database.
 * 
 * @author Noovop
 * @version 1.0
 */
public abstract class AbstractDao<T> extends CrudService<T> {

  /* This class it responsible of initializing database connection.
   * The type of connection choosen here is a JDBC Driver
   * @see com.noovop.noovodb.service.CrudService#setUp(java.lang.String)
   */
  @Override
  protected void setUp(final String label) throws ModelException {
    this.connection = DbManager.openConnection(Constant.STRING_DB_DRIVER, Constant.DATABASE_URL, label);
  }
}
