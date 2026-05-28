package de.muehlencord.pfadm.template.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * control layout settings
 *
 * @author Joern Muehlencord, 2026-03-12
 * @since 0.2.0
 */
@Setter
@Getter
@Named
@SessionScoped
public class LayoutView implements Serializable {

  private static final long serialVersionUID = 1L;

  private String template;

  @Inject
  public LayoutView(PfAdmConfig pfAdmConfig) {
    this.template = pfAdmConfig.getTemplatePath();
  }

}
