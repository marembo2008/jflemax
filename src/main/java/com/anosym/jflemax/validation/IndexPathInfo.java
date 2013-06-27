/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author marembo
 */
public class IndexPathInfo extends BasicInfo {

  private Method indexPathMethod;

  public IndexPathInfo() {
  }

  public IndexPathInfo(Class<?> controllerClass, Method indexPathMethod) {
    super(controllerClass);
    this.indexPathMethod = indexPathMethod;
  }

  public String getIndexPath() {
    try {
      return (String) indexPathMethod.invoke(getController(), new Object[]{});
    } catch (IllegalAccessException ex) {
      Logger.getLogger(PrincipalInfo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(PrincipalInfo.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvocationTargetException ex) {
      Logger.getLogger(PrincipalInfo.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
}
