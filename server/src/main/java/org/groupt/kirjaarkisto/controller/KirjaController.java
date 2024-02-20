package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.micrometer.common.lang.NonNull;

import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.groupt.kirjaarkisto.services.KirjaService;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaDTO;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/kirjat")
public class KirjaController {

    @Autowired
    private KirjaService kirjaService;

    @Autowired
    private KirjaSarjaService kirjaSarjaService;

    @GetMapping
    public List<Kirja> getKirjat() {
        return kirjaService.getKirjat();
    }

    @GetMapping("/{id}")
    public Kirja getKirja(@PathVariable Long id) {
        return kirjaService.getKirjaById(id);
    }

    /**
     * Metodi määrittää endpointin POST-pyynnöille /api/kirjat-osoitteeseen. Ottaa parametreinaan KirjaDTO-olion, 
     * joka muodostuu pyynnön rungosta ja tämän pohjalta luo tietokantaan lisättävän kirjan olion. 
     * Pyynnössä tulee olla mukana kentät merkkijono "nimi", merkkijono "kirjailija", kokonaisluku "julkaisuVuosi", kokonaisluku "sarja", 
     * kokonaisluku "järjestysNro" ja kokonaisluku "kuvaus".
     * 
     * @param kirjaDTO Kirjan datansiirto-olio. Huomattavaa on että "sarja"-kenttä on kirjan sarjan id tietokannassa.
     * @return Tietokantaan lisätty kirja.
     */
    @PostMapping(path = "")
    public ResponseEntity<Kirja> createKirja(@NonNull @RequestBody KirjaDTO kirjaDTO) {
      Kirja lisattava = new Kirja();
      lisattava.setNimi(kirjaDTO.getNimi());
      lisattava.setKirjailija(kirjaDTO.getKirjailija());
      lisattava.setJulkaisuVuosi(kirjaDTO.getJulkaisuVuosi());
      lisattava.setBookSeries(kirjaSarjaService.getKirjasarjaById(kirjaDTO.getSarja()));
      lisattava.setJarjestysNro(kirjaDTO.getJarjestysNro());
      lisattava.setKuvaus(kirjaDTO.getKuvaus());

      Kirja lisatty = kirjaService.addKirja(lisattava);

      URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(lisatty.getId())
        .toUri();

      return ResponseEntity.created(uri)
        .body(lisatty);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteKirja(@PathVariable Long id) {
        kirjaService.deleteKirja(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
