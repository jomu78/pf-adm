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
  SKIN_BLUE_LIGHT("skin-blue-light"),
  SKIN_BLACK("skin-black"),
  SKIN_BLACK_LIGHT("skin-black-light"),
  SKIN_GREEN("skin-green"),
  SKIN_GREEN_LIGHT("skin-green-light"),
  SKIN_RED("skin-red"),
  SKIN_RED_LIGHT("skin-red-light"),
  SKIN_YELLOW("skin-yellow"),
  SKIN_YELLOW_LIGHT("skin-yellow-light"),
  SKIN_PURPLE("skin-purple"),
  SKIN_PURPLE_LIGHT("skin-purple-light"),
  SKIN_TEAL("skin-teal"),
  SKIN_TEAL_LIGHT("skin-teal-light");

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
