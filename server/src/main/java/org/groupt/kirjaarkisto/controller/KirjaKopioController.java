package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.payload.KirjaKopioDTO;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.groupt.kirjaarkisto.services.KirjaKopioService;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.groupt.kirjaarkisto.services.KirjaService;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/kirjakopiot")
public class KirjaKopioController {

  @Autowired
  private KirjaHyllyService kirjaHyllyService;

  @Autowired
  private KirjaService kirjaService;

  @Autowired
  private KirjaSarjaService kirjaSarjaService;

    @Autowired
    private KirjaKopioService kirjakopioService;

    @GetMapping
    public List<KirjaKopio> getKirjakopiot() {
        return kirjakopioService.getKirjakopiot();
    }

    @GetMapping("/{id}")
    public KirjaKopio getKirjakopio(@PathVariable Long id) {
        return kirjakopioService.getKirjakopioById(id);
    }

    @PostMapping
    public KirjaKopio addKirjaKopio(@RequestBody KirjaKopioDTO kirjaKopio) {

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      KirjaHylly hylly = kirjaHyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

      List<KirjaSarja> sarjat = hylly.getOmatSarjat();

      boolean found = false;

      for(KirjaSarja sarja : sarjat) {
        if (Objects.equals(sarja.getId(), kirjaKopio.getIdKirjaSarja())) {
          found = true;
          break;
        }
      }

      if (!found) {
        hylly.addToOmatSarjat(kirjaSarjaService.getKirjasarjaById(kirjaKopio.getIdKirjaSarja()));
        kirjaHyllyService.saveKirjaHylly(hylly);
      }

      Kirja kirja = kirjaService.getKirjaById(kirjaKopio.getKirjaId());


      KirjaKopio kopio = new KirjaKopio(kirjaKopio, kirja, hylly.getId());

      return kirjakopioService.saveKirjaKopio(kopio);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjakopioille
}