package org.groupt.kirjaarkisto.models;
import java.util.List;

import jakarta.persistence.*;

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

    @Column(name = "tiedostonimi")
    private String tiedostonimi;

    @OneToMany(mappedBy = "kuva")
    private List<Kuvitus> kuvitukset;

    @Column(name = "picByte", length = 1000)
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

    public Long getIdkuva() {
        return idkuva;
        
    }


    /**
     * @param idkuva the idkuva to set
     */
    public void setIdkuva(Long idkuva) {
        this.idkuva = idkuva;
    }

    /**
     * @return String return the kuvanimi
     */
    public String getKuvanimi() {
        return kuvanimi;
    }

    /**
     * @param kuvanimi the kuvanimi to set
     */
    public void setKuvanimi(String kuvanimi) {
        this.kuvanimi = kuvanimi;
    }

    /**
     * @return Integer return the julkaisuvuosi
     */
    public Integer getJulkaisuvuosi() {
        return julkaisuvuosi;
    }

    /**
     * @param julkaisuvuosi the julkaisuvuosi to set
     */
    public void setJulkaisuvuosi(Integer julkaisuvuosi) {
        this.julkaisuvuosi = julkaisuvuosi;
    }

    /**
     * @return String return the taiteilija
     */
    public String getTaiteilija() {
        return taiteilija;
    }

    /**
     * @param taiteilija the taiteilija to set
     */
    public void setTaiteilija(String taiteilija) {
        this.taiteilija = taiteilija;
    }

    /**
     * @return String return the tyyli
     */
    public String getTyyli() {
        return tyyli;
    }

    /**
     * @param tyyli the tyyli to set
     */
    public void setTyyli(String tyyli) {
        this.tyyli = tyyli;
    }

    /**
     * @return String return the kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * @param kuvaus the kuvaus to set
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * @return String return the tiedostonimi
     */
    public String getTiedostonimi() {
        return tiedostonimi;
    }

    /**
     * @param tiedostonimi the tiedostonimi to set
     */
    public void setTiedostonimi(String tiedostonimi) {
        this.tiedostonimi = tiedostonimi;
    }

    public List<Kuvitus> getKuvitukset() {
        return kuvitukset;
    }

    public void setKuvitukset(List<Kuvitus> kuvitukset) {
        this.kuvitukset = kuvitukset;
    }

    public byte[] getPicByte() {
        return picByte;
    }

    public void setPicByte(byte[] picByte) {
        this.picByte = picByte;
    }

}