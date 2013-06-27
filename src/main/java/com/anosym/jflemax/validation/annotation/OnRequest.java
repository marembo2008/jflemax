/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

import com.anosym.jflemax.validation.RequestStatus;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author marembo
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface OnRequest {

  /**
   * Controller for which an ajax or complete request will be executed
   *
   * @return
   */
  String controller() default "";

  /**
   * The request path which will make the request method of the controller be executed Note: toPages
   * = "*" implies to any page.
   *
   * @return
   */
  String[] toPages();

  /**
   * The on request method on the controller which will be called. This method should not throw an
   * exception, and must not accept any argument. When redirect is true, this method must return a
   * boolean indicating failure or success. In this case, the returned state is used, together with
   * the redirect option to redirect on failures.
   *
   * @return
   */
  String onRequestMethod() default "onRequest";

  /**
   * The page to redirect to when on request method fails. If empty, and redirect is true and the
   * onRequestMethod has failed, the redirector will redirect to the default initial page of the web
   * application.
   *
   * @return
   */
  String redirectPage() default "";

  /**
   * If true, the redirect will be executed. This occurs if and only if the onRequestmethod fails.
   *
   * @return
   */
  boolean redirect() default false;

  /**
   * Indicates the request status to execute the on request method.
   *
   * @return
   */
  RequestStatus requestStatus() default RequestStatus.ANY_REQUEST;

  /**
   * This request is only executed if user principal is logged in. The definition of user principal
   * is user defined.
   *
   * @return
   */
  LoginStatus logInStatus() default LoginStatus.WHEN_LOGGED_IN;

  /**
   * Pages that are excluded during the invocation of this request.
   *
   * @return
   */
  String[] excludedPages() default {};

  /**
   * Determines the redirect scenarios. Whether to redirect on failure or on success.
   *
   * @return
   */
  RedirectStatus redirectStatus() default RedirectStatus.ON_FAILURE;

  /**
   * The priority of this OnRequest call. Larger values generally indicate higher priority.
   *
   * @return
   */
  int priority() default 0;

  /**
   * The phases for which this request will be executed. By default, this is RENDER_RESPONSE and
   * before phase.
   *
   * It is not necessary to specify this value, unless you want multiple execution depending on the
   * scenarios. Otherwise, it suffices to execute this request in the jsf RENDER_RESPONSE phase
   * BEFORE IT STARTS.
   *
   * @return
   */
  JsfPhase[] jsfPhases() default {};
}
