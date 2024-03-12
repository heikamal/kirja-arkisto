package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Virhe tilanteessa jossa käyttäjää ei löydy.
 */
public class UserNotFoundException extends RuntimeException implements ErrorCode {

  /**
   * Konstruktori, joka luo uuden olion annetun merkkijonon pohjalta.
   * 
   * @param message virheviesti merkkijonona.
   */
  public UserNotFoundException(final String message) {
    super(message);
  }

  /**
   * Palauttaa virhekoodin. Tälle virheelle se on "USRNF".
   * 
   * @return Virhekoodi merkkijonona.
   */
  @Override
  public String getErrorCode() {
    return "USRNF";
  }
}
