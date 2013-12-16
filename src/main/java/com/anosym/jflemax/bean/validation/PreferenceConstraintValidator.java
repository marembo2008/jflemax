/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author marembo
 */
public class PreferenceConstraintValidator implements ConstraintValidator<Preference, Object> {

  private Class<? extends PreferenceHandler> validator;

  public void initialize(Preference constraintAnnotation) {
    validator = constraintAnnotation.handler();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      PreferenceHandler pv = validator.newInstance();
      return pv.isValid(value);
    } catch (InstantiationException ex) {
      Logger.getLogger(PreferenceConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(PreferenceConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
