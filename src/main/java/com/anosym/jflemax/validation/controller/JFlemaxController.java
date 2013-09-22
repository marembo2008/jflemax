/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.controller;

import com.anosym.jflemax.validation.PageInformation;
import com.anosym.jflemax.validation.RequestInfo;
import com.anosym.jflemax.validation.RequestStatus;
import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.RedirectStatus;
import com.anosym.jflemax.validation.browser.UserAgent;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marembo
 */
public class JFlemaxController {

  private static final String IGNORE_VALIDATION = "ignore_validate";
  public static final String APPLICATION_PACKAGE = "application_package";

  public String getIGNORE_VALIDATION() {
    return IGNORE_VALIDATION;
  }

  public static <T> T getPrinciple() {
    if (PageInformation.getPageProcessor().getPrincipalInfo() != null) {
      return PageInformation.getPageProcessor().getPrincipalInfo().getUserPrinciple();
    }
    return null;
  }

  public static String indexPath() {
    if (PageInformation.getPageProcessor().getIndexPathInfo() != null) {
      return PageInformation.getPageProcessor().getIndexPathInfo().getIndexPath();
    }
    return null;
  }

  public static String getContext() {
    FacesContext context = FacesContext.getCurrentInstance();
    return ((ServletContext) context.getExternalContext().getContext()).getContextPath();
  }

  public static String getCurrentSessionId() {
    HttpSession httpSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    if (httpSession != null) {
      return httpSession.getId();
    }
    return null;
  }

  public static void redirect(String to) {
    try {
      String referringPath = getReferringPath();
      if (referringPath != null && referringPath.equals(to)) {
        return;
      }
      String referer = getReferer();
      if (referringPath != null && referer.equals(to)) {
        return;
      }
      String host = getRequestHeader("host");
      String contextUrl = getContext() + "/";
      if (to != null && !to.startsWith(contextUrl) && !to.contains(host)) {
        to = getContext() + to;
      }
      FacesContext context = FacesContext.getCurrentInstance();
      if (!to.contains("?faces-redirect=true")) {
        if (to.contains("?")) {
          to += "&faces-redirect=true";
        } else {
          to += "?faces-redirect=true";
        }
      }
      context.getExternalContext().redirect(to);
      context.responseComplete();
    } catch (IOException ex) {
      java.util.logging.Logger.getLogger(JFlemaxController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static void externalRedirect(String to) {
    try {
      if (!to.startsWith("http")) {
        to = "http://" + to;
      }
      FacesContext context = FacesContext.getCurrentInstance();
      context.getExternalContext().redirect(to);
    } catch (IOException ex) {
      java.util.logging.Logger.getLogger(JFlemaxController.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  public static PageInformation getPageInformation() {
    return PageInformation.getPageProcessor();
  }

  public static String getContextPath() {
    FacesContext context = FacesContext.getCurrentInstance();
    return ((ServletContext) context.getExternalContext().getContext()).getContextPath();
  }

  public static String getContextName() {
    String context = getContextPath();
    return context.substring(1);
  }

  public static String getRequestPath() {
    FacesContext context = FacesContext.getCurrentInstance();
    return context.getExternalContext().getRequestServletPath();
  }

  /**
   * Returns the relative referring path to the context root, if the referring path refers to this
   * application, otherwise the entire url is returned.
   *
   * @return
   */
  public static String getReferringPath() {
    FacesContext context = FacesContext.getCurrentInstance();
    String referringPath = getRequestHeader("Referer");
    String contextPath = ((ServletContext) context.getExternalContext().getContext()).getContextPath() + "/";
    if (referringPath != null && referringPath.contains(contextPath)) {
      referringPath = referringPath.substring(referringPath.indexOf(contextPath) + contextPath.length() - 1);
    }
    return referringPath;
  }

  public static String getReferer() {
    return getRequestHeader("Referer");
  }

  public static String getRequestHeader(String headerId) {
    try {
      FacesContext context = FacesContext.getCurrentInstance();
      if (context != null) {
        ExternalContext ec = context.getExternalContext();
        Map<String, String> headers = ec.getRequestHeaderMap();
        return headers.get(headerId);
      }
    } catch (Exception ex) {
      logError(ex);
    }
    return null;
  }

  public static UserAgent getUserAgent() {
    String userAgentHeader = getRequestHeader("User-Agent");
    if (userAgentHeader == null) {
      userAgentHeader = getRequestHeader("user-agent");
      if (userAgentHeader == null) {
        return null;
      }
    }
    return getUserAgent(userAgentHeader);
  }

  /**
   * Returns the applications full url, including the context path attached.
   *
   * @return
   */
  public static String getApplicationUrl() {
    String host = getRequestHeader("host");
    System.out.println("getApplicationUrl: " + host);
    String context = getContextPath();
    return host + context;
  }

  public static UserAgent getCurrentUserAgent() {
    String userAgentHeader = getRequestHeader("User-Agent");
    if (userAgentHeader == null) {
      userAgentHeader = getRequestHeader("user-agent");
      if (userAgentHeader == null) {
        return null;
      }
    }
    return getUserAgent(userAgentHeader);
  }

  private static UserAgent getUserAgent(String userAgentHeader) {
    if (userAgentHeader != null) {
      UserAgent agent = new UserAgent();
      if (userAgentHeader.contains("MSIE")) {
        agent.setBrowserType("MSIE");
      } else if (userAgentHeader.contains("Firefox")) {
        agent.setBrowserType("Firefox");
      } else if (userAgentHeader.contains("Chrome") || userAgentHeader.contains("AppleWebKit")) {
        agent.setBrowserType("AppleWebKit");
      } else if (userAgentHeader.contains("Opera")) {
        agent.setBrowserType("Opera");
      } else if (userAgentHeader.contains("MultiZilla")) {
        agent.setBrowserType("MultiZilla");
      } else if (userAgentHeader.contains("Safari")) {
        agent.setBrowserType("Safari");
      }
      if (userAgentHeader.contains("Windows")) {
        agent.setOs("Windows");
      } else if (userAgentHeader.contains("Linux")) {
        agent.setOs("Linux");
      } else if (userAgentHeader.contains("Macintosh")) {
        agent.setOs("Macintosh");
      }
      return agent;
    }
    return null;
  }

  public static HttpSession getCurrentSession() {
    return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
  }

  /**
   * Call this method at prerender view
   */
  protected void validateRequest() {
    //get parameter for ignore request
    String value = getParameter(IGNORE_VALIDATION);
    if (value != null && Boolean.valueOf(value)) {
      System.out.println("Ignoring validation: " + value);
      return;
    }
    String requestPath = getRequestPath();
    String referingPath = getReferringPath();
    String contextPath = getContextPath();
    if (referingPath != null && referingPath.contains(contextPath)) {
      referingPath = referingPath.substring(referingPath.indexOf(contextPath) + contextPath.length());
    }
    PageInformation pageInformation = getPageInformation();
    if (pageInformation != null) {
      Set<RequestInfo> requestInfos = pageInformation.getRequestInfos(requestPath,
              getPrinciple() != null ? LoginStatus.WHEN_LOGGED_IN : LoginStatus.WHEN_LOGGED_OUT);
      if (requestInfos == null) {
        return;
      }
      FacesContext context = FacesContext.getCurrentInstance();
      boolean ajaxRequest = context.getPartialViewContext().isAjaxRequest();
      boolean loginRequest = (getPrinciple() != null);
      List<RequestInfo> infos = new ArrayList<RequestInfo>();
      for (RequestInfo requestInfo : requestInfos) {
        RequestStatus requestStatus = requestInfo.getRequestStatus();
        LoginStatus loginStatus = requestInfo.getLoginStatus();
        boolean executeOnAjax = (ajaxRequest && (requestStatus == RequestStatus.AJAX_REQUEST))
                || (!ajaxRequest && (requestStatus == RequestStatus.FULL_REQUEST))
                || (requestStatus == RequestStatus.ANY_REQUEST);
        boolean executeOnLogin = (loginRequest && (loginStatus == LoginStatus.WHEN_LOGGED_IN))
                || (!loginRequest && (loginStatus == LoginStatus.WHEN_LOGGED_OUT))
                || (loginStatus == LoginStatus.EITHER);
        boolean executeValidate = executeOnAjax && executeOnLogin;
        if (executeValidate) {
          Object controller = JFlemaxController.findManagedBean(requestInfo.getController());
          if (controller != null) {
            try {
              Class<?> controllerClass = controller.getClass();
              Method validatingMethod = controllerClass.getDeclaredMethod(requestInfo.getOnRequestMethod(), new Class<?>[]{});
              if (validatingMethod != null) {
                Object res = validatingMethod.invoke(controller, new Object[]{});
                if (res != null && res instanceof Boolean) {
                  Boolean state = (Boolean) res;
                  boolean doRedirect = (state && requestInfo.getRedirectStatus() == RedirectStatus.ON_SUCCESS)
                          || (!state && requestInfo.getRedirectStatus() == RedirectStatus.ON_FAILURE);
                  if (doRedirect && requestInfo.isRedirect()) {
                    infos.add(requestInfo);
                  }
                }
              }
            } catch (Exception e) {
              logError(e);
            }
          }
        }
      }
      redirectRequest(infos, referingPath);
    }
  }

  protected void validateRequest(PhaseId phaseId, JsfPhaseIdOption jsfPhaseIdOption) {
    //get parameter for ignore request
    String value = getParameter(IGNORE_VALIDATION);
    if (value != null && Boolean.valueOf(value)) {
      System.out.println("Ignoring validation: " + value);
      return;
    }
    String requestPath = getRequestPath();
    String referingPath = getReferringPath();
    String contextPath = getContextPath();
    if (referingPath != null && referingPath.contains(contextPath)) {
      referingPath = referingPath.substring(referingPath.indexOf(contextPath) + contextPath.length());
    }
    System.err.println("Request Path: " + requestPath);
    PageInformation pageInformation = getPageInformation();
    if (pageInformation != null) {
      Set<RequestInfo> requestInfos = pageInformation.getRequestInfos(requestPath,
              getPrinciple() != null ? LoginStatus.WHEN_LOGGED_IN : LoginStatus.WHEN_LOGGED_OUT,
              phaseId, jsfPhaseIdOption);
      if (requestInfos == null) {
        return;
      }
      FacesContext context = FacesContext.getCurrentInstance();
      boolean ajaxRequest = context.getPartialViewContext().isAjaxRequest();
      boolean loginRequest = (getPrinciple() != null);
      List<RequestInfo> infos = new ArrayList<RequestInfo>();
      System.out.println("Request Infos: " + infos);
      for (RequestInfo requestInfo : requestInfos) {
        RequestStatus requestStatus = requestInfo.getRequestStatus();
        LoginStatus loginStatus = requestInfo.getLoginStatus();
        boolean executeOnAjax = (ajaxRequest && (requestStatus == RequestStatus.AJAX_REQUEST))
                || (!ajaxRequest && (requestStatus == RequestStatus.FULL_REQUEST))
                || (requestStatus == RequestStatus.ANY_REQUEST);
        boolean executeOnLogin = (loginRequest && (loginStatus == LoginStatus.WHEN_LOGGED_IN))
                || (!loginRequest && (loginStatus == LoginStatus.WHEN_LOGGED_OUT))
                || (loginStatus == LoginStatus.EITHER);
        boolean executeValidate = executeOnAjax && executeOnLogin;
        if (executeValidate) {
          Object controller = JFlemaxController.findManagedBean(requestInfo.getController());
          if (controller != null) {
            try {
              Class<?> controllerClass = controller.getClass();
              Method validatingMethod = controllerClass.getDeclaredMethod(requestInfo.getOnRequestMethod(), new Class<?>[]{});
              if (validatingMethod != null) {
                Object res = validatingMethod.invoke(controller, new Object[]{});
                if (res != null && res instanceof Boolean) {
                  Boolean state = (Boolean) res;
                  boolean doRedirect = (state && requestInfo.getRedirectStatus() == RedirectStatus.ON_SUCCESS)
                          || (!state && requestInfo.getRedirectStatus() == RedirectStatus.ON_FAILURE);
                  if (doRedirect && requestInfo.isRedirect()) {
                    infos.add(requestInfo);
                  }
                }
              }
            } catch (Exception e) {
              logError(e);
            }
          }
        }
      }
      redirectRequest(infos, referingPath);
    }
  }

  private void redirectRequest(List<RequestInfo> infos, String referingPath) {
    for (RequestInfo requestInfo : infos) {
      String redirectPage = requestInfo.getRedirectPage();
      if (redirectPage.trim().isEmpty()) {
        redirectPage = indexPath();
      } else {
        if (!redirectPage.contains(".xhtml")) {
          redirectPage += ".xhtml";
        }
      }
      /**
       * We should not redirect to the same page
       */
      if (!redirectPage.equals(referingPath)) {
        if (!FacesContext.getCurrentInstance().getResponseComplete()) {
          redirect(redirectPage);
        }
        break; //after first redirect, we move out since we cannot redirect to more than one page.
      }
    }
  }

  public static <T> T findManagedBean(Class<T> beanClass) {
    try {
      String name = beanClass.getSimpleName();
      Object o = findManagedBean(name);
      return beanClass.cast(o);
    } catch (Exception e) {
      logError(e);
      return null;
    }
  }

  public static Object findManagedBean(String name) {
    try {
      FacesContext facesContext = FacesContext.getCurrentInstance();
      name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
      Object o = facesContext.getApplication().getELResolver().
              getValue(facesContext.getELContext(), null, name);
      return o;
    } catch (Exception e) {
      logError(e);
      return null;
    }
  }

  public static <T> T findManagedBean(Class<T> beanClass, FacesContext facesContext) {
    try {
      String name = beanClass.getSimpleName();
      name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
      ELResolver eLResolver = facesContext.getApplication().getELResolver();
      Object o = eLResolver.getValue(facesContext.getELContext(), null, name);
      System.err.println("findManagedBean: resolution state for: " + name + " = " + facesContext.getELContext().isPropertyResolved());
      return beanClass.cast(o);
    } catch (Exception e) {
      logError(e);
      return null;
    }
  }

  public static boolean isAjaxRequest() {
    return FacesContext.getCurrentInstance().getPartialViewContext().isAjaxRequest();
  }

  public static boolean isLocalHost() {
    String ip = getRequestHeader("host");
    if (ip != null && ip.contains("//")) {
      ip = ip.substring(ip.indexOf("//") + 2);
    }
    return ip != null && (ip.contains("localhost") || ip.startsWith("192.") || ip.startsWith("127.0.0.1"));
  }

  public static boolean isSessionInvalidated() {
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
    HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    return request != null && request.getRequestedSessionId() != null && !request.isRequestedSessionIdValid();
  }

  public static String getParameter(String key) {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    String paramValue = request.getParameter(key);
    return paramValue;
  }

  public static void logError(Throwable error) {
    Logger.getLogger(JFlemaxController.class.getName()).log(Level.SEVERE, null, error);
  }

  public static void logError(Throwable error, String message) {
    Logger.getLogger(JFlemaxController.class.getName()).log(Level.SEVERE, message, error);
  }

  public static void logMessage(String message) {
    Logger.getLogger(JFlemaxController.class.getName()).log(Level.INFO, message);
  }

  public static void logMessage(String title, String message) {
    Logger.getLogger(JFlemaxController.class.getName()).log(Level.INFO, title + "{0}", message);
  }
}
