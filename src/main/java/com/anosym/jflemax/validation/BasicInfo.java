/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import com.anosym.utilities.Utility;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 *
 * @author marembo
 */
public abstract class BasicInfo {

  private Class<?> controllerClass;
  private Object controllerBean;
  private boolean applicationScoped;

  public BasicInfo(Class<?> controllerClass) {
    setControllerClass(controllerClass);
  }

  public BasicInfo() {
  }

  public Class<?> getControllerClass() {
    return controllerClass;
  }

  public final void setControllerClass(Class<?> controllerClass) {
    this.controllerClass = controllerClass;
    this.applicationScoped = controllerClass.isAnnotationPresent(ApplicationScoped.class);
  }

  public Object getController() {
    //We cache this bean since we call this method so often, it brings some overhead.
    if (controllerBean != null && applicationScoped) {
      return controllerBean;
    }
    if (controllerClass != null) {
      Named named = controllerClass.getAnnotation(Named.class);
      String controller = named == null ? null : named.value();
      if (!Utility.isNullOrEmpty(controller)) {
        return controllerBean = JFlemaxController.findManagedBean(controller);
      }
      return controllerBean = JFlemaxController.findManagedBean(controllerClass);
    }
    return null;
  }
}
