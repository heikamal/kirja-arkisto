package org.groupt.kirjaarkisto.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.Objects;

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

  public KuvitusId getId() {
    return id;
  }

  public void setId(KuvitusId id) {
    this.id = id;
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
    if (sameAsFormer(kuva)) {
      return;
    }
    Kuva vanhaKuva = this.kuva;
    this.kuva = kuva;
    if (vanhaKuva != null){
      vanhaKuva.removeKuvitus(this);
    }
    if (kuva != null){
      kuva.addKuvitus(this);
    }
  }

  private boolean sameAsFormer(Kuva uusiKuva) {
    return kuva == null ? uusiKuva == null : kuva.equals(uusiKuva);
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


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", kirja='" + getKirja() + "'" +
      ", kuva='" + getKuva() + "'" +
      ", sivunro='" + getSivunro() + "'" +
      "}";
  }
  

}