package org.groupt.kirjaarkisto.payload;

import java.util.List;

import org.groupt.kirjaarkisto.models.Kirja;
import org.groupt.kirjaarkisto.models.Kuvitus;

public class KirjaResponse {
  private Long id;
  private String nimi;
  private String kirjailija;
  private Integer julkaisuVuosi;
  private Integer jarjestysNro;
  private String kuvaus;
  private List<Kuvitus> kuvitukset;

  public KirjaResponse() {}

  public KirjaResponse(Kirja kirja){
    this.id = kirja.getId();
    this.nimi = kirja.getNimi();
    this.kirjailija = kirja.getKirjailija();
    this.julkaisuVuosi = kirja.getJulkaisuVuosi();
    this.jarjestysNro = kirja.getJarjestysNro();
    this.kuvaus = kirja.getKuvaus();
    this.kuvitukset = kirja.getKuvitukset();
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNimi() {
    return this.nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public String getKirjailija() {
    return this.kirjailija;
  }

  public void setKirjailija(String kirjailija) {
    this.kirjailija = kirjailija;
  }

  public Integer getJulkaisuVuosi() {
    return this.julkaisuVuosi;
  }

  public void setJulkaisuVuosi(Integer julkaisuVuosi) {
    this.julkaisuVuosi = julkaisuVuosi;
  }

  public Integer getJarjestysNro() {
    return this.jarjestysNro;
  }

  public void setJarjestysNro(Integer jarjestysNro) {
    this.jarjestysNro = jarjestysNro;
  }

  public String getKuvaus() {
    return this.kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  public List<Kuvitus> getKuvitukset() {
    return this.kuvitukset;
  }

  public void setKuvitukset(List<Kuvitus> kuvitukset) {
    this.kuvitukset = kuvitukset;
  }

}
