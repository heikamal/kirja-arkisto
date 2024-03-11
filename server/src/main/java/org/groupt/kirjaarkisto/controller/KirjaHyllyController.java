package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.payload.OmaSarjaDTO;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@RestController
@RequestMapping("/api/kirjahyllyt")
public class KirjaHyllyController {

    @Autowired
    private KirjaSarjaService kirjaSarjaService;

    @Autowired
    private KirjaHyllyService kirjahyllyService;

    @GetMapping
    public List<KirjaHylly> getKirjahyllyt() {
        return kirjahyllyService.getKirjahyllyt();
    }

    @GetMapping("/{id}")
    public KirjaHylly getKirjahylly(@PathVariable Long id) {
        return kirjahyllyService.getKirjahyllyById(id);
    }

    @GetMapping("/self")
    public KirjaHylly getOwnKirjaHylly() {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      return kirjahyllyService.getKirjaHyllyByOmistaja(userDetails.getId());
    }

    @PostMapping("/self")
    public KirjaHylly addSarja(@NonNull @RequestBody OmaSarjaDTO sarja) {
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      KirjaHylly hylly = kirjahyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

      hylly.addToOmatSarjat(kirjaSarjaService.getKirjasarjaById(sarja.getSarjaId()));

      return kirjahyllyService.saveKirjaHylly(hylly);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjahyllyille
}