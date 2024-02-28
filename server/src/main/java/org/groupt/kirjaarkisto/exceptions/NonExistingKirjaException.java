package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

public class NonExistingKirjaException extends RuntimeException implements ErrorCode{
  public NonExistingKirjaException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "K-NF";
  }
}
