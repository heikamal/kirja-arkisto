package org.groupt.kirjaarkisto.models;
import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Tämä luokka edustaa Kuvitus-olion yhdistelmäavainta.
 *
 * Käytännössä se siis yksilöi kuvitukset siihen liittyvien kuvien ja kirjojen pääavaimien ja tunnisteiden perusteella
 */
@Embeddable
public
class KuvitusId implements Serializable {

  @Column(name = "idkirja")
  private Long idkirja;

  @Column(name = "idkuva")
  private Long idkuva;

  public KuvitusId() {
  }

  //parametrillinen konstruktori :D
  public KuvitusId(Long idkirja, Long idkuva) {
    this.idkirja = idkirja;
    this.idkuva = idkuva;
  }

  //get ja setterit, kyl te tiiätte... 

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

  //equals metodin implementaatio kuvituksille, käytetään tarkistukseen onko kuvitus eri kuin parametrikuvitus
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


  @Override
  public String toString() {
    return "{" +
      " idkirja='" + getIdkirja() + "'" +
      ", idkuva='" + getIdkuva() + "'" +
      "}";
  }

}