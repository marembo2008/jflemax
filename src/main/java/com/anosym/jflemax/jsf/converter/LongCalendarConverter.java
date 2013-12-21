/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter(value = "longCalendarConverter")
public class LongCalendarConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value != null && value instanceof Calendar) {
      SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
      return sdf.format(((Calendar) value).getTime());
    }
    return null;
  }
}
