package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import java.util.List;
import org.groupt.kirjaarkisto.repositories.KirjaSarjaRepository;

@Service
public class KirjaSarjaService {

    @Autowired
    private KirjaSarjaRepository KirjasarjaRepository;

    public List<KirjaSarja> getKirjasarjat() {
        return KirjasarjaRepository.findAll();
    }

    public KirjaSarja getKirjasarjaById(Long id) {
        return KirjasarjaRepository.findById(id).orElse(null);
    }

    // Lisää tarvittavat liiketoimintalogiikkametodit
}