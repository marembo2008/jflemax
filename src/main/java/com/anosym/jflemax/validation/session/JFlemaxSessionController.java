/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.session;

import com.anosym.ip.mapping.IpMapping;
import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.Duplex;
import com.anosym.utilities.geocode.CountryCode;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 *
 * @author marembo
 */
@WebListener
public class JFlemaxSessionController implements HttpSessionListener {

  private static final Map<String, Object> SESSION_USERS = new HashMap<String, Object>();
  //get active sessions and countries.
  private static final Map<String, Duplex<String, String>> CURRENT_ACTIVE_SESSIONS = new HashMap<String, Duplex<String, String>>();

  public void sessionCreated(HttpSessionEvent se) {
    //nothing to be done currently. Just ignore.
    String sessionId = se.getSession().getId();
    String ip = JFlemaxController.getCurrentSessionClientIp();
    CountryCode country = IpMapping.findCountryCodeFromIpAddress(ip);
    String name = country != null ? country.getName() : "No Country found for: " + ip;
    CURRENT_ACTIVE_SESSIONS.put(sessionId, new Duplex<String, String>(name, JFlemaxController.getRequestPath()));
  }

  public void sessionDestroyed(HttpSessionEvent se) {
    //remove any session object associated with session.
    String sessionId = se.getSession().getId();
    SESSION_USERS.remove(sessionId);
    CURRENT_ACTIVE_SESSIONS.remove(sessionId);
  }

  public static void updateRequestPath(String sessionId, String requestPath) {
    try {
      CURRENT_ACTIVE_SESSIONS.get(sessionId).setSecondValue(requestPath);
    } catch (Exception e) {
      Logger.getLogger(JFlemaxSessionController.class.getName()).log(Level.SEVERE, sessionId, e);
    }
  }

  public static void registerSessionPrinciple(String sessionId, Object principle) {
    SESSION_USERS.put(sessionId, principle);
  }

  public static void onUserLoggedOut(String sessionId) {
    SESSION_USERS.remove(sessionId);
  }

  public static <T> Collection<T> getSessionUsers() {
    return (Collection<T>) SESSION_USERS.values();
  }

  public static <T> T getSessionUser(String sessionId) {
    return (T) SESSION_USERS.get(sessionId);
  }

  public static Collection<Duplex<String, String>> getCurrentActiveSessionInformation() {
    return CURRENT_ACTIVE_SESSIONS.values();
  }
}
