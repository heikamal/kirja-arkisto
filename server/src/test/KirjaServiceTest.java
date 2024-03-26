package org.groupt.kirjaarkisto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KirjaServiceTest {

    @InjectMocks
    private KirjaService kirjaService;

    @Mock
    private KirjaRepository kirjaRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddKirja_Success() {
        
        Kirja testiKirja = new Kirja();
        testiKirja.setNimi("Testikirja");
        testiKirja.setKirjailija("Testikirjailija");
        testiKirja.setJulkaisuVuosi(2022);

        
        when(kirjaRepository.save(any(Kirja.class))).thenReturn(testiKirja);

       Kirja lisattyKirja = kirjaService.addKirja(testiKirja);

        assertNotNull(lisattyKirja);
        assertEquals("Testikirja", lisattyKirja.getNimi());
        assertEquals("Testikirjailija", lisattyKirja.getKirjailija());
        assertEquals(2022, lisattyKirja.getJulkaisuVuosi());
    }
}