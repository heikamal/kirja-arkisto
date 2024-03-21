package org.groupt.kirjaarkisto.services;
import java.util.ArrayList;
import java.util.List;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.KuvitusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.io.IOException;
import org.groupt.kirjaarkisto.services.TiedostonhallintaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class KuvaService {

  @Autowired
  private KuvaRepository kuvaRepository;

  @Autowired
  private KirjaRepository kirjaRepository;

  @Autowired
  private KuvitusRepository kuvitusRepository;


  @Autowired
  private TiedostonhallintaService tiedostonhallintaservice;


    public Kuva getKuvaById(Long id) {
        return kuvaRepository.findById(id).orElse(null);
    }

    public void poistaKuva(Long id) {
        kuvaRepository.deleteById(id);
    }

    public List<Kuvitus> getKuvaByKirja(Kirja kirja){
      return kuvitusRepository.findByKirja(kirja);
    }
     //kuvan lisäys metodi
     @Transactional
     public Kuvitus lisaaKuvaKirjalle(Long kirjaId, MultipartFile tiedosto, Integer julkaisuvuosi, String taiteilija,
         String tyyli, String kuvaus, Integer sivunro) {

      Kirja kirja = kirjaRepository.findById(kirjaId)
        .orElseThrow(() -> new EntityNotFoundException("Kirjaa ei löydy id:llä " + kirjaId));

       String tiedostoNimi = tiedostonhallintaservice.tallennaKuva(tiedosto);
       
       // Luo kuvaolio ja tallenna tiedostonimi tietokantaan
       Kuva kuva = new Kuva();
       kuva.setJulkaisuvuosi(julkaisuvuosi);
       kuva.setTaiteilija(taiteilija);
       kuva.setTyyli(tyyli);
       kuva.setKuvaus(kuvaus);
       kuva.setTiedostonimi(tiedostoNimi); // Tallennetaan tiedostonimi tietokantaan

       kuvaRepository.save(kuva);

       // Luo kuvitusolio ja tallenna tietokantaan
       Kuvitus kuvitus = new Kuvitus();
       kuvitus.setKirja(kirja);
       kuvitus.setKuva(kuva);
       kuvitus.setSivunro(sivunro);

       return kuvitusRepository.save(kuvitus);
     }
  
     @Transactional
     public void poistaKuvaKirjalta(Long kirjaId, Long kuvaId) {
         Kuva kuva = kuvaRepository.findById(kuvaId)
                 .orElseThrow(() -> new EntityNotFoundException("Kuvaa ei löydy id:llä " + kuvaId));
 
          //TODO  Poista kuva kirjalta
     }
}