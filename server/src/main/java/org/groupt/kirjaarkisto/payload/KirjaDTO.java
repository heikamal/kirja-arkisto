package org.groupt.kirjaarkisto.payload;

public class KirjaDTO {
  
  /*
   * Kirjan nimi merkkijonona.
   */
  private String nimi;

  /**
   * Kirjan kirjailijan nimi merkkijonona.
   */
  private String kirjailija;

  /**
   * Kirjan julkaisuvuosi kokonaislukuna.
   */
  private Integer julkaisuVuosi;

  /**
   * Kirjan sarjan id kokonaislukuna. Kirjasarjan pitäisi olla tietokannassa jo valmiiksi.
   */
  private Long sarja;

  /**
   * Kirjan järjestynumero kirjasarjassa kokonaislukuna.
   */
  private Integer jarjestysNro;

  /**
   * Kirjan kuvaus merkkijonona.
   */
  private String kuvaus;

  /**
   * Parametriton alustaja joka luo uuden tyhjän KirjaDTO-olion.
   */
  public KirjaDTO() {}

  /**
   * Alustaa uuden KirjaDTO-olion kopioimalla parametina annetun olion kentät.
   * 
   * @param og KirjaDTO-olio joka kopioidaan.
   */
  public KirjaDTO(KirjaDTO og) {
    nimi = og.getNimi();
    kirjailija = og.kirjailija;
    julkaisuVuosi = og.getJulkaisuVuosi();
    sarja = og.getSarja();
    jarjestysNro = og.getJarjestysNro();
    kuvaus = og.getKuvaus();
  }


  /**
   * Metodi kirjan nimen saamiselle.
   * 
   * @return Kirjan nimi merkkijonona.
   */
  public String getNimi() {
    return this.nimi;
  }

  /**
   * Metodi kirjan nimen asettamiselle.
   *
   * @param nimi Kirjan uusi nimi merkkijonona.
   */
  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  /**
   * Metodi kirjan kirjailijan nimen palauttamiselle.
   * @return Kirjailijan nimi merkkijonona.
   */
  public String getKirjailija() {
    return this.kirjailija;
  }

  /**
   * Metodi kirjan kirjailijan nimen asettamiselle.
   * 
   * @param kirjailija Uusi kirjailijan nimi merkkijonona.
   */
  public void setKirjailija(String kirjailija) {
    this.kirjailija = kirjailija;
  }

  /**
   * Metodi kirjan julkaisuvuoden palauttamiselle.
   * @return Kirjan julkaisuvuosi kokonaislukuna.
   */
  public Integer getJulkaisuVuosi() {
    return this.julkaisuVuosi;
  }

  /**
   * Metodi kirjan julkaisuvuoden asettamiselle.
   * @param julkaisuVuosi Uusi kirjan julkaisuvuosi kokonaislukuna.
   */
  public void setJulkaisuVuosi(int julkaisuVuosi) {
    this.julkaisuVuosi = julkaisuVuosi;
  }

  /**
   * Metodi kirjan sarjan id:n palauttamiselle.
   * @return Kirjan sarjan id kokonaislukuna.
   */
  public Long getSarja() {
    return this.sarja;
  }

  /**
   * Metodi kirjan sarjan id:n asettamiselle.
   * @param sarja Uusi kirjan sarjan id kokonaislukuna.
   */
  public void setSarja(Long sarja) {
    this.sarja = sarja;
  }

  /**
   * Metodi kirjan järjestynumerojen asettamiselle.
   * @return Kirjan järjestynumero kirjasarjassa kokonaislukuna.
   */
  public Integer getJarjestysNro() {
    return this.jarjestysNro;
  }

  /**
   * Metodi kirjan järjestynumeron asettamiselle.
   * @param jarjestysNro Uusi kirjan järjestynumero kirjasarjassa kokonaislukuna.
   */
  public void setJarjestysNro(int jarjestysNro) {
    this.jarjestysNro = jarjestysNro;
  }

  /**
   * Metodi kirjan kuvauksen palauttamiselle.
   * @return Kirjan kuvaus merkkijonona.
   */
  public String getKuvaus() {
    return this.kuvaus;
  }

  /**
   * Metodi kirjan kuvauksen asettamiselle.
   * @param kuvaus Uusi kirjan kuvaus merkkijonona.
   */
  public void setKuvaus(String kuvaus) {
    this.kuvaus = kuvaus;
  }

  /**
   * Luokan toString-metodi olion tietojen näyttämiselle. Tulostaa kaikkien kenttien arvot näkyviin.
   * 
   * @return Kirjan tiedot merkkijonona.
   */
  @Override
  public String toString() {
    return "{" +
      " nimi='" + getNimi() + "'" +
      ", kirjailija='" + getKirjailija() + "'" +
      ", julkaisuvuosi='" + getJulkaisuVuosi() + "'" +
      ", sarja='" + getSarja() + "'" +
      ", jarjestysNro='" + getJarjestysNro() + "'" +
      ", kuvaus='" + getKuvaus() + "'" +
      "}";
  }


}