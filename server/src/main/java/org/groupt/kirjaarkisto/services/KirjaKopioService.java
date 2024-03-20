package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.io.IOException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaKopioException;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.models.Valokuva;
import org.groupt.kirjaarkisto.repositories.KirjaKopioRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.ValokuvaRepository;

import java.util.List;

/**
 * Palveluluokka kirjakopioiden hallintaa varten.
 */
@Service
public class KirjaKopioService {

  /**
   * Repository, jolla voidaan hallita kirjakopioita tietokannassa.
   */
  @Autowired
  private KirjaKopioRepository kirjakopioRepository;

  @Autowired
  private ValokuvaRepository valokuvaRepository;

  @Autowired
  private KuvaRepository kuvaRepository;


  @Autowired
  private TiedostonhallintaService tiedostonhallintaService;

  /**
   * Palauttaa kaikki tietokannasta löytyvät kirjakopiot.
   * 
   * @return Lista kirjakopioista.
   */
  public List<KirjaKopio> getKirjakopiot() {
    return kirjakopioRepository.findAll();
  }

  public List<KirjaKopio> getKirjaKopioByKirja(Kirja kirja) {
    return kirjakopioRepository.findByBook(kirja);
  }

  /**
   * Palauttaa kirjakopion, joka vastaa annettua ID:ta.
   * 
   * @param id Kirjakopion ID
   * @return Tietokannasta haettu kirjakopio.
   */
  public KirjaKopio getKirjakopioById(Long id) {
    KirjaKopio kopio = kirjakopioRepository.findById(id).orElse(null);
    if (kopio == null) {
      throw new NonExistingKirjaKopioException("Kirja kopio with ID " + id + " does not exist!");
    }
    return kopio;
  }

  /**
   * Tallentaa kirjakopion tietokantaan. Käyttää repositoryn save-metodia, joka
   * ylikirjoittaa olemassa olevan kopion.
   *
   * @param kirjaKopio tallennettavat kirjakopio.
   * @return Tallennettu kirjakopio.
   */
  public KirjaKopio saveKirjaKopio(KirjaKopio kirjaKopio) {
    return kirjakopioRepository.save(kirjaKopio);
  }

  public List<KirjaKopio> getBySarja(KirjaSarja sarja) {
    return kirjakopioRepository.findByIdKirjaSarja(sarja.getId());
  }

  public void remove(KirjaKopio kopio) {
    kirjakopioRepository.delete(kopio);
  }

  @Transactional
  public void lisaaKuvaKirjakopiolle(Long kirjakopioId, MultipartFile tiedosto, Integer julkaisuvuosi,
      String taiteilija, String tyyli, String kuvaus, Integer sivunro) throws IOException {
    KirjaKopio kirjakopio = kirjakopioRepository.findById(kirjakopioId)
        .orElseThrow(() -> new EntityNotFoundException("Kirjakopiota ei löydy id:llä " + kirjakopioId));

    // Tallenna tiedosto ja palauta sen polku
    String tiedostoNimi = tiedostonhallintaService.tallennaKuva(tiedosto);

    // Luo uusi valokuva
    Valokuva valokuva = new Valokuva();
    valokuva.setKuvanimi(tiedosto.getOriginalFilename());
    valokuva.setKirjaKopio(kirjakopio);
    valokuva.setTiedostonimi(tiedostoNimi);
    valokuva.setSivunnro(sivunro);

    // Tallenna valokuva tietokantaan
    valokuvaRepository.save(valokuva);
  }
}
