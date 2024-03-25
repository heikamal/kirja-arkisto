package org.groupt.kirjaarkisto.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.payload.KirjaResponse;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class KirjaService {

  @Autowired
  private KirjaRepository kirjaRepository;

  @Autowired
  private KuvaService kuvaService;

  public List<Kirja> getKirjat() {
    List<Kirja> kirjat = kirjaRepository.findAll();
    List<Kirja> palautus = new ArrayList<>();
    for (Kirja kirja : kirjat) {
      palautus.add(handlePics(kirja));
    }
    return palautus;
  }

  public List<KirjaResponse> getKirjatBySarja(KirjaSarja sarja) {
    List<Kirja> alkuKirjat = kirjaRepository.findByKirjaSarja(sarja);
    List<Kirja> kirjat = new ArrayList<>();
    for (Kirja kirja : alkuKirjat) {
      kirjat.add(handlePics(kirja));
    }
    List<KirjaResponse> response = new ArrayList<>();
    for (Kirja kirja : kirjat) {
      response.add(new KirjaResponse(kirja));
    }
    return response;
  }

  public Kirja getKirjaById(Long id) {
    Kirja kirja = kirjaRepository.findById(id).orElse(null);
    if (kirja != null) {
      return handlePics(kirja);
    } else {
      throw new NonExistingKirjaException("Kirja with ID " + id + " does not exist!");
    }
  }

  // metodi siis lisää sen kirjan :D hagrid tales :D
  public Kirja addKirja(Kirja kirja) {
    try {
      return kirjaRepository.save(kirja);
    } catch (IllegalArgumentException e) {
      throw new NullKirjaException("Annettu kirja tyhjä. Tarkasta parametrit.");
    }
  }

  // tää pooistaa :D
  public void deleteKirja(Long id) {
    kirjaRepository.deleteById(id);
    try {
      kirjaRepository.deleteById(id);
    } catch (IllegalArgumentException e) {
      throw new BadIdException("Annettu Id ei tullut perille asti. Tarkasta parametrit.");
    }
  }

  private Kirja handlePics(Kirja kirja) {
    if (kirja.getKuvitukset() != null) {
      for (Kuvitus k : kirja.getKuvitukset()) {
        Kuva kuva = kuvaService.getKuvaById(k.getKuva().getIdkuva());
        k.setKuva(kuva);
      }
    }
    return kirja;
  }

  // kirjan muokkaus.
  @Transactional
  public Kirja editKirja(Long id, Kirja muokattavaKirja) {
    Kirja kirja = kirjaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("kirjaa ei löytynyt id:llä " + id));

    // näissä vissii... päivitetää kirjojen tiedot...
    if (muokattavaKirja.getNimi() != null) {
      kirja.setNimi(muokattavaKirja.getNimi());
    }
    if (muokattavaKirja.getKirjailija() != null) {
      kirja.setKirjailija(muokattavaKirja.getKirjailija());
    }
    if (muokattavaKirja.getJulkaisuVuosi() != null) {
      kirja.setJulkaisuVuosi(muokattavaKirja.getJulkaisuVuosi());
    }
    if (muokattavaKirja.getKirjaSarja() != null) {
      kirja.setKirjaSarja(muokattavaKirja.getKirjaSarja());
    }
    if (muokattavaKirja.getJarjestysNro() != null) {
      kirja.setJarjestysNro(muokattavaKirja.getJarjestysNro());
    }
    if (muokattavaKirja.getKuvaus() != null) {
      kirja.setKuvaus(muokattavaKirja.getKuvaus());
    }

    return kirjaRepository.save(kirja);
  }
}
