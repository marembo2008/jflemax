package com.anosym.jflemax.validation.session;

import com.anosym.ip.mapping.IpMapping;
import com.anosym.jflemax.validation.controller.JFlemaxController;
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
@Deprecated
public class JFlemaxSessionController implements HttpSessionListener {

    private static final Logger LOG = Logger.getLogger(JFlemaxSessionController.class.getName());

    private static final Map<String, Object> SESSION_USERS = new HashMap<String, Object>();
    //get active sessions and countries.
    private static final Map<String, JCurrentSession> CURRENT_ACTIVE_SESSIONS = new HashMap<String, JCurrentSession>();

    public void sessionCreated(HttpSessionEvent se) {
        //nothing to be done currently. Just ignore.
        try {
            String sessionId = se.getSession().getId();
            String ip = JFlemaxController.getCurrentSessionClientIp();
            CountryCode country = IpMapping.findCountryCodeFromIpAddress(ip);
            String countryName = country != null ? country.getName() : "No Country found for: " + ip;
            CURRENT_ACTIVE_SESSIONS.put(sessionId, new JCurrentSession(sessionId, countryName));
        } catch (Exception ex) {
            LOG.log(Level.SEVERE, "Error on session created", ex);
        }
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        //remove any session object associated with session.
        String sessionId = se.getSession().getId();
        SESSION_USERS.remove(sessionId);
        CURRENT_ACTIVE_SESSIONS.remove(sessionId);
    }

    public static void updateRequestPath(String sessionId, String requestPath) {
        try {
            JCurrentSession currentSession = CURRENT_ACTIVE_SESSIONS.get(sessionId);
            if (currentSession != null) {
                currentSession.setCurrentPage(requestPath);
                currentSession.setUserAgent(JFlemaxController.getCurrentUserAgent());
            }
        } catch (Exception e) {
            LOG.log(Level.SEVERE, "Error updating session path for current session", e);
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

    /**
     * Returns true if the current session has logged in user.
     *
     * @param sessionId
     * @return
     */
    public static boolean isLoggedInProfileSession(String sessionId) {
        return getSessionUser(sessionId) != null;
    }

    public static Collection<JCurrentSession> getCurrentActiveSessionInformation() {
        return CURRENT_ACTIVE_SESSIONS.values();
    }

}
