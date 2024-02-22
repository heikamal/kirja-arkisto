package org.groupt.kirjaarkisto.controller.errors;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebErrorConfiguration {

  //TODO: nämä enveiksi
  //@Value("${kirjaarkisto.sendreport.uri}")
  private String sendReportUri = "Huuda 'Heikki auta' niin kovaa kuin keuhkoista lähtee";
  //@Value("${kirjaarkisto.api.version}")
  private String currentApiVersion = "0.1";

  @Bean
  public ErrorAttributes errorAttributes() {
      return new KirjaArkistoAppErrorAttributes(currentApiVersion, sendReportUri);
  }
}
