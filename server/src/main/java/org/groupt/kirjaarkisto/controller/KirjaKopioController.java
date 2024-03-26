package org.groupt.kirjaarkisto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.models.Valokuva;
import org.groupt.kirjaarkisto.payload.KirjaKopioDTO;
import org.groupt.kirjaarkisto.payload.KirjaKopioResponse;
import org.groupt.kirjaarkisto.payload.KirjaResponse;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.groupt.kirjaarkisto.services.KirjaKopioService;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.groupt.kirjaarkisto.services.KirjaService;

import java.util.ArrayList;
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
  public List<KirjaKopioResponse> getKirjakopiot() {
    List<KirjaKopio> kopiot = kirjakopioService.getKirjakopiot();
    List<KirjaKopioResponse> vastaus = new ArrayList<>();

    for (KirjaKopio kirjakopio : kopiot) {
      Kirja kirja = kirjaService.getKirjaById(kirjakopio.getBook().getId());
      vastaus.add(new KirjaKopioResponse(kirjakopio, new KirjaResponse(kirja), kirjakopioService.getValokuvatByKirjaKopio(kirjakopio)));
    }

    return vastaus;
  }

  @GetMapping("/{id}")
  public KirjaKopioResponse getKirjakopio(@PathVariable Long id) {
    KirjaKopio kirjakopio = kirjakopioService.getKirjakopioById(id);
    Kirja kirja = kirjaService.getKirjaById(kirjakopio.getBook().getId());
    return new KirjaKopioResponse(kirjakopio, new KirjaResponse(kirja), kirjakopioService.getValokuvatByKirjaKopio(kirjakopio));
  }

  @PostMapping
  public KirjaKopio addKirjaKopio(@RequestBody KirjaKopioDTO kirjaKopio) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    KirjaHylly hylly = kirjaHyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

    List<KirjaSarja> sarjat = hylly.getOmatSarjat();

    boolean found = false;

    for (KirjaSarja sarja : sarjat) {
      if (Objects.equals(sarja.getId(), kirjaKopio.getIdKirjaSarja())) {
        found = true;
        break;
      }
    }

    if (!found) {
      hylly.addToOmatSarjat(kirjaSarjaService.getKirjasarjaById(kirjaKopio.getIdKirjaSarja()));
      kirjaHyllyService.saveKirjaHylly(hylly);
    }

    Kirja kirja = kirjaService.getKirjaByIdEncrypted(kirjaKopio.getKirjaId());

    KirjaKopio kopio = new KirjaKopio(kirjaKopio, kirja, hylly.getId());

    return kirjakopioService.saveKirjaKopio(kopio);
  }

  @GetMapping("/{kirjakopioId}/kuvat")
  public List<Valokuva> haeKopionKuvat(@PathVariable Long kirjakopioId) {
    KirjaKopio kirjakopio = kirjakopioService.getKirjakopioById(kirjakopioId);
    return kirjakopioService.getValokuvatByKirjaKopio(kirjakopio);
  }

  @PostMapping("/{kirjakopioId}/kuvat")
  public ResponseEntity<String> lisaaValokuvaKirjakopiolle(
      @PathVariable Long kirjakopioId,
      @RequestParam("tiedosto") MultipartFile tiedosto,
      @RequestParam("julkaisuvuosi") Integer julkaisuvuosi,
      @RequestParam("taiteilija") String taiteilija,
      @RequestParam("tyyli") String tyyli,
      @RequestParam("kuvaus") String kuvaus,
      @RequestParam("sivunro") Integer sivunro,
      @RequestParam("kuvannimi") String kuvannimi) throws java.io.IOException {
    try {
      kirjakopioService.lisaaKuvaKirjakopiolle(kirjakopioId, tiedosto, julkaisuvuosi, taiteilija, tyyli, kuvaus,
          sivunro, kuvannimi);
      return ResponseEntity.ok("Valokuva lisätty kirjakopiolle onnistuneesti.");
    } catch (EntityNotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kirjakopiota ei löydy id:llä " + kirjakopioId);
    } catch (IOException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Tiedoston tallentaminen epäonnistui.");
    }
  }

  @PutMapping("/{kirjakopioId}")
  public KirjaKopio updateKirjakopio(@PathVariable Long kirjakopioId, @RequestBody KirjaKopioDTO kirjakopio) {


    KirjaKopio kopio = kirjakopioService.getKirjakopioById(kirjakopioId);

    System.out.println(kopio);

    kopio.setTitle(kirjakopio.getNimi());
    kopio.setEditions(kirjakopio.getPainos());
    kopio.setEditionYear(kirjakopio.getPainosVuosi());
    kopio.setPurchasePrice(kirjakopio.getOstoHinta());
    kopio.setPurchaseDate(kirjakopio.getOstoPvm());
    kopio.setCondition(kirjakopio.getKunto());
    kopio.setDescription(kirjakopio.getKuvaus());
    kopio.setSaleDate(kirjakopio.getMyyntiPvm());
    kopio.setSalePrice(kirjakopio.getMyyntiHinta());

    System.out.println(kopio);

    return kirjakopioService.saveKirjaKopio(kopio);
  }

  @DeleteMapping("/{kirjakopioId}")
  public ResponseEntity<?> deleteKirjakopio(@PathVariable Long kirjakopioId) {

    KirjaKopio poistettava = kirjakopioService.getKirjakopioById(kirjakopioId);

    List<Valokuva> valukuvat = kirjakopioService.getValokuvatByKirjaKopio(poistettava);

    for (Valokuva valokuva : valukuvat) {
      kirjakopioService.poistaValokuva(valokuva.getIdkuva());;
    }

    try {
      kirjakopioService.remove(poistettava);
      return ResponseEntity.ok(poistettava.getTitle() + " on poistettu onnistuneesti");
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Poistaminen epäonnistui");
    }
  }
}