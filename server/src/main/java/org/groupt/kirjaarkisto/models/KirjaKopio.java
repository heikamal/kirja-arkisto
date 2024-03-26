package org.groupt.kirjaarkisto.models;
import java.sql.Date;
import java.util.List;
import org.groupt.kirjaarkisto.payload.KirjaKopioDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

/**
   * Kirjakopio on olio joka luodaan kun kirja lisätään omaan kokoelmaan.
   * Kirjakopio on siis oma henkilökohtainen fyysinen kirjasi jonka tiedot tallennetaan kirjahyllyyn.
   */
@Entity
@Table(name = "kirjakopio")
public class KirjaKopio {

   /**
   * Pääavaimen määrittely kirjakopion id:lle.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idkirjakopio")
  private Long id;
  
   /**
   * Kirjakopion nimi
   */
  @Column(name = "kirjanimi")
  private String title;

  /**
   * Kopion painos, esim ensimmäinen painos
   */
  @Column(name = "painos")
  private Integer editions;

  /**
   * Kopion painosvuosi, esim 1947 painos
   */
  @Column(name = "painosvuosi")
  private Integer editionYear;

   /**
   * Monesta yhteen -suhteen määrittely kirjan ja kirjakopion välillä.
   */
  @ManyToOne
  @JoinColumn(name = "idkirja")
  private Kirja book;

   /**
   * Millä hinnalla käyttäjä on ostanut kirjan itsellensä
   */
  @Column(name = "hankintahinta")
  private Double purchasePrice;

   /**
   * Milloin käyttäjä osti/sai kirjan itsellensä
   */
  @Column(name = "hankintapvm")
  private Date purchaseDate;

  /**
   * Kirjan kunto kokonaislukuasteikolla 0-5
   */
  @Column(name = "kunto")
  private Integer condition;

  /**
   * Kirjakopion kuvaus, omia muistiinpanoja kirjasta yms...
   */
  @Column(name = "kuvaus")
  private String description;

  /**
   * myyntipäivämäärä, jos käyttäjä on esimerkiksi myynyt kirjansa mutta haluaa silti muistaa sen omistamisen
   */
  @Column(name = "myyntipvm")
  private Date saleDate;

  /**
   * millä hinnalla käyttäjä on myynyt kirjan
   */
  @Column(name = "myyntihinta")
  private Double salePrice;

  /**
   * Mihin kirjahyllyyn kirjakopio kuuluu
   */
  @Column(name = "idkirjahylly")
  private Long idKirjaHylly;

   /**
   * Mihin kirjasarjaan kirjakopio kuuluu
   */
  @Column(name = "idkirjasarja")
  private Long idKirjaSarja;

  public KirjaKopio() {

  }

  public KirjaKopio(KirjaKopioDTO dto, Kirja kirja, Long idKirjaHylly) {
    this.title = dto.getNimi();
    this.editions = dto.getPainos();
    this.editionYear = dto.getPainosVuosi();
    this.book = kirja;
    this.purchasePrice = dto.getOstoHinta();
    this.purchaseDate = dto.getOstoPvm();
    this.condition = dto.getKunto();
    this.description = dto.getKuvaus();
    this.saleDate = dto.getMyyntiPvm();
    this.salePrice = dto.getMyyntiHinta();
    this.idKirjaHylly = idKirjaHylly;
    this.idKirjaSarja = dto.getIdKirjaSarja();
  }

  /**
   * palauttaa kirjakopion id:n
   * @return Long id
   */
  public Long getId() {
    return id;
  }

  /**
   * Asettaa kirjakopion id:n 
   * @param id kirjakopion id
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Palauttaa kirjakopion nimen/otsikon
   * @return String title
   */
  public String getTitle() {
    return title;
  }

  /**
   * Asettaa kirjakopion nimen merkkijonona
   * @param title kirjakopion nimi
   */
  public void setTitle(String title) {
    this.title = title;
  }

  /**
   * Palauttaa kirjan painoksen kokonaislukuna
   * @return Integer painos
   */
  public Integer getEditions() {
    return editions;
  }

  /**
   * Asettaa kirjakopion painoksen kokonaislukuna
   * @param editions painos
   */
  public void setEditions(Integer editions) {
    this.editions = editions;
  }

  /**
   * Palauttaa kirjakopion painosvuoden kokonaislukuna
   * @return Integer painosvuosi
   */
  public Integer getEditionYear() {
    return editionYear;
  }

  /**
   * Asettaa kirjakopion painosvuoden kokonaislukuna
   * @param editionYear painosvuosi
   */
  public void setEditionYear(Integer editionYear) {
    this.editionYear = editionYear;
  }

  /**
   * Palauttaa minkä kirjan kopio kirjakopio on
   * @return kirja
   */
  public Kirja getBook() {
    return book;
  }

  /**
   * Asettaa minkä kirjan kopio kirjakopio on, parametrina kirjaolio
   * @param book kirja
   */
  public void setBook(Kirja book) {
    this.book = book;
  }

  /**
   * Palauttaa kirjan ostohinnan desimaalilukuna
   * @return Double purchasePrice
   */
  public Double getPurchasePrice() {
    return purchasePrice;
  }

  /**
   * Asettaa kirjan ostohinnan desimaalilukuna
   * @param purchasePrice purchasePrice
   */
  public void setPurchasePrice(Double purchasePrice) {
    this.purchasePrice = purchasePrice;
  }

  /**
   * Palauttaa kirjakopion ostopäivämäärän Date-muodossa DD:MM:YYYY
   * @return Date purchaseDate
   */
  public Date getPurchaseDate() {
    return purchaseDate;
  }

  /**
   * Asettaa kirjakopion ostopäivämäärään, Date-muodossa DD:MM:YYYY
   * @param purchaseDate ostopvm
   */
  public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  /**
   * Palauttaa kirjakopion kuntoluokituksen kokonaislukuna asteikolla 0-5
   * @return Integer condition
   */
  public Integer getCondition() {
    return condition;
  }

  /**
   * Asettaa kirjakopion kuntoluokituksen kokonaislukuna asteikolla 0-5
   * @param condition
   */
  public void setCondition(Integer condition) {
    this.condition = condition;
  }

  /**
   * Palauttaa kirjakopion kuvauksen merkkijonona
   * @return String description
   */
  public String getDescription() {
    return description;
  }

  /**
   * Asettaa kirjakopiolle kuvauksen merkkijonona
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Palauttaa kirjakopion myyntipäivän Date-muodossa DD:MM:YYYY
   * @return Date return saleDate
   */
  public Date getSaleDate() {
    return saleDate;
  }

  /**
   * Asettaa kirjakopion myyntipäivämäärän Date-muodossa DD:MM:YYYY
   * @param saleDate 
   */
  public void setSaleDate(Date saleDate) {
    this.saleDate = saleDate;
  }

  /**
   * Palauttaa kirjakopion myyntihinnan desimaalilukuna
   * @return Double return salePrice
   */
  public Double getSalePrice() {
    return salePrice;
  }

  /**
   * Asettaa kirjakopion myyntihinnan desimaalilukuna
   * @param salePrice 
   */
  public void setSalePrice(Double salePrice) {
    this.salePrice = salePrice;
  }

  /**
   * Palauttaa kirjakopion omistajan kirjahyllyn id:n
   * @return Long return idKirjaHylly
   */
  public Long getIdKirjaHylly() {
    return idKirjaHylly;
  }

  /**
   * Asettaa kirjakopion kirjahyllyn (omistajan) id:n
   * @param idKirjaHylly 
   */
  public void setIdKirjaHylly(Long idKirjaHylly) {
    this.idKirjaHylly = idKirjaHylly;
  }

  /**
   * Palauttaa kirjasarjan id:n mihin kirjakopio kuuluu
   * @return Long return idKirjaSarja
   */
  public Long getIdKirjaSarja() {
    return idKirjaSarja;
  }

  /**
   * Asettaa kirjasarjan mihin kirjakopio kuuluu
   * @param idKirjaSarja 
   */
  public void setIdKirjaSarja(Long idKirjaSarja) {
    this.idKirjaSarja = idKirjaSarja;
  }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", title='" + getTitle() + "'" +
      ", editions='" + getEditions() + "'" +
      ", editionYear='" + getEditionYear() + "'" +
      ", book='" + getBook() + "'" +
      ", purchasePrice='" + getPurchasePrice() + "'" +
      ", purchaseDate='" + getPurchaseDate() + "'" +
      ", condition='" + getCondition() + "'" +
      ", description='" + getDescription() + "'" +
      ", saleDate='" + getSaleDate() + "'" +
      ", salePrice='" + getSalePrice() + "'" +
      ", idKirjaHylly='" + getIdKirjaHylly() + "'" +
      ", idKirjaSarja='" + getIdKirjaSarja() + "'" +
      "}";
  }

}
