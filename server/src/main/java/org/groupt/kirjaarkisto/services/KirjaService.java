package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.payload.KirjaResponse;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import java.util.ArrayList;
import java.util.List;

@Service
public class KirjaService {

    @Autowired
    private KirjaRepository kirjaRepository;


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
}
