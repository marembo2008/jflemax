/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.validator;

import java.math.BigDecimal;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author marembo
 */
@FacesValidator("positiveNumberValidator")
public class PositiveNumberValidator implements Validator {

  public void validate(FacesContext fc, UIComponent uic, Object value) throws ValidatorException {
    try {
      if (new BigDecimal(value.toString()).signum() < 1) {
        FacesMessage msg = new FacesMessage("Validation failed. Number must be strictly positive",
                "Validation failed. Number must be strictly positive");
        msg.setSeverity(FacesMessage.SEVERITY_ERROR);
        throw new ValidatorException(msg);
      }
    } catch (NumberFormatException ex) {
      FacesMessage msg = new FacesMessage("Validation failed. Not a number", "Validation failed. Not a number");
      msg.setSeverity(FacesMessage.SEVERITY_ERROR);
      throw new ValidatorException(msg);
    }
  }
}
