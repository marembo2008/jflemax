/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author marembo
 */
public class NotEmptyValidator implements ConstraintValidator<NotEmpty, List> {

  public void initialize(NotEmpty constraintAnnotation) {
    //do nothing
  }

  public boolean isValid(List value, ConstraintValidatorContext context) {
    return value != null && !value.isEmpty();
  }

}
