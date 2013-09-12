/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marembo
 */
public class CacheControlInfoTest {

  public CacheControlInfoTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testIsCacheControlled_global() {
    String url = "/path/to/url/page.xhtml";
    CacheControlInfo cci = new CacheControlInfo(new String[]{"*"}, true);
    boolean actual = cci.isCacheControlled(url);
    assertTrue(actual);
  }

  @Test
  public void testIsCacheControlled_fullUrl() {
    String url = "/path/to/url/page.xhtml", url2 = "/path/to/someother/url/page.xhtml";
    CacheControlInfo cci = new CacheControlInfo(new String[]{url, url2}, true);
    boolean actual = cci.isCacheControlled(url);
    assertTrue(actual);
  }

  @Test
  public void testIsCacheControlled_partOfPathControl() {
    String url = "/path/to/url/page.xhtml", url2 = "/path/to/someother/url/page.xhtml", partPathUrl = "/path/to/url/*";
    CacheControlInfo cci = new CacheControlInfo(new String[]{partPathUrl, url2}, true);
    boolean actual = cci.isCacheControlled(url);
    assertTrue(actual);
  }

  @Test
  public void testIsCacheControlled_notPartOfCacheControl() {
    String url = "/path/to/url/page.xhtml", url2 = "/path/to/someother/url/page.xhtml", partPathUrl = "/path/to/nonexisting/url/*";
    CacheControlInfo cci = new CacheControlInfo(new String[]{partPathUrl, url2}, true);
    boolean actual = cci.isCacheControlled(url);
    assertFalse(actual);
  }
}