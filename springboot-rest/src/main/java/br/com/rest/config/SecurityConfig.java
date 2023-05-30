package br.com.rest.config;

import br.com.rest.securityJwt.JwtConfigurer;
import br.com.rest.securityJwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(
            AuthenticationConfiguration configuration
    ) throws Exception{
        return configuration.getAuthenticationManager();
    }

    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(
                        sessionManagement -> sessionManagement
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(
                        authorizeHttpRequests -> authorizeHttpRequests
                                .requestMatchers(
                                        "/auth/signin",
                                        "/auth/refresh/**",
                                        "/swagger-ui/**",
                                        "v3/api-docs/**"
                                ).permitAll()
                                .requestMatchers("/api/**").authenticated()
                                .requestMatchers("/users").denyAll()
                )
                .apply(new JwtConfigurer(tokenProvider));
    }
}
