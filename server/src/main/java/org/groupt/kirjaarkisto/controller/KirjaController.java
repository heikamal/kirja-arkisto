package org.groupt.kirjaarkisto.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.core.Authentication;
import org.groupt.kirjaarkisto.services.KirjaHyllyService;
import org.groupt.kirjaarkisto.services.KirjaKopioService;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.groupt.kirjaarkisto.services.KirjaService;
import org.groupt.kirjaarkisto.services.KuvaService;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.payload.KirjaDTO;
import org.groupt.kirjaarkisto.security.services.UserDetailsImpl;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
    private KirjaHyllyService kirjaHyllyService;

    @Autowired
    private KirjaKopioService kirjaKopioService;

    @GetMapping
    public List<Kirja> getKirjat() {
        return kirjaService.getKirjat();
    }

    @GetMapping("/{id}")
    public Kirja getKirja(@PathVariable Long id) {
        return kirjaService.getKirjaById(id);
    }
    /**
     * Metodi määrittää endpointin GET-pyynnölle /{id}/owned-osoitteeseen. Metodin on tarkoitus katsoa että onko id:n määrittämän kirja jo valmiiksi kirjautuneen käyttäjän kirjahyllyssä.
     * Kirjan ID:n metodi saa osoiteparametrinä ja kirjahylly haetaan pyynnön mukana tulevan tokenin perusteella.
     * 
     * @param id Kirjan ID kokonaislukuna.
     * @return Kuvaus, joka sisältää tiedon löytyykö tarkasteltu kirja kirjautuneen käyttäjän kirjahyllystä.
     */
    @GetMapping("/{id}/owned")
    public Map<String, Object> isKirjaOwned(@PathVariable Long id){
      Map<String, Object> response = new HashMap<>();
      boolean owned = false;

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      SecurityContextHolder.getContext().setAuthentication(authentication);
    
      UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

      KirjaHylly hylly = kirjaHyllyService.getKirjaHyllyByOmistaja(userDetails.getId());

      List<KirjaKopio> lista = kirjaKopioService.getKirjaKopioByKirja(kirjaService.getKirjaById(id));
      for (KirjaKopio kopio : lista) {
        if (Objects.equals(kopio.getIdKirjaHylly(), hylly.getId())) {
          owned = true;
          break;
        }
      }

      response.put("owned", owned);

      return response;
      
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
    public ResponseEntity<String> lisaakuvaKirjalle(
            @PathVariable Long id,
            @RequestParam("julkaisuvuosi") Integer julkaisuvuosi,
            @RequestParam("taiteilija") String taiteilija,
            @RequestParam("tyyli") String tyyli,
            @RequestParam("kuvaus") String kuvaus,
            @RequestParam("sivunro") Integer sivunro) {

        try {
            kuvaservice.lisaaKuvaKirjalle(id, julkaisuvuosi, taiteilija, tyyli, kuvaus, sivunro);
            return ResponseEntity.ok("Kuva lisätty onnistuneesti.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Kirjaa ei löydy id:llä " + id);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Virhe lisättäessä kuvaa.");
        }
    }
}
