package de.muehlencord.pfadm.showcase.view;

import de.muehlencord.pfadm.template.config.PfAdmConfig;
import de.muehlencord.pfadm.template.config.SkinEnum;
import de.muehlencord.pfadm.template.view.LayoutView;
import jakarta.faces.model.SelectItem;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
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
  private final PfAdmConfig pfAdmConfig;

  @Inject
  public ShowcaseLayoutView(PfAdmConfig pfAdmConfig) {
    super(pfAdmConfig);
    this.pfAdmConfig = pfAdmConfig;
  }

  public List<SelectItem> getAvailableSkins() {
    return Arrays.stream(SkinEnum.values())
      .map(skin -> new SelectItem(skin.getLabel(), toDisplayName(skin.getLabel())))
      .toList();
  }

  public String getDefaultSkin() {
    return pfAdmConfig.getSkin();
  }

  private String toDisplayName(String skinLabel) {
    return skinLabel.replace("skin-", "")
      .replace("-light", " light")
      .replace('-', ' ');
  }
}
