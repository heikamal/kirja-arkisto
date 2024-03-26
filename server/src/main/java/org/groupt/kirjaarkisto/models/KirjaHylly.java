package org.groupt.kirjaarkisto.models;
import java.util.List;

import jakarta.persistence.*;

@Entity 
@Table(name = "kirjahylly")
public class KirjaHylly {

    /**
    * Pääavaimen määrittely, kirjahyllyn id.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjahylly")
    private Long id;

    /**
    * Kirjahyllyn omistajan id
    */
    @Column(name = "idkayttaja")
    private Long omistaja;

    /**
     * Monesta moneen -suhteen määrittely kirjahyllyn ja kirjasarjojen välillä.
     */
    @ManyToMany
    @JoinTable(
            name = "omatsarjat",
            joinColumns = @JoinColumn(name = "idkirjahylly"),
            inverseJoinColumns = @JoinColumn(name = "idkirjasarja")
    )
    private List<KirjaSarja> omatSarjat;

     /**
     * Oletuskonstruktori KirjaHylly-luokalle.
     */
    public KirjaHylly() {

    }

    
    /**
     * Metodi palauttaa kirjahyllyn id:n.
     * @return Kokonaisluku kirjahyllyn id.
     */
    public Long getId() {
        return id;
    }

    /**
     * Metodi asettaa kirjahyllyn id:n.
     * @param id Kokonaisluku kirjahyllyn id.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Metodi palauttaa kirjahyllyn omistajan id:n.
     * @return Kokonaisluku kirjahyllyn omistajan id.
     */
    public Long getOmistaja() {
        return omistaja;
    }

    /**
     * Metodi asettaa kirjahyllyn omistajan id:n.
     * @param omistaja Kokoknaisluku kirjahyllyn omistajan id.
     */
    public void setOmistaja(Long omistaja) {
        this.omistaja = omistaja;
    }

    /**
     * Metodi palauttaa kirjahyllyn omat kirjasarjat.
     * @return Kirjahyllyn omat kirjasarjat listana.
     */
    public List<KirjaSarja> getOmatSarjat() {
        return omatSarjat;
    }

    /**
     * Metodi asettaa kirjahyllylle kirjasarja-listan.
     * @param omatSarjat Kirjahyllyn omat kirjasarjat.
     */
    public void setOmatSarjat(List<KirjaSarja> omatSarjat) {
        this.omatSarjat = omatSarjat;
    }

    /**
     * Metodi lisää kirjasarjan kirjahyllyn omiin kirjasarjoihin.
     * @param sarja Lisättävä kirjasarja.
     */
    public void addToOmatSarjat(KirjaSarja sarja) {
      this.omatSarjat.add(sarja);
    }

    public void removeFromOmatSarjat(KirjaSarja sarja) {
      this.omatSarjat.remove(sarja);
    }
}