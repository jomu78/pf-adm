package de.muehlencord.pfadm.template.view;

import jakarta.enterprise.context.SessionScoped;
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
@Named
@SessionScoped
@Getter
@Setter
public class LayoutView implements Serializable {

  private String template = "/admin.xhtml";
}
