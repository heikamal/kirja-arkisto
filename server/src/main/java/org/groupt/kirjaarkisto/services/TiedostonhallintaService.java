package org.groupt.kirjaarkisto.services;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class TiedostonhallintaService {

    @Value("${kuvat.kansio}")
    private String kuvatKansio;

    public String tallennaKuva(MultipartFile file) throws IOException {
        // luodaan polku
        Path tallennusPolku = Path.of(kuvatKansio).toAbsolutePath().normalize();
        Files.createDirectories(tallennusPolku);

        // nimen generointi
        String tiedostonimi = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // tallenna tiedosto
        Path tallennettuPolku = tallennusPolku.resolve(tiedostonimi).normalize();
        Files.copy(file.getInputStream(), tallennettuPolku, StandardCopyOption.REPLACE_EXISTING);

        return tiedostonimi;
    }

    public void poistaKuva(String tiedostonimi) throws IOException {

        Path tallennettuPolku = Path.of(kuvatKansio).resolve(tiedostonimi).normalize();

        // poista tiedosto luodusta polusta, parametrinä poistettavan tiedoston polku
        Files.deleteIfExists(tallennettuPolku);
    }
}

