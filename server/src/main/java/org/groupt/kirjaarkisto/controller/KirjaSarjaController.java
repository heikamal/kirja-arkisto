package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import jakarta.persistence.EntityNotFoundException;

import org.groupt.kirjaarkisto.models.KirjaSarja;
import java.util.List;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;

@RestController
@RequestMapping("/api/kirjasarjat")
public class KirjaSarjaController {

    @Autowired
    private KirjaSarjaService kirjaSarjaService;

    /**
     * Metodi määrittämään GET-endpointti, joka palauttaa kaikki tietokannasta löytyvät kirjasarjat.
     * 
     * @return ResponseEntity, joka sisältää kaikki kirjasarjat listana.
     */
    @GetMapping(path = "")
    public ResponseEntity<List<KirjaSarja>> getAllSarja(){
        return new ResponseEntity<>(kirjaSarjaService.getKirjasarjat(), HttpStatus.OK);
    }

    @PostMapping(path = "")
    public ResponseEntity<?> addNewSarja(@NonNull @RequestBody KirjaSarja sarja){
        KirjaSarja response = null;
        try {
            response = kirjaSarjaService.saveKirjaSarja(sarja);
            //TODO: Parempi virheenkäsittely
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    @GetMapping("/{id}")
    public KirjaSarja getSarja(@PathVariable Long id) {
        return kirjaSarjaService.getKirjasarjaById(id);
    }

    /**
     * Metodi määrittää DELETE-endpointin joka poistaa kirjasarjan tietokannasta annetun ID-numeron perusteella.
     *
     * @param id Poistettavan kirjasarjan ID.
     * @return ResponseEntity, joka antaa OK-viestin, onnistui poisto tai ei.
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteSarja(@PathVariable(value = "id") Long id) {
        kirjaSarjaService.removeKirjaSarja(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * Määrittää PUT-endpointin tietokannassa jo valmiiksi olevan kirjasarjan päivittämistä varten. Metodi hakee kirjasarjan tietokannasta ID:n perusteella ja korvaa sen saamallaan kirjasarjalla.
     *
     * @param  id    Päivitettävän kirjasarjan ID, saa url-parametrina.
     * @param  alue  Päivitetty Alue-olio. Huomaa että korvaa tietokannassa olevan yksilön kokonaan joten ei hyväksy tyhjiä kenttiä.
     * @return       ResponseEntity joka sisältää tietokantaan päivitetyn kirjasarjan tai virheen jos tiedoissa oli jotakin vialla.
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateSarja(@PathVariable(value = "id") Long id, @RequestBody KirjaSarja sarja) {
        KirjaSarja toBeModified = null;
        KirjaSarja response = null;
        // TODO: Parempi validointi
        try{
            toBeModified = kirjaSarjaService.getKirjasarjaById(id);
        } catch (EntityNotFoundException e){
            return new ResponseEntity<>("Invalid area ID!", HttpStatus.NOT_FOUND);
        }
        toBeModified.setTitle(sarja.getTitle());
        toBeModified.setKustantaja(sarja.getKustantaja());
        toBeModified.setKuvaus(sarja.getKuvaus());
        toBeModified.setLuokittelu(sarja.getLuokittelu());
        try {
            response = kirjaSarjaService.saveKirjaSarja(toBeModified);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}