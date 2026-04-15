package es.codeujrc.distribuidos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register", "/social", "/decks", "/cardDetail").permitAll()
                .requestMatchers("/card/*/image", "/user/*/image").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/style.css").permitAll()
                .requestMatchers("/profile", "/editUser", "/addDeck").hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers("/adminUsers", "/editUserAdmin", "/addCards", "/adminCard").hasRole("ADMIN")
                .anyRequest().authenticated());

        http.formLogin(form -> form
                .loginPage("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/", true)
                .failureUrl("/login?errorlogin=true")
                .permitAll());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .permitAll());
        
        http.exceptionHandling(exception -> exception
        .accessDeniedPage("/error/403"));
        return http.build();
    }
}