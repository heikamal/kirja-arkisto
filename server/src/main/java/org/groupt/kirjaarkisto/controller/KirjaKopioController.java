package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.services.KirjaKopioService;

import java.util.List;

@RestController
@RequestMapping("/api/kirjakopiot")
public class KirjaKopioController {

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
    public KirjaKopio addKirjaKopio(@RequestBody KirjaKopio kirjakopio) {
        return kirjakopioService.saveKirjaKopio(kirjakopio);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjakopioille
}