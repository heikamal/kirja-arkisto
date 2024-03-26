package org.groupt.kirjaarkisto.services;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuva;
import org.groupt.kirjaarkisto.models.Kuvitus;
import org.groupt.kirjaarkisto.repositories.KirjaRepository;
import org.groupt.kirjaarkisto.repositories.KuvaRepository;
import org.groupt.kirjaarkisto.repositories.KuvitusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import io.jsonwebtoken.io.IOException;
import org.groupt.kirjaarkisto.services.TiedostonhallintaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class KuvaService {

  @Autowired
  private KuvaRepository kuvaRepository;

  @Autowired
  private KirjaRepository kirjaRepository;

  @Autowired
  private KuvitusRepository kuvitusRepository;


  //@Autowired
  //private TiedostonhallintaService tiedostonhallintaservice;

    public void poistaKuva(Long id) {
        kuvaRepository.deleteById(id);
    }

    public List<Kuvitus> getKuvaByKirja(Kirja kirja){
      return kuvitusRepository.findByKirja(kirja);
    }

    public Kuva getKuvaById(Long id) {
      Kuva kuva = kuvaRepository.findById(id).orElse(null);
      Kuva img = new Kuva();
      if (kuva != null) {
        img.setIdkuva(id);
        img.setKuvanimi(kuva.getKuvanimi());
        img.setJulkaisuvuosi(kuva.getJulkaisuvuosi());
        img.setTaiteilija(kuva.getTaiteilija());
        img.setTyyli(kuva.getTyyli());
        img.setKuvaus(kuva.getKuvaus());
        img.setTiedostonimi(kuva.getTiedostonimi());
        img.setKuvitukset(kuva.getKuvitukset());
        img.setPicByte(decompressBytes(kuva.getPicByte()));
      }
      return img;
    }
     //kuvan lisäys metodi
     @Transactional
     public Kuvitus lisaaKuvaKirjalle(Long kirjaId, MultipartFile tiedosto, Integer julkaisuvuosi, String taiteilija,
         String tyyli, String kuvaus, Integer sivunro, String nimi) throws java.io.IOException {

      Kirja kirja = kirjaRepository.findById(kirjaId)
        .orElseThrow(() -> new EntityNotFoundException("Kirjaa ei löydy id:llä " + kirjaId));

       //String tiedostoNimi = tiedostonhallintaservice.tallennaKuva(tiedosto);
       
       // Luo kuvaolio ja tallenna tiedostonimi tietokantaan
       Kuva kuva = new Kuva();
       kuva.setJulkaisuvuosi(julkaisuvuosi);
       kuva.setTaiteilija(taiteilija);
       kuva.setTyyli(tyyli);
       kuva.setKuvaus(kuvaus);
       kuva.setTiedostonimi(nimi);
       kuva.setPicByte(compressBytes(tiedosto.getBytes()));
       kuva.setKuvanimi(nimi);

       Kuva lisatty = kuvaRepository.save(kuva);

       System.out.println("Lisätty kuva... Jatketaan kvuitukseen");

       // Luo kuvitusolio ja tallenna tietokantaan
       Kuvitus kuvitus = new Kuvitus(kirja, lisatty, sivunro);

       return kuvitusRepository.save(kuvitus);
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
  
     @Transactional
     public void poistaKuvaKirjalta(Long kirjaId, Long kuvaId) {
         Kuva kuva = kuvaRepository.findById(kuvaId)
                 .orElseThrow(() -> new EntityNotFoundException("Kuvaa ei löydy id:llä " + kuvaId));
 
          //TODO  Poista kuva kirjalta
     }
}