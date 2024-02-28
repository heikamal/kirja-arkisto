package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

public class BadIdException extends RuntimeException implements ErrorCode {

  public BadIdException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "BADID";
  }
  
}
