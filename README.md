Noovodp
======
--------------

 **Noovodb** is a light Java framework to make your DAO classes easier to write.

##Prerequesites##

--------------
[Maven](http://maven.apache.org/) and [Git](https://git-scm.com/)

##Usage##

--------------
###Install###

Noovodb is available as a maven project on Github. You can download thee zip or clone the project with :

```
$ git clone https://github.com/noovop/noovodp.git
```

Once you have the cloned or downloaded the zip, you can build the project like :

```
$ cd <directory>
$ mvn clean install
```

So you can import the ***jar***  to your ***class path*** or if your project is on maven, you can add the maven dependency like :

```
<!-- noovodb lib -->
<dependency>
	<groupId>com.noovop</groupId>
	<artifactId>noovodb</artifactId>
	<version>1.0</version>
</dependency>
```

Getting a database connection
-------------

To let ***noovodb*** manage the database connection, you just need to add an ***abstract***  in the same package as your DAO classes like this

```java
...
import com.noovop.noovodb.manager.DbManager;
import com.noovop.noovodb.service.CrudService;

/**
 * This abstract class implements the setUp method
 * to connect to the database.
 * @version 1.0
 */
public abstract class AbstractDao<T> extends CrudService<T> {

  /* This class it responsible of initializing database connection.
   * The type of connection choosen here is a JDBC Driver
   *
   */
  @Override
  protected void setUp(final String label) throws ModelException {
    this.connection = DbManager.openConnection("org.postgresql.Driver", "jdbc:postgresql://localhost:5432/demo?user=postgres&password=secret", label);
  }
}
```

As you can see in this code spinet, the  **AbstractDao** extends **CrudService** class and overrides only **setUp** method for getting database connection. The database connection is initialized by calling **openConnection** of **DbManager**.

This this code spinet, we use JDBC. But you can also use a data source connection.

 Table information & SQL Queries
-------------
To make the things more organized, we recommend you to write an **SQLContant** to put all the constants  such as :

> - Entity table definition
>- SQL queries templates

For example, for a single Contact entity class, the constants will look like :

```java
package com.noovop.noovodb.example.constant;

/**
 * All SQL Constants are in this interface
 * @version 1.0
 */
public interface SQLContant {

  /**
   * The contacts for the contact table
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

// One more table
public interface TABLE_COMPANY {
	// ...
}
}
```
These are the descriptions :

###**Table name**

```java
public final static String TABLE_NAME = "CONTACT"
```
This is just the table for contact entity

###**Table sequence**

```java
    public final static String SEQUENCE_NAME        = "CONTACT_SEQ";
```
Use recommend to use sequences for ID columns generation.  

###**Table column definitions**

Each column has a COLUMN_* string contant.

###**SQL Queries templates**

Each database action has a SQL_STATEMENT_* string constant.

In SQL queries templates, the values are replaced by question mark (**?**) .

Example :

```sql
SELECT * FROM CONTACT WHERE ID = ?
```

The ID is a parameter for this query. An example the complete query can be

```sql
SELECT * FROM CONTACT WHERE ID = 40
```

Writing your DAO class
-------------

Each DAO class just need to extend your **AbstractDao** class.

Let say, we want to create a ContactDao class for our contact table, it will look like :

```java
...
import java.sql.ResultSet;
import java.sql.SQLException;
import com.noovop.noovodb.exception.ModelException;
import com.noovop.noovodb.sql.SQLQueryBuilder;

public class ContactDao extends AbstractDao<Contact>{

}
```

We know need to implement the methods of getResult method to defined bean wiring and mapping.

```java
...
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
...
```

As our DAO provide CRUD service, we need to implement the other methods. This is the implementation of buildCreateQuery method :

```java
...
  /**
   * Builder the Insert Query of bean creation to datatabas
   */
  @Override
  protected String buildCreateQuery(Contact objectBean) throws ModelException {
    objectBean.setContactId(Long.parseLong(String.valueOf(this.getSequenceNextValue(SQLContant.TABLE_CONTACT.SEQUENCE_NAME).toString())));
    return SQLQueryBuilder.completeQuery(SQLContant.TABLE_CONTACT.SQL_STATEMENT_INSERT, objectBean.getContactId(), objectBean.getFirstName(), objectBean.getLastName(), objectBean.getPhone(), objectBean.getEmail());
  }
...
```
As the ID column values are generated by a sequence, just call this.getSequenceNextValue(sequenceName) to get next ID value

The **completeQuery** method of **SQLQueryBuilder** class is used to produces the complete the final query string ready for database execution.

Each build query method is just for mapping the SQL query templates with the values.

You have to implement the following methods :

 >- buildReadQuery
 >- buildReadAllQuery
 >- buildDeleteQuery

The complete example project
-------------

You can get the complete example project at [noovodb example](https://github.com/noovop/noovodb/tree/master/examples)

Table of content
===

[TOC]
