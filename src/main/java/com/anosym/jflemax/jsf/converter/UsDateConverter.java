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
@FacesConverter(value = "usDateConverter")
public class UsDateConverter implements Converter {

  private static final String US_DATE_FORMAT = "MM-dd-yyyy";

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return FormattedCalendar.parseDate(value, US_DATE_FORMAT);
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    return (value != null && (Calendar.class.isAssignableFrom(value.getClass()))) ? FormattedCalendar.toDateString((Calendar) value, US_DATE_FORMAT) : null;
  }
}
