/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation.series;

import com.anosym.jflemax.bean.validation.Sequence;
import com.anosym.jflemax.bean.validation.SequenceConstraintValidator;
import com.anosym.jflemax.bean.validation.SequenceOption;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author marembo
 */
public class SequenceConstraintValidatorTest {

  @Sequence(fields = {
    @SequenceOption(field = "id", sequenceId = "test_id", type = SequenceOption.SequenceType.SERIES)})
  public static class Series {

    private String id;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

  }

  @Sequence(fields = {
    @SequenceOption(
            field = "id",
            sequenceId = "test_padded_id",
            type = SequenceOption.SequenceType.SERIES,
            length = 7,
            prefix = "T")})
  public static class PaddedSeries {

    private String id;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

  }

  public SequenceConstraintValidatorTest() {
  }

  @Before
  public void setUp() {
    SeriesSequence ss = SeriesSequence.getInstance("test_id");
    ss.reset(3456);
    SeriesSequence sss = SeriesSequence.getInstance("test_padded_id");
    sss.reset(3456);
  }

  @After
  public void tearDown() {
  }

  @Test
  public void testIsValid() {
    Series s = new Series();
    SequenceConstraintValidator scv = new SequenceConstraintValidator();
    scv.initialize(s.getClass().getAnnotation(Sequence.class));
    boolean result = scv.isValid(s, null);
    assertTrue(result);
    assertEquals(s.id, "3457");
  }

  @Test
  public void testIsValid_PaddedSeries() {
    PaddedSeries s = new PaddedSeries();
    SequenceConstraintValidator scv = new SequenceConstraintValidator();
    scv.initialize(s.getClass().getAnnotation(Sequence.class));
    boolean result = scv.isValid(s, null);
    assertTrue(result);
    assertEquals(s.id, "T0003457");
  }

}
