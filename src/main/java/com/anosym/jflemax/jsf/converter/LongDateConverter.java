/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.swing.text.DateFormatter;

/**
 *
 * @author marembo
 */
@FacesConverter(value = "longDateConverter")
public class LongDateConverter implements Converter {

  @Override
  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    return null;
  }

  @Override
  public String getAsString(FacesContext context, UIComponent component, Object value) {
    try {
      //most important
      DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG);
      DateFormatter formatter = new DateFormatter(dateFormat);
      if (value != null && value instanceof Calendar) {
        return formatter.valueToString(((Calendar) value).getTime());
      }
    } catch (ParseException ex) {
      Logger.getLogger(LongDateConverter.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
  }
}
