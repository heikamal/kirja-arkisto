package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

public class BadUserCredentialsException extends RuntimeException implements ErrorCode{

  public BadUserCredentialsException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "BADCRED";
  }
  
}
