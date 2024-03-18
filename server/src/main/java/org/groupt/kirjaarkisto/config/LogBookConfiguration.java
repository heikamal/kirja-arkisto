package org.groupt.kirjaarkisto.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.logbook.Logbook;
import static org.zalando.logbook.json.JsonPathBodyFilters.jsonPath;
@Configuration
public class LogBookConfiguration {

  @Value("${kirjaarkisto.api.environment}")
  private String environment;
  
  @Bean
  public Logbook logbook() {
    switch (environment) {
      case "dev":
        return Logbook.create();
      case "test":
        return Logbook.create();
      default:
        return Logbook.builder().bodyFilter(jsonPath("$.salasana").delete()).build();
    }
    
  }
}
