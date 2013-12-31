/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marembo
 */
public class CacheControlInfo implements Serializable {

  private final List<String> urls;
  private final boolean cached;

  public CacheControlInfo(List<String> urls, boolean cached) {
    this.urls = urls;
    this.cached = cached;
  }

  public CacheControlInfo(String[] urls, boolean cached) {
    this.urls = Arrays.asList(urls);
    this.cached = cached;
  }

  public List<String> getUrls() {
    return urls;
  }

  public boolean isCached() {
    return cached;
  }

  public boolean isCacheControlled(String url) {
    //check if this url was defined within this cache control or global url exists
    //first get the global one if any
    String extensionUrl = !url.contains(".") ? (url + ".xhtml") : url;
    boolean isCacheControlled = urls.contains(url) || urls.contains(extensionUrl) || urls.contains("*");
    if (!isCacheControlled) {
      final Pattern p = Pattern.compile("[^/]/");
      final Matcher m = p.matcher(url);
      while (m.find()) {
        String mainPage = (url.substring(0, m.end()));
        //check if this page belongs to any of the parent which may have been specified.
        if (urls.contains(mainPage + "*")) {
          return true;
        }
      }
    }
    return isCacheControlled;
  }
}
