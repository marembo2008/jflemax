/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.browser;

import java.util.Locale;

/**
 *
 * @author marembo
 */
public class UserAgent {

  private static final String GOOGLE_CHROME = "AppleWebKit";
  private static final String MOZILLA_FIREFOX = "Firefox";
  private static final String INTERNET_EXPLORER = "MSIE";
  private static final String OPERA = "Opera";
  private static final String MULTIZILLA = "MultiZilla";
  private static final String SAFARI = "Safari";
  private static final String OS_WINDOWS = "Windows";
  private static final String OS_LINUX = "Linux";
  private static final String OS_MACINTOSH = "Macintosh";
  private String browserType;
  private String browserVersion;
  private String os;
  private String osVersion;
  private Locale locale;

  public boolean isWindows() {
    return os != null && os.trim().equals(OS_WINDOWS);
  }

  public boolean isLinux() {
    return os != null && os.trim().equals(OS_LINUX);
  }

  public boolean isMacintosh() {
    return os != null && os.trim().equals(OS_MACINTOSH);
  }

  public boolean isChrome() {
    return browserType != null && browserType.trim().equalsIgnoreCase(GOOGLE_CHROME);
  }

  public boolean isIE() {
    return browserType != null && browserType.trim().equalsIgnoreCase(INTERNET_EXPLORER);
  }

  public boolean isFirefox() {
    return browserType != null && browserType.trim().equalsIgnoreCase(MOZILLA_FIREFOX);
  }

  public boolean isOpera() {
    return browserType != null && browserType.trim().equalsIgnoreCase(OPERA);
  }

  public boolean isSafari() {
    return browserType != null && browserType.trim().equalsIgnoreCase(SAFARI);
  }

  public boolean isMultizilla() {
    return browserType != null && browserType.trim().equalsIgnoreCase(MULTIZILLA);
  }

  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  public Locale getLocale() {
    return locale;
  }

  public String getBrowserType() {
    return browserType;
  }

  public void setBrowserType(String browserType) {
    this.browserType = browserType;
  }

  public String getBrowserVersion() {
    return browserVersion;
  }

  public void setBrowserVersion(String browserVersion) {
    this.browserVersion = browserVersion;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getOsVersion() {
    return osVersion;
  }

  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }
}
