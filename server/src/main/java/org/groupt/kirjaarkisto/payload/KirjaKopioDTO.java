package org.groupt.kirjaarkisto.payload;

import java.sql.Date;

public class KirjaKopioDTO {

    private String nimi;

    private Integer painos;

    private Integer painosVuosi;

    private Long kirjaId;

    private Double ostoHinta;

    private Date ostoPvm;

    private Integer kunto;

    private String kuvaus;

    private Date myyntiPvm;

    private Double myyntiHinta;

    private Long idKirjaSarja;

    public KirjaKopioDTO() {}


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

  public Long getKirjaId() {
    return this.kirjaId;
  }

  public void setKirjaId(Long kirjaId) {
    this.kirjaId = kirjaId;
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

  public Long getIdKirjaSarja() {
    return this.idKirjaSarja;
  }

  public void setIdKirjaSarja(Long idKirjaSarja) {
    this.idKirjaSarja = idKirjaSarja;
  }

}
