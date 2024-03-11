package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.micrometer.common.lang.NonNull;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.groupt.kirjaarkisto.services.KirjaService;
import org.groupt.kirjaarkisto.services.KuvaService;
import org.groupt.kirjaarkisto.services.TiedostonhallintaService;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.payload.KirjaDTO;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/kirjat")
public class KirjaController {

    @Autowired
    private KirjaService kirjaService;

    @Autowired
    private KuvaService kuvaservice;

    @Autowired
    private KirjaSarjaService kirjaSarjaService;

    @Autowired
    private TiedostonhallintaService tiedostonhallintaService;

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

    /**
     * tää epic metodi määrittää endpointin PUT-pyynnöille /api/kirjat/{id}-osoitteessa. 
     * ottaa parametrina kirjan id:n ja KirjaDTO-olion, joka muodostuu pyynnön rungosta. 
     * tämän pohjalta siis päivittää tietokantaan kirjan tiedot.
     * 
     * @param id kirjan id tietokannassa, jota halutaan muokata.
     * @param kirjaDTO kirjan datansiirto-olio.
     * @return päivitetty kirja.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Kirja> editKirja(@PathVariable Long id,@NonNull @RequestBody KirjaDTO kirjaDTO) {

    Kirja muokattava = kirjaService.getKirjaById(id);

    if (muokattava == null) {
        return ResponseEntity.notFound().build();
    }

    muokattava.setNimi(kirjaDTO.getNimi());
    muokattava.setKirjailija(kirjaDTO.getKirjailija());
    muokattava.setJulkaisuVuosi(kirjaDTO.getJulkaisuVuosi());
    muokattava.setBookSeries(kirjaSarjaService.getKirjasarjaById(kirjaDTO.getSarja()));
    muokattava.setJarjestysNro(kirjaDTO.getJarjestysNro());
    muokattava.setKuvaus(kirjaDTO.getKuvaus());

    Kirja muokattuKirja = kirjaService.editKirja(id, muokattava);

    return ResponseEntity.ok(muokattuKirja);
}
@PostMapping("/{id}/kuvat")
public ResponseEntity<String> lisaakuvaKirjalle(@PathVariable Long id,
                                                @RequestParam("file") MultipartFile file,
                                                @RequestParam("julkaisuvuosi") Integer julkaisuvuosi,
                                                @RequestParam("taiteilija") String taiteilija,
                                                @RequestParam("tyyli") String tyyli,
                                                @RequestParam("kuvaus") String kuvaus,
                                                @RequestParam("sivunro") Integer sivunro) {
    try {
       
        String tiedostoNimi = TiedostonhallintaService.tallennaKuva(file);

        
        kirjaService.lisaaKuvaKirjalle(id, tiedostoNimi, julkaisuvuosi, taiteilija, tyyli, kuvaus, sivunro);

        
        return ResponseEntity.ok(tiedostoNimi);
    } catch (IOException e) {
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("virhe tallennettaessa tiedostoa.");
    }
}
@DeleteMapping("/{kirjaId}/kuvat/{kuvaId}")
public ResponseEntity<String> poistaKirjaJaKuva(@PathVariable Long kirjaId, @PathVariable Long kuvaId) {
    try {
        kirjaService.poistaKuvaKirjalta(kirjaId, kuvaId);
        return new ResponseEntity<>("Kirja ja kuva poistettu onnistuneesti", HttpStatus.OK);
    } catch (Exception e) {
        return new ResponseEntity<>("Kirjan ja kuvan poistaminen epäonnistui", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
}
