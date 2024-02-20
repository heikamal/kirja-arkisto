package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
