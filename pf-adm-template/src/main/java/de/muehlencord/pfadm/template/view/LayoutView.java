package de.muehlencord.pfadm.template.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

/**
 * control layout settings
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Named
@SessionScoped
public class LayoutView implements Serializable {

  private final PfAdmConfig pfAdmConfig;

  @Inject
  public LayoutView(PfAdmConfig pfAdmConfig) {
    this.pfAdmConfig = pfAdmConfig;
  }

  public String getTemplate() {
    return pfAdmConfig.getTemplatePath();
  }

  public void setTemplate(String template) {
    pfAdmConfig.setTemplatePath (template);

  }
}
