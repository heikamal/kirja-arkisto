package org.groupt.kirjaarkisto.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

/**

 * Tämä luokka edustaa "Kuvitus"-entiteettiä
 * Se yhdistää tietyn kuvan (Kuva-luokka) kirjaan (Kirja-luokkaan) ja sisältää sivunumeron.
 * jossa kuvitus esiintyy kirjassa.
 * 
 * Kuvalla voi siis olla monta kuvitusta. (Siis monia kuvia kirjasta, esimerkiksi eri sivuilta)
 */
@Entity
@Table(name = "kuvitus")
public class Kuvitus {


  @EmbeddedId
  private KuvitusId id;

  @ManyToOne
  @MapsId("idkirja")
  @JoinColumn(name = "idkirja")
  @JsonBackReference
  private Kirja kirja;

  @ManyToOne
  @MapsId("idkuva")
  @JoinColumn(name = "idkuva")
  @JsonManagedReference
  private Kuva kuva;

  private Integer sivunro;

  public Kuvitus() {
  }

  //parametrillinen konstruktori :D
  public Kuvitus(Kirja kirja, Kuva kuva, Integer sivunro) {
    this.id = new KuvitusId(kirja.getId(), kuva.getIdkuva());
    this.kirja = kirja;
    this.kuva = kuva;
    this.sivunro = sivunro;
  }


  /**
   * Palauttaa kirjan mihin kuvitus kuuluu
   * @return Kirja
   */
  public Kirja getKirja() {
    return kirja;
  }

  /**
   * Asettaa kuvitukselle kirjan
   * @param kirja 
   */
  public void setKirja(Kirja kirja) {
    this.kirja = kirja;
  }

  /**
   * Palauttaa kuvan mihin kuvitus kuuluu
   * @return Kuva 
   */
  public Kuva getKuva() {
    return kuva;
  }

  /**
   * Asettaa kuvan mihin kuvitus kuuluu
   * @param kuva
   */
  public void setKuva(Kuva kuva) {
    this.kuva = kuva;
  }

  /**
   * Palauttaa kuvituksen sivunumeron kokonaislukuna
   * @return Integer sivunro
   */
  public Integer getSivunro() {
    return sivunro;
  }

  /**
   * Asettaa kuvituksen sivunumeron kokonaislukuna
   * @param sivunro
   */
  public void setSivunro(Integer sivunro) {
    this.sivunro = sivunro;
  }

}