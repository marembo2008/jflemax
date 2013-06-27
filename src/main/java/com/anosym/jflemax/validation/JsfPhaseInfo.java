/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation;

import com.anosym.jflemax.validation.annotation.JsfPhaseId;
import com.anosym.jflemax.validation.annotation.JsfPhaseIdOption;

/**
 *
 * @author marembo
 */
public class JsfPhaseInfo {

  private JsfPhaseId phaseId;
  private JsfPhaseIdOption phaseIdOption;

  public JsfPhaseInfo() {
  }

  public JsfPhaseInfo(JsfPhaseId phaseId, JsfPhaseIdOption phaseIdOption) {
    this.phaseId = phaseId;
    this.phaseIdOption = phaseIdOption;
  }

  public JsfPhaseId getPhaseId() {
    return phaseId;
  }

  public void setPhaseId(JsfPhaseId phaseId) {
    this.phaseId = phaseId;
  }

  public JsfPhaseIdOption getPhaseIdOption() {
    return phaseIdOption;
  }

  public void setPhaseIdOption(JsfPhaseIdOption phaseIdOption) {
    this.phaseIdOption = phaseIdOption;
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 97 * hash + (this.phaseId != null ? this.phaseId.hashCode() : 0);
    hash = 97 * hash + (this.phaseIdOption != null ? this.phaseIdOption.hashCode() : 0);
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
    final JsfPhaseInfo other = (JsfPhaseInfo) obj;
    if (this.phaseId != other.phaseId) {
      return false;
    }
    if (this.phaseIdOption != other.phaseIdOption) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "JsfPhaseInfo{" + "phaseId=" + phaseId + ", phaseIdOption=" + phaseIdOption + '}';
  }
}
