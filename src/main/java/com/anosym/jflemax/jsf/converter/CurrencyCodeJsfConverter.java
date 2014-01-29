/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jsf.converter;

import com.anosym.utilities.Utility;
import com.anosym.utilities.currency.CurrencyCode;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author marembo
 */
@FacesConverter(forClass = CurrencyCode.class)
public class CurrencyCodeJsfConverter implements Converter {

  public Object getAsObject(FacesContext context, UIComponent component, String value) {
    if (Utility.isNullOrEmpty(value)) {
      return null;
    }
    String[] parts = value.split(":");
    return Utility.findCurrencyCodeFromCountryIsoCodeAndCurrencySymbol(parts[0], parts[1]);
  }

  public String getAsString(FacesContext context, UIComponent component, Object value) {
    if (value instanceof CurrencyCode) {
      CurrencyCode c = (CurrencyCode) value;
      return c.getCountryCode().getIsoCode() + ":" + c.getCurrencySymbol();
    }
    return null;
  }

}
