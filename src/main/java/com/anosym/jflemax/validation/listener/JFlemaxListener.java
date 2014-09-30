package com.anosym.jflemax.validation.listener;

import com.anosym.jflemax.validation.PageInformation;
import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.google.common.base.Throwables;
import java.util.logging.Level;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author marembo
 */
public class JFlemaxListener extends JFlemaxController implements PhaseListener {

    private void processCacheControl() {
        String requestPath = getRequestPath();
        if (!PageInformation.isCurrentPageCached(requestPath)) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpServletResponse response = (HttpServletResponse) facesContext
                    .getExternalContext().getResponse();
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Cache-Control", "no-store");
            response.addHeader("Cache-Control", "must-revalidate");
            response.addHeader("Expires", "Mon, 8 Aug 1980 10:00:00 GMT");
        }
    }

    @Override
    public void afterPhase(PhaseEvent event) {
        try {
            PhaseId phaseId = event.getPhaseId();
            JsfPhaseIdOption jsfPhaseIdOption = JsfPhaseIdOption.AFTER_PHASE;
            com.anosym.jflemax.JFlemaxLogger.log(Level.FINE, "jsf after Current Phase: {0}", phaseId);
            validateRequest(phaseId, jsfPhaseIdOption);
        } catch (Exception ex) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        try {
            PhaseId phaseId = event.getPhaseId();
            if (phaseId.equals(PhaseId.RENDER_RESPONSE)) {
                processCacheControl();
            }
            com.anosym.jflemax.JFlemaxLogger.log(Level.FINE, "jsf before Current Phase: {0}", phaseId);
            JsfPhaseIdOption jsfPhaseIdOption = JsfPhaseIdOption.BEFORE_PHASE;
            validateRequest(phaseId, jsfPhaseIdOption);
        } catch (Exception ex) {
            throw Throwables.propagate(ex);
        }
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
