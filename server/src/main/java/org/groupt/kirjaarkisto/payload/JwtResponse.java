package org.groupt.kirjaarkisto.payload;

import java.util.List;

public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private Long id;
  private String nimi;
  private List<String> roolit;

  public JwtResponse(String accessToken, Long id, String nimi, List<String> roolit) {
    this.token = accessToken;
    this.id = id;
    this.nimi = nimi;
    this.roolit = roolit;
  }

  public String getAccessToken() {
    return token;
  }

  public void setAccessToken(String accessToken) {
    this.token = accessToken;
  }

  public String getTokenType() {
    return type;
  }

  public void setTokenType(String tokenType) {
    this.type = tokenType;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String username) {
    this.nimi = username;
  }

  public List<String> getRoolit() {
    return roolit;
  }
}
