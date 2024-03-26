# Kirja-Arkisto

Kirja-arkisto ollaan tarkoitettu kirjasarjojen ja kirjojen tietojen arkistointiin ja helpottamaan esimerkiksi kokoelman keräämistä. Tämä dokumentti on yksinkertaistettu käyttöohjekirja sovelluksen käyttöön.

## Asennus

Kehittämisympäristön saa Docker - konttiin suorittamalla komennon:

```bash
docker compose -f docker-compose.dev.yml up
```
Tietokantakontti käyttää viimeisintä mysql - imagea ja siihen saa yhteyden portissa 3306.
Backend amazoncorretto:21-alpine-jdk - imagea pohjanaan ja pyörii portissa 8080.
Frontend käyttää pohjanaan node:21-alpine3.19 - imagea ja siihen saa yhteyden portissa 4200.

Tuotantoympäristo vaatii toimiakseen docker stack deployta ja dockerille secretien asettamista docker swarmilla. Tuotantokonttia varten tarvitsee määrittää secretit:
- db_user_password, tietokannan käyttäjän salasana
- api_admin_password, sovelluksen adminisalasana. Backend luo adminikäyttäjän tällä salasanalla.
- jwt_secret, JSON Webtokenia varten salasana, jota käytetään tokenien muodostamiseen. Ota huomion pituusvaativuudet.

Tämän lisäksi sekä frontend ja backend tarvitsevat omat imagensa. Niiden nimiä voi muokata docker-compose.yml-tiedostossa.


## Osallistuminen

Älä osallistu vielä, tää on aik pahasti kesken..

## Lisenssi

[Ei me mitää lisenssei tarvita](https://www.youtube.com/watch?v=tdSJjY3lWvE)