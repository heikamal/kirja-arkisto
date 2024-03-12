package org.groupt.kirjaarkisto.models;
import jakarta.persistence.*;
@Entity
@Table(name = "kuvitus")
public class Kuvitus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idkuva")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "idkirja")
    private Kirja kirja;

    @ManyToOne
    @JoinColumn(name = "idkuva", insertable = false, updatable = false)
    private Kuva kuva;

    private Integer sivunro;

    

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
     * @return Kirja return the kirja
     */
    public Kirja getKirja() {
        return kirja;
    }

    /**
     * @param kirja the kirja to set
     */
    public void setKirja(Kirja kirja) {
        this.kirja = kirja;
    }

    /**
     * @return Kuva return the kuva
     */
    public Kuva getKuva() {
        return kuva;
    }

    /**
     * @param kuva the kuva to set
     */
    public void setKuva(Kuva kuva) {
        this.kuva = kuva;
    }

    /**
     * @return Integer return the sivunro
     */
    public Integer getSivunro() {
        return sivunro;
    }

    /**
     * @param sivunro the sivunro to set
     */
    public void setSivunro(Integer sivunro) {
        this.sivunro = sivunro;
    }

}