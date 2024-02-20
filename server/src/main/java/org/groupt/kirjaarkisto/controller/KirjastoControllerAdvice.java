package org.groupt.kirjaarkisto.controller;

import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaSarjaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Luokka, määrittämään sovelluksen virheenkäsittelymetodit.
 */

public class KirjastoControllerAdvice {

  @ExceptionHandler(NonExistingKirjaSarjaException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public void handeNonExistingKirjaSarja() {
    // testailen vielä
  }
  
}
