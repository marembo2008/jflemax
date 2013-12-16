/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

/**
 *
 * @author marembo
 * @param <T>
 */
public interface PreferenceHandler<T> {

  boolean isValid(T value);
}
