package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.repositories.KirjaKopioRepository;

import java.util.List;

@Service
public class KirjaKopioService {

    @Autowired
    private KirjaKopioRepository KirjakopioRepository;

    public List<KirjaKopio> getKirjakopiot() {
        return KirjakopioRepository.findAll();
    }

    public KirjaKopio getKirjakopioById(Long id) {
        return KirjakopioRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}