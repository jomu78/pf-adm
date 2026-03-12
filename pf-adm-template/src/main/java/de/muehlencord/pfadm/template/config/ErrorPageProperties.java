package de.muehlencord.pfadm.template.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

/**
 * pre-defined error pages for PfAdmProperties
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Setter
@Getter
@Validated
public class ErrorPageProperties {

  private String page403 = "/403.xhtml";
  private String page404 = "/404.xhtml";
  private String page500 = "/500.xhtml";


}
