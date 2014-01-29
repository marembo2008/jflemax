/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.OnRequest;

/**
 *
 * @author marembo
 */
@OnRequest(toPages = "*")
class AbstractControllerTest {

  public void onRequest() {
    System.out.println("AbstractController: onRequest()");
  }
}
