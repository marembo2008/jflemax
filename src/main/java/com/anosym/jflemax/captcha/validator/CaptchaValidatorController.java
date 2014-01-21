/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.captcha.validator;

import com.anosym.jflemax.validation.controller.JFlemaxController;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Named;
import nl.captcha.Captcha;

/**
 *
 * @author marembo
 */
@RequestScoped
@Named("captchaValidatorController")
@Alternative
public class CaptchaValidatorController {

  private String captchaAnswer;

  public void setCaptchaAnswer(String captchaAnswer) {
    this.captchaAnswer = captchaAnswer;
  }

  public String getCaptchaAnswer() {
    return captchaAnswer;
  }

  public void verifyCaptcha(FacesContext ctx, UIComponent cmp, Object value)
          throws ValidatorException {
    String answer = value != null ? value.toString() : "";
    Captcha captcha = JFlemaxController.getSessionAttribute(Captcha.NAME);
    if (captcha == null || !captcha.isCorrect(answer)) {
      this.captchaAnswer = null;
      String details = "The captcha value you have provided is invalid";
      FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, details, details);
      throw new ValidatorException(msg);
    }
  }
}
