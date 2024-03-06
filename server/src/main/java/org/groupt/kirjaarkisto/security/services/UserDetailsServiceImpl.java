package org.groupt.kirjaarkisto.security.services;

import org.groupt.kirjaarkisto.models.Kayttaja;
import org.groupt.kirjaarkisto.repositories.KayttajaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
  
  @Autowired
  KayttajaRepository kayttajaRepository;

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Kayttaja kayttaja = kayttajaRepository.findByNimi(username)
    .orElseThrow(() -> new UsernameNotFoundException("Käyttäjää nimellä " + username + " ei loytynyt"));
    return UserDetailsImpl.build(kayttaja);
  }
}
