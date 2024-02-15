package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.repositories.KirjaHyllyRepository;

import java.util.List;

@Service
public class KirjaHyllyService {

    @Autowired
    private KirjaHyllyRepository KirjahyllyRepository;

    public List<KirjaHylly> getKirjahyllyt() {
        return KirjahyllyRepository.findAll();
    }

    public KirjaHylly getKirjahyllyById(Long id) {
        return KirjahyllyRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}