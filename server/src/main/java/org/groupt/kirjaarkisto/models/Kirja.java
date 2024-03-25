package org.groupt.kirjaarkisto.models;
import java.util.List;
import jakarta.persistence.*;

/**
 * Luokka määrittelee kirja-olion tietokantaa varten.
 * Tämä luokka on JPA-entity, joka vastaa tietokantataulua
 */
@Entity
@Table(name = "kirja")
public class Kirja {

    /**
    * Kirjan yksilöllinen tunniste tietokannassa (id).
    * Springin @Id-annotaatio tarkoittaa että tämä attribuutti on pääavain tietokannassa.
    * Springin @GeneratedValue-annotaatio kertoo, että id-arvot ovat auto-increment.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirja")
    private Long id;

    /**
    * Kirjan nimi tietokannassa.
    * Spring @Column-annotaatio määrittelee sarakkeen nimen tietokannassa, tässä tapauksessa "kirjanimi".
    */
    @Column(name = "kirjanimi")
    private String nimi;

    /**
    *   Kirjan kirjailijan nimi
    */
    @Column(name = "kirjailija") 
    private String kirjailija;

    /**
    *   Kirjan julkaisuvuosi
    */
    @Column(name = "julkaisuvuosi")
    private Integer julkaisuVuosi;

    /**
    *   Kirjaan liittyvä kirjasarja tietokannassa.
    * Springin @ManyToOne-annotaatio merkitsee monen suhde yhteen -suhteen kirjan ja kirjasarjan välillä.
    * Springin @JoinColumn-annotaatio määrittelee liitostaulun sarakkeen nimen, tässä tapauksessa "idkirjasarja".
    */
    @ManyToOne
    @JoinColumn(name = "idkirjasarja")
    private KirjaSarja kirjaSarja;

    /**
    * Kirjasarjassa olevan kirjan järjestysnumero.
    * Tämä attribuutti määrittää kirjan järjestysnumeron tietokannassa, mikäli kirja kuuluu johonkin kirjasarjaan.
    * Springin @Column-annotaatio määrittelee sarakkeen nimen tietokannassa, tässä tapauksessa "jarjestysnro".
    */
    @Column(name = "jarjestysnro")
    private Integer jarjestysNro;

    /**
    *   Kirjan kuvaus tietokannassa, esimerkiksi takakannen teksti
    */
    @Column(name = "kuvaus")
    private String kuvaus;

    /**
     * Kirjaan liittyvät kuvitukset
     * yhden suhde moneen -suhteen kirjan ja kuvitusten välillä.
    */
    @OneToMany(mappedBy = "kirja", cascade = CascadeType.ALL)
    private List<Kuvitus> kuvitukset;

    public Kirja() {

    }

    /**
     * Alustaja, jonka tarkoitus on luoda kopio parametreina annetusta oliosta.
     * 
     * @param og Kirja-olio joka halutaan kopioida.
     */
    public Kirja(Kirja og) {
      this.id = og.getId();
      this.nimi = og.getNimi();
      this.kirjailija = og.getKirjailija();
      this.julkaisuVuosi = og.getJulkaisuVuosi();
      this.kirjaSarja = og.getBookSeries();
      this.jarjestysNro = og.getJarjestysNro();
      this.kuvaus = og.kuvaus;
    }

    /**
     * @return Long Palauttaa kirjan id:n
     */
    public Long getId() {
        return id;
    }

    /**
     * Asettaa kirjan id:n
     * @param id Kirjan id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Palauttaa kirjan otsikkonimen/titlen
     * @return String kirjan nimi
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * Asettaa kirjan otsikkonimen tai "titlen"
     * @param title ottaa parametrina merkkijonon joka on kirjan "title"
     */
    public void setNimi(String title) {
        this.nimi = title;
    }

    /**
     * Palauttaa kirjan julkaisuvuoden kokonaislukuna
     * @return Integer julkaisuvuosi
     */
    public Integer getJulkaisuVuosi() {
        return julkaisuVuosi;
    }

    /**
     * Asettaa julkaisuvuoden kirjalle, ottaa parametrina kokonaisluvun
     * @param publicationYear kirjan julkaisuvuosi
     */
    public void setJulkaisuVuosi(Integer publicationYear) {
        this.julkaisuVuosi = publicationYear;
    }

    /**
     * Palauttaa kirjasarja olion mihin sitä kutsuva kirja kuuluu
     * @return kirjasarja palauttaa kirjasarja olion
     */
    public KirjaSarja getBookSeries() {
        return kirjaSarja;
    }

    /**
     * asettaa kirjasarjan mihin sitä kutsuva kirja kuuluu
     * @param bookSeries asetettava kirjasarja
     */
    public void setBookSeries(KirjaSarja bookSeries) {
        this.kirjaSarja = bookSeries;
    }

    /**
     * Palauttaa kirjan järjestysnumeron sarjassa kokonaislukuna
     * @return Integer kokonaisluku järjestysnumeron
     */
    public Integer getJarjestysNro() {
        return jarjestysNro;
    }

    /**
     * Asettaa kirjan järjestysnumeron sarjassa
     * @param seriesOrder Kirjan järjestysnumero sarjassa kokonaislukuna
     */
    public void setJarjestysNro(Integer seriesOrder) {
        this.jarjestysNro = seriesOrder;
    }

    /**
     *  Palauttaa kirjan kuvauksen merkkijonona
     * @return String kirjan kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * Asettaa kirjan kuvauksen merkkijonona
     * @param description kirjan kuvaus
     */
    public void setKuvaus(String description) {
        this.kuvaus = description;
    }


    /**
     * Palauttaa kirjan kirjailijan nimen
     * @return String kirjailijan nimi
     */
    public String getKirjailija() {
        return kirjailija;
    }

    /**
     * Asettaa kirjan kirjailijan
     * @param kirjailija kirjailijan nimi
     */
    public void setKirjailija(String kirjailija) {
        this.kirjailija = kirjailija;
    }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", title='" + getNimi() + "'" +
      ", kirjailija='" + getKirjailija() + "'" +
      ", julkaisuVuosi='" + getJulkaisuVuosi() + "'" +
      ", kirjasarja='" + getBookSeries() + "'" +
      ", jarjestysNro='" + getJarjestysNro() + "'" +
      ", kuvaus='" + getKuvaus() + "'" +
      "}";
  }

    /**
     * Suomenkielinen metodi kirjasarjojen haulle :D duplikaatti siis :D
     * Jatkokehitystä varten, voi poistaa jos jaksaa etsiä kaikki kohdat missä käytetään
     * @return KirjaSarja palauttaa kirjasarjaolion
     */
    public KirjaSarja getKirjaSarja() {
        return kirjaSarja;
    }

    /**
     * Toinen duplikaattimetodi mutta suomeksi :D miksköhän täällä tämmöinen on :D
     * @param kirjaSarja asetettava kirjasarja kirjalle...
     */
    public void setKirjaSarja(KirjaSarja kirjaSarja) {
        this.kirjaSarja = kirjaSarja;
    }

    /**
     * Palauttaa listan kuvituksia joita kirjalle kuuluu
     * @return List<Kuvitus> palauttaa kuvituslistan
     */
    public List<Kuvitus> getKuvitukset() {
        return kuvitukset;
    }

    /**
     * asetataa listan kuvituksia joita kirjalle kuuluu
     * @param kuvitukset kuvituslista
     */
    public void setKuvitukset(List<Kuvitus> kuvitukset) {
        this.kuvitukset = kuvitukset;
    }

}