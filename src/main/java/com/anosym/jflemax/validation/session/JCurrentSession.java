/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.session;

import com.anosym.jflemax.validation.browser.UserAgent;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author marembo
 */
public class JCurrentSession implements Serializable {

  private static final long serialVersionUID = 178378237822L;
  private String sessionId;
  private String country;
  private Set<String> visitedPages;
  private String currentPage;
  private UserAgent userAgent;

  public JCurrentSession(String sessionId, String country) {
    this();
    this.sessionId = sessionId;
    this.country = country;
  }

  public JCurrentSession() {
    visitedPages = new HashSet<String>();
  }

  public String getSessionId() {
    return sessionId;
  }

  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }

  public String getCountry() {
    return country;
  }

  public void setCountry(String country) {
    this.country = country;
  }

  public void setVisitedPages(Set<String> visitedPages) {
    this.visitedPages = visitedPages;
  }

  public Set<String> getVisitedPages() {
    return visitedPages;
  }

  public String getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(String currentPage) {
    this.currentPage = currentPage;
    this.visitedPages.add(currentPage);
  }

  public UserAgent getUserAgent() {
    return userAgent;
  }

  public void setUserAgent(UserAgent userAgent) {
    this.userAgent = userAgent;
  }

}
