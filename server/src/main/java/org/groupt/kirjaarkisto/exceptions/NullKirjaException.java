package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Luokka virheelle kun annettu kirja on ollut tyhjä.
 */
public class NullKirjaException extends RuntimeException implements ErrorCode {
  /**
   * Luokan alustaja, joka luo uuden virheen sille annetun viestin pohjalta.
   * @param message
   */
  public NullKirjaException(final String message) {
    super(message);
  }

  /**
   * Palautta virhekoodin. Tälle virheelle se on "NullBook".
   * 
   * @return Virhekoodi merkkijonona.
   */
  @Override
  public String getErrorCode() {
    return "K-N";
  }
}
