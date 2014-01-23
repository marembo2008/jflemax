/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.OnRequest;
import java.util.Set;
import javax.faces.event.PhaseId;
import javax.inject.Named;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marembo
 */
public class PageInformationTest {

  @OnRequest(toPages = "*")
  public static class AbstractController {

    public void onRequest() {
      System.out.println("AbstractController: onRequest()");
    }
  }

  @Named
  public static class ControllerImpl extends AbstractController {

    @Override
    public void onRequest() {
      System.out.println("ControllerImpl: OnRequest()");
    }

  }

  public PageInformationTest() {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testOnRequestsLoading() {
    PageInformation pi = PageInformation.getPageProcessor();
    String page = "/index";
    LoginStatus ls = LoginStatus.WHEN_LOGGED_IN;
    Set<RequestInfo> infos = pi.getRequestInfos(page, ls, PhaseId.RENDER_RESPONSE, JsfPhaseIdOption.BEFORE_PHASE);
    Assert.assertTrue(infos.size() == 1);
    RequestInfo i = infos.iterator().next();
    String expected = "controllerImpl";
    Assert.assertEquals(expected, i.getController());
  }

}
