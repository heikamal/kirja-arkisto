package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;

/**
* Tämä luokka edustaa eri rooleja sovelluksessa.
*
* sisältää tiedot käyttäjärooleista kuten niiden tunnukset ja nimet.
*
* Käyttäjäroolit: Admin, User
*/
@Entity
@Table(name = "rooli")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idrooli")
  private Integer id;

  @Enumerated(EnumType.ORDINAL)
  @Column(name = "nimi")
  private ERole name;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

   /**
     * Palauttaa käyttäjän roolin ID:n kokonaislukuna
     * @return Integer rooli-ID
     */
  public Integer getId() {
    return this.id;
  }

   /**
     * Asettaa käyttäjän roolin ID:n kokonaislukuna
     * @Param Integer käyttäjän rooli-ID
     */
  public void setId(Integer id) {
    this.id = id;
  }

   /**
     * Palauttaa käyttäjän roolin nimen
     * @return ERole roolin nimi
     */
  public ERole getName() {
    return this.name;
  }
   /**
     * Asettaa käyttäjän roolin nimen
     * @Param ERole roolin nimi
     */
  public void setName(ERole name) {
    this.name = name;
  }
  
}
