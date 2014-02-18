/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import com.anosym.jflemax.JFlemaxLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter("commaSeparatedListOfStringConverter")
public class CommaSeparatedListOfStringConverter implements Converter {

  public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    JFlemaxLogger.fine("Getting list for: " + string);
    if (string != null) {
      return new ArrayList<String>(Arrays.asList(string.split(",\\s*")));
    }
    return null;
  }

  public String getAsString(FacesContext fc, UIComponent uic, Object o) {
    JFlemaxLogger.fine("Getting string from list: " + o);
    if (o != null && List.class.isAssignableFrom(o.getClass())) {
      String str = o.toString();
      return str.substring(1, str.length() - 1);
    }
    return null;
  }
}
