/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marembo
 */
public class PrincipalInfo extends BasicInfo {

  private Method principalMethod;

  public PrincipalInfo() {
  }

  public PrincipalInfo(Class<?> controllerClass, Method principalMethod) {
    super(controllerClass);
    this.principalMethod = principalMethod;
  }

  public <T> T getUserPrinciple() {
    try {
      if (principalMethod != null) {
        return (T) principalMethod.invoke(getController(), new Object[]{});
      }
    } catch (Exception ex) {
      Logger.getLogger(PrincipalInfo.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
}
