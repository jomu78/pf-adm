package de.muehlencord.pfadm.template.config;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * enumeration of available skins
 *
 * @author Joern Muehlencord, 2025-07-30
 * @since 0.1.0
 */
public enum SkinEnum {

  SKIN_BLUE("skin-blue"),
  SKIN_GREEN("skin-green");

  private final static Map<String, SkinEnum>  LABEL_MAP = new HashMap();

  static {
    for (SkinEnum skin : SkinEnum.values()) {
      LABEL_MAP.put(skin.label, skin);
    }
  }

  public static SkinEnum fromLabel(String label) {
    if (label == null) {
      return null;
    }
    return LABEL_MAP.get(label);
  }

  @Getter
  private String label;

  SkinEnum(String label) {
    this.label = label;
  }


}
