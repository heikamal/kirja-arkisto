package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.groupt.kirjaarkisto.services.kirjaService;
import org.groupt.kirjaarkisto.models.kirja;
import java.util.List;

@RestController
@RequestMapping("/api/kirjat")
public class kirjaController {

    @Autowired
    private kirjaService kirjaService;

    @GetMapping
    public List<kirja> getKirjat() {
        return kirjaService.getKirjat();
    }

    @GetMapping("/{id}")
    public kirja getKirja(@PathVariable Long id) {
        return kirjaService.getKirjaById(id);
    }
 
    public ResponseEntity<kirja> createKirja(@RequestBody kirja lisattava) {
        kirja lisattyKirja = kirjaService.addKirja(lisattava);
        return new ResponseEntity<>(lisattyKirja, HttpStatus.CREATED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKirja(@PathVariable Long id) {
        kirjaService.deleteKirja(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
