# Käyttöohjeet

Kirja-arkisto ollaan tarkoitettu kirjasarjojen ja kirjojen tietojen arkistointiin ja helpottamaan esimerkiksi kokoelman keräämistä. Tämä dokumentti on yksinkertaistettu käyttöohjekirja sovelluksen käyttöön.

## Asennus ja käyttö

Tietokannan luontiskripti löytyy ./database/skriptit-kansiosta. Tällä hetkellä ainoastaan kehitystietokanta on toiminnallinen ja sen saa alustettua komennolla:

```bash
docker compose -f docker-compose.dev.yml up
```

Tietokantakontti käyttää viimeisintä mysql-imagea ja siihen saa yhteyden portissa 3306.

## Changelog

04/02/2024: readme.md luotu, lisätty ohjeet kehitystietokannan käyttöön.