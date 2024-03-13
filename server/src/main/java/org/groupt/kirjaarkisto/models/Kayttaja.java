package org.groupt.kirjaarkisto.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * Luokka määrittämään käyttäjä-oliot käyttäjähallintaa varten.
 */
@Entity
@Table(name = "kayttaja")
public class Kayttaja {

  /**
   * Käyttäjän ID. Kokonaisnumero, joka toimii tietokannassa pääavaimena. Arvo määräytyy tietokannan mukaan.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "idkayttaja")
  private Long id;

  /**
   * Käyttäjän nimi merkkijonona.
   */
  @Column(name = "kayttajanimi")
  private String nimi;

  /**
   * Käyttäjän salasana salattuna merkkijonona.
   */
  @Column(name = "salasana")
  private String salasana;

  /**
   * Kokoelma käyttäjän rooleista. Käyttäjän ja roolin välillä on moni-moneen-suhde.
   */
  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "kayttajan_rooli", 
        joinColumns = @JoinColumn(name = "idkayttaja"), 
        inverseJoinColumns = @JoinColumn(name = "idrooli"))
  private Set<Role> roles = new HashSet<>();

  /**
   * Parametriton alustaja.
   */
  public Kayttaja() {

  }

  /**
   * Parametrillinen alustaja. Luo uuden olion nimen ja salasanan pohjalta.
   * 
   * @param nimi Käyttäjän nimi merkkijonona.
   * @param salasana Käyttäjän salasana jo valmiiksi hashattynä merkkijonona.
   */
  public Kayttaja(String nimi, String salasana) {
    this.nimi = nimi;
    this.salasana = salasana;
  }


  /**
   * Palauttaa käyttäjän ID:n.
   * 
   * @return ID-kentä arvo kokonaislukuna.
   */
  public Long getId() {
    return this.id;
  }

  /**
   * Asettaa ID-kentälle uuden arvon.
   * 
   * @param id Uusi ID kokonaislukuna.
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * Palauttaa käyttäjän nimen.
   * 
   * @return Käyttäjän nimi merkkijonona.
   */
  public String getNimi() {
    return this.nimi;
  }

  /**
   * Asettaa käyttäjän nimelle uuden arvon.
   * 
   * @param nimi Uusi nimi merkkijonona.
   */
  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  /**
   * Palauttaa käyttäjän salasanan. Salasanaa säilyetetään ainoastaan salatussa muodossa.
   * 
   * @return Käyttäjän hashatty salasana merkkijonona.
   */
  public String getSalasana() {
    return this.salasana;
  }

  /**
   * Asettaa käyttäjän salasanan. Salasanaa säilyetetään ainoastaan salatussa muodossa.
   *
   * @param salasana Uusi salattu salasana merkkijonona.
   */
  public void setSalasana(String salasana) {
    this.salasana = salasana;
  }

  /**
   * Palauttaa kokoelman käyttäjän rooleista.
   * 
   * @return Kokoelma rooleista.
   */
  public Set<Role> getRoles() {
    return this.roles;
  }

  /**
   * Asettaa käyttäjän roolit. Tällä hetkellä ainoat tuetut roolit ovat admin ja user. Arvot määräytyvät ERole-luokan mukaan.
   * 
   * @param roles Uusi kokoelma rooleista.
   */
  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
