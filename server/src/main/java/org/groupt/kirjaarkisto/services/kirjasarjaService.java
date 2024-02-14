package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.kirjasarja;
import java.util.List;
import org.groupt.kirjaarkisto.repositories.kirjasarjaRepository;

@Service
public class kirjasarjaService {

    @Autowired
    private kirjasarjaRepository KirjasarjaRepository;

    public List<kirjasarja> getKirjasarjat() {
        return KirjasarjaRepository.findAll();
    }

    public kirjasarja getKirjasarjaById(Long id) {
        return KirjasarjaRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}