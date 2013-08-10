/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.jpa;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 *
 * @author marembo
 */
public class JpaUtil {

  private static Field getIdField(Class<?> cls) {
    for (Field f : cls.getDeclaredFields()) {
      if (f.getAnnotation(Id.class) != null) {
        return f;
      }
    }
    //if we reach here, we try the super class only if it is annotated @Entity or @MappedSupperclass
    cls = cls.getSuperclass();
    if (cls.getAnnotation(Entity.class) != null || cls.getAnnotation(MappedSuperclass.class) != null) {
      return getIdField(cls);
    }
    return null;
  }

  private static Method getIdMethod(Class<?> cls) {
    for (Method m : cls.getMethods()) {
      if (m.getAnnotation(Id.class) != null) {
        return m;
      }
    }
    return null;
  }

  public static String getEntityId(Object entity) {
    String idValue = null;
    try {
      Field f = getIdField(entity.getClass());
      if (f != null) {
        f.setAccessible(true);
        Object idValue_ = f.get(entity);
        if (idValue_ != null) {
          idValue = idValue_.toString();
        }
      } else {
        Method m = getIdMethod(entity.getClass());
        if (m != null) {
          m.setAccessible(true);
          Object idValue_ = m.invoke(entity, new Object[]{});
          if (idValue_ != null) {
            idValue = idValue_.toString();
          }
        }
      }
    } catch (Exception e) {
      Logger.getLogger(JpaUtil.class.getName()).log(Level.SEVERE, null, e);
    }
    return idValue;
  }
}
