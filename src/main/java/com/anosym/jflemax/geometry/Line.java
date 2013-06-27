/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.geometry;

import com.anosym.utilities.IdGenerator;
import java.io.Serializable;

/**
 *
 * @author marembo
 */
public class Line implements Serializable {

  private static final long serialVersionUID = IdGenerator.serialVersionUID(Line.class);
  private Coordinate startCoordinate;
  private Coordinate endCoordinate;

  public Line() {
  }

  public Line(Coordinate startCoordinate, Coordinate endCoordinate) {
    this.startCoordinate = startCoordinate;
    this.endCoordinate = endCoordinate;
  }

  public Coordinate getStartCoordinate() {
    return startCoordinate;
  }

  public void setStartCoordinate(Coordinate startCoordinate) {
    this.startCoordinate = startCoordinate;
  }

  public Coordinate getEndCoordinate() {
    return endCoordinate;
  }

  public void setEndCoordinate(Coordinate endCoordinate) {
    this.endCoordinate = endCoordinate;
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 97 * hash + (this.startCoordinate != null ? this.startCoordinate.hashCode() : 0);
    hash = 97 * hash + (this.endCoordinate != null ? this.endCoordinate.hashCode() : 0);
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
    final Line other = (Line) obj;
    if (this.startCoordinate != other.startCoordinate && (this.startCoordinate == null || !this.startCoordinate.equals(other.startCoordinate))) {
      return false;
    }
    if (this.endCoordinate != other.endCoordinate && (this.endCoordinate == null || !this.endCoordinate.equals(other.endCoordinate))) {
      return false;
    }
    return true;
  }

  public String toLineString() {
    return "LineString(" + startCoordinate.toPointString() + "," + endCoordinate.toPointString() + ")";
  }

  @Override
  public String toString() {
    return "Line{(" + startCoordinate + "),(" + endCoordinate + ")}";
  }
}
