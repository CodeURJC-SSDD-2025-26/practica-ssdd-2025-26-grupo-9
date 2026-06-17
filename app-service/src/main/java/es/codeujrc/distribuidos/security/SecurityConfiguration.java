package es.codeujrc.distribuidos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.codeujrc.distribuidos.security.jwt.JwtRequestFilter;
import es.codeujrc.distribuidos.security.jwt.JwtTokenProvider;
import es.codeujrc.distribuidos.security.jwt.UnauthorizedHandlerJwt;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RepositoryUserDetailsService userDetailsService;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
                .securityMatcher("/api/**")
                .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/register", "/api/v1/auth/logout",
                        "/api/v1/auth/refresh")
                .permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/decks/*/commentaries/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/v1/decks/*/commentaries/**").permitAll()
                .requestMatchers(HttpMethod.DELETE, "/api/v1/decks/*/commentaries/**").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/v1/cards", "/api/v1/cards/**", "/api/v1/decks/**", "/api/v1/users/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/v1/cards", "/api/v1/cards/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/cards/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/cards/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/v1/decks", "/api/v1/decks/**").hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/v1/decks/**", "/api/v1/users/**")
                .hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/v1/users/**", "/api/v1/decks/**").hasRole("ADMIN")

                .anyRequest().authenticated());

        http.formLogin(formLogin -> formLogin.disable());
        http.csrf(csrf -> csrf.disable());
        http.httpBasic(httpBasic -> httpBasic.disable());
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(new JwtRequestFilter(userDetailsService, jwtTokenProvider),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain webFilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/", "/login", "/register", "/decks", "/social").permitAll()
                .requestMatchers("/card/*/image", "/user/*/image").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/style.css").permitAll()
                .requestMatchers("/addCards", "/adminCard", "/saveCard", "/deleteCard/**").hasRole("ADMIN")
                .requestMatchers("/adminUsers", "/editUserAdmin/**", "/deleteUser/**").hasRole("ADMIN")
                .requestMatchers("/profile", "/editUser", "/downloadMyDecks").hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers("/addDeck", "/saveDeck", "/admindeck/**", "/editDeck/**", "/deleteDeck/**")
                .hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers("/commentDeck/**", "/deleteComment/**").hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers("/user/*/follow", "/user/*/unfollow").hasAnyRole("REGISTERED", "ADMIN")
                .requestMatchers("/cardDetail").hasAnyRole("REGISTERED", "ADMIN")
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