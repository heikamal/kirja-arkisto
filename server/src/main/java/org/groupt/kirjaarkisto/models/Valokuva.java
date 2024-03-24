package org.groupt.kirjaarkisto.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;

@Entity
@Table(name = "valokuva")
public class Valokuva {

    @ManyToOne
    @MapsId("idkuva")
    @JoinColumn(name = "idkuva")
    @JsonManagedReference
    private Kuva kuva;

    @Column(name = "kuvanimi")
    private String kuvanimi;

    @ManyToOne
    @MapsId("idkirjakopio")
    @JoinColumn(name = "idkirjakopio")
    @JsonBackReference
    private KirjaKopio kirjaKopio;

    @Column(name = "sivunnro")
    private Integer sivunnro;

    @Column(name = "tiedostonimi")
    private String tiedostonimi; 

    @Column(name = "picbyte", length = 10000000)
    private byte[] picByte;
   
    // Getters and setters
    
    /**
     * @return Long return the idkuva
     *  public Long getIdkuva() {
        return idkuva;
    }
     * 
     */
   
    /**
     * @param idkuva the idkuva to set
     *   public void setIdkuva(Long idkuva) {
        this.idkuva = idkuva;
    }
     */
  
    /**
     * @return String return the kuvanimi
     */
    public String getKuvanimi() {
        return kuvanimi;
    }

    /**
     * @param kuvanimi the kuvanimi to set
     */
    public void setKuvanimi(String kuvanimi) {
        this.kuvanimi = kuvanimi;
    }

    /**
     * @return KirjaKopio return the kirjaKopio
     */
    public KirjaKopio getKirjaKopio() {
        return kirjaKopio;
    }

    /**
     * @param kirjaKopio the kirjaKopio to set
     */
    public void setKirjaKopio(KirjaKopio kirjaKopio) {
        this.kirjaKopio = kirjaKopio;
    }

    /**
     * @return Integer return the sivunnro
     */
    public Integer getSivunnro() {
        return sivunnro;
    }

    /**
     * @param sivunnro the sivunnro to set
     */
    public void setSivunnro(Integer sivunnro) {
        this.sivunnro = sivunnro;
    }


    /**
     * @return String return the tiedostonimi
     */
    public String getTiedostonimi() {
        return tiedostonimi;
    }

    /**
     * @param tiedostonimi the tiedostonimi to set
     */
    public void setTiedostonimi(String tiedostonimi) {
        this.tiedostonimi = tiedostonimi;
    }

}

