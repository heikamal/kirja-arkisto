package org.groupt.kirjaarkisto.models;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "kirja")
public class Kirja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirja")
    private Long id;

    @Column(name = "kirjanimi")
    private String nimi;

    @Column(name = "kirjailija") 
    private String kirjailija;

    @Column(name = "julkaisuvuosi")
    private Integer julkaisuVuosi;

    @ManyToOne
    @JoinColumn(name = "idkirjasarja")
    private KirjaSarja kirjaSarja;

    @Column(name = "jarjestysnro")
    private Integer jarjestysNro;

    @Column(name = "kuvaus")
    private String kuvaus;

    @OneToMany(mappedBy = "kirja")
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
      this.kirjaSarja = og.getKirjaSarja();
      this.jarjestysNro = og.getJarjestysNro();
      this.kuvaus = og.kuvaus;
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the title
     */
    public String getNimi() {
        return nimi;
    }

    /**
     * @param title the title to set
     */
    public void setNimi(String title) {
        this.nimi = title;
    }

    /**
     * @return Integer return the publicationYear
     */
    public Integer getJulkaisuVuosi() {
        return julkaisuVuosi;
    }

    /**
     * @param publicationYear the publicationYear to set
     */
    public void setJulkaisuVuosi(Integer publicationYear) {
        this.julkaisuVuosi = publicationYear;
    }

    /**
     * @return kirjasarja return the bookSeries
     */
    public KirjaSarja getKirjaSarja() {
        return kirjaSarja;
    }

    /**
     * @return Integer return the seriesOrder
     */
    public Integer getJarjestysNro() {
        return jarjestysNro;
    }

    /**
     * @param seriesOrder the seriesOrder to set
     */
    public void setJarjestysNro(Integer seriesOrder) {
        this.jarjestysNro = seriesOrder;
    }

    /**
     * @return String return the description
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * @param description the description to set
     */
    public void setKuvaus(String description) {
        this.kuvaus = description;
    }


    /**
     * @return String return the kirjailija
     */
    public String getKirjailija() {
        return kirjailija;
    }

    /**
     * @param kirjailija the kirjailija to set
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
      ", kirjasarja='" + getKirjaSarja() + "'" +
      ", jarjestysNro='" + getJarjestysNro() + "'" +
      ", kuvaus='" + getKuvaus() + "'" +
      "}";
  }

    /**
     * @param kirjaSarja the kirjaSarja to set
     */
    public void setKirjaSarja(KirjaSarja kirjaSarja) {
        this.kirjaSarja = kirjaSarja;
    }

    /**
     * @return List<Kuvitus> return the kuvitukset
     */
    public List<Kuvitus> getKuvitukset() {
        return kuvitukset;
    }

    /**
     * @param kuvitukset the kuvitukset to set
     */
    public void setKuvitukset(List<Kuvitus> kuvitukset) {
        this.kuvitukset = kuvitukset;
    }

}