/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author marembo
 */
@WebServlet(initParams = {
  @WebInitParam(name = "width", value = "250"),
  @WebInitParam(name = "height", value = "75")},
        urlPatterns = {"/security/captcha"})
public class SimpleCaptchaServlet extends nl.captcha.servlet.SimpleCaptchaServlet {

  @Override
  public void init() throws ServletException {
    super.init(); //To change body of generated methods, choose Tools | Templates.
  }
}
