package com.noovop.noovodb.example;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.noovop.noovodb.example.dao.AbstractDao;
import com.noovop.noovodb.exception.ModelException;

/**
 * @author Noovop
 * @version 1.0
 */
public abstract class AbstractTest<T extends AbstractDao<Y>, Y> {

  final static String ENV_PROPERTY_FILE_GLOBAL = "test.global.properties";

  final static String ENV_PROPERTY_FILE_BASIC_LOG4J = "test.log4j.properties";

  /**
   * The list of bean to create
   */
  protected List<Y> beanList = new ArrayList<Y>();

  /**
   * The corresponding DAO to the current bean
   */
  protected T daoBean = null;

  /**
   * Loads properties files
   * 
   * @throws Exception
   */
  public void setUp() throws Exception {
    //
  }

  /**
   * Tests the create method
   */
  public void testCreate() {
    for (final Y bean : this.beanList) {
      try {
        this.daoBean.create(bean);
      } catch (final ModelException e) {
        e.printStackTrace();
        Assert.fail(e.getMessage());
        break;
      }
    }
  }

  /**
   * Tests the read method
   */
  public void testRead() {
    try {
      this.beanList = this.daoBean.readAll();
      if (this.beanList != null && this.beanList.size() != 0) {
        this.daoBean.read(this.beanList.get(0));
      }
    } catch (final ModelException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Tests the readAll method
   */
  public void testReadAll() {
    try {
      final List<Y> lBean = this.daoBean.readAll();
      Assert.assertNotNull(lBean);
    } catch (final ModelException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Tests the update method
   */
  public void testUpdate() {
    try {
      this.beanList = this.daoBean.readAll();
      for (final Y bean : this.beanList) {
        this.daoBean.update(bean);
      }
    } catch (final ModelException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }

  /**
   * Tests the delete method
   */
  public void testDelete() {

    try {
      this.beanList = this.daoBean.readAll();
      for (final Y bean : this.beanList) {
        this.daoBean.delete(bean);
      }
    } catch (final ModelException e) {
      e.printStackTrace();
      Assert.fail(e.getMessage());
    }
  }
}
