package org.groupt.kirjaarkisto.payload;

import java.util.List;

import org.groupt.kirjaarkisto.models.KirjaHylly;

public class HyllyResponse {
  private Long id;
  private Long omistaja;
  private List<SarjaResponse> sarjat;

  public HyllyResponse() {}

  public HyllyResponse(KirjaHylly hylly, List<SarjaResponse> sarjat) {
    this.id = hylly.getId();
    this.omistaja = hylly.getOmistaja();
    this.sarjat = sarjat;
  }


  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getOmistaja() {
    return this.omistaja;
  }

  public void setOmistaja(Long omistaja) {
    this.omistaja = omistaja;
  }

  public List<SarjaResponse> getSarjat() {
    return this.sarjat;
  }

  public void setKirjat(List<SarjaResponse> sarjat) {
    this.sarjat = sarjat;
  }

}
