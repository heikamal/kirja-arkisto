package org.groupt.kirjaarkisto.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Testikontrolleri kehitystarkoituksiin. Pls ignore.
 * 
 * @author Heikki Malkavaara
 *
 */
@RestController
public class ApiController {

  /**
   * Käyttää @GetMapping -annotaatiota kartoittaakseen metodin GET-pyyntöön osoitteessa "/api". 
   * Metodi palauttaa merkkijonon "Harri, voisitko hakee mulle tulisiemeniä?".
   *
   * @return         	Merkkijono "Harri, voisitko hakee mulle tulisiemeniä?"
   */
  @GetMapping("/api")
  public String api() {
    return "Harri, voisitko hakee mulle tulisiemeniä? Niitä sais harvinaisesta kasvista, joka kasvaa mettässä.";
  }
}
