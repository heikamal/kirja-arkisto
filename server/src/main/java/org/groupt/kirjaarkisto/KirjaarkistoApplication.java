package org.groupt.kirjaarkisto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class KirjaarkistoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KirjaarkistoApplication.class, args);
	}

  /* 
  @Bean
	public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/api/**");
			}
		};
	}
  */

}
