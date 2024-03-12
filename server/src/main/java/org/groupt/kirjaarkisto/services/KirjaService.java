package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.payload.KirjaResponse;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.KuvitusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KirjaService {

    @Autowired
    private KirjaRepository kirjaRepository;

    @Autowired
    private KuvaRepository kuvaRepository;

     @Autowired
    private KuvitusRepository kuvitusRepository;

    public List<Kirja> getKirjat() {
        return kirjaRepository.findAll();
    }
public List<KirjaResponse> getKirjatBySarja(KirjaSarja sarja) {
      List<Kirja> kirjat = kirjaRepository.findByKirjaSarja(sarja);
      List<KirjaResponse> response = new ArrayList<>();
      for (Kirja kirja : kirjat) {
        response.add(new KirjaResponse(kirja));
      }
      return response;
    }
    public Kirja getKirjaById(Long id) {
        Kirja kirja = kirjaRepository.findById(id).orElse(null);
        if (kirja != null) {
          return kirja;
        } else {
          throw new NonExistingKirjaException("Kirja with ID " + id + " does not exist!");
        }
    }
    //metodi siis lisää sen kirjan :D hagrid tales :D
    public Kirja addKirja(Kirja kirja) {
        try {
          return kirjaRepository.save(kirja);
        } catch (IllegalArgumentException e) {
          throw new NullKirjaException("Annettu kirja tyhjä. Tarkasta parametrit.");
        }
    } 
    //tää pooistaa :D
    public void deleteKirja(Long id) {
      kirjaRepository.deleteById(id);
      try {
        kirjaRepository.deleteById(id);
      } catch (IllegalArgumentException e) {
        throw new BadIdException("Annettu Id ei tullut perille asti. Tarkasta parametrit.");
      }
  }

  //kirjan muokkaus.
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
      if (muokattavaKirja.getBookSeries() != null) {
          kirja.setBookSeries(muokattavaKirja.getBookSeries());
      }
      if (muokattavaKirja.getJarjestysNro() != null) {
          kirja.setJarjestysNro(muokattavaKirja.getJarjestysNro());
      }
      if (muokattavaKirja.getKuvaus() != null) {
          kirja.setKuvaus(muokattavaKirja.getKuvaus());
      }

      return kirjaRepository.save(kirja);
  }
  //kuvan lisäys metodi
  @Transactional
  public void lisaaKuvaKirjalle(Long kirjaId, Integer julkaisuvuosi, String taiteilija,
                                String tyyli, String kuvaus, Integer sivunro) {
      Kirja kirja = kirjaRepository.findById(kirjaId)
              .orElseThrow(() -> new EntityNotFoundException("Kirjaa ei löydy id:llä " + kirjaId));

      Kuva kuva = new Kuva();
      kuva.setJulkaisuvuosi(julkaisuvuosi);
      kuva.setTaiteilija(taiteilija);
      kuva.setTyyli(tyyli);
      kuva.setKuvaus(kuvaus);

      kuvaRepository.save(kuva);

      Kuvitus kuvitus = new Kuvitus();
      kuvitus.setKirja(kirja);
      kuvitus.setKuva(kuva);
      kuvitus.setSivunro(sivunro);

      kuvitusRepository.save(kuvitus);
  }
}
