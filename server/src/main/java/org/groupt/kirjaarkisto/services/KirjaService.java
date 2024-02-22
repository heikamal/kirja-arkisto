package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;

import java.util.List;

@Service
public class KirjaService {

    @Autowired
    private KirjaRepository kirjaRepository;

    public List<Kirja> getKirjat() {
        return kirjaRepository.findAll();
    }

    public Kirja getKirjaById(Long id) {
        return kirjaRepository.findById(id).orElse(null);
    }
    //metodi siis lisää sen kirjan :D hagrid tales :D
    public Kirja addKirja(Kirja kirja) {
        return kirjaRepository.save(kirja);
    } 
    //tää pooistaa :D
     public void deleteKirja(Long id) {
        kirjaRepository.deleteById(id);
    }
    
    //vittu tää metodi on ydinjätettä :-D, mut siis joo TODO: parempi virheenkäsittely
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


