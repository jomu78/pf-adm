package de.muehlencord.pfbs.template.config;

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

  public String getIndexPage() {
    return SpringContext.getBean(PfbsProperties.class).getIndexPage();
  }

  public boolean isRenderMessages() {
    return SpringContext.getBean(PfbsProperties.class).isRenderMessages();
  }

  public boolean isSkipMessageDetailIfEqualsSummary() {
    return SpringContext.getBean(PfbsProperties.class).isSkipMessageDetailIfEqualsSummary();
  }


  public boolean isEnableSlideMenu() {
    return SpringContext.getBean(PfbsProperties.class).isEnableSlideMenu();
  }

  public String getSkin() {
    return SpringContext.getBean(PfbsProperties.class).getSkin().getLabel();
  }

  public boolean isRenderMenuSearch() {
    return SpringContext.getBean(PfbsProperties.class).isRenderMenuSearch();
  }

  public boolean isRenderLogo() {
    return SpringContext.getBean(PfbsProperties.class).isRenderLogo();
  }

  public boolean isRenderBrandText() {
    return SpringContext.getBean(PfbsProperties.class).isRenderBrandText();
  }

  public boolean isRenderFullScreenToggle() {
    return SpringContext.getBean(PfbsProperties.class).isRenderFullScreenToggle();
  }



}
