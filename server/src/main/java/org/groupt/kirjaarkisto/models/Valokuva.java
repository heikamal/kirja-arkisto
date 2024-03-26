package org.groupt.kirjaarkisto.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;

/**
 * Valokuva luokka ja/tai entiteetti on käytännössä kirjakopioiden kuvitus.
 *
 * Sitä käytetään erottelemaan Kirjan ja kirjakopioiden "kuvitukset" tai kuvat
 * Käytännössä siis valokuvat ovat käyttäjän itse ottamia kuvia jotka lisätään kirjakopioille omaan kirjahyllyyn.
 * 
 * Tiedot: idkuva (tunniste), kuvan nimi, sivunumero, tiedostonimi, kuvan bittimuoto tietokantaan tallennusta varten ja siihen liittyvä kirjakopio
 */
@Entity
@Table(name = "valokuva")
public class Valokuva {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idkuva")
  private Long idkuva;

  @Column(name = "kuvanimi")
  private String kuvanimi;

  @ManyToOne
  @JoinColumn(name = "idkirjakopio")
  @JsonBackReference
  private KirjaKopio kirjaKopio;

  @Column(name = "sivunnro")
  private Integer sivunnro;

  @Column(name = "tiedostonimi")
  private String tiedostonimi;

  @Column(name = "picbyte", length = 10000000)
  private byte[] picByte;

  // Getters ja setters

  /**
   * Palauttaa valokuvan nimen merkkijonona
   * @return String return the kuvanimi
   */
  public String getKuvanimi() {
    return kuvanimi;
  }

  /**
   * Asettaa valokuvan nimen
   * @param kuvanimi nimi
   */
  public void setKuvanimi(String kuvanimi) {
    this.kuvanimi = kuvanimi;
  }

  /**
   * palauttaa kirjakopion mihin valokuva kuuluu
   * @return KirjaKopio kirjakopio
   */
  public KirjaKopio getKirjaKopio() {
    return kirjaKopio;
  }

  /**
   * asettaa kirjakopion mihin valokuva kuuluu
   * @param kirjaKopio 
   */
  public void setKirjaKopio(KirjaKopio kirjaKopio) {
    this.kirjaKopio = kirjaKopio;
  }

  /**
   * Palauttaa sivunumeron kokonaislukuna
   * @return Integer sivunnro
   */
  public Integer getSivunnro() {
    return sivunnro;
  }

  /**
   * asettaa sivunumeron kokonaislukuna
   * @param sivunnro sivunnro
   */
  public void setSivunnro(Integer sivunnro) {
    this.sivunnro = sivunnro;
  }

  /**
   * legacy-metodi
   */
  public String getTiedostonimi() {
    return tiedostonimi;
  }

  /**
   * legacy-metodi
   */
  public void setTiedostonimi(String tiedostonimi) {
    this.tiedostonimi = tiedostonimi;
  }
  /**
   * asettaa valokuvan id:n
   * @param idkuva
   */
  public void setIdkuva(Long idkuva) {
    this.idkuva = idkuva;
  }
  /**
   * palauttaa valokuvan id:n
   * @return Long idkuva
   */
  public Long getIdkuva() {
    return this.idkuva;
  }

  public byte[] getPicByte() {
    return this.picByte;
  }

  public void setPicByte(byte[] picByte) {
    this.picByte = picByte;
  }


}
