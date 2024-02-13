package org.groupt.kirjaarkisto.models;
import javax.persistence.*;

@Entity
@Table(name = "kirja")
public class kirja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirja")
    private Long id;

    @Column(name = "kirjanimi")
    private String title;

    @Column(name = "julkaisuvuosi")
    private Integer publicationYear;

    @ManyToOne
    @JoinColumn(name = "idkirjasarja")
    private kirjasarja bookSeries;

    @Column(name = "jarjestysnro")
    private Integer seriesOrder;

    @Column(name = "kuvaus")
    private String description;


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
    public kirjasarja getBookSeries() {
        return bookSeries;
    }

    /**
     * @param bookSeries the bookSeries to set
     */
    public void setBookSeries(kirjasarja bookSeries) {
        this.bookSeries = bookSeries;
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

}