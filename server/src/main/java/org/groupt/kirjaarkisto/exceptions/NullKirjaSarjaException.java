package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Luokka virheelle kun annettu objekti on ollut tyhjä, toisin sanoen null-objekti.
 */
public class NullKirjaSarjaException extends RuntimeException implements ErrorCode {

  /**
   * Luokan alustaja, joka luo uuden virheen sille annetun viestin pohjalta.
   * @param message
   */
  public NullKirjaSarjaException(final String message) {
    super(message);
  }

  /**
   * Palautta virhekoodin. Tälle virheelle se on "NullBookSeries".
   * 
   * @return Virhekoodi merkkijonona.
   */
  @Override
  public String getErrorCode() {
    return "KS-N";
  }
}
