/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;
import com.anosym.jflemax.validation.annotation.LoginStatus;
import com.anosym.jflemax.validation.annotation.Principal;
import java.lang.reflect.Method;
import java.util.Set;
import javax.faces.event.PhaseId;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author marembo
 */
public class PageInformationTest {

  public static class PrincipleTest {

    @Principal
    public Object getPrinciple() {
      return "aha-principle";
    }
  }

  public static class PrincipleTestImpl extends PrincipleTest {

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

  @Test
  public void testSubclassPrincipleInherited() {
    Class cl = PrincipleTestImpl.class;
    Method[] ms = cl.getMethods();
    boolean present = false;
    for (Method m : ms) {
      if (m.isAnnotationPresent(Principal.class)) {
        present = true;
        System.out.println(m);
        break;
      }
    }
    Assert.assertTrue(present);
  }
}
