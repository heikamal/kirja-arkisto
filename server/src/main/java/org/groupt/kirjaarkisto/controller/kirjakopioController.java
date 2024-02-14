package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.kirjakopio;
import org.groupt.kirjaarkisto.services.kirjakopioService;

import java.util.List;

@RestController
@RequestMapping("/api/kirjakopiot")
public class kirjakopioController {

    @Autowired
    private kirjakopioService kirjakopioService;

    @GetMapping
    public List<kirjakopio> getKirjakopiot() {
        return kirjakopioService.getKirjakopiot();
    }

    @GetMapping("/{id}")
    public kirjakopio getKirjakopio(@PathVariable Long id) {
        return kirjakopioService.getKirjakopioById(id);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjakopioille
}