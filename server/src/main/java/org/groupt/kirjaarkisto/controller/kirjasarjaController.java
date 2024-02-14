package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.kirjasarja;
import java.util.List;
import org.groupt.kirjaarkisto.services.kirjasarjaService;

@RestController
@RequestMapping("/api/kirjasarjat")
public class kirjasarjaController {

    @Autowired
    private kirjasarjaService kirjasarjaService;

    @GetMapping
    public List<kirjasarja> getKirjasarjat() {
        return kirjasarjaService.getKirjasarjat();
    }

    @GetMapping("/{id}")
    public kirjasarja getKirjasarja(@PathVariable Long id) {
        return kirjasarjaService.getKirjasarjaById(id);
    }

    // lisää tarvittavat endpointit post,put,delete
}