package org.groupt.kirjaarkisto.services;
import java.util.ArrayList;
import java.util.List;

import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.KuvitusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KuvaService {

  @Autowired
  private KuvaRepository kuvaRepository;

  @Autowired
  private KuvitusRepository kuvitusRepository;

    public Kuva getKuvaById(Long id) {
        return kuvaRepository.findById(id).orElse(null);
    }

    public void poistaKuva(Long id) {
        kuvaRepository.deleteById(id);
    }

    public List<Kuvitus> getKuvaByKirja(Kirja kirja){
      return kuvitusRepository.findByKirja(kirja);
    }
}