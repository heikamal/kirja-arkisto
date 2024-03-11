package org.groupt.kirjaarkisto.services;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KuvaService {

    private final KuvaRepository kuvaRepository;

    @Autowired
    public KuvaService(KuvaRepository kuvaRepository) {
        this.kuvaRepository = kuvaRepository;
    }

    public Kuva getKuvaById(Long id) {
        return kuvaRepository.findById(id).orElse(null);
    }

    public void poistaKuva(Long id) {
        kuvaRepository.deleteById(id);
    }
}