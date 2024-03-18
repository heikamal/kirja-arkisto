package org.groupt.kirjaarkisto.models;

import jakarta.persistence.*;

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


  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public ERole getName() {
    return this.name;
  }

  public void setName(ERole name) {
    this.name = name;
  }
  
}
