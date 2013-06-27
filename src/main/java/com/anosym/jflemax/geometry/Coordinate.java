/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.geometry;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 *
 * @author marembo
 */
public class Coordinate implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(Coordinate.class);
  private BigDecimal latitude;
  private BigDecimal longitude;
  private BigDecimal altitude;

  public Coordinate(BigDecimal latitude, BigDecimal longitude) {
    this(latitude, longitude, BigDecimal.ZERO);
  }

  public Coordinate(BigDecimal latitude, BigDecimal longitude, BigDecimal altitude) {
    this.latitude = latitude;
    this.longitude = longitude;
    this.altitude = altitude;
  }

  public Coordinate() {
    this(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
  }

  public BigDecimal getLatitude() {
    return latitude;
  }

  public void setLatitude(BigDecimal latitude) {
    this.latitude = latitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public BigDecimal getAltitude() {
    return altitude;
  }

  public void setAltitude(BigDecimal altitude) {
    this.altitude = altitude;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 23 * hash + (this.latitude != null ? this.latitude.hashCode() : 0);
    hash = 23 * hash + (this.longitude != null ? this.longitude.hashCode() : 0);
    hash = 23 * hash + (this.altitude != null ? this.altitude.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Coordinate other = (Coordinate) obj;
    if (this.latitude != other.latitude && (this.latitude == null || !this.latitude.equals(other.latitude))) {
      return false;
    }
    if (this.longitude != other.longitude && (this.longitude == null || !this.longitude.equals(other.longitude))) {
      return false;
    }
    if (this.altitude != other.altitude && (this.altitude == null || !this.altitude.equals(other.altitude))) {
      return false;
    }
    return true;
  }

  public static Coordinate fromPointString(String pointString) {
    pointString = pointString.trim();
    if (pointString.startsWith("POINT(") && pointString.endsWith(")")) {
      int i = pointString.indexOf("POINT(") + "POINT(".length();
      int j = pointString.lastIndexOf(")");
      pointString = pointString.substring(i, j);
      String parts[] = pointString.split(" ");
      if (parts.length == 2) {
        Coordinate coord = new Coordinate(new BigDecimal(parts[1]), new BigDecimal(parts[0]));
        return coord;
      }
    }
    throw new IllegalArgumentException("Invalid Point String: " + pointString);
  }

  public String toPointString() {
    return "Point(" + longitude + " " + latitude + ")";
  }

  @Override
  public String toString() {
    return "Coordinate(" + longitude + "," + latitude + "," + altitude + ')';
  }
}
