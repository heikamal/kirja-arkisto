package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;



import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.payload.HyllyResponse;
import org.groupt.kirjaarkisto.payload.KirjaKopioResponse;
import org.groupt.kirjaarkisto.payload.KirjaResponse;
import org.groupt.kirjaarkisto.payload.OmaSarjaDTO;
import org.groupt.kirjaarkisto.payload.SarjaResponse;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.groupt.kirjaarkisto.services.KirjaKopioService;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/kirjahyllyt")
public class KirjaHyllyController {

    @Autowired
    private KirjaSarjaService kirjaSarjaService;

    @Autowired
    private KirjaHyllyService kirjahyllyService;

    @Autowired
    private KirjaKopioService kirjaKopioService;

    @GetMapping
    public List<KirjaHylly> getKirjahyllyt() {
        return kirjahyllyService.getKirjahyllyt();
    }

    @GetMapping("/{id}")
    public KirjaHylly getKirjahylly(@PathVariable Long id) {
        return kirjahyllyService.getKirjahyllyById(id);
    }

    @GetMapping("/self")
    public HyllyResponse getOwnKirjaHylly() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      KirjaHylly hylly = kirjahyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

      List<SarjaResponse> sarjat = new ArrayList<>();

      List<KirjaKopioResponse> k = new ArrayList<>();

      for (KirjaSarja sarja : hylly.getOmatSarjat()) {
        List<KirjaKopio> kopiot = kirjaKopioService.getBySarja(sarja);
        List<KirjaKopioResponse> kopioResponseList = new ArrayList<>();
        for (KirjaKopio kopio : kopiot) {
          KirjaKopioResponse kopioResponse = new KirjaKopioResponse(kopio, new KirjaResponse(kopio.getBook()));
          kopioResponseList.add(kopioResponse);
          k.add(kopioResponse);
        }
        SarjaResponse a = new SarjaResponse(sarja, kopioResponseList, true);
        sarjat.add(a);
      }

      return new HyllyResponse(hylly, sarjat, k);
    }

    @PostMapping("/self")
    public KirjaHylly addSarja(@NonNull @RequestBody OmaSarjaDTO sarja) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      KirjaHylly hylly = kirjahyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

      List<KirjaSarja> sarjat = hylly.getOmatSarjat();
      boolean found = false;

      for(KirjaSarja k : sarjat) {
        if (Objects.equals(k.getId(), sarja.getSarjaId())) {
          found = true;
          break;
        }
      }

      if (!found) {
        hylly.addToOmatSarjat(kirjaSarjaService.getKirjasarjaById(sarja.getSarjaId()));
      }

      return kirjahyllyService.saveKirjaHylly(hylly);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjahyllyille
}