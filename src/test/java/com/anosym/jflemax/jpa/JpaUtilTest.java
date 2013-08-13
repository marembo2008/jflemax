/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marembo
 */
public class JpaUtilTest {

  @Entity
  public static class JpaEntity {

    @Id
    private String id;
    private String data;

    public JpaEntity() {
    }

    public JpaEntity(String id, String data) {
      this.id = id;
      this.data = data;
    }

    @Override
    public String toString() {
      return "JpaEntity{" + "id=" + id + ", data=" + data + '}';
    }
  }

  @Entity
  public static class JpaEntityMethodTest {

    private String id;
    private String data;

    public JpaEntityMethodTest() {
    }

    public JpaEntityMethodTest(String id, String data) {
      this.id = id;
      this.data = data;
    }

    @Id
    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getData() {
      return data;
    }

    public void setData(String data) {
      this.data = data;
    }

    @Override
    public String toString() {
      return "JpaEntity{" + "id=" + id + ", data=" + data + '}';
    }
  }

  public JpaUtilTest() {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testGetEntityId() {
    System.out.println("testGetEntityId................");
    JpaEntity entity = new JpaEntity("4247282", "My jpa entty test");
    String actual = JpaUtil.getEntityId(entity);
    String expected = "4247282";
    Assert.assertEquals(expected, actual);
  }

  @Test
  public void testGetEntityId_method() {
    System.out.println("testGetEntityId................");
    JpaEntityMethodTest entity = new JpaEntityMethodTest("4247282", "My jpa entty test");
    String actual = JpaUtil.getEntityId(entity);
    String expected = "4247282";
    Assert.assertEquals(expected, actual);
  }
}
