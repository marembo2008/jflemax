/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.controller;

import com.anosym.jflemax.validation.PageInformation;
import com.anosym.jflemax.validation.RequestInfo;
import com.anosym.jflemax.validation.RequestStatus;
import com.anosym.jflemax.validation.annotation.ExecuteCycle;
import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.RedirectStatus;
import com.anosym.jflemax.validation.browser.UserAgent;
import com.anosym.utilities.Utility;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.el.ELResolver;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.servlet.ServletContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author marembo
 */
public class JFlemaxController {

  public static final String IGNORE_VALIDATION = "ignore_validate";
  public static final String APPLICATION_PACKAGE = "application_package";

  public static String getIGNORE_VALIDATION() {
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
    HttpSession httpSession = (HttpSession) FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .getSession(true);
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
      context.responseComplete();
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
    String referringPath = getRequestHeader("Referer");
    String contextPath = getContextPath();
    String url = getApplicationUrl();
    if (!Utility.isNullOrEmpty(referringPath)) {
      if (referringPath.contains(url)) {
        int ix = referringPath.indexOf(url) + url.length();
        referringPath = referringPath.substring(ix);
      }
      if (!Utility.isNullOrEmpty(contextPath) && referringPath.contains(contextPath)) {
        int ix = referringPath.indexOf(contextPath) + contextPath.length();
        referringPath = referringPath.substring(ix);
      }
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
    String context = getContextPath();
    return host + context;
  }

  /**
   * Returns the applications full url, including the context path attached and request scheme.
   *
   * @return
   */
  public static String getSchematicApplicationUrl() {
    String scheme = getScheme();
    String host = getRequestHeader("host");
    String context = getContextPath();
    return scheme + "://" + host + context;
  }

  /**
   * Returns true if the current scheme is https.
   *
   * @return
   */
  public static boolean isSecure() {
    String scheme = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme();
    return scheme != null && scheme.trim().equalsIgnoreCase("https");
  }

  /**
   * Get the current scheme for used by the current request.F
   *
   * @return
   */
  public static String getScheme() {
    return FacesContext.getCurrentInstance().getExternalContext().getRequestScheme();
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

  private static UserAgent tryGetMicrosoftAgent(String uah) {
    //if we have both mozilla and windows nt string, then we are definitely working with windows and msie.
    uah = uah.toLowerCase();
    if (uah.contains("mozilla") && uah.contains("windows nt")) {
      UserAgent ua = new UserAgent();
      ua.setOs("Windows");
      //get windows version
      String regex = "windows\\s+[^\\d*\\w*]\\d+\\.*\\d*";
      Pattern p = Pattern.compile(regex);
      Matcher m = p.matcher(uah);
      if (m.find()) {
        int ix = m.start(), ix0 = m.end();
        //get the sub
        String windowsVersion = uah.substring(ix, ix0);
        int ix1 = windowsVersion.indexOf("nt");
        String version = windowsVersion.substring(ix1).trim();
        ua.setOsVersion(version);
      }
      return ua;
    }
    return null;
  }

  protected static UserAgent getUserAgent(String userAgentHeader) {
    if (userAgentHeader != null) {
      UserAgent agent = new UserAgent();
      if (userAgentHeader.contains("MSIE")) {
        agent.setBrowserType("MSIE");
        //get the ie version
        int ix0 = userAgentHeader.indexOf("MSIE") + 4;
        int ix1 = userAgentHeader.indexOf(";", ix0);
        if (ix1 > 0) {
          String version = userAgentHeader.substring(ix0, ix1);
          agent.setBrowserVersion(version);
        }
      } else if (userAgentHeader.contains("Mozilla")
              && userAgentHeader.contains("Windows")
              && userAgentHeader.contains("Trident/7.0")) {
        //we are working with IE
        agent.setBrowserType("MSIE");
        //get the ie version
        int ix0 = userAgentHeader.indexOf("rv:");
        if (ix0 > -1) {
          ix0 += 3;
        }
        int ix1 = userAgentHeader.indexOf(")", ix0);
        if (ix1 > 0) {
          String version = userAgentHeader.substring(ix0, ix1);
          agent.setBrowserVersion(version);
        }
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

  public static String getCookie(String name) {
    Map<String, Object> m = FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .getRequestCookieMap();
    Object cookie = m.get(name);
    if (cookie != null) {
      if (cookie instanceof Cookie) {
        Cookie ck = (Cookie) cookie;
        return ck.getValue();
      } else {
        return cookie.toString();
      }
    }
    return null;
  }

  public static void addCookie(String name, String value) {
    Map<String, Object> props = new HashMap<String, Object>();
    props.put("maxAge", Integer.MAX_VALUE);
    props.put("secure", true);
    FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .addResponseCookie(name, value, props);
  }

  public static void addCookie(String name, String value, Map<String, Object> cookieParams) {
    FacesContext
            .getCurrentInstance()
            .getExternalContext()
            .addResponseCookie(name, value, cookieParams);
  }

  /**
   * Call this method at prerender view
   */
  protected void validateRequest() {
    //get parameter for ignore request
    String value = getParameter(IGNORE_VALIDATION);
    if (value != null && Boolean.valueOf(value)) {
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
            } finally {
              //check if the request is to be executed only once, and then removed from the queue
              if (requestInfo.getExecuteCycle() == ExecuteCycle.ONCE) {
                pageInformation.removeFromQueue(requestInfo);
              }
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
              getPrinciple() != null ? LoginStatus.WHEN_LOGGED_IN : LoginStatus.WHEN_LOGGED_OUT,
              phaseId, jsfPhaseIdOption);
      if (requestInfos == null) {
        return;
      }
      FacesContext context = FacesContext.getCurrentInstance();
      boolean ajaxRequest = context.getPartialViewContext().isAjaxRequest();
      boolean loginRequest = (getPrinciple() != null);
      Map<RequestInfo, Boolean> infos = new HashMap<RequestInfo, Boolean>();
      Map<RequestInfo, Integer> infos0 = new HashMap<RequestInfo, Integer>();
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
                if (res != null) {
                  if (res instanceof Boolean) {
                    Boolean state = (Boolean) res;
                    boolean doRedirect = (state && requestInfo.getRedirectStatus() == RedirectStatus.ON_SUCCESS)
                            || (!state && requestInfo.getRedirectStatus() == RedirectStatus.ON_FAILURE)
                            || requestInfo.getRedirectStatus() == RedirectStatus.ALWAYS;
                    if (doRedirect && requestInfo.isRedirect()) {
                      System.out.println("Redirect: OnRequestMethod=" + validatingMethod.getName());
                      infos.put(requestInfo, state);
                    }
                  } else if (res instanceof Integer) {
                    Integer i = (Integer) res;
                    boolean doRedirect = requestInfo.getRedirectStatus() == RedirectStatus.ALWAYS;
                    if (doRedirect && requestInfo.isRedirectOnResult()) {
                      infos0.put(requestInfo, i);
                    }
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
      redirectRequest0(infos0, referingPath);
    }
  }

  private void redirectRequest(Map<RequestInfo, Boolean> infos, String referingPath) {
    for (Entry<RequestInfo, Boolean> requestInfos : infos.entrySet()) {
      RequestInfo requestInfo = requestInfos.getKey();
      String redirectPage = requestInfo.getRedirectPage();
      if (requestInfo.getRedirectStatus() == RedirectStatus.ALWAYS && !requestInfos.getValue()) {
        redirectPage = requestInfo.getRedirectFailurePage();
      }
      if (Utility.isNullOrEmpty(redirectPage)) {
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

  private void redirectRequest0(Map<RequestInfo, Integer> infos, String referingPath) {
    for (Entry<RequestInfo, Integer> requestInfos : infos.entrySet()) {
      RequestInfo requestInfo = requestInfos.getKey();
      String redirectPage = requestInfo.getRedirectPages()[requestInfos.getValue()];
      if (Utility.isNullOrEmpty(redirectPage)) {
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
      name = Character.toLowerCase(name.charAt(0)) + name.substring(1);
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

  public static <T> T getRequestAttribute(String key) {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    T paramValue = (T) request.getAttribute(key);
    return paramValue;
  }

  public static <T> T getSessionAttribute(String key) {
    FacesContext context = FacesContext.getCurrentInstance();
    HttpSession session = (HttpSession) context.getExternalContext().getSession(false);
    T paramValue = (T) session.getAttribute(key);
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

  public static HttpServletRequest getRequest(FacesContext context) {
    return (HttpServletRequest) context.getExternalContext().getRequest();
  }

  /**
   * From OmniFaces Library. Returns the Internet Protocol (IP) address of the client that sent the
   * request. This will first check the <code>X-Forwarded-For</code> request header and if it's
   * present, then return its first IP address, else just return
   * {@link HttpServletRequest#getRemoteAddress()} unmodified.
   *
   * @return The IP address of the client.
   * @see HttpServletRequest#getRemoteAddress()
   * @since 1.2
   */
  public static String getRemoteAddress() {
    String forwardedFor = getRequestHeader("X-Forwarded-For");
    if (!Utility.isNullOrEmpty(forwardedFor)) {
      return forwardedFor.split("\\s*,\\s*", 2)[0]; // It's a comma separated string: client,proxy1,proxy2,...
    }
    return getRequest(FacesContext.getCurrentInstance()).getRemoteAddr();
  }

  public static String getCurrentSessionClientIp() {
    String ipAddress = getRemoteAddress();
    return ipAddress;
  }

  /**
   * Returns all context relative paths of all resources that end in .xhtml This method depends on
   * the current web application request context.
   *
   * @return
   */
  public static List<String> applicationResourcePaths() {
    HttpSession session = getCurrentSession();
    ServletContext sc = session.getServletContext();
    String path = sc.getRealPath("/");
    if (!Utility.isNullOrEmpty(path)) {
      List<String> resources = new ArrayList<String>();
      getResources(path, new File(path), resources);
      return resources;
    }
    return Collections.EMPTY_LIST;
  }

  private static void getResources(String realPath, File path, List<String> resources) {
    if (path.isDirectory()) {
      File[] paths = path.listFiles();
      for (File path_d : paths) {
        getResources(realPath, path_d, resources);
      }
    } else if (path.isFile() && path.getName().endsWith(".xhtml")) {
      String actualPath = path.getAbsolutePath();
      String contextPath = actualPath.substring(realPath.length());
      contextPath = contextPath.startsWith("/") ? contextPath : "/" + contextPath;
      resources.add(contextPath);
    }
  }
}
