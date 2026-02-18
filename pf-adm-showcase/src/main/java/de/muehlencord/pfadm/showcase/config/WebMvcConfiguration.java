package de.muehlencord.pfadm.showcase.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configure Spring MVC
 *
 * @author Joern Muehlencord, 2025-04-14
 * @since 0.1.0
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {


  @Bean("messageSource")
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasenames("messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry
      .addRedirectViewController("/", "/web/index.xhtml")
      .setStatusCode(HttpStatus.PERMANENT_REDIRECT)
      .setContextRelative(true);
    registry
      .addRedirectViewController("/web", "/web/index.xhtml")
      .setStatusCode(HttpStatus.PERMANENT_REDIRECT)
      .setContextRelative(true);
    registry
      .addRedirectViewController("/web/", "/web/index.xhtml")
      .setStatusCode(HttpStatus.PERMANENT_REDIRECT)
      .setContextRelative(true);
    registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
  }

  @Override
  public void configurePathMatch(PathMatchConfigurer configurer) {
    configurer.addPathPrefix("api", HandlerTypePredicate.forAnnotation(RestController.class));
  }
}
