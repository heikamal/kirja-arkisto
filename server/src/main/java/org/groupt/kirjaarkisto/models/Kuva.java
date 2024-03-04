package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;

@Entity
@Table(name = "kuva")
public class Kuva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkuva")
    private Long idkuva;

    @Column(name = "kuvanimi")
    private String kuvanimi;

    @Column(name = "julkaisuvuosi")
    private Integer julkaisuvuosi;

    @Column(name = "taiteilija")
    private String taiteilija;

    @Column(name = "tyyli")
    private String tyyli;

    @Column(name = "kuvaus")
    private String kuvaus;

    @Column(name = "tiedostonimi")
    private String tiedostonimi;

  
    public Long getIdkuva() {
        return idkuva;
        
    }

}