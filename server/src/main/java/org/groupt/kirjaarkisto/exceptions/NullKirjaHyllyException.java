package org.groupt.kirjaarkisto.exceptions;

import org.groupt.kirjaarkisto.controller.errors.ErrorCode;

public class NullKirjaHyllyException extends RuntimeException implements ErrorCode{
  public NullKirjaHyllyException(final String message) {
    super(message);
  }

  @Override
  public String getErrorCode() {
    return "KH-N";
  }
  
}
