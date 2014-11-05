package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.Principal;
import java.lang.reflect.Method;
import java.util.Set;
import org.junit.Test;

import static com.anosym.jflemax.validation.annotation.JsfPhaseIdOption.BEFORE_PHASE;
import static javax.faces.event.PhaseId.RENDER_RESPONSE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author marembo
 */
public class PageInformationTest {

    public static class PrincipleTest {

        @Principal
        public Object getPrinciple() {
            return "aha-principle";
        }
    }

    public static class PrincipleTestImpl extends PrincipleTest {

    }

    @Test
    public void testOnRequestsLoading() {
        final PageInformation pi = PageInformation.getPageProcessor();
        final String page = "/index";
        final LoginStatus ls = LoginStatus.WHEN_LOGGED_IN;
        final Set<RequestInfo> infos = pi.getRequestInfos(page, ls, RENDER_RESPONSE, BEFORE_PHASE);
        assertTrue(infos.size() == 1);
        final RequestInfo i = infos.iterator().next();
        final Class<?> expected = ControllerImpl.class;
        assertEquals(expected, i.getController());
    }

    @Test
    public void testSubclassPrincipleInherited() {
        final Class cl = PrincipleTestImpl.class;
        final Method[] ms = cl.getMethods();
        boolean present = false;
        for (Method m : ms) {
            if (m.isAnnotationPresent(Principal.class)) {
                present = true;
                com.anosym.jflemax.JFlemaxLogger.fine(m);
                break;
            }
        }
        assertTrue(present);
    }
}
