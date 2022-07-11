package de.hsrm.mi.web.projekt.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import de.hsrm.mi.web.projekt.projektuser.ProjektUser;
import de.hsrm.mi.web.projekt.projektuser.ProjektUserRepository;

@Service
public class ProjektUserDetailsService implements UserDetailsService {
    
    @Autowired private ProjektUserRepository pUserRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
         // Schritt 1: notwendige Daten zu Login-Name 'username' besorgen, z.B. aus Datenbank
         ProjektUser user = pUserRepository.findById(username)
         .orElseThrow(() -> new UsernameNotFoundException(username));
        // Schritt 2: Spring 'User'-Objekt mit relevanten Daten für 'username' zurückgeben
        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password(user.getPassword()) // falls in DB encoded gespeichert
            .roles(user.getRole()) // Rolle könnte auch aus DB kommen
            .build();
    }
}
