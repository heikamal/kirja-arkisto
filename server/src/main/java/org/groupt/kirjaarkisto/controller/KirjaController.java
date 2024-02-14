package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.services.KirjaService;
import org.groupt.kirjaarkisto.models.Kirja;
import java.util.List;

@RestController
@RequestMapping("/api/kirjat")
public class KirjaController {

    @Autowired
    private KirjaService kirjaService;

    @GetMapping
    public List<Kirja> getKirjat() {
        return kirjaService.getKirjat();
    }

    @GetMapping("/{id}")
    public Kirja getKirja(@PathVariable Long id) {
        return kirjaService.getKirjaById(id);
    }
 
    public ResponseEntity<Kirja> createKirja(@RequestBody Kirja lisattava) {
        Kirja lisattyKirja = kirjaService.addKirja(lisattava);
        return new ResponseEntity<>(lisattyKirja, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKirja(@PathVariable Long id) {
        kirjaService.deleteKirja(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
