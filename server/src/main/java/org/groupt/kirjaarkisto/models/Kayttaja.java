package org.groupt.kirjaarkisto.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "kayttaja")
public class Kayttaja {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "idkayttaja")
  private Long id;

  @Column(name = "kayttajanimi")
  private String nimi;

  @Column(name = "salasana")
  private String salasana;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(  name = "kayttajan_rooli", 
        joinColumns = @JoinColumn(name = "idkayttaja"), 
        inverseJoinColumns = @JoinColumn(name = "idrooli"))
  private Set<Role> roles = new HashSet<>();

  public Kayttaja() {

  }

  public Kayttaja(String nimi, String salasana) {
    this.nimi = nimi;
    this.salasana = salasana;
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNimi() {
    return this.nimi;
  }

  public void setNimi(String nimi) {
    this.nimi = nimi;
  }

  public String getSalasana() {
    return this.salasana;
  }

  public void setSalasana(String salasana) {
    this.salasana = salasana;
  }

  public Set<Role> getRoles() {
    return this.roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

}
