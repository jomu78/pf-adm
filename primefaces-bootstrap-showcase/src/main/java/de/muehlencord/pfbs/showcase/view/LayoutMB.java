package de.muehlencord.pfbs.showcase.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.Setter;

/**
 * bean controlling used layout options.
 *
 * @author Joern Muehlencord, 2025-05-05
 * @since 0.1.0
 */
@ViewScoped
@Named("layoutMB")
@Getter
@Setter
public class LayoutMB {

  private boolean borderless = false;

}
