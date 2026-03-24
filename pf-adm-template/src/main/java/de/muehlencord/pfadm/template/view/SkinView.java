package de.muehlencord.pfadm.template.view;

import de.muehlencord.pfadm.template.config.SkinEnum;
import de.muehlencord.pfadm.template.config.PfAdmConfig;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import org.springframework.util.StringUtils;

/**
 * Managed Bean for managing the current skin
 *
 * @author Joern Muehlencord, 2025-07-30
 * @since 0.1.0
 */
@Named("skinView")
@SessionScoped
public class SkinView implements Serializable {

  private final PfAdmConfig config;
  private String skin;

  @Inject
  public SkinView(PfAdmConfig config) {
    this.config = config;
  }

  @PostConstruct
  public void init() {
    skin = config.getSkin();
  }

  public String getSkin() {
    if (!StringUtils.hasText(skin)) {
      skin = config.getSkin();
    }
    return skin;
  }

  public void setSkin(String skin) {
    if (!StringUtils.hasText(skin)) {
      this.skin = config.getSkin();
      return;
    }

    var resolvedSkin = SkinEnum.fromLabel(skin);
    this.skin = resolvedSkin != null ? resolvedSkin.getLabel() : config.getSkin();
  }

}
