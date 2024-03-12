package org.groupt.kirjaarkisto.payload;

import java.sql.Date;

import org.groupt.kirjaarkisto.models.KirjaKopio;

public class KirjaKopioResponse {
  private Long id;
  private String nimi;
  private Integer painos;
  private Integer painosVuosi;
  private KirjaResponse kirja;
  private Double ostoHinta;
  private Date ostoPvm;
  private Integer kunto;
  private String kuvaus;
  private Date myyntiPvm;
  private Double myyntiHinta;
  private Long idKirjaHylly;
  private Long idKirjaSarja;

  public KirjaKopioResponse() {}

  public KirjaKopioResponse(KirjaKopio kopio, KirjaResponse kirja){
    this.id = kopio.getId();
    this.nimi = kopio.getTitle();
    this.painos = kopio.getEditions();
    this.painosVuosi = kopio.getEditionYear();
    this.kirja = kirja;
    this.ostoHinta = kopio.getPurchasePrice();
    this.ostoPvm = kopio.getPurchaseDate();
    this.kunto = kopio.getCondition();
    this.kuvaus = kopio.getDescription();
    this.myyntiPvm = kopio.getSaleDate();
    this.myyntiHinta = kopio.getSalePrice();
    this.idKirjaHylly = kopio.getIdKirjaHylly();
    this.idKirjaSarja = kopio.getIdKirjaSarja();
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

  public Integer getPainos() {
    return this.painos;
  }

  public void setPainos(Integer painos) {
    this.painos = painos;
  }

  public Integer getPainosVuosi() {
    return this.painosVuosi;
  }

  public void setPainosVuosi(Integer painosVuosi) {
    this.painosVuosi = painosVuosi;
  }

  public KirjaResponse getKirja() {
    return this.kirja;
  }

  public void setKirja(KirjaResponse kirja) {
    this.kirja = kirja;
  }

  public Double getOstoHinta() {
    return this.ostoHinta;
  }

  public void setOstoHinta(Double ostoHinta) {
    this.ostoHinta = ostoHinta;
  }

  public Date getOstoPvm() {
    return this.ostoPvm;
  }

  public void setOstoPvm(Date ostoPvm) {
    this.ostoPvm = ostoPvm;
  }

  public Integer getKunto() {
    return this.kunto;
  }

  public void setKunto(Integer kunto) {
    this.kunto = kunto;
  }

  public String getKuvaus() {
    return this.kuvaus;
  }

  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  public Date getMyyntiPvm() {
    return this.myyntiPvm;
  }

  public void setMyyntiPvm(Date myyntiPvm) {
    this.myyntiPvm = myyntiPvm;
  }

  public Double getMyyntiHinta() {
    return this.myyntiHinta;
  }

  public void setMyyntiHinta(Double myyntiHinta) {
    this.myyntiHinta = myyntiHinta;
  }

  public Long getIdKirjaHylly() {
    return this.idKirjaHylly;
  }

  public void setIdKirjaHylly(Long idKirjaHylly) {
    this.idKirjaHylly = idKirjaHylly;
  }

  public Long getIdKirjaSarja() {
    return this.idKirjaSarja;
  }

  public void setIdKirjaSarja(Long idKirjaSarja) {
    this.idKirjaSarja = idKirjaSarja;
  }


}
