package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.KuvitusRepository;

import java.io.IOException;
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
   public void lisaaKuvaKirjalle(Long kirjaId, String tiedostoNimi, Integer julkaisuvuosi, String taiteilija,
                                  String tyyli, String kuvaus, Integer sivunro) {
        Optional<Kirja> kirjaOptional = kirjaRepository.findById(kirjaId);

        if (kirjaOptional.isPresent()) {
            Kirja kirja = kirjaOptional.get();

    
            Kuva kuva = new Kuva();
            kuva.setKuvanimi(tiedostoNimi);
            kuva.setJulkaisuvuosi(julkaisuvuosi);
            kuva.setTaiteilija(taiteilija);
            kuva.setTyyli(tyyli);
            kuva.setKuvaus(kuvaus);
            
          
            Kuva tallennettuKuva = kuvaRepository.save(kuva);

            // Luo uusi kuvitus ja liitä se kirjaan
            Kuvitus kuvitus = new Kuvitus();
            kuvitus.setKirja(kirja);
            kuvitus.setKuva(tallennettuKuva);
            kuvitus.setSivunro(sivunro);

            // Tallenna kuvitus tietokantaan
            kuvitusRepository.save(kuvitus);
        } else {
            // Käsittely, kun kirjaa ei löydy
            throw new EntityNotFoundException("kirjaa ei löydy id:llä " + kirjaId);
        }
    }
    public void poistaKuvaKirjalta(Long kirjaId, Long kuvaId) {
        Optional<Kirja> kirjaOptional = kirjaRepository.findById(kirjaId);
    
        if (kirjaOptional.isPresent()) {
            Kirja kirja = kirjaOptional.get();
    
            Optional<Kuvitus> kuvitusOptional = kuvitusRepository.findByKirjaAndKuva_Id(kirja, kuvaId);
    
            if (kuvitusOptional.isPresent()) {
                kuvitusRepository.deleteByKirjaAndKuva_Id(kirja, kuvaId);
            } else {
                throw new EntityNotFoundException("Kuvitusta ei löydy annetulla kirjaId:llä ja kuvaId:llä");
            }
        } else {
            throw new EntityNotFoundException("Kirjaa ei löydy id:llä " + kirjaId);
        }
    }
}
