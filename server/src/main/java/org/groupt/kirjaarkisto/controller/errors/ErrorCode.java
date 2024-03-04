package org.groupt.kirjaarkisto.controller.errors;

public interface ErrorCode {

  /**
   * Tarjoaa sovelluksessa tapahtuvan virheen koodin.
   *
   * @return Lyhyt merkkijono tunnisteena virheelle.
   */
  String getErrorCode();
}
