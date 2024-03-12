package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

/**
 * Virheluokka tilanteelle, jossa annettu Kirjakopio on tyhjä.
 */
public class NullKirjaKopioException extends RuntimeException implements ErrorCode{

  /**
   * Luo uuden olion virheviestin pohjalta.
   * 
   * @param message Virheen viesti.
   */
  public NullKirjaKopioException(final String message) {
    super(message);
  }
  
  /**
   * Palauttaa virheen koodin.
   * 
   * @return Virheen koodi. Tässä tapauksessa KK-N (KirjaKopio Null)
   */
  @Override
  public String getErrorCode() {
    return "KK-N";
  }
  
}
