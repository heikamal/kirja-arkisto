package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.kirjakopio;
import org.groupt.kirjaarkisto.repositories.kirjakopioRepository;

import java.util.List;

@Service
public class kirjakopioService {

    @Autowired
    private kirjakopioRepository KirjakopioRepository;

    public List<kirjakopio> getKirjakopiot() {
        return KirjakopioRepository.findAll();
    }

    public kirjakopio getKirjakopioById(Long id) {
        return KirjakopioRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}