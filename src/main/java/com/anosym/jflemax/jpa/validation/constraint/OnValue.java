/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa.validation.constraint;

/**
 *
 * @author marembo
 */
public @interface OnValue {

  String property();

  /**
   * The string value that this property must be equal to.
   *
   * @return
   */
  String toStringValue();
}
