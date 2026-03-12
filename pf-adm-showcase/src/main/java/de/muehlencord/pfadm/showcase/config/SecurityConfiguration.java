package de.muehlencord.pfadm.showcase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;

/**
 * configuzre security filter chain
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Configuration
public class SecurityConfiguration {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http)  {
    PathPatternRequestMatcher.Builder pathPattern = PathPatternRequestMatcher.withDefaults();
    return http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers(
          pathPattern.matcher("/web/denied.xhtml")
        ).denyAll()
        .requestMatchers(
          pathPattern.matcher("/**")
        ).permitAll()
        .anyRequest().permitAll()
      ).build();
  }
}
