package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import java.util.List;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;

@RestController
@RequestMapping("/api/kirjasarjat")
public class KirjaSarjaController {

    @Autowired
    private KirjaSarjaService kirjasarjaService;

    @GetMapping
    public List<KirjaSarja> getKirjasarjat() {
        return kirjasarjaService.getKirjasarjat();
    }

    @GetMapping("/{id}")
    public KirjaSarja getKirjasarja(@PathVariable Long id) {
        return kirjasarjaService.getKirjasarjaById(id);
    }

    // lisää tarvittavat endpointit post,put,delete
}