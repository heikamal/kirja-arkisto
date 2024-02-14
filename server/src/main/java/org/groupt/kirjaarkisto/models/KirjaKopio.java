package org.groupt.kirjaarkisto.models;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "kirjakopio")
public class KirjaKopio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjakopio")
    private Long id;

    @Column(name = "kirjanimi")
    private String title;

    @Column(name = "painos")
    private Integer editions;

    @Column(name = "painosvuosi")
    private Integer editionYear;

    @ManyToOne
    @JoinColumn(name = "idkirja")
    private kirja book;

    @Column(name = "hankintahinta")
    private Double purchasePrice;

    @Column(name = "hankintapvm")
    private Date purchaseDate;

    @Column(name = "kunto")
    private Integer condition;

    @Column(name = "kuvaus")
    private String description;

    @Column(name = "myyntipvm")
    private Date saleDate;

    @Column(name = "myyntihinta")
    private Double salePrice;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "idkirjahylly", referencedColumnName = "idkirjahylly"),
            @JoinColumn(name = "idkirjasarja", referencedColumnName = "idkirjasarja")
    })
    private kirjahylly bookShelf;

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
     * @return Integer return the editions
     */
    public Integer getEditions() {
        return editions;
    }

    /**
     * @param editions the editions to set
     */
    public void setEditions(Integer editions) {
        this.editions = editions;
    }

    /**
     * @return Integer return the editionYear
     */
    public Integer getEditionYear() {
        return editionYear;
    }

    /**
     * @param editionYear the editionYear to set
     */
    public void setEditionYear(Integer editionYear) {
        this.editionYear = editionYear;
    }

    /**
     * @return kirja return the book
     */
    public kirja getBook() {
        return book;
    }

    /**
     * @param book the book to set
     */
    public void setBook(kirja book) {
        this.book = book;
    }

    /**
     * @return Double return the purchasePrice
     */
    public Double getPurchasePrice() {
        return purchasePrice;
    }

    /**
     * @param purchasePrice the purchasePrice to set
     */
    public void setPurchasePrice(Double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    /**
     * @return Date return the purchaseDate
     */
    public Date getPurchaseDate() {
        return purchaseDate;
    }

    /**
     * @param purchaseDate the purchaseDate to set
     */
    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    /**
     * @return Integer return the condition
     */
    public Integer getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(Integer condition) {
        this.condition = condition;
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
     * @return Date return the saleDate
     */
    public Date getSaleDate() {
        return saleDate;
    }

    /**
     * @param saleDate the saleDate to set
     */
    public void setSaleDate(Date saleDate) {
        this.saleDate = saleDate;
    }

    /**
     * @return Double return the salePrice
     */
    public Double getSalePrice() {
        return salePrice;
    }

    /**
     * @param salePrice the salePrice to set
     */
    public void setSalePrice(Double salePrice) {
        this.salePrice = salePrice;
    }

    /**
     * @return kirjahylly return the bookShelf
     */
    public kirjahylly getBookShelf() {
        return bookShelf;
    }

    /**
     * @param bookShelf the bookShelf to set
     */
    public void setBookShelf(kirjahylly bookShelf) {
        this.bookShelf = bookShelf;
    }
}
