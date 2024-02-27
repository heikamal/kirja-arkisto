package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.BadIdException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaException;
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
}
