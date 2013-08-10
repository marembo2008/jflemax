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
public class OnNotNullValidator implements ConstraintValidator<OnNotNull, Object> {

  private List<OnNotNull> notNulls;

  public void initialize(OnNotNull constraintAnnotation) {
    notNulls = Arrays.asList(constraintAnnotation);
  }

  public boolean isValid(Object value, ConstraintValidatorContext context) {
    System.out.println("OnNotNullValidator: " + value.getClass());
    String[] fields = notNulls.get(0).properties();
    for (String f : fields) {
      try {
        //get the fields for the object.
        Field field = value.getClass().getField(f);
        if (field != null) {
          field.setAccessible(true);
          Object fieldValue = field.get(value);
          if (fieldValue == null) {
            return false;
          }
        } else {
          //try method
          String method = "get" + f.substring(0, 1).toUpperCase() + f.substring(1);
          Method m = value.getClass().getMethod(method, new Class[]{});
          if (m != null) {
            Object methodValue = m.invoke(value, new Object[]{});
            if (methodValue == null) {
              return false;
            }
          } else {
            return false;
          }
        }
      } catch (Exception ex) {
        Logger.getLogger(OnNotNullValidator.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    return true;
  }
}
