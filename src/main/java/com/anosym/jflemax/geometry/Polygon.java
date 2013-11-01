/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.geometry;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marembo
 */
public class Polygon implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(Polygon.class);
  private List<Line> polygonLineBounds;

  public Polygon() {
  }

  public Polygon(List<Line> polygonLineBounds) {
    this.polygonLineBounds = polygonLineBounds;
  }

  public List<Line> getPolygonLineBounds() {
    return polygonLineBounds;
  }

  public void setPolygonLineBounds(List<Line> polygonLineBounds) {
    this.polygonLineBounds = polygonLineBounds;
  }

  public void addLine(Line line) {
    if (polygonLineBounds == null) {
      polygonLineBounds = new ArrayList<Line>();
    }
    polygonLineBounds.add(line);
  }

  public static Polygon fromPolygonString(String polygonString) {
    polygonString = polygonString.trim();
    if (polygonString.startsWith("POLYGON((") && polygonString.endsWith("))")) {
      int i = polygonString.indexOf("POLYGON((") + "POLYGON((".length();
      int j = polygonString.lastIndexOf("))");
      polygonString = polygonString.substring(i, j);
      String parts[] = polygonString.split(",");
      Polygon p = new Polygon();
      for (int k = 0, n = 1; n < parts.length; k++, n++) {
        String p0 = "POINT(" + parts[k] + ")";
        String p1 = "POINT(" + parts[n] + ")";
        Line l = new Line(Coordinate.fromPointString(p0), Coordinate.fromPointString(p1));
        p.addLine(l);
      }
      return p;
    } else if (polygonString.startsWith("MULTIPOLYGON(((") && polygonString.endsWith(")))")) {
      int i = polygonString.indexOf("MULTIPOLYGON(((") + "MULTIPOLYGON(((".length();
      int j = polygonString.lastIndexOf(")))");
      polygonString = polygonString.substring(i, j);
      String parts[] = polygonString.split(",");
      Polygon p = new Polygon();
      for (int k = 0, n = 1; n < parts.length; k++, n++) {
        String p0 = "POINT(" + parts[k] + ")";
        String p1 = "POINT(" + parts[n] + ")";
        Line l = new Line(Coordinate.fromPointString(p0), Coordinate.fromPointString(p1));
        p.addLine(l);
      }
      return p;
    }
    throw new IllegalArgumentException("Invalid Polygon String: " + polygonString);
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 59 * hash + (this.polygonLineBounds != null ? this.polygonLineBounds.hashCode() : 0);
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
    final Polygon other = (Polygon) obj;
    if (this.polygonLineBounds != other.polygonLineBounds && (this.polygonLineBounds == null || !this.polygonLineBounds.equals(other.polygonLineBounds))) {
      return false;
    }
    return true;
  }

  public String toPolygonString() {
    String polygon = "";
    if (polygonLineBounds != null) {
      for (Line l : polygonLineBounds) {
        if (!polygon.isEmpty()) {
          polygon += ",";
        }
        polygon += l.toLineString();
      }
    }
    return "Polygon(" + polygon + ")";
  }

  @Override
  public String toString() {
    return "Polygon{" + polygonLineBounds + '}';
  }
}
