/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

/**
 *
 * @author marembo
 */
public @interface SequenceOption {

  public static enum SequenceType {

    RANDOM,
    SERIES,
    GENERATOR;
  }

  /**
   * The field to generate its value.
   *
   * Must be of type {@link String}
   *
   * @return
   */
  String field();

  SequenceType type() default SequenceType.RANDOM;

  /**
   * Must be unique within the context of all sequences.
   *
   * @return
   */
  String sequenceId();

  /**
   * The length of the sequence such that if the generated value is of lower length, it will be
   * padded with '0'; Only applies to {@link SequenceType#SERIES}
   *
   * @return
   */
  int length() default 0;

  /**
   * Prefix to be appended to the generated sequence.
   *
   * @return
   */
  String prefix() default "";
}
