/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

/**
 *
 * @author marembo
 */
public class OnRequestException extends IllegalArgumentException {

  public OnRequestException() {
  }

  public OnRequestException(String s) {
    super(s);
  }

  public OnRequestException(String message, Throwable cause) {
    super(message, cause);
  }

  public OnRequestException(Throwable cause) {
    super(cause);
  }
}
