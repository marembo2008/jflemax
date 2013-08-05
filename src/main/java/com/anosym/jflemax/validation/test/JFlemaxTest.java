/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.test;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author marembo
 */
public class JFlemaxTest {

  public static void main(String[] args) {
    final Pattern p = Pattern.compile("[^/]/");
    final String s = "/accounts/main/index/page.xhtml";
    final Matcher m = p.matcher(s);
    while (m.find()) {
      System.out.println(s.substring(0, m.end()));
    }
  }
}
