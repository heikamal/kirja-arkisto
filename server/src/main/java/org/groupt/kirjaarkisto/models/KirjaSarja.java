package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;
    /**
     * Tämä on spring model-luokka kirjasarjoille
     * Jokainen kirja kuuluu kirjasarjaan
     */
@Entity
@Table(name = "kirjasarja")
public class KirjaSarja {

    /**
     * Pääavaimen määrittely, kirjasarjan id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjasarja")
    private Long id;

    /**
     * Kirjasarjan nimi
     */
    @Column(name = "kirjasarja")
    private String title;

    /**
     * Kirjasarjan kustantaja, WSOY? Otava?
     */
    @Column
    private String kustantaja;

    /**
     * Kuvaus kirjasarjasta
     */
    @Column
    private String kuvaus;

    /**
     * Kirjasarjan luokittelu TAI GENRE
     */
    @Column
    private String luokittelu;

    /**
     * Parametritön alustaja
     */
    public KirjaSarja() {
        //on tyhjä koska parametritön alustaja ei tee mitään muuta
    }

    /**
     * Palauttaa kirjasarjan id:n
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Asettaa kirjasarjan id:n
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Palauttaa kirjasarjan nimen
     * @return String return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Asettaa kirjasarjan nimen
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Palauttaa kirjasarjan kustantajan
     * @return String return the kustantaja
     */
    public String getKustantaja() {
        return kustantaja;
    }

    /**
     * Asettaa kirjasarjan kustantajan
     * @param kustantaja the kustantaja to set
     */
    public void setKustantaja(String kustantaja) {
        this.kustantaja = kustantaja;
    }

    /**
     * Palauttaa kirjasarjan kuvauksen
     * @return String return the kuvaus
     */
    public String getKuvaus() {
        return kuvaus;
    }

    /**
     * Asettaa kirjasarjan kuvauksen
     * @param kuvaus the kuvaus to set
     */
    public void setKuvaus(String kuvaus) {
        this.kuvaus = kuvaus;
    }

    /**
     * Palauttaa kirjasarjan genren
     * @return String return the luokittelu
     */
    public String getLuokittelu() {
        return luokittelu;
    }

    /**
     * Asettaa kirjasarjan genren
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

}