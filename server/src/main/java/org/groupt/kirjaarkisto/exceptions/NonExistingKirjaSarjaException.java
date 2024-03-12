package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Virheluokka tilanteelle jossa etsittyä kirjasarjaa ei ole saatavilla.
 */
public class NonExistingKirjaSarjaException extends RuntimeException implements ErrorCode {

  public NonExistingKirjaSarjaException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "KS-NF";
  }

}
