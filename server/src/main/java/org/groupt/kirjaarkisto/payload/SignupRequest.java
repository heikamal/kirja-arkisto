package org.groupt.kirjaarkisto.payload;

import java.util.Set;

import jakarta.validation.constraints.*;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String nimi;

  private Set<String> rooli;

  @NotBlank
  @Size(min = 6, max = 40)
  private String salasana;

  public String getNimi() {
    return nimi;
  }

  public void setNimi(String username) {
    this.nimi = username;
  }

  public String getSalasana() {
    return salasana;
  }

  public void setSalasana(String password) {
    this.salasana = password;
  }

  public Set<String> getRooli() {
    return this.rooli;
  }

  public void setRooli(Set<String> role) {
    this.rooli = role;
  }
}