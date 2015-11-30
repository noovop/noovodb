package com.noovop.noovodb.example;

import org.junit.Before;
import org.junit.Test;

import com.noovop.noovodb.example.bean.Contact;
import com.noovop.noovodb.example.dao.ContactDao;

public class ContactDaoTest extends AbstractTest<ContactDao, Contact>{
  @Override
  @Before
  public void setUp() throws Exception {
    super.setUp();

    this.daoBean = new ContactDao();

    for (int i = 0; i < 20; i++) {
      final Contact contact = new Contact();
      contact.setContactId(Long.parseLong(Integer.valueOf(i+1).toString()));
      contact.setFirstName("Nom Index");
      contact.setLastName("Label Index");
      contact.setPhone("1234567890");
      contact.setEmail("stidiovip@yahoo.fr");

      this.beanList.add(contact);
    }
  }

  @Override
  @Test
  public void testCreate() {
    super.testCreate();
  }

  @Override
  @Test
  public void testRead() {
    super.testRead();
  }

  @Override
  @Test
  public void testUpdate() {
    super.testUpdate();
  }

  @Override
  @Test
  public void testDelete() {
    super.testDelete();
  }
}
