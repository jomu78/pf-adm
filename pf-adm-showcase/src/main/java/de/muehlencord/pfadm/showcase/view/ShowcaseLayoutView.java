package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import de.muehlencord.pfadm.template.view.LayoutView;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * bean controlling used layout options.
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@ViewScoped
@Named
@Getter
@Setter
public class ShowcaseLayoutView extends LayoutView implements Serializable {

  private boolean borderless = false;

  @Inject
  public ShowcaseLayoutView(PfAdmConfig pfAdmConfig) {
    super(pfAdmConfig);
  }
}
