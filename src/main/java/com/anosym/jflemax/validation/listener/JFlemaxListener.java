package com.anosym.jflemax.validation.listener;

import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.controller.JFlemaxController;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author marembo
 */
public class JFlemaxListener extends JFlemaxController implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();
        JsfPhaseIdOption jsfPhaseIdOption = JsfPhaseIdOption.AFTER_PHASE;
        validateRequest(phaseId, jsfPhaseIdOption);
    }

    @Override
    public void beforePhase(PhaseEvent event) {
        PhaseId phaseId = event.getPhaseId();
        JsfPhaseIdOption jsfPhaseIdOption = JsfPhaseIdOption.BEFORE_PHASE;
        validateRequest(phaseId, jsfPhaseIdOption);
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
