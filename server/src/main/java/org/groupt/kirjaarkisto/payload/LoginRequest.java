package org.groupt.kirjaarkisto.payload;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
  private String nimi;

	@NotBlank
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
}