package dev.LibraLoom.AppConfiguration;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class AppConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

         http    .sessionManagement(session -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // Default to stateles
                    session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                  .sessionFixation().none();})
                  .authorizeHttpRequests(Authorize -> Authorize.requestMatchers("/api/**", "/auth/**").permitAll()
                  .anyRequest().authenticated())
                //   .authorizeHttpRequests(Authorize -> Authorize.anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .addFilterBefore(new JwtTokenValidator(),BasicAuthenticationFilter.class)
                .csrf(AbstractHttpConfigurer::disable)
                .cors((cors) -> cors.configurationSource(corsConfigurationSource()))
                .httpBasic(Customizer.withDefaults())
                 ;
        return http.build();
    }

       private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration cfg = new CorsConfiguration();
            cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000/"));
            cfg.addAllowedOriginPattern("*");
            cfg.setAllowedMethods(Collections.singletonList("*"));
            cfg.setAllowedHeaders(Arrays.asList("*")); 
            cfg.setAllowCredentials(true);
            cfg.setExposedHeaders(Arrays.asList("Authorization"));
            cfg.setMaxAge(3600L);
            return cfg;
        };
    }

  

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
