package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaHyllyException;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaSarjaException;
import org.groupt.kirjaarkisto.exceptions.NullKirjaHyllyException;
import org.groupt.kirjaarkisto.models.KirjaHylly;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.repositories.KirjaHyllyRepository;
import org.groupt.kirjaarkisto.repositories.KirjaSarjaRepository;

import java.util.List;

/**
 * Kirjahyllyjen hallintaan tehty palveluluokka.
 */
@Service
public class KirjaHyllyService {

  /**
   * Repository hallitsemaan kirjahyllyjä tietokannassa.
   */
    @Autowired
    private KirjaHyllyRepository kirjahyllyRepository;

    @Autowired
    private KirjaSarjaRepository kirjasarjaRepository;

    /**
     * Metodi hakee kaikki kirjahyllyt.
     * 
     * @return Kaikki tietokannassa olevat kirjahyllyt listana.
     */
    public List<KirjaHylly> getKirjahyllyt() {
        return kirjahyllyRepository.findAll();
    }

    /**
     * Metodi hakee kirjahyllyn id:n perusteella.
     * 
     * @param id Kirjahyllyn id.
     * @return Kirjahyllyn id:n perusteella oleva kirjahylly.
     */
    public KirjaHylly getKirjahyllyById(Long id) {
        KirjaHylly hylly = kirjahyllyRepository.findById(id).orElse(null);
        if (hylly == null) {
          throw new NonExistingKirjaHyllyException("Kirjahyllya ei loydy.");
        }

        return hylly;
    }

    /**
     * Metodi hakee kirjahyllyn omistajan id:n perusteella.
     * 
     * @param omistaja Kirjahyllyn omistajan id.
     * @return Kirjahyllyn omistajan id:n perusteella oleva kirjahylly.
     */
    public KirjaHylly getKirjaHyllyByOmistaja(Long omistaja) {
        KirjaHylly hylly = kirjahyllyRepository.findByOmistaja(omistaja).orElse(null);
        if (hylly == null) {
          throw new NonExistingKirjaHyllyException("Käyttäjällä ei kirjahyllyä. Oletko admin?");
        }

        return hylly;
    }

    /**
     * Metodi tallentaa uuden kirjahyllyn tietokantaan. Käyttää repositoryn save-metodia, joten jos kirjahylly 
     * löytyy jo tietokannasta, niin metodi kirjoittaa olemassa olevan kirjahyllyn päälle.
     * 
     * @param kirjahylly Uusi kirjahylly.
     * @return Tallennettu kirjahylly.
     */
    public KirjaHylly saveKirjaHylly(KirjaHylly kirjahylly) {
      try {
        return kirjahyllyRepository.save(kirjahylly);
      } catch (IllegalArgumentException e) {
        throw new NullKirjaHyllyException("Annettu kirjahylly tyhjä. Tarkasta parametrit.");
      }
    }

    public List<KirjaHylly> getKirjaHyllytBySarjat(List<KirjaSarja> sarjat) {
      return kirjahyllyRepository.findByOmatSarjatIn(sarjat);
    }

    /**
     * Poistaa kirjasarjan kirjahyllystä.
     *
     * @param hyllyId Kirjahyllyn ID, josta poistetaan kirjasarja.
     * @param sarjaId Kirjasarjan ID, joka poistetaan.
     * @return Päivitetty kirjahylly.
     */
    public void poistaSarja(Long hyllyId, Long sarjaId) {
       KirjaHylly hylly = kirjahyllyRepository.findById(hyllyId)
            .orElseThrow(() -> new NonExistingKirjaHyllyException("Kirjahyllyä ei löydy id:llä " + hyllyId));
    
    KirjaSarja sarja = kirjasarjaRepository.findById(sarjaId)
            .orElseThrow(() -> new NonExistingKirjaSarjaException("Kirjasarjaa ei löydy id:llä " + sarjaId));

    if (!hylly.getOmatSarjat().contains(sarja)) {
        throw new IllegalArgumentException("Kirjahyllyssä ei ole tätä kirjasarjaa.");
    }

    hylly.getOmatSarjat().remove(sarja);
    kirjahyllyRepository.save(hylly);
    }
    
}