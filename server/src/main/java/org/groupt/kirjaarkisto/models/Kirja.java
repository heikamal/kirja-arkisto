package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;

@Entity
@Table(name = "kirja")
public class Kirja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirja")
    private Long id;

    @Column(name = "kirjanimi")
    private String title;

    @Column(name = "kirjailija") 
    private String kirjailija;

    @Column(name = "julkaisuvuosi")
    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "idkirjasarja")
    private KirjaSarja kirjasarja;

    @Column(name = "jarjestysnro")
    private Integer seriesOrder;

    @Column(name = "kuvaus")
    private String description;

    public Kirja() {

    }

    /**
     * Alustaja, jonka tarkoitus on luoda kopio parametreina annetusta oliosta.
     * 
     * @param og Kirja-olio joka halutaan kopioida.
     */
    public Kirja(Kirja og) {
      this.id = og.getId();
      this.title = og.getTitle();
      this.kirjailija = og.getKirjailija();
      this.publicationYear = og.getPublicationYear();
      this.kirjasarja = og.getBookSeries();
      this.seriesOrder = og.getSeriesOrder();
      this.description = og.description;
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
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return Integer return the publicationYear
     */
    public Integer getPublicationYear() {
        return publicationYear;
    }

    /**
     * @param publicationYear the publicationYear to set
     */
    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    /**
     * @return kirjasarja return the bookSeries
     */
    public KirjaSarja getBookSeries() {
        return kirjasarja;
    }

    /**
     * @param bookSeries the bookSeries to set
     */
    public void setBookSeries(KirjaSarja bookSeries) {
        this.kirjasarja = bookSeries;
    }

    /**
     * @return Integer return the seriesOrder
     */
    public Integer getSeriesOrder() {
        return seriesOrder;
    }

    /**
     * @param seriesOrder the seriesOrder to set
     */
    public void setSeriesOrder(Integer seriesOrder) {
        this.seriesOrder = seriesOrder;
    }

    /**
     * @return String return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
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
      ", title='" + getTitle() + "'" +
      ", kirjailija='" + getKirjailija() + "'" +
      ", publicationYear='" + getPublicationYear() + "'" +
      ", kirjasarja='" + getBookSeries() + "'" +
      ", seriesOrder='" + getSeriesOrder() + "'" +
      ", description='" + getDescription() + "'" +
      "}";
  }


}