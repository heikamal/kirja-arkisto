package org.groupt.kirjaarkisto.payload;

import java.util.List;

import org.groupt.kirjaarkisto.models.KirjaSarja;

public class SarjaResponse {
  private Long id;

  private String title;

  private String kustantaja;

  private String kuvaus;

  private String luokittelu;

  private List<KirjaResponse> kirjat;

  private List<KirjaKopioResponse> kopiot;

  public SarjaResponse() {}

  /**
   * Alustaja, joka luo uuden olion annetun kirjasarjan ja tähän kuuluvien kirjojen listauksen pohjalta.
   * 
   * @param sarja Kirjasarja, joka halutaan lähettää frontendille.
   * @param kirjat Lista kirjoista jotka kuuluvat sarjaan.
   */
  public SarjaResponse(KirjaSarja sarja, List<KirjaResponse> kirjat){
    this.id = sarja.getId();
    this.title = sarja.getTitle();
    this.kustantaja = sarja.getKustantaja();
    this.kuvaus = sarja.getKuvaus();
    this.luokittelu = sarja.getLuokittelu();
    this.kirjat = kirjat;
  }

  public SarjaResponse(KirjaSarja sarja, List<KirjaKopioResponse> kopiot, boolean mummokytkin){
    this.id = sarja.getId();
    this.title = sarja.getTitle();
    this.kustantaja = sarja.getKustantaja();
    this.kuvaus = sarja.getKuvaus();
    this.luokittelu = sarja.getLuokittelu();
    this.kopiot = kopiot;
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKustantaja() {
    return this.kustantaja;
  }

  public void setKustantaja(String kustantaja) {
    this.kustantaja = kustantaja;
  }

  public String getKuvaus() {
    return this.kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  public String getLuokittelu() {
    return this.luokittelu;
  }

  public void setLuokittelu(String luokittelu) {
    this.luokittelu = luokittelu;
  }

  public List<KirjaResponse> getKirjat() {
    return this.kirjat;
  }

  public void setKirjat(List<KirjaResponse> kirjat) {
    this.kirjat = kirjat;
  }

  public List<KirjaKopioResponse> getKopiot() {
    return this.kopiot;
  }

  public void setKopiot(List<KirjaKopioResponse> kopiot) {
    this.kopiot = kopiot;
  }


}
