package org.groupt.kirjaarkisto.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "kuvitus")
public class Kuvitus {

  /*
   * @Id
   * 
   * @GeneratedValue(strategy = GenerationType.IDENTITY)
   * 
   * @Column(name = "idkuva")
   * private Long id;
   */

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

  public Kuvitus(Kirja kirja, Kuva kuva, Integer sivunro) {
    this.id = new KuvitusId(kirja.getId(), kuva.getIdkuva());
    this.kirja = kirja;
    this.kuva = kuva;
    this.sivunro = sivunro;
  }

  /* 
  /**
   * @return Long return the id
   *
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   *
  public void setId(Long id) {
    this.id = id;
  }*/

  /**
   * @return Kirja return the kirja
   */
  public Kirja getKirja() {
    return kirja;
  }

  /**
   * @param kirja the kirja to set
   */
  public void setKirja(Kirja kirja) {
    this.kirja = kirja;
  }

  /**
   * @return Kuva return the kuva
   */
  public Kuva getKuva() {
    return kuva;
  }

  /**
   * @param kuva the kuva to set
   */
  public void setKuva(Kuva kuva) {
    this.kuva = kuva;
  }

  /**
   * @return Integer return the sivunro
   */
  public Integer getSivunro() {
    return sivunro;
  }

  /**
   * @param sivunro the sivunro to set
   */
  public void setSivunro(Integer sivunro) {
    this.sivunro = sivunro;
  }

}