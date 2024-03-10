package org.groupt.kirjaarkisto.models;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "kirjahylly")
public class KirjaHylly {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjahylly")
    private Long id;

    @Column(name = "idkayttaja")
    private Long omistaja;

    
    @ManyToMany
    @JoinTable(
            name = "omatsarjat",
            joinColumns = @JoinColumn(name = "idkirjahylly"),
            inverseJoinColumns = @JoinColumn(name = "idkirjasarja")
    )
    private List<KirjaSarja> omatSarjat;


    public KirjaHylly() {

    }

    // Getterit ja setterit

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOmistaja() {
        return omistaja;
    }

    public void setOmistaja(Long omistaja) {
        this.omistaja = omistaja;
    }

    /*
    public List<KirjaKopio> getKirjakopiot() {
        return kirjakopiot;
    }

    public void setKirjakopiot(List<KirjaKopio> kirjakopiot) {
        this.kirjakopiot = kirjakopiot;
    }

    */
    public List<KirjaSarja> getOmatSarjat() {
        return omatSarjat;
    }

    public void setOmatSarjat(List<KirjaSarja> omatSarjat) {
        this.omatSarjat = omatSarjat;
    }
}