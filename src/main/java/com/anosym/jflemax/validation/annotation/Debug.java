/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If found on any controller, the application will be set to debug mode and the configuration
 * informations will be loaded everytime a request is made. It has a default value of true. The
 * reload is done after every few seconds. You can change the reload period by setting the {@link Debug#reloadPeriodInSeconds()
 * } value
 *
 * @author marembo
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Debug {

  boolean value() default true;

  int reloadPeriodInSeconds() default 60;
}
