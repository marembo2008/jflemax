/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

/**
 *
 * @author marembo
 */
public interface PreferenceValidator<T> {

  boolean isValid(T value, String[] fieldsToValidate);
}
