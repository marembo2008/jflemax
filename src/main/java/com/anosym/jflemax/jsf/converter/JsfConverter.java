/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import com.anosym.jflemax.jpa.JpaUtil;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter("jsfConverter")
public class JsfConverter implements Converter {

  private static final Map<String, Object> map = new HashMap<String, Object>();

  @Override
  public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    return map.get(string);
  }

  @Override
  public String getAsString(FacesContext fc, UIComponent uic, Object o) {
    if (o != null) {
      Object id = JpaUtil.getEntityId(o);
      if (id != null) {
        map.put(id.toString(), o);
        return id.toString();
      }
    }
    return null;
  }
}
