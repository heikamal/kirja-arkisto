package org.groupt.kirjaarkisto.controller.errors;

import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

public class KirjaArkistoAppErrorAttributes extends DefaultErrorAttributes {

  private final String currentApiVersion;
  private final String sendReportUri;

  public KirjaArkistoAppErrorAttributes(final String currentApiVersion, final String sendReportUri) {
    this.currentApiVersion = currentApiVersion;
    this.sendReportUri = sendReportUri;
  }

  @Override
  public Map<String, Object> getErrorAttributes(final WebRequest webRequest, final boolean includeStackTrace) {
    final Map<String, Object> defaultErrorAttributes = super.getErrorAttributes(webRequest, false);
    final KirjaArkistoAppError kirjaArkistoAppError = KirjaArkistoAppError.fromDefaultAttributeMap(
      currentApiVersion, defaultErrorAttributes, sendReportUri
    );
    return kirjaArkistoAppError.toAttributeMap();
  }
  
}
