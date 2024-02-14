package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.kirjahylly;
import org.groupt.kirjaarkisto.repositories.kirjahyllyRepository;

import java.util.List;

@Service
public class kirjahyllyService {

    @Autowired
    private kirjahyllyRepository KirjahyllyRepository;

    public List<kirjahylly> getKirjahyllyt() {
        return KirjahyllyRepository.findAll();
    }

    public kirjahylly getKirjahyllyById(Long id) {
        return KirjahyllyRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}