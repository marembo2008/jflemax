/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;

/**
 * This is a simple wrapper over jpa String which is by default converted to varchar(255)
 *
 * @author marembo
 */
public class Text implements Serializable, CharSequence {

  private String value;

  public Text(String value) {
    this.value = value;
  }

  public Text() {
    this("");
  }

  public int length() {
    return value.length();
  }

  public boolean isEmpty() {
    return value.isEmpty();
  }

  public char charAt(int index) {
    return value.charAt(index);
  }

  public int codePointAt(int index) {
    return value.codePointAt(index);
  }

  public int codePointBefore(int index) {
    return value.codePointBefore(index);
  }

  public int codePointCount(int beginIndex, int endIndex) {
    return value.codePointCount(beginIndex, endIndex);
  }

  public int offsetByCodePoints(int index, int codePointOffset) {
    return value.offsetByCodePoints(index, codePointOffset);
  }

  public void getChars(int srcBegin, int srcEnd, char[] dst, int dstBegin) {
    value.getChars(srcBegin, srcEnd, dst, dstBegin);
  }

  @Deprecated
  public void getBytes(int srcBegin, int srcEnd, byte[] dst, int dstBegin) {
    value.getBytes(srcBegin, srcEnd, dst, dstBegin);
  }

  public byte[] getBytes(String charsetName) throws UnsupportedEncodingException {
    return value.getBytes(charsetName);
  }

  public byte[] getBytes(Charset charset) {
    return value.getBytes(charset);
  }

  public byte[] getBytes() {
    return value.getBytes();
  }

  public boolean contentEquals(StringBuffer sb) {
    return value.contentEquals(sb);
  }

  public boolean contentEquals(CharSequence cs) {
    return value.contentEquals(cs);
  }

  public boolean equalsIgnoreCase(String anotherString) {
    return value.equalsIgnoreCase(anotherString);
  }

  public int compareTo(String anotherString) {
    return value.compareTo(anotherString);
  }

  public int compareToIgnoreCase(String str) {
    return value.compareToIgnoreCase(str);
  }

  public boolean regionMatches(int toffset, String other, int ooffset, int len) {
    return value.regionMatches(toffset, other, ooffset, len);
  }

  public boolean regionMatches(boolean ignoreCase, int toffset, String other, int ooffset, int len) {
    return value.regionMatches(ignoreCase, toffset, other, ooffset, len);
  }

  public boolean startsWith(String prefix, int toffset) {
    return value.startsWith(prefix, toffset);
  }

  public boolean startsWith(String prefix) {
    return value.startsWith(prefix);
  }

  public boolean endsWith(String suffix) {
    return value.endsWith(suffix);
  }

  @Override
  public int hashCode() {
    int hash = 5;
    hash = 53 * hash + (this.value != null ? this.value.hashCode() : 0);
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
    final Text other = (Text) obj;
    if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
      return false;
    }
    return true;
  }

  public int indexOf(int ch) {
    return value.indexOf(ch);
  }

  public int indexOf(int ch, int fromIndex) {
    return value.indexOf(ch, fromIndex);
  }

  public int lastIndexOf(int ch) {
    return value.lastIndexOf(ch);
  }

  public int lastIndexOf(int ch, int fromIndex) {
    return value.lastIndexOf(ch, fromIndex);
  }

  public int indexOf(String str) {
    return value.indexOf(str);
  }

  public int indexOf(String str, int fromIndex) {
    return value.indexOf(str, fromIndex);
  }

  public int lastIndexOf(String str) {
    return value.lastIndexOf(str);
  }

  public int lastIndexOf(String str, int fromIndex) {
    return value.lastIndexOf(str, fromIndex);
  }

  public Text substring(int beginIndex) {
    return new Text(value.substring(beginIndex));
  }

  public Text substring(int beginIndex, int endIndex) {
    return new Text(value.substring(beginIndex, endIndex));
  }

  public CharSequence subSequence(int beginIndex, int endIndex) {
    return new Text((String) value.subSequence(beginIndex, endIndex));
  }

  public Text concat(String str) {
    return new Text(value.concat(str));
  }

  public Text replace(char oldChar, char newChar) {
    return new Text(value.replace(oldChar, newChar));
  }

  public boolean matches(String regex) {
    return value.matches(regex);
  }

  public boolean contains(CharSequence s) {
    return value.contains(s);
  }

  public Text replaceFirst(String regex, String replacement) {
    return new Text(value.replaceFirst(regex, replacement));
  }

  public Text replaceAll(String regex, String replacement) {
    return new Text(value.replaceAll(regex, replacement));
  }

  public Text replace(CharSequence target, CharSequence replacement) {
    return new Text(value.replace(target, replacement));
  }

  public Text[] split(String regex, int limit) {
    String[] ss = value.split(regex, limit);
    Text[] txts = new Text[ss.length];
    for (int i = 0; i < ss.length; i++) {
      txts[i] = new Text(ss[i]);
    }
    return txts;
  }

  public Text[] split(String regex) {
    String[] ss = value.split(regex);
    Text[] txts = new Text[ss.length];
    for (int i = 0; i < ss.length; i++) {
      txts[i] = new Text(ss[i]);
    }
    return txts;
  }

  public Text toLowerCase(Locale locale) {
    return new Text(value.toLowerCase(locale));
  }

  public Text toLowerCase() {
    return new Text(value.toLowerCase());
  }

  public Text toUpperCase(Locale locale) {
    return new Text(value.toUpperCase(locale));
  }

  public Text toUpperCase() {
    return new Text(value.toUpperCase());
  }

  public Text trim() {
    return new Text(value.trim());
  }

  @Override
  public String toString() {
    return value;
  }

  public char[] toCharArray() {
    return value.toCharArray();
  }

  public Text intern() {
    return new Text(value.intern());
  }
}
