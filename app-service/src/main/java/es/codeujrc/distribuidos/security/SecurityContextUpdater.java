package es.codeujrc.distribuidos.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.List;
import es.codeujrc.distribuidos.entity.User;

@Component 
public class SecurityContextUpdater {

    public void updateSession(User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (auth != null) {
            String role = "ROLE_" + user.getRole().name();
            
            UserDetails userDetails = org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(List.of(new SimpleGrantedAuthority(role)))
                    .build();

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                    userDetails, 
                    auth.getCredentials(),
                    userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(newAuth);
        }
    }
}