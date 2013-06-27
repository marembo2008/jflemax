/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.RedirectStatus;
import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author marembo
 */
public class RequestInfo implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(RequestInfo.class);
  private String controller;
  private Set<String> toPages;
  private String onRequestMethod;
  private String redirectPage;
  private boolean redirect;
  private RequestStatus requestStatus;
  private LoginStatus loginStatus;
  private String[] excludedPages;
  private RedirectStatus redirectStatus;
  private int priority;
  private JsfPhaseInfo[] jsfPhaseInfos;

  public RequestInfo() {
    toPages = new HashSet<String>();
  }

  public RequestInfo(
          String controller, Set<String> toPages, String onRequestMethod,
          String redirectPage, boolean redirect, RequestStatus requestStatus, LoginStatus loginStatus,
          String[] excludedPages, RedirectStatus redirectStatus, JsfPhaseInfo[] jsfPhaseInfos) {
    this.controller = controller;
    this.toPages = toPages;
    this.onRequestMethod = onRequestMethod;
    this.redirectPage = redirectPage;
    this.redirect = redirect;
    this.requestStatus = requestStatus;
    this.loginStatus = loginStatus;
    this.excludedPages = excludedPages;
    this.redirectStatus = redirectStatus;
    this.jsfPhaseInfos = jsfPhaseInfos;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public int getPriority() {
    return priority;
  }

  public JsfPhaseInfo[] getJsfPhaseInfos() {
    return jsfPhaseInfos;
  }

  public void setJsfPhaseInfos(JsfPhaseInfo[] jsfPhaseInfos) {
    this.jsfPhaseInfos = jsfPhaseInfos;
  }

  public RedirectStatus getRedirectStatus() {
    return redirectStatus;
  }

  public void setRedirectStatus(RedirectStatus redirectStatus) {
    this.redirectStatus = redirectStatus;
  }

  public String[] getExcludedPages() {
    return excludedPages;
  }

  public RequestStatus getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(RequestStatus requestStatus) {
    this.requestStatus = requestStatus;
  }

  public void setExcludedPages(String[] excludedPages) {
    this.excludedPages = excludedPages;
  }

  public LoginStatus getLoginStatus() {
    return loginStatus;
  }

  public void setLoginStatus(LoginStatus loginStatus) {
    this.loginStatus = loginStatus;
  }

  public String getRedirectPage() {
    return redirectPage;
  }

  public void setRedirectPage(String redirectPage) {
    this.redirectPage = redirectPage;
  }

  public boolean isRedirect() {
    return redirect;
  }

  public void setRedirect(boolean redirect) {
    this.redirect = redirect;
  }

  public String getController() {
    return controller;
  }

  public void setController(String controller) {
    this.controller = controller;
  }

  public Set<String> getToPages() {
    return toPages;
  }

  public void setToPages(Set<String> toPages) {
    this.toPages = toPages;
  }

  public String getOnRequestMethod() {
    return onRequestMethod;
  }

  public void setOnRequestMethod(String onRequestMethod) {
    this.onRequestMethod = onRequestMethod;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 37 * hash + (this.controller != null ? this.controller.hashCode() : 0);
    hash = 37 * hash + (this.toPages != null ? this.toPages.hashCode() : 0);
    hash = 37 * hash + (this.onRequestMethod != null ? this.onRequestMethod.hashCode() : 0);
    hash = 37 * hash + (this.redirectPage != null ? this.redirectPage.hashCode() : 0);
    hash = 37 * hash + (this.redirect ? 1 : 0);
    hash = 37 * hash + (this.requestStatus != null ? this.requestStatus.hashCode() : 0);
    hash = 37 * hash + (this.loginStatus != null ? this.loginStatus.hashCode() : 0);
    hash = 37 * hash + Arrays.deepHashCode(this.excludedPages);
    hash = 37 * hash + (this.redirectStatus != null ? this.redirectStatus.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final RequestInfo other = (RequestInfo) obj;
    if ((this.controller == null) ? (other.controller != null) : !this.controller.equals(other.controller)) {
      return false;
    }
    if (this.toPages != other.toPages && (this.toPages == null || !this.toPages.equals(other.toPages))) {
      return false;
    }
    if ((this.onRequestMethod == null) ? (other.onRequestMethod != null) : !this.onRequestMethod.equals(other.onRequestMethod)) {
      return false;
    }
    if ((this.redirectPage == null) ? (other.redirectPage != null) : !this.redirectPage.equals(other.redirectPage)) {
      return false;
    }
    if (this.redirect != other.redirect) {
      return false;
    }
    if (this.requestStatus != other.requestStatus) {
      return false;
    }
    if (this.loginStatus != other.loginStatus) {
      return false;
    }
    if (!Arrays.deepEquals(this.excludedPages, other.excludedPages)) {
      return false;
    }
    if (this.redirectStatus != other.redirectStatus) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "RequestInfo{" + "controller=" + controller + ", toPages=" + toPages + ","
            + " onRequestMethod=" + onRequestMethod + ", redirectPage=" + redirectPage + ","
            + " redirect=" + redirect + ", requestStatus=" + requestStatus + ", loginStatus=" + loginStatus + ","
            + " excludedPages=" + (excludedPages != null ? Arrays.toString(excludedPages) : excludedPages) + ","
            + " phasesInfo=" + (jsfPhaseInfos != null ? Arrays.toString(jsfPhaseInfos) : null) + '}';
  }
}
