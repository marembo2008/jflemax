/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.component;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIInput;

/**
 *
 * @author marembo
 */
@FacesComponent(JRadioSelectMenu.COMPONENT_NAME)
public class JRadioSelectMenu extends UIInput {

  public static final String COMPONENT_FAMILIY = "com.anosym.jflemax.RadioSelectMenu";
  public static final String COMPONENT_NAME = "jradio-select-menu";

  @Override
  public String getFamily() {
    return COMPONENT_FAMILIY;
  }

}
