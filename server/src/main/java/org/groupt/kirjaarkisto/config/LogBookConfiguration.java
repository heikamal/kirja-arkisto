package org.groupt.kirjaarkisto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
@Configuration
public class LogBookConfiguration {
  
  @Bean
  public Logbook logbook() {
    return Logbook.create();
  }
}
