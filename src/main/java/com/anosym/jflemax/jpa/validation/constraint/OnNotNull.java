/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa.validation.constraint;

import java.lang.annotation.Documented;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 *
 * @author marembo
 */
@Documented
@Constraint(validatedBy = OnNotNullValidator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface OnNotNull {

  String message() default "You must specify some fields within this constraint";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

  String[] properties() default {};
}
