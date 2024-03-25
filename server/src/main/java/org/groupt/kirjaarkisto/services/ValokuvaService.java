package org.groupt.kirjaarkisto.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

import org.groupt.kirjaarkisto.models.KirjaKopio;
import org.groupt.kirjaarkisto.repositories.KirjaKopioRepository;
import org.groupt.kirjaarkisto.repositories.ValokuvaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import org.groupt.kirjaarkisto.models.Valokuva;

public class ValokuvaService {

  @Autowired
  private ValokuvaRepository valokuvaRepository;

  @Autowired
  private KirjaKopioRepository kirjaKopioRepository;

  public void poistaValokuva(Long id) {
    valokuvaRepository.deleteById(id);
  }

  public List<Valokuva> getByKirjaKopio(KirjaKopio kopio) {
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

  public Valokuva addValokuva(Long idKirjakopio, String kuvanimi, int sivunumero, MultipartFile tiedosto) throws IOException {

    Optional<KirjaKopio> kopio = kirjaKopioRepository.findById(idKirjakopio);

    Valokuva valokuva = new Valokuva();
    valokuva.setKuvanimi(kuvanimi);
    valokuva.setKirjaKopio(kopio.isPresent() ? kopio.get() : null);
    valokuva.setSivunnro(sivunumero);
    valokuva.setPicByte(compressBytes(tiedosto.getBytes()));
    return valokuvaRepository.save(valokuva);
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
