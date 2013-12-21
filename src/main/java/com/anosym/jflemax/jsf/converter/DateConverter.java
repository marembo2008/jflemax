/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import com.anosym.utilities.FormattedCalendar;
import java.util.Calendar;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter(value = "dateConverter")
public class DateConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return FormattedCalendar.parseISODate(value);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    return (value != null && (Calendar.class.isAssignableFrom(value.getClass()))) ? FormattedCalendar.getISODateString((Calendar) value) : null;
  }
}
