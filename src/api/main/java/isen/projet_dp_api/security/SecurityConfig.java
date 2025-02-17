package isen.projet_dp_api.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    @Profile("dev")
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        log.info("Running in dev mode");
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/**").permitAll() // Autoriser l'accès à Swagger
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }

    @Bean
    @Profile("!dev")
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Running in prod mode");
        http
                .requiresChannel(channel -> channel
                        .anyRequest().requiresSecure() // Forcer l'utilisation de HTTPS
                )
                .csrf(AbstractHttpConfigurer::disable) // Using csrf(Customizer) to disable CSRF
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**").permitAll() // Autoriser les requêtes sans JWT pour l'endpoint de connexion
                        .anyRequest().authenticated() // Exiger une authentification pour toutes les autres requêtes
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        return http.build();
    }
}
