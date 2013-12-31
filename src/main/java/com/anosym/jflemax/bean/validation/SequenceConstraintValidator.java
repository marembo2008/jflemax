/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation;

import com.anosym.jflemax.bean.validation.series.SeriesSequence;
import com.anosym.utilities.Utility;
import java.lang.reflect.Field;
import java.util.Random;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 *
 * @author marembo
 */
public class SequenceConstraintValidator implements ConstraintValidator<Sequence, Object> {

  private Sequence sequence;

  public void initialize(Sequence constraintAnnotation) {
    this.sequence = constraintAnnotation;
  }

  private Field getField(Class c, String field) {
    try {
      return c.getDeclaredField(field);
    } catch (NoSuchFieldException e) {
      if (c == Object.class) {
        return null;
      } else {
        return getField(c.getSuperclass(), field);
      }
    }
  }

  @SuppressWarnings({"UseSpecificCatch", "BroadCatchBlock", "TooBroadCatch"})
  public boolean isValid(Object value, ConstraintValidatorContext context) {
    //get the fields and then set them.
    for (SequenceOption so : sequence.fields()) {
      try {
        String fName = so.field();
        Field f = getField(value.getClass(), fName);
        f.setAccessible(true);
        if (f.get(value) != null) {
          return true;
        }
        switch (so.type()) {
          case GENERATOR:
            throw new UnsupportedOperationException("Not supported yet");
          case RANDOM:
            Random r = new Random(System.currentTimeMillis());
            String v = r.nextLong() + "";
            if (!Utility.isNullOrEmpty(so.prefix())) {
              v = so.prefix() + v;
            }
            f.set(value, v);
            return true;
          case SERIES:
            String sequenceId = so.sequenceId();
            SeriesSequence ss = SeriesSequence.getInstance(sequenceId);
            if (ss != null) {
              String next = ss.increaseAndGet() + "";
              if (so.length() > 0) {
                int l = next.length();
                for (int i = 0; i < so.length() - l; i++) {
                  next = '0' + next;
                }
              }
              if (!Utility.isNullOrEmpty(so.prefix())) {
                next = so.prefix() + next;
              }
              f.set(value, next);
              return true;
            } else {
              throw new RuntimeException("Error initializing sequence generator");
            }
        }
      } catch (Exception ex) {
        throw new RuntimeException(ex);
      }
    }
    return false;
  }

}
