package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Virheluokka tilanteelle jossa kirjakopioa ei ole olemassa
 */
public class NonExistingKirjaKopioException extends RuntimeException implements ErrorCode{

  /**
   * Alustaja
   * @param message Virheen viesti.
   */
  public NonExistingKirjaKopioException(final String message) {
    super(message);
  }

  /**
   * Palauttaa virheen koodin.
   * 
   * @return Virheen koodi. Tässä tapauksessa KK-NF (KirjaKopio Not Found)
   */
  @Override
  public String getErrorCode() {
    return "KK-NF";
  }
}

