package org.groupt.kirjaarkisto.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Testikontrolleri kehitystarkoituksiin. Pls ignore.
 * 
 * @author Heikki Malkavaara
 *
 */
@RestController
@RequestMapping("/api/test")
public class ApiController {

  /**
   * Käyttää @GetMapping -annotaatiota kartoittaakseen metodin GET-pyyntöön osoitteessa "/api". Metodi ollaan suunniteltu lähinnä testaustarkoituksiin.
   *
   * @return Kuvaus, joka pitää sisällään uniikin id:n ja merkkijonon "Harri, voisitko hakee mulle tulisiemeniä? Niää sais harvinaisesta kasvista, joka kasvaa mettässä."
   */
  @GetMapping("/")
  public Map<String,Object> api() {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Harri, voisitko hakee mulle tulisiemeniä? Niitä sais harvinaisesta kasvista, joka kasvaa mettässä.");
    return model;
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
  public Map<String,Object> userAccess() {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Olet käyttäjä, Harri.");
    return model;
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public Map<String,Object> adminAccess() {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("id", UUID.randomUUID().toString());
    model.put("content", "Halusin vain ilmoittaa että olet kirjautunut sisään adminina.");
    return model;
  }
}
