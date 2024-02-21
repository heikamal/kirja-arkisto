package org.groupt.kirjaarkisto;

import java.io.IOException;

import org.springframework.http.HttpMethod;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CorsFilter implements Filter{

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    if (res instanceof HttpServletRequest) {
      final HttpServletResponse response = (HttpServletResponse) res;
      response.setHeader("Access-Control-Allow-Origin", "*");
      response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
      response.setHeader("Access-Control-Allow-Headers", "Authorization, Content-Type");
      response.setHeader("Access-Control-Max-Age", "3600");
      if (HttpMethod.OPTIONS.name().equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
        response.setStatus(HttpServletResponse.SC_OK);
      } else {
        chain.doFilter(req, res);
      }
    } else {
      //TODO: Kehitystarkoituksiin, muista poistaa
      System.out.println("deeb doob I'm a boob");
    }
  }

  @Override
    public void destroy() {
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    } 
}
