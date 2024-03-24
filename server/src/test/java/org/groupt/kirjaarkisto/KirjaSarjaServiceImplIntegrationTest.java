package org.groupt.kirjaarkisto;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.groupt.kirjaarkisto.models.KirjaSarja;
import org.groupt.kirjaarkisto.repositories.KirjaSarjaRepository;
import org.groupt.kirjaarkisto.services.KirjaSarjaService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class KirjaSarjaServiceImplIntegrationTest {

  @TestConfiguration
  static class KirjaSarjaServiceImplTestContextConfiguration {
    @Bean
    public KirjaSarjaService kirjaSarjaService() {
      return new KirjaSarjaService();
    }
  }

  @Autowired
  private KirjaSarjaService kirjaSarjaService;

  @MockBean
  private KirjaSarjaRepository kirjaSarjaRepository;

  @Before
  public void setUp() {
    KirjaSarja sarja = new KirjaSarja();
    sarja.setId(1L);
    sarja.setTitle("TestiSarja");
    sarja.setKustantaja("Otava");
    sarja.setKuvaus("Testisarjan testikuvaus testaustarkoituksiin-");
    sarja.setKuvaus("Kauhu");

    Mockito.when(kirjaSarjaRepository.findById(sarja.getId())).thenReturn(Optional.of(sarja));
  }

  @Test
  public void testFindById() {
    KirjaSarja sarja = kirjaSarjaService.getKirjasarjaById(1L);
    assertThat(sarja).isNotNull();
    assertThat(sarja.getId()).isEqualTo(1L);
  }
}