package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * backing bean for toolbar showcase page
 *
 * @author Joern Muehlencord, 2026-03-20
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class ToolbarView implements Serializable {

  private String searchQuery = "customer success";
  private String status = "Active";
  private String scope = "All teams";
}
