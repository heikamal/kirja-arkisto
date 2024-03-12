package org.groupt.kirjaarkisto.payload;

public class OmaSarjaDTO {

  Long sarjaId;

  public OmaSarjaDTO() {}

  public OmaSarjaDTO(Long id) {
    this.sarjaId = id;
  }

  public Long getSarjaId() {
    return sarjaId;
  }

  public void setSarjaId(Long id) {
    this.sarjaId = id;
  }
}
