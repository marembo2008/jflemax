/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author marembo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({})
public @interface JsfPhase {

  JsfPhaseId phaseId() default JsfPhaseId.RENDER_RESPONSE;

  JsfPhaseIdOption phaseIdOption() default JsfPhaseIdOption.AFTER_PHASE;
}
