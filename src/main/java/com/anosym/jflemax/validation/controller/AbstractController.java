/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.controller;

import com.anosym.utilities.IdGenerator;
import javax.annotation.PostConstruct;

public class AbstractController {

  public static final String JFLEMAX_EPOCH = "2012-11-01 00:00:00";

  static {
    System.setProperty(IdGenerator.ID_GENERATOR_EPOCH, JFLEMAX_EPOCH);
  }
  public static final String PAGE_INFORMATION = "page_information_3264278468249924822342";

  @PostConstruct
  public void onPostConstruct() {
//    com.anosym.jflemax.JFlemaxLogger.fine("Processing Page Information: " + this.getClass());
//    //we need to add page information to application scope
//    PageInformation pageInformation = PageInformation.getPageProcessor();
//    pageInformation.processController(this);
  }
}
