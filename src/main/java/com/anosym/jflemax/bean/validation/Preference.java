/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author marembo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = PreferenceConstraintValidator.class)
public @interface Preference {

  String message() default "This field is required for SA customers";

  Class<?>[] group() default {};

  Class<? extends Payload>[] payload() default {};

  String[] validate();

  Class<? extends PreferenceValidator> validator();
}
