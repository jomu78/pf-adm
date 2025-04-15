package de.muehlencord.pfbs.template;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * TODO Add a short description of the class
 * 
 * @author Joern Muehlencord, 2025-04-15
 * @since TODO - add version
 */
@Named ("pfbsConfig")
@ApplicationScoped
public class PfbsConfig implements Serializable {

  public boolean isEnableSlideMenu() {
    return SpringContext.getBean(PfbsProperties.class).isEnableSlideMenu();
  }


}
