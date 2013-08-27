/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

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
public class CommSeparatedListOfStringConverter implements Converter {

  public Object getAsObject(FacesContext fc, UIComponent uic, String string) {
    if (string != null) {
      return new ArrayList<String>(Arrays.asList(string.split(",")));
    }
    return null;
  }

  public String getAsString(FacesContext fc, UIComponent uic, Object o) {
    if (o != null && List.class.isAssignableFrom(o.getClass())) {
      List<String> str = (List<String>) o;
      String ss = "";
      for (String s : str) {
        if (!ss.isEmpty()) {
          ss += ",";
        }
        ss += s;
      }
      return ss;
    }
    return null;
  }
}
