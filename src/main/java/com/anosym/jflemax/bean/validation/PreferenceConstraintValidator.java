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
public class PreferenceConstraintValidator implements ConstraintValidator<Validation, Object> {

  private Class<? extends Validator> validator;

  public void initialize(Validation constraintAnnotation) {
    validator = constraintAnnotation.validator();
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    try {
      Validator pv = validator.newInstance();
      return pv.isValid(value);
    } catch (InstantiationException ex) {
      Logger.getLogger(PreferenceConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(PreferenceConstraintValidator.class.getName()).log(Level.SEVERE, null, ex);
    }
    return false;
  }
}
