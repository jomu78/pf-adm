package de.muehlencord.pfadm.template.config;

import jakarta.inject.Inject;
import org.springframework.boot.web.error.ErrorPage;
import org.springframework.boot.web.error.ErrorPageRegistrar;
import org.springframework.boot.web.error.ErrorPageRegistry;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;

/**
 * register error pages for SpringBoot
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {

  private final PfAdmProperties properties;

  @Inject
  public ErrorConfig(PfAdmProperties properties) {
    this.properties = properties;
  }

  @Override
  public void registerErrorPages(ErrorPageRegistry registry) {
    registry.addErrorPages(
      new ErrorPage(HttpStatus.FORBIDDEN, properties.getError().getPage403()),
      new ErrorPage(HttpStatus.NOT_FOUND, properties.getError().getPage404()),
      new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, properties.getError().getPage500())
    );
  }
}
