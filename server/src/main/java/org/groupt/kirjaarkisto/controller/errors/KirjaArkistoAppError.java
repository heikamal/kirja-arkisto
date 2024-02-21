package org.groupt.kirjaarkisto.controller.errors;

import java.util.UUID;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Mukautettu virheluokka sovelluksen käyttöön. Tarkoitettu muuttaamaan kaapattu virhe JSON-muotoon noudattaen 
 * Googlen JSON-tyyliohjeistusta (https://google.github.io/styleguide/jsoncstyleguide.xml) joka voidaan sitten 
 * lähettää frontendille jotta täällä saadaan tarvittavat tiedot virheestä selkeästi luettavassa muodossa.
 */
public class KirjaArkistoAppError {

  private final String apiVersion;
  private final ErrorBlock error;

  public KirjaArkistoAppError(final String apiVersion, final String code, final String message, final String domain,
                             final String reason, final String errorMessage, final String errorReportUri) {
    this.apiVersion = apiVersion;
    this.error = new ErrorBlock(code, message, domain, reason, errorMessage, errorReportUri);
  }

  /**
   * Metodi, jonka on tarkoitus luoda uusi virhe Spring Bootin vakiovirheen pohjalta.
   * 
   * @param apiVersion
   * @param defaultErrorAttributes
   * @param sendReportBaseUri
   * @return
   */
  public static KirjaArkistoAppError fromDefaultAttributeMap(final String apiVersion,
                                                            final Map<String, Object> defaultErrorAttributes,
                                                            final String sendReportBaseUri) {
        // original attribute values are documented in org.springframework.boot.web.servlet.error.DefaultErrorAttributes
    return new KirjaArkistoAppError(
      apiVersion,
      ((Integer) defaultErrorAttributes.get("status")).toString(),
      (String) defaultErrorAttributes.getOrDefault("message", "no message available"),
      (String) defaultErrorAttributes.getOrDefault("path", "no domain available"),
      (String) defaultErrorAttributes.getOrDefault("error", "no reason available"),
      (String) defaultErrorAttributes.get("message"),
      sendReportBaseUri
    );
  }

  /**
   * Palauttaa kentät ja niiden arvot avain-arvoparina.
   * 
   * @return Api-versio ja virhe itsessään Map-kokoelmana.
   */
  public Map<String, Object> toAttributeMap() {
    return Map.of(
      "apiVersion", apiVersion,
      "error", error
      );
  }

  /**
   * Palauttaa apiVersion-kentän.
   * 
   * @return Api-versio merkkijonona.
   */
  public String getApiVersion() {
    return apiVersion;
  }

  /**
   * Palauttaa error-kentän.
   * 
   * @return Virheen sisältö.
   */
  public ErrorBlock getError() {
    return error;
  }

  /**
   * Alaluokka määrittämään virheviestin tyyli ensin Javan oliona, joka sitten myöhemmin käännetään JSON-muotoon.
   */
  private static final class ErrorBlock {
    /**
     * Virheen uniikki tunniste UUID-oliona.
     */
    @JsonIgnore
    private final UUID uniqueId;

    /**
     * Virheen HTTP-statuskoodi merkkijonona
     */
    private final String code;

    /**
     * Virheen viesti.
     */
    private final String message;

    /**
     * Lista virheistä mitkä ovat johtaneet virheviestin palautukseen.
     */
    private final List<Error> errors;

    /**
     * Alustaja joka tekee uuden olion statuskoodista, viestistä, palvelutunnisteesta, virheen syystä, virheviestistä ja raportointiosoitteesta.
     * Alustajaa käyttäessä virheen tunnisteeksi tulee uusi satunnainen UUID-olio.
     * 
     * @param code Virheen statuskoodi merkkijonona.
     * @param message Virheen viesti merkkijonona.
     * @param domain Virheen palvelutunniste merkkijonona.
     * @param reason Virheen syy merkkijonona.
     * @param errorMessage Virheviesti merkkijonona.
     * @param errorReportUri Osoite, johon virheen voi raportoida merkkijonona.
     */
    public ErrorBlock(final String code, final String message, final String domain, 
                      final String reason, final String errorMessage, final String errorReportUri) {
      this.code = code;
      this.message = message;
      this.uniqueId = UUID.randomUUID();
      this.errors = List.of(new Error(domain, reason, errorMessage, errorReportUri + "?id=" + uniqueId));
    }

    /**
     * Alustaja joka tekee uuden olion olemassa olevan tunnisteen, statuskoodin, viestin ja virhelistan pohjalta.
     * 
     * @param uniqueId Virheen uniikki tunniste UUID-oliona.
     * @param code Virheen statuskoodi merkkionona.
     * @param message Virheen viesti merkkijonona.
     * @param errors Lista tapatuneista virheistä.
     */
    public ErrorBlock(final UUID uniqueId, final String code, final String message, final List<Error> errors) {
      this.uniqueId = uniqueId;
      this.code = code;
      this.message = message;
      this.errors = errors;
    }

    /**
     * Metodi palauttaa kopion annetusta virheestä muuttaen virheen viestin uudeksi annetuksi.
     * 
     * @param s Virheblokki, joka halutaan kopioida.
     * @param message Uusi viesti.
     * @return Uusi virheblokki jossa viesti ollaan korvattu uudella.
     */
    public static ErrorBlock copyWithMessage(final ErrorBlock s, final String message) {
      return new ErrorBlock(s.uniqueId, s.code, message, s.errors);
    }

    /**
     * Palauttaa uniqueId-kentän.
     *
     * @return Virheblokin uniikki ID.
     */
    public UUID getUniqueId() {
      return uniqueId;
    }

    /**
     * Palauttaa code-kentän.
     *
     * @return Virheblokin statuskoodi merkkijonona.
     */
    public String getCode() {
      return code;
    }

    /**
     * Palauttaa message-kentän.
     *
     * @return Virheblokin viesti merkkijonona.
     */
    public String getMessage() {
      return message;
    }

    /**
     * Palauttaa errors-kentän.
     *
     * @return Lista virheblokin sisältämistä virheistä.
     */
    public List<Error> getErrors() {
      return errors;
    }
  }


  /**
   * Alaluokka määrittämään mukautettu virheen sisältö ja tämän raportointi.
   */
  private static final class Error {
    /**
     * Uniikki tunniste palvelulle missä virhe on tapahtunut.
     */
    private final String domain;

    /**
     * Syy miksi virhe tapahtui merkkijonona.
     */
    private final String reason;

    /**
     * Virheen viesti merkkijonona.
     */
    private final String message;

    /**
     * Raportoinnin osoite merkkijonona.
     */
    private final String sendReport;

    /**
     * Alaluokan konstruktori.
     * 
     * @param domain
     * @param reason
     * @param message
     * @param sendReport
     */
    public Error(final String domain, final String reason, final String message, final String sendReport) {
      this.domain = domain;
      this.reason = reason;
      this.message = message;
      this.sendReport = sendReport;
    }

    /**
     * Palauttaa domain-kentän.
     * @return
     */
    public String getDomain() {
      return domain;
    }

    /**
     * Palauttaa reason-kentän.
     * @return
     */
    public String getReason() {
      return reason;
    }

    /**
     * Palauttaa message-kentän.
     * @return
     */
    public String getMessage() {
      return message;
    }

    /**
     * Palauttaa sendReport-kentän.
     * @return
     */
    public String getSendReport() {
      return sendReport;
    }
  }
  
}
