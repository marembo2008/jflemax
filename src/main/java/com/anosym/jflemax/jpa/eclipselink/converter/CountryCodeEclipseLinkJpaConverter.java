/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa.eclipselink.converter;

import com.anosym.utilities.Utility;
import com.anosym.utilities.geocode.CountryCode;
import org.eclipse.persistence.mappings.DatabaseMapping;
import org.eclipse.persistence.mappings.converters.Converter;
import org.eclipse.persistence.sessions.Session;

/**
 *
 * @author marembo
 */
@javax.persistence.Converter
public class CountryCodeEclipseLinkJpaConverter implements Converter {

  public Object convertObjectValueToDataValue(Object objectValue, Session session) {
    if (objectValue == null) {
      return null;
    }
    if (objectValue instanceof CountryCode) {
      CountryCode cc = (CountryCode) objectValue;
      return cc.getIsoCode();
    }
    throw new IllegalArgumentException("Expected CountryCode object, but got: " + objectValue.getClass().getName());
  }

  public Object convertDataValueToObjectValue(Object dataValue, Session session) {
    if (dataValue == null) {
      return null;
    }
    if (dataValue instanceof String) {
      String isoCode = dataValue.toString();
      return Utility.findCountryCodeFromCountryIsoCode(isoCode);
    }
    throw new IllegalArgumentException("Expected country iso code string, but got " + dataValue.getClass().getName());
  }

  public boolean isMutable() {
    return true;
  }

  public void initialize(DatabaseMapping mapping, Session session) {
  }
}
