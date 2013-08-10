/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import com.anosym.jflemax.jpa.Text;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter(forClass = Text.class)
public class TextConverter implements Converter {

  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return new Text(value);
  }

  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value instanceof Text) {
      return value.toString();
    }
    return null;
  }
}
