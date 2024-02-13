package org.groupt.kirjaarkisto.models;
import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name = "kirjakopio")
public class kirjakopio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkirjakopio")
    private Long id;

    @Column(name = "kirjanimi")
    private String title;

    @Column(name = "painos")
    private Integer editions;

    @Column(name = "painosvuosi")
    private Integer editionYear;

    @ManyToOne
    @JoinColumn(name = "idkirja")
    private kirja book;

    @Column(name = "hankintahinta")
    private Double purchasePrice;

    @Column(name = "hankintapvm")
    private Date purchaseDate;

    @Column(name = "kunto")
    private Integer condition;

    @Column(name = "kuvaus")
    private String description;

    @Column(name = "myyntipvm")
    private Date saleDate;

    @Column(name = "myyntihinta")
    private Double salePrice;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "idkirjahylly", referencedColumnName = "idkirjahylly"),
            @JoinColumn(name = "idkirjasarja", referencedColumnName = "idkirjasarja")
    })
    private kirjahylly bookShelf;
    // Getterit ja setterit
}
