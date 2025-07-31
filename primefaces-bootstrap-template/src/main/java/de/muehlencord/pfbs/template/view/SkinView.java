package de.muehlencord.pfbs.template.view;

import de.muehlencord.pfbs.template.config.PfbsConfig;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;

import java.io.Serializable;

/**
 * Managed Bean for managing the current skin
 *
 * @author Joern Muehlencord, 2025-07-30
 * @since 0.1.0
 */
@Named("skinView")
@SessionScoped
public class SkinView implements Serializable {

  @Getter
  private final String skin;

  @Inject
  public SkinView(PfbsConfig config) {
    this.skin = config.getSkin();
  }

}
