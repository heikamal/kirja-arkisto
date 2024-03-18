package org.groupt.kirjaarkisto.services;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class TiedostonhallintaService {
private static final String KUVAKANSIO = "kuvat";

    public static String tallennaKuva(MultipartFile file) throws IOException {
        String tiedostoNimi = file.getOriginalFilename();
        Path tallennusPolku = Paths.get(KUVAKANSIO).toAbsolutePath().normalize();
        Path tallennusTiedosto = tallennusPolku.resolve(tiedostoNimi);

        File tallennusKansio = new File(String.valueOf(tallennusPolku));
        if (!tallennusKansio.exists()) {
            tallennusKansio.mkdirs();
        }

        file.transferTo(tallennusTiedosto.toFile());
        return tiedostoNimi;
    }
    
    public void poistaKuva(String tiedostoNimi) throws IOException {
        Path tallennettuPolku = Paths.get(KUVAKANSIO).resolve(tiedostoNimi).normalize();

        Files.deleteIfExists(tallennettuPolku);
    }
}
