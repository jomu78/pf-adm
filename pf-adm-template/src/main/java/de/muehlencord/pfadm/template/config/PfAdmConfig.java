package de.muehlencord.pfadm.template.config;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;

/**
 * application scoped bean proving access to the application properties
 * 
 * @author Joern Muehlencord, 2025-04-15
 * @since 0.1.0
 */
@Named ("pfAdmConfig")
@ApplicationScoped
public class PfAdmConfig implements Serializable {

  public String getIndexPage() {
    return SpringContext.getBean(PfAdmProperties.class).getIndexPage();
  }

  public boolean isRenderMessages() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderMessages();
  }

  public boolean isSkipMessageDetailIfEqualsSummary() {
    return SpringContext.getBean(PfAdmProperties.class).isSkipMessageDetailIfEqualsSummary();
  }

  public boolean isRenderBreadCrumb() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderBreadCrumb();
  }

  public boolean isEnableSlideMenu() {
    return SpringContext.getBean(PfAdmProperties.class).isEnableSlideMenu();
  }

  public String getSkin() {
    return SpringContext.getBean(PfAdmProperties.class).getSkin().getLabel();
  }

  public boolean isRenderMenuSearch() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderMenuSearch();
  }

  public boolean isRenderLogo() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderLogo();
  }

  public boolean isRenderBrandText() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderBrandText();
  }

  public boolean isRenderFullScreenToggle() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderFullScreenToggle();
  }

  public boolean isRenderSlideMenuToggle() {
    return SpringContext.getBean(PfAdmProperties.class).isRenderSlideMenuToggle();
  }



}
