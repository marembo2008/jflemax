/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import java.util.Calendar;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marembo
 */
public class DateConverterTest {

  public DateConverterTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testGetAsObject() {
    Calendar expected = Calendar.getInstance();
    expected.setTimeInMillis(0);
    expected.set(2013, 0, 1);
    DateConverter converter = new DateConverter();
    String dateString = "2013-01-01";
    Calendar actual = (Calendar) converter.getAsObject(null, null, dateString);
    assertEquals(expected, actual);
  }

  @Test
  public void testGetAsString() {
    String expected = "2013-05-06";
    Calendar cal = Calendar.getInstance();
    cal.set(2013, 4, 6); //java calendar months start from 0! so april is may!!
    DateConverter converter = new DateConverter();
    String actual = converter.getAsString(null, null, cal);
    assertEquals(expected, actual);
  }
}
