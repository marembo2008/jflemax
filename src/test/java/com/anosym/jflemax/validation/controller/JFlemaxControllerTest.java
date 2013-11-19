/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.controller;

import com.anosym.jflemax.validation.browser.UserAgent;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marembo
 */
public class JFlemaxControllerTest {

  public JFlemaxControllerTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testWindowsIE8() {
    String userAgent = "Mozilla/5.0 (compatible; MSIE 8.0; Windows NT 6.1; Trident/5.0)";
    UserAgent ua = JFlemaxController.getUserAgent(userAgent);
    assertTrue(ua.isIE8OrLower());
  }

  @Test
  public void testWindowsIE11() {
    String userAgent = "Mozilla/5.0 (Windows NT 6.3; Trident/7.0; rv:11.0) like Gecko";
    UserAgent ua = JFlemaxController.getUserAgent(userAgent);
    assertTrue(ua.isIE11OrHigher());
  }
}