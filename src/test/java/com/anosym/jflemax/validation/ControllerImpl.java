/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import javax.inject.Named;

/**
 *
 * @author marembo
 */
@Named
class ControllerImpl extends AbstractControllerTest {

  @Override
  public void onRequest() {
    System.out.println("ControllerImpl: OnRequest()");
  }

}
