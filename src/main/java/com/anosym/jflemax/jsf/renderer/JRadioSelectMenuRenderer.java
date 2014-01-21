/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.renderer;

import com.anosym.jflemax.jsf.component.JRadioSelectMenu;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;

/**
 *
 * @author marembo
 */
@FacesRenderer(rendererType = JRadioSelectMenuRenderer.RENDERER_TYPE,
        componentFamily = JRadioSelectMenu.COMPONENT_FAMILIY)
public class JRadioSelectMenuRenderer extends Renderer {

  public static final String RENDERER_TYPE = "jradio-select-menu-renderer";
}
