package org.groupt.kirjaarkisto.controller;

import org.groupt.kirjaarkisto.controller.errors.KirjaArkistoAppError;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaSarjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaSarjaException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Luokka, määrittämään sovelluksen virheenkäsittelymetodit.
 */
@RestControllerAdvice
public class KirjastoControllerAdvice {

  //TODO: nämä enveiksi
  //@Value("${kirjaarkisto.sendreport.uri}")
  private String sendReportUri = "Huuda 'Heikki auta' niin kovaa kuin keuhkoista lähtee";
  //@Value("${kirjaarkisto.api.version}")
  private String currentApiVersion = "0.1";

  @ExceptionHandler(NonExistingKirjaSarjaException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<KirjaArkistoAppError> handeNonExistingKirjaSarja() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.NOT_FOUND.value()), 
      "Kirjasarjaa ei löydy!",
      "kirjasarja-exceptions", 
      "Hakemaasi kirjasarjaa ei löydy tietokannasta.",
      "Not found!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NullKirjaSarjaException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<KirjaArkistoAppError> handleNullKirjaSarja() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.BAD_REQUEST.value()), 
      "Kirjasarjassa on tyhjiä kenttiä! Tarkista parametrit",
      "kirjasarja-exceptions", 
      "Kirjasarjassa tyhjiä kenttiä.",
      "Bad request!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }
}
