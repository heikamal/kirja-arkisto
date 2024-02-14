package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;

import java.util.List;

@RestController
@RequestMapping("/api/kirjahyllyt")
public class KirjaHyllyController {

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

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjahyllyille
}