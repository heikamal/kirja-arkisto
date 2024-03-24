package org.groupt.kirjaarkisto.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

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

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((idkirja == null) ? 0 : idkirja.hashCode());
    result = prime * result + ((idkuva == null) ? 0 : idkuva.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    KuvitusId other = (KuvitusId) obj;
    if (idkirja == null) {
      if (other.idkirja != null)
        return false;
    } else if (!idkirja.equals(other.idkirja))
      return false;
    if (idkuva == null) {
      if (other.idkuva != null)
        return false;
    } else if (!idkuva.equals(other.idkuva))
      return false;
    return true;
  }
}