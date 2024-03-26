package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.groupt.kirjaarkisto.exceptions.NonExistingKirjaKopioException;
import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.models.Valokuva;
import org.groupt.kirjaarkisto.repositories.KirjaKopioRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.ValokuvaRepository;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

/**
 * Palveluluokka kirjakopioiden hallintaa varten.
 */
@Service
public class KirjaKopioService {

  /**
   * Repository, jolla voidaan hallita kirjakopioita tietokannassa.
   */
  @Autowired
  private KirjaKopioRepository kirjaKopioRepository;

  @Autowired
  private ValokuvaRepository valokuvaRepository;

  @Autowired
  private KuvaRepository kuvaRepository;

  @Autowired
  private KirjaService kirjaService;


  //@Autowired
  //private TiedostonhallintaService tiedostonhallintaService;

  /**
   * Palauttaa kaikki tietokannasta löytyvät kirjakopiot.
   * 
   * @return Lista kirjakopioista.
   */
  public List<KirjaKopio> getKirjakopiot() {
    return kirjaKopioRepository.findAll();
  }

  public List<KirjaKopio> getKirjaKopioByKirja(Kirja kirja) {
    return kirjaKopioRepository.findByBook(kirja);
  }

  /**
   * Palauttaa kirjakopion, joka vastaa annettua ID:ta.
   * 
   * @param id Kirjakopion ID
   * @return Tietokannasta haettu kirjakopio.
   */
  public KirjaKopio getKirjakopioById(Long id) {
    KirjaKopio kopio = kirjaKopioRepository.findById(id).orElse(null);
    if (kopio == null) {
      throw new NonExistingKirjaKopioException("Kirja kopio with ID " + id + " does not exist!");
    }
    return kopio;
  }

  /**
   * Tallentaa kirjakopion tietokantaan. Käyttää repositoryn save-metodia, joka
   * ylikirjoittaa olemassa olevan kopion.
   *
   * @param kirjaKopio tallennettavat kirjakopio.
   * @return Tallennettu kirjakopio.
   */
  public KirjaKopio saveKirjaKopio(KirjaKopio kirjaKopio) {
    return kirjaKopioRepository.save(kirjaKopio);
  }

  public List<KirjaKopio> getByOmaSarja(Long hyllyId, Long sarjaId) {
    List<KirjaKopio> kopiot = kirjaKopioRepository.findByIdKirjaSarjaAndIdKirjaHylly(sarjaId, hyllyId);
    for (KirjaKopio kopio : kopiot) {
      Kirja kirja = kopio.getBook();
      kopio.setBook(kirjaService.handlePics(kirja));
    }
    return kopiot;
  }

  public List<KirjaKopio> getBySarja(KirjaSarja sarja) {
    return kirjaKopioRepository.findByIdKirjaSarja(sarja.getId());
  }

  public void remove(KirjaKopio kopio) {
    kirjaKopioRepository.delete(kopio);
  }

  @Transactional
  public void lisaaKuvaKirjakopiolle(Long kirjakopioId, MultipartFile tiedosto, Integer julkaisuvuosi,
      String taiteilija, String tyyli, String kuvaus, Integer sivunro, String nimi) throws IOException {
    KirjaKopio kirjakopio = kirjaKopioRepository.findById(kirjakopioId)
        .orElseThrow(() -> new EntityNotFoundException("Kirjakopiota ei löydy id:llä " + kirjakopioId));

    // Tallenna tiedosto ja palauta sen polku
    //String tiedostoNimi = tiedostonhallintaService.tallennaKuva(tiedosto);

    // Luo uusi valokuva
    Valokuva valokuva = new Valokuva();
    valokuva.setKuvanimi(tiedosto.getOriginalFilename());
    valokuva.setKirjaKopio(kirjakopio);
    valokuva.setTiedostonimi(nimi);
    valokuva.setSivunnro(sivunro);

    valokuva.setPicByte(compressBytes(tiedosto.getBytes()));

    // Tallenna valokuva tietokantaan
    valokuvaRepository.save(valokuva);
  }

  public void poistaValokuva(Long id) {
    valokuvaRepository.deleteById(id);
  }

  public List<Valokuva> getValokuvatByKirjaKopio(KirjaKopio kopio) {
    List<Valokuva> valokuvat = valokuvaRepository.findByKirjaKopio(kopio);
    List<Valokuva> vastaus = new ArrayList<>();
    for (Valokuva valokuva : valokuvat) {
      Valokuva v = new Valokuva();
      v.setIdkuva(valokuva.getIdkuva());
      v.setKuvanimi(valokuva.getKuvanimi());
      v.setSivunnro(valokuva.getSivunnro());
      v.setPicByte(decompressBytes(valokuva.getPicByte()));
      vastaus.add(v);
    }
    return vastaus;
  }

  public static byte[] compressBytes(byte[] data) {
    Deflater deflater = new Deflater();
    deflater.setInput(data);
    deflater.finish();

    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    while (!deflater.finished()) {
      int count = deflater.deflate(buffer);
      outputStream.write(buffer, 0, count);
    }
    try {
      outputStream.close();
    } catch (java.io.IOException e) {
    }
    System.out.println("Compressed Image Byte Size - " + outputStream.toByteArray().length);
    return outputStream.toByteArray();
  }

  public static byte[] decompressBytes(byte[] data) {
    Inflater inflater = new Inflater();
    inflater.setInput(data);
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
    byte[] buffer = new byte[1024];
    try {
      while (!inflater.finished()) {
        int count = inflater.inflate(buffer);
        outputStream.write(buffer, 0, count);
      }
      outputStream.close();
    } catch (java.util.zip.DataFormatException e) {
    } catch (java.io.IOException e) {
    }
    return outputStream.toByteArray();
  }
}
