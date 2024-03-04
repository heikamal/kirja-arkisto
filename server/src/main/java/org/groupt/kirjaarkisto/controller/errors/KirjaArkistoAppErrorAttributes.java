package org.groupt.kirjaarkisto.controller.errors;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.util.StringUtils;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Date;

/**
 * Luokka määrittää sovelluksen käyttämät oletusvirheilmoitusominaisuudet (sano se kymmenen kertaa nopeasti). Käyttää pohjana DefaultErrorAttributes-luokkaa
 * ja on varastanut suuren osan privaattimetodeita sieltä varmistaakseen tämän implementaation sujuvan toimivuuden.
 */
public class KirjaArkistoAppErrorAttributes extends DefaultErrorAttributes {

  /**
   * Apin versionumero
   */
  private final String currentApiVersion;

  /**
   * Osoite, jonne virheet voi raportoida.
   */
  private final String sendReportUri;

  /**
   * Luokan konstruktori. Luo uuden olion apin versionumeron ja raportointiosoitteen pohjalta.
   * 
   * @param currentApiVersion Nykyinen apiversio merkkijonona.
   * @param sendReportUri Osoite, jonne virheet voi raportoida merkkijonona.
   */
  public KirjaArkistoAppErrorAttributes(final String currentApiVersion, final String sendReportUri) {
    this.currentApiVersion = currentApiVersion;
    this.sendReportUri = sendReportUri;
  }

  /**
   * Palauttaa virheilmoituksen ominaisuudet. Ylikirjoittaa vastaavan metodin DefaultErrorAttributes-luokasta. 
   * Käyttää privaattimetodia getErrorAttributes, joka ollaan kirjoitettu uudelleen alla muiden lainattujen metodien kanssa.
   * @param webRequest Pyyntö joka aiheuttaa virheen WebRequest-oliona.
   * @param options Virheen ominaisuusasetukset ErrorAttributeOptions-oliona.
   * @return Map<String, Object> Virheen ominaisuudet.
   */
  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
    final Map<String, Object> defaultErrorAttributes = getErrorAttributes(webRequest, false);
    final KirjaArkistoAppError kirjaArkistoAppError = KirjaArkistoAppError.fromDefaultAttributeMap(
                currentApiVersion, defaultErrorAttributes, sendReportUri
    );
    return kirjaArkistoAppError.toAttributeMap();
  }

  private Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
		Map<String, Object> errorAttributes = new LinkedHashMap<>();
		errorAttributes.put("timestamp", new Date());
		addStatus(errorAttributes, webRequest);
		addErrorDetails(errorAttributes, webRequest, includeStackTrace);
		addPath(errorAttributes, webRequest);
		return errorAttributes;
	}

  private void addStatus(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		Integer status = getAttribute(requestAttributes, RequestDispatcher.ERROR_STATUS_CODE);
		if (status == null) {
			errorAttributes.put("status", 999);
			errorAttributes.put("error", "None");
			return;
		}
		errorAttributes.put("status", status);
		try {
			errorAttributes.put("error", HttpStatus.valueOf(status).getReasonPhrase());
		}
		catch (Exception ex) {
			// Unable to obtain a reason
			errorAttributes.put("error", "Http Status " + status);
		}
	}

	private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest,
			boolean includeStackTrace) {
		Throwable error = getError(webRequest);
		if (error != null) {
			while (error instanceof ServletException && error.getCause() != null) {
				error = error.getCause();
			}
			errorAttributes.put("exception", error.getClass().getName());
			if (includeStackTrace) {
				addStackTrace(errorAttributes, error);
			}
		}
		addErrorMessage(errorAttributes, webRequest, error);
	}

	private void addErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest, Throwable error) {
		BindingResult result = extractBindingResult(error);
		if (result == null) {
			addExceptionErrorMessage(errorAttributes, webRequest, error);
		}
		else {
			addBindingResultErrorMessage(errorAttributes, result);
		}
	}

	private void addExceptionErrorMessage(Map<String, Object> errorAttributes, WebRequest webRequest, Throwable error) {
		errorAttributes.put("message", getMessage(webRequest, error));
	}

	protected String getMessage(WebRequest webRequest, Throwable error) {
		Object message = getAttribute(webRequest, RequestDispatcher.ERROR_MESSAGE);
		if (!ObjectUtils.isEmpty(message)) {
			return message.toString();
		}
		if (error != null && StringUtils.hasLength(error.getMessage())) {
			return error.getMessage();
		}
		return "No message available";
	}

	private void addBindingResultErrorMessage(Map<String, Object> errorAttributes, BindingResult result) {
		errorAttributes.put("message", "Validation failed for object='" + result.getObjectName() + "'. "
				+ "Error count: " + result.getErrorCount());
		errorAttributes.put("errors", result.getAllErrors());
	}

	private BindingResult extractBindingResult(Throwable error) {
		if (error instanceof BindingResult bindingResult) {
			return bindingResult;
		}
		return null;
	}

	private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
		StringWriter stackTrace = new StringWriter();
		error.printStackTrace(new PrintWriter(stackTrace));
		stackTrace.flush();
		errorAttributes.put("trace", stackTrace.toString());
	}

	private void addPath(Map<String, Object> errorAttributes, RequestAttributes requestAttributes) {
		String path = getAttribute(requestAttributes, RequestDispatcher.ERROR_REQUEST_URI);
		if (path != null) {
			errorAttributes.put("path", path);
		}
	}

  @SuppressWarnings("unchecked")
	private <T> T getAttribute(RequestAttributes requestAttributes, String name) {
		return (T) requestAttributes.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
	}
  
}
