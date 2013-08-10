/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa.validation.constraint;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author marembo
 */
public class OnCertainValueValidator implements ConstraintValidator<OnCertainValue, Object> {

  private List<OnCertainValue> certainValues;

  public void initialize(OnCertainValue constraintAnnotation) {
    certainValues = Arrays.asList(constraintAnnotation);
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    System.out.println("OnCertainValueValidator: " + value.getClass());
    OnValue[] onValues = certainValues.get(0).values();
    for (OnValue onV : onValues) {
      String f = onV.property();
      String equalValue = onV.toStringValue() + "";
      try {
        //get the fields for the object.
        Field field = value.getClass().getField(f);
        if (field != null) {
          field.setAccessible(true);
          Object fieldValue = field.get(value);
          String expectedValue = fieldValue + "";
          if (!expectedValue.equals(equalValue)) {
            return false;
          }
        } else {
          //try method
          String method = "get" + f.substring(0, 1).toUpperCase() + f.substring(1);
          Method m = value.getClass().getMethod(method, new Class[]{});
          if (m != null) {
            Object methodValue = m.invoke(value, new Object[]{});
            String expectedValue = methodValue + "";
            if (!expectedValue.equals(equalValue)) {
              return false;
            }
          } else {
            return false;
          }
        }
      } catch (Exception ex) {
        Logger.getLogger(OnCertainValueValidator.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return true;
  }
}
