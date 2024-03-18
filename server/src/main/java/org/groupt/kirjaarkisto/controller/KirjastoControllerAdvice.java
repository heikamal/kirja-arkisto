package org.groupt.kirjaarkisto.controller;

import org.groupt.kirjaarkisto.controller.errors.KirjaArkistoAppError;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.BadUserCredentialsException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaHyllyException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaKopioException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaSarjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaHyllyException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaKopioException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaSarjaException;
import org.groupt.kirjaarkisto.exceptions.UserNotFoundException;
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

  @Value("${kirjaarkisto.sendreport.uri}")
  private String sendReportUri;
  @Value("${kirjaarkisto.api.version}")
  private String currentApiVersion;

  @ExceptionHandler(BadIdException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<KirjaArkistoAppError> handleBadId() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.BAD_REQUEST.value()), 
      "Virheellinen ID!",
      "id-exceptions", 
      "Virheellinen ID.",
      "Bad request!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(BadUserCredentialsException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ResponseEntity<KirjaArkistoAppError> handleBadUserCredentials() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.UNAUTHORIZED.value()), 
      "Kirjautuminen epäonnistui!",
      "authorization-exceptions", 
      "Kirjautuminen epäonnistui.",
      "Unauthorized!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(NonExistingKirjaException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<KirjaArkistoAppError> handleNonExistingKirja() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.NOT_FOUND.value()), 
      "Kirjaa ei löydy!",
      "kirja-exceptions", 
      "Kirjaa ei löydy.",
      "Not found!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NonExistingKirjaHyllyException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<KirjaArkistoAppError> handleNonExistingKirjaHylly() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.NOT_FOUND.value()), 
      "Kirjahyllya ei löydy!",
      "kirjahylly-exceptions", 
      "Haettua kirjahyllyä ei löydy tietokannasta. Käyttäjä voi olla admin tai id ei palauta tietokannasta hyllyä.",
      "Not found!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(NonExistingKirjaKopioException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<KirjaArkistoAppError> handleNonExistingKirjaKopio() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.NOT_FOUND.value()), 
      "Kirjan kopio ei löydy.",
      "kirjakopio-exceptions", 
      "Kirjasta ei kopiota. Onko kirja varmasti hyllyssä?",
      "Not found!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

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

  @ExceptionHandler(NullKirjaException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<KirjaArkistoAppError> handleNullKirja() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.BAD_REQUEST.value()), 
      "Kirjaa on tyhjä kenttiä! Tarkista parametrit",
      "kirja-exceptions", 
      "Kirjaa tyhjä kenttiä.",
      "Bad request!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullKirjaHyllyException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<KirjaArkistoAppError> handleNullKirjaHylly() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.BAD_REQUEST.value()), 
      "Kirjahyllyllä on tyhjä kenttiä! Tarkista parametrit",
      "kirjahylly-exceptions", 
      "Kirjahyllyä ei voitu tallentaa tietokantaan.",
      "Bad request!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NullKirjaKopioException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ResponseEntity<KirjaArkistoAppError> handleNullKirjaKopio() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.BAD_REQUEST.value()), 
      "Kirjakopioon on tyhjä kenttiä! Tarkista parametrit",
      "kirjakopio-exceptions", 
      "Kirjakopioon tyhjä kenttiä.",
      "Bad request!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
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

  @ExceptionHandler(UserNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<KirjaArkistoAppError> handleUserNotFound() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.NOT_FOUND.value()), 
      "Käyttäjäa ei löytynyt!",
      "user-exceptions", 
      "Käyttäjäa ei ole tietokannassa",
      "Not found!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<KirjaArkistoAppError> handleException() {
    final KirjaArkistoAppError error = new KirjaArkistoAppError(
      currentApiVersion,
      Integer.toString(HttpStatus.INTERNAL_SERVER_ERROR.value()), 
      "Jotain meni pieleen!",
      "server-exceptions", 
      "Jotain meni pieleen.",
      "Internal server error!",
      sendReportUri
    );

    return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
