/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

import javax.faces.event.PhaseId;

/**
 *
 * @author marembo
 */
public enum JsfPhaseId {

  RESTORE_VIEW(PhaseId.RESTORE_VIEW),
  APPLY_REQUEST_VALUES(PhaseId.APPLY_REQUEST_VALUES),
  PROCESS_VALIDATIONS(PhaseId.PROCESS_VALIDATIONS),
  UPDATE_MODEL_VALUES(PhaseId.UPDATE_MODEL_VALUES),
  INVOKE_APPLICATION(PhaseId.INVOKE_APPLICATION),
  RENDER_RESPONSE(PhaseId.RENDER_RESPONSE),
  ANY_PHASE(PhaseId.ANY_PHASE);
  private PhaseId phaseId;

  private JsfPhaseId(PhaseId phaseId) {
    this.phaseId = phaseId;
  }

  public PhaseId getPhaseId() {
    return phaseId;
  }

  public static JsfPhaseId findJsfPhaseId(PhaseId phaseId) {
    for (JsfPhaseId id : values()) {
      if (id.getPhaseId().equals(phaseId)) {
        return id;
      }
    }
    return null;
  }
}
