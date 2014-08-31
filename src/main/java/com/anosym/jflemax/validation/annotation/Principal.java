package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.inject.Qualifier;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * When found on a bean controller, the method returns the current logged in principal in the web application.
 *
 * The method must not have parameter argument.
 *
 * The return type can be any object.
 *
 * @author marembo
 */
@Qualifier
@Documented
@Inherited
@Target({ElementType.METHOD, FIELD, PARAMETER, TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Principal {
}
