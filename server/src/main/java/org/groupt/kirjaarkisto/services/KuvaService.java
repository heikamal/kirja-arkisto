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
  public void lisaaKuvaKirjalle(Long kirjaId, Integer julkaisuvuosi, String taiteilija,
                                String tyyli, String kuvaus, Integer sivunro) {
                                  
      Kirja kirja = kirjaRepository.findById(kirjaId)
              .orElseThrow(() -> new EntityNotFoundException("Kirjaa ei löydy id:llä " + kirjaId));
              
              List<Kuvitus> kirjanKuvitukset = getKuvaByKirja(kirja);
              
              if (kirjanKuvitukset.isEmpty()) {
                // Lisää kuva ja kuvitus vain, jos kirjalla ei ole vielä kuvaa
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
            } else {
                // Lisää kuvitus vain, jos kirjalla on jo yksi kuva
                Kuva ensimmainenKuva = kirjanKuvitukset.get(0).getKuva();
        
                Kuvitus kuvitus = new Kuvitus();
                kuvitus.setKirja(kirja);
                kuvitus.setKuva(ensimmainenKuva);
                kuvitus.setSivunro(sivunro);
        
                kuvitusRepository.save(kuvitus);
            }
    } 
}