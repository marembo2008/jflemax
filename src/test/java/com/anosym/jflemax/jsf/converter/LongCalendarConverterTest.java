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
public class LongCalendarConverterTest {

  public LongCalendarConverterTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testCalendarToString() {
    String expected = "Oct 14, 2013 17:50:00";
    Calendar cal = Calendar.getInstance();
    cal.set(2013, 9, 14, 17, 50, 00);
    String actual = new LongCalendarConverter().getAsString(null, null, cal);
    assertEquals(expected, actual);
  }
}
