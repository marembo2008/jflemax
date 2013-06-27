/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.validation.annotation;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * @author marembo
 */
public class DefaultJsfPhaseAnnotationImp implements InvocationHandler {

  public static final JsfPhase DEFAULT_JSF_PHASE = DefaultJsfPhaseAnnotationImp.of(JsfPhase.class);

  public static <A extends Annotation> A of(Class<A> annotation) {
    return (A) Proxy.newProxyInstance(annotation.getClassLoader(),
            new Class[]{annotation}, new DefaultJsfPhaseAnnotationImp());
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args)
          throws Throwable {
    return method.getDefaultValue();
  }
}
