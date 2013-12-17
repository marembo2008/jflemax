/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.bean.validation.series;

import com.anosym.vjax.VXMLBindingException;
import com.anosym.vjax.annotations.v3.Converter;
import com.anosym.vjax.v3.VObjectMarshaller;
import com.anosym.vjax.xml.VDocument;
import java.io.File;
import java.util.concurrent.atomic.AtomicLong;

/**
 *
 * @author marembo
 */
public class SeriesSequence {

  public static class AtomicLongConverter implements com.anosym.vjax.converter.v3.Converter<AtomicLong, Long> {

    public Long convertFrom(AtomicLong value) {
      return value.get();
    }

    public AtomicLong convertTo(Long value) {
      return new AtomicLong(value);
    }

  }
  @Converter(AtomicLongConverter.class)
  @SuppressWarnings("FieldMayBeFinal")
  private AtomicLong current;
  @SuppressWarnings("FieldMayBeFinal")
  private String sequenceId;

  public SeriesSequence() {
  }

  public SeriesSequence(String sequenceId) {
    current = new AtomicLong(0);
    this.sequenceId = sequenceId;
  }

  public long increaseAndGet() {
    long l = current.incrementAndGet();
    persist();
    return l;
  }

  protected void reset() {
    current.set(0);
    persist();
  }

  protected void reset(long value) {
    current.set(value);
    persist();
  }

  public static SeriesSequence getInstance(String sequenceId) {
    String path = "series_" + sequenceId + ".xml";
    VObjectMarshaller<SeriesSequence> vom = new VObjectMarshaller<SeriesSequence>(SeriesSequence.class);
    File file = new File(path);
    if (file.exists()) {
      try {
        VDocument doc = VDocument.parseDocument(file);
        return vom.unmarshall(doc);
      } catch (VXMLBindingException ex) {
        throw new RuntimeException(ex);
      }
    } else {
      SeriesSequence ss = new SeriesSequence(sequenceId);
      ss.persist();
      return ss;
    }
  }

  private synchronized void persist() {
    String path = "series_" + sequenceId + ".xml";
    VObjectMarshaller<SeriesSequence> vom = new VObjectMarshaller<SeriesSequence>(SeriesSequence.class);
    VDocument doc = vom.marshall(this);
    doc.setDocumentName(new File(path));
    doc.writeDocument();
  }
}
