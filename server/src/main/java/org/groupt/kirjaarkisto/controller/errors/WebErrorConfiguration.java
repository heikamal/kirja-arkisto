package org.groupt.kirjaarkisto.controller.errors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Luokka määrittämään uusi konfiguraatio webbiserverin virheilmoituksille. Lähinnä ainoa mitä tarvitaan on virheistä raportoinnin osoite ja nykyinen apiversio.
 */
@Configuration
public class WebErrorConfiguration {
  /**
   * Raporttiosoite. Jos palvelulla on jokin valmis osoite mihin tiedon virheestä voi lähettää, niin se määritellään tässä. 
   * Saa arvonsa application.properties-tiedoston kirjaarkisto.sendreport.uri -kentästä.
   */
  @Value("${kirjaarkisto.sendreport.uri}")
  private String sendReportUri;

  /**
   * Apin versionumero. Saa arvonsa application.properties-tiedoston kirjaarkisto.api.version -kentästä.
   */
  @Value("${kirjaarkisto.api.version}")
  private String currentApiVersion;

  /**
   * Beani määrittämään sovelluksen käyttämät virheilmoitusten ominaisuudet.
   * 
   * @return ErrorAttributes-olio virheilmoitusten ominaisuuksista.
   */
  @Bean
  public ErrorAttributes errorAttributes() {
      return new KirjaArkistoAppErrorAttributes(currentApiVersion, sendReportUri);
  }
}
