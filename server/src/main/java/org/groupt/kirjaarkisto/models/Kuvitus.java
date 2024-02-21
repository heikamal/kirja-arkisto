package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;
@Entity
@Table(name = "kuvitus")
public class Kuvitus {
    @EmbeddedId
    private KuvitusId id;

    @ManyToOne
    @JoinColumn(name = "idkuva", insertable = false, updatable = false)
    private Kuva kuva;

    @ManyToOne
    @JoinColumn(name = "idkirja", insertable = false, updatable = false)
    private Kirja kirja;

    @Column(name = "sivunro")
    private Integer sivunro;

    // getterit ja setterit
}
