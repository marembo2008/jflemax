/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

/**
 *
 * @author marembo
 */
public interface PreferenceHandler<T> {

  boolean isValid(T value);
}
