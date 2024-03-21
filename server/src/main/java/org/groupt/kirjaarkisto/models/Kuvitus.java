package org.groupt.kirjaarkisto.models;

import java.io.Serializable;

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
  private Kirja kirja;

  @ManyToOne
  @MapsId("idkuva")
  @JoinColumn(name = "idkuva")
  private Kuva kuva;

  private Integer sivunro;

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

  @Embeddable
  class KuvitusId implements Serializable {

    @Column(name = "idkirja")
    private Long idkirja;

    @Column(name = "idkuva")
    private Long idkuva;


    public KuvitusId() {
    }

    public KuvitusId(Long idkirja, Long idkuva) {
      this.idkirja = idkirja;
      this.idkuva = idkuva;
    }

    public Long getIdkirja() {
      return idkirja;
    }

    public void setIdkirja(Long idkirja) {
      this.idkirja = idkirja;
    }

    public Long getIdkuva() {
      return idkuva;
    }

    public void setIdkuva(Long idkuva) {
      this.idkuva = idkuva;
    }
  }

}