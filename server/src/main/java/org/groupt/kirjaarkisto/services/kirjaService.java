package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.kirja;
import org.groupt.kirjaarkisto.repositories.kirjaRepository;

import java.util.List;

@Service
public class kirjaService {

    @Autowired
    private kirjaRepository KirjaRepository;

    public List<kirja> getKirjat() {
        return KirjaRepository.findAll();
    }

    public kirja getKirjaById(Long id) {
        return KirjaRepository.findById(id).orElse(null);
    }
    //metodi siis lisää sen kirjan :D hagrid tales :D
    public kirja addKirja(kirja kirja) {
        return KirjaRepository.save(kirja);
    } 
    //tää pooistaa :D hagrid :D
     public void deleteKirja(Long id) {
        KirjaRepository.deleteById(id);
    }
}
