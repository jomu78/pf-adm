package de.muehlencord.pfbs.template;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * application scoped bean proving access to the application properties
 * 
 * @author Joern Muehlencord, 2025-04-15
 * @since 0.1.0
 */
@Named ("pfbsConfig")
@ApplicationScoped
public class PfbsConfig implements Serializable {

  public boolean isEnableSlideMenu() {
    return SpringContext.getBean(PfbsProperties.class).isEnableSlideMenu();
  }


}
