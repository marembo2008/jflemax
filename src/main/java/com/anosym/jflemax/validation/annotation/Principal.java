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
 * When found on a bean controller, the method returns the current logged in principal in the web
 * application.
 *
 * The method must not have parameter argument.
 *
 * The return type can be any object.
 *
 * @author marembo
 */
@Documented
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Principal {
}
