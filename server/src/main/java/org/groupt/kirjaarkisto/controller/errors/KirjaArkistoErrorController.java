package org.groupt.kirjaarkisto.controller.errors;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * Luokka määrittää oletusvirheen, joka tulee kun yritetään hakea apista osoitetta, jota ei ole olemassakaan, ns. "whitelabel"-virhe.
 */
@RestController
@RequestMapping(KirjaArkistoErrorController.ERROR_PATH)
public class KirjaArkistoErrorController extends AbstractErrorController {

  static final String ERROR_PATH = "/error";

  public KirjaArkistoErrorController(final ErrorAttributes errorAttributes) {
    super(errorAttributes, Collections.emptyList());
  }

  @RequestMapping
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
    Map<String, Object> body = this.getErrorAttributes(request, ErrorAttributeOptions.defaults());
    HttpStatus status = this.getStatus(request);
    return new ResponseEntity<>(body, status);
  }

  public String getErrorPath() {
    return ERROR_PATH;
  }
  
}
