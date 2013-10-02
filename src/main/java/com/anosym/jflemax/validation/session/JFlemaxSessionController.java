/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.session;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
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

  public void sessionCreated(HttpSessionEvent se) {
    //nothing to be done currently. Just ignore.
  }

  public void sessionDestroyed(HttpSessionEvent se) {
    //remove any session object associated with session.
    String sessionId = se.getSession().getId();
    SESSION_USERS.remove(sessionId);
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
}
