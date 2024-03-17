package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

public class NonExistingKirjaHyllyException extends RuntimeException implements ErrorCode {
  
  public NonExistingKirjaHyllyException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "KH-NF";
  }
}
