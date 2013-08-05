/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import javax.inject.Named;

/**
 *
 * @author marembo
 */
public abstract class BasicInfo {

  private Class<?> controllerClass;

  public BasicInfo(Class<?> controllerClass) {
    this.controllerClass = controllerClass;
  }

  public BasicInfo() {
  }

  public Class<?> getControllerClass() {
    return controllerClass;
  }

  public void setControllerClass(Class<?> controllerClass) {
    this.controllerClass = controllerClass;
  }

  public Object getController() {
    if (controllerClass != null) {
      Named named = controllerClass.getAnnotation(Named.class);
      if (named != null) {
        return JFlemaxController.findManagedBean(controllerClass);
      }
    }
    return null;
  }
}
