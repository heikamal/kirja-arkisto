package org.groupt.kirjaarkisto.models;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

/**
 * Tämä luokka edustaa "Kuva"-oliota kirja-arkistosovelluksessa.
 * 
 * Se sisältää tietoa kuvasta, kuten sen nimi, julkaisuvuosi, taiteilija, tyyli, kuvaus,
 * tiedostonimi ja itse kuvan bittitiedot. Se ylläpitää myös luetteloa "Kuvitus"-olioista.
 * jotka edustavat tähän kuvaan liittyviä kuvituksia.
 */
@Entity
@Table(name = "kuva")
public class Kuva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkuva")
    private Long idkuva;

    @Column(name = "kuvanimi")
    private String kuvanimi;

    @Column(name = "julkaisuvuosi")
    private Integer julkaisuvuosi;

    @Column(name = "taiteilija")
    private String taiteilija;

    @Column(name = "tyyli")
    private String tyyli;

    @Column(name = "kuvaus")
    private String kuvaus;

    //legacy koodia
    @Column(name = "tiedostonimi")
    private String tiedostonimi;

    @OneToMany(mappedBy = "kuva")
    @JsonBackReference
    private List<Kuvitus> kuvitukset;

    @Column(name = "picbyte", length = 10000000)
    private byte[] picByte;

    public Kuva() {
      super();
    }

    public Kuva(String kuvanimi, Integer julkaisuvuosi, String taiteilija, String tyyli, String kuvaus, String tiedostonimi, byte[] picByte) {
      this.kuvanimi = kuvanimi;
      this.julkaisuvuosi = julkaisuvuosi;
      this.taiteilija = taiteilija;
      this.tyyli = tyyli;
      this.kuvaus = kuvaus;
      this.tiedostonimi = tiedostonimi;
      this.picByte = picByte;
    }

     /**
     * Palauttaa kuvan id:n
     * @return idkuva
     */
    public Long getIdkuva() {
        return idkuva;
    }

    /**
     * Asettaa kuvan id:n
     * @param idkuva the idkuva to set
     */
    public void setIdkuva(Long idkuva) {
        this.idkuva = idkuva;
    }

    /**
     * Palauttaa kuvan nimen
     * @return String kuvanimi
     */
    public String getKuvanimi() {
        return kuvanimi;
    }

    /**
     * Asettaa kuvan nimen
     * @param kuvanimi kuvan nimi
     */
    public void setKuvanimi(String kuvanimi) {
        this.kuvanimi = kuvanimi;
    }

    /**
     * Palauttaa kuvan julkaisuvuoden kokonaislukuna
     * @return Integer julkaisuvuosi
     */
    public Integer getJulkaisuvuosi() {
        return julkaisuvuosi;
    }

    /**
     * Asettaa kuvan julkaisuvuoden kokonaislukuna
     * @param julkaisuvuosi 1999 esim
     */
    public void setJulkaisuvuosi(Integer julkaisuvuosi) {
        this.julkaisuvuosi = julkaisuvuosi;
    }

    /**
     * Palauttaa kuvan taiteilijan nimen merkkijonona
     * @return String taiteilija
     */
    public String getTaiteilija() {
        return taiteilija;
    }

    /**
     * Asettaa kuvan taiteilijan nimen merkkijonona
     * @param taiteilija the taiteilija to set
     */
    public void setTaiteilija(String taiteilija) {
        this.taiteilija = taiteilija;
    }

    /**
     * Palauttaa kuvan tyylin merkkijonona
     * @return String return the tyyli
     */
    public String getTyyli() {
        return tyyli;
    }

    /**
     * Asettaa kuvan tyylin merkkijonona
     * @param tyyli the tyyli to set
     */
    public void setTyyli(String tyyli) {
        this.tyyli = tyyli;
    }

    /**
     * Palauttaa kuvan kuvauksen merkkijonona, genre?
     * @return String return the kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * Asettaa kuvan kuvauksen merkkijonona, genre?
     * @param kuvaus the kuvaus to set
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * Palauttaa kuvan tiedostonimen, muodossa originalfilename tai millä nimellä tiedosto on ladattu palvelimelle
     * @return String return the tiedostonimi
     */
    public String getTiedostonimi() {
        return tiedostonimi;
    }

    /**
     * Asettaa kuvan tiedostonimen. pitäisi olla tarpeeton
     * @param tiedostonimi the tiedostonimi to set
     */
    public void setTiedostonimi(String tiedostonimi) {
        this.tiedostonimi = tiedostonimi;
    }
     /**
     * Palauttaa sitä kutsuvan kuvan kuvitukset listana
     */
    public List<Kuvitus> getKuvitukset() {
        return new ArrayList<>(kuvitukset);
    }
    
     /**
     * Palauttaa kuvan bittimuodossa
     * @return byte[]
     */
    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }


  @Override
  public String toString() {
    return "{" +
      " idkuva='" + getIdkuva() + "'" +
      ", kuvanimi='" + getKuvanimi() + "'" +
      ", julkaisuvuosi='" + getJulkaisuvuosi() + "'" +
      ", taiteilija='" + getTaiteilija() + "'" +
      ", tyyli='" + getTyyli() + "'" +
      ", kuvaus='" + getKuvaus() + "'" +
      ", tiedostonimi='" + getTiedostonimi() + "'" +
      "," + "'" +
      "}";
  }

  public void removeKuvitus(Kuvitus kuvitus) {
    if (!kuvitukset.contains(kuvitus)) {
      return;
    }
    kuvitukset.remove(kuvitus);
    kuvitus.setKuva(null);
  }

  public void addKuvitus(Kuvitus kuvitus) {
    if (kuvitukset.contains(kuvitus)) {
      return;
    }
    kuvitukset.add(kuvitus);
    kuvitus.setKuva(this);
  }

}