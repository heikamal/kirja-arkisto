package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.kirjahylly;
import org.groupt.kirjaarkisto.services.kirjahyllyService;

import java.util.List;

@RestController
@RequestMapping("/api/kirjahyllyt")
public class kirjahyllyController {

    @Autowired
    private kirjahyllyService kirjahyllyService;

    @GetMapping
    public List<kirjahylly> getKirjahyllyt() {
        return kirjahyllyService.getKirjahyllyt();
    }

    @GetMapping("/{id}")
    public kirjahylly getKirjahylly(@PathVariable Long id) {
        return kirjahyllyService.getKirjahyllyById(id);
    }

    // Lisää tarvittavat endpointit (POST, PUT, DELETE) kirjahyllyille
}