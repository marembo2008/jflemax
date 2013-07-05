/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.anosym.jflemax.security;

import com.anosym.utilities.IdGenerator;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

/**
 * Currently, secure entity does not support primitive types nor their wrappers.
 *
 * @author marembo
 * @version 1.0.0
 */
@MappedSuperclass
public class SecureEntity {

  /**
   * The system property to be set when an application requires a unique, but system wide security
   * hash. The system must ensure that thie hash value is constant indefinitely. If this value
   * changes after an entity has been secured, there is no recovery mechanism.
   */
  public static final String SECURITY_HASH_PROPERTY = "com.anosym.secureentity.hash";
  private static final long serialVersionUID = IdGenerator.serialVersionUID(SecureEntity.class);
  private static final String SECURITY_HASH = IdGenerator.serialVersionUID(SecureEntity.class).toString();

  /**
   * Override this method to return a different security hash for the specified entity. However,
   * this hash must always be the same once used.
   *
   * Currently this returns a default SECURE HASH.
   *
   * @return the hash to be used for a secure entity class.
   */
  protected String getSecurityHash() {
    return System.getProperty(SECURITY_HASH_PROPERTY, SECURITY_HASH);
  }

  @PrePersist
  @PreUpdate
  public void onWrite() {
    try {
      com.anosym.utilities.security.SecurityManager securityManager = new com.anosym.utilities.security.SecurityManager(
              IdGenerator.generateStringId(getClass()), getSecurityHash());
      securityManager.encryptObject(this);
    } catch (InvalidKeyException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidAlgorithmParameterException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalBlockSizeException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ShortBufferException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BadPaddingException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @PostLoad
  @PostPersist
  @PostUpdate
  public void onRead() {
    try {
      com.anosym.utilities.security.SecurityManager securityManager = new com.anosym.utilities.security.SecurityManager(
              IdGenerator.generateStringId(getClass()), getSecurityHash());
      securityManager.decryptObject(this);
    } catch (ClassNotFoundException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidKeyException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (InvalidAlgorithmParameterException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalBlockSizeException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (ShortBufferException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (BadPaddingException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalArgumentException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IllegalAccessException ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    } catch (Exception ex) {
      Logger.getLogger(SecureEntity.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
