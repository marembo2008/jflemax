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
 *
 * @author marembo If found on a managed bean, reads the urls and adds no cached to them. This will
 * always be done at the render response state. A wild card indicates that all urls will be affected
 * by the cached control. a path followed by a wild card indicates that all urls within the specified
 * path.  <pre>
 * If a global directive [*] has been specified, all urls are
 * considered to belong to this global url and hence no further check will be done.
 * Normally, an application will only define a single cached control.
 * It makes no sense to have several cached controls defined everywhere.
 * </pre>
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Target(ElementType.TYPE)
public @interface CacheControl {

  /**
   *
   * @return
   */
  boolean cached() default false;

  /**
   * urls to affect with this control. Current implementation is only valid if the cached value is
   * false.
   *
   * @return
   */
  String[] urls() default {};
}
