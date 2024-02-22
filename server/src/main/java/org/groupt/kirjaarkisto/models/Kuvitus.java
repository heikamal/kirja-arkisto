package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;

@Entity
@Table(name = "kuvitus")
public class Kuvitus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idkuva;

    @ManyToOne
    @JoinColumn(name = "idkirja")
    private Kirja kirja;

    @ManyToOne
    @JoinColumn(name = "idkuva")
    private Kuva kuva;

    private Integer sivunro;

}