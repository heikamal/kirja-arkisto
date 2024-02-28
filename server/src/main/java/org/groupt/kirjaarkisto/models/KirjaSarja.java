package org.groupt.kirjaarkisto.models;
import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "kirjasarja")
public class KirjaSarja {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjasarja")
    private Long id;

    @Column(name = "kirjasarja")
    private String title;

    @Column
    private String kustantaja;

    @Column
    private String kuvaus;

    @Column
    private String luokittelu;


    //vittu poista tää :D
    /** 
    * @OneToMany(mappedBy = "kirjasarja", cascade = CascadeType.ALL)
        private List<Kirja> kirjat;
    */
   
    //vittu poista tää :D
    /** 
    * @OneToMany(mappedBy = "kirjasarja", cascade = CascadeType.ALL)
        private List<Kirja> kirjat;
    */

    public KirjaSarja() {
        
    }

    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return String return the kustantaja
     */
    public String getKustantaja() {
        return kustantaja;
    }

    /**
     * @param kustantaja the kustantaja to set
     */
    public void setKustantaja(String kustantaja) {
        this.kustantaja = kustantaja;
    }

    /**
     * @return String return the kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * @param kuvaus the kuvaus to set
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * @return String return the luokittelu
     */
    public String getLuokittelu() {
        return luokittelu;
    }

    /**
     * @param luokittelu the luokittelu to set
     */
    public void setLuokittelu(String luokittelu) {
        this.luokittelu = luokittelu;
    }


  @Override
  public String toString() {
    return "{" +
      " id='" + getId() + "'" +
      ", title='" + getTitle() + "'" +
      ", kustantaja='" + getKustantaja() + "'" +
      ", kuvaus='" + getKuvaus() + "'" +
      ", luokittelu='" + getLuokittelu() + "'" +
      "}";
  }

  /**
   * Tarkistaa onko kirjasarjan mikään kenttä null. Jos yksikin kenttä on tyhjä, 
   * eli kentän sisältö palauttaa arvon null, niin metodi palauttaa true-totuusarvomuuttujan.
   * 
   * @return Palauttaa totuusarvomuuttujan. True jos oliolla on yksikin kenttä null, muuten false.
   */
  public boolean isNull(){
    return (this.getId() == null || this.getTitle() == null || this.getKustantaja() == null || this.getKuvaus() == null || this.getLuokittelu() == null);
  }

}