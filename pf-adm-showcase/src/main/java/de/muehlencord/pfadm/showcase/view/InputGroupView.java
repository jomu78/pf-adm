package de.muehlencord.pfadm.showcase.view;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;

/**
 * backing bean for input group showcase page
 *
 * @author Joern Muehlencord, 2026-03-20
 * @since 0.2.0
 */
@Named
@ViewScoped
@Getter
@Setter
public class InputGroupView implements Serializable {

  private String emailLocalPart = "team";
  private String amount = "149.00";
  private String city = "Berlin";
  private String contactChannel = "Email";
  private String searchQuery = "PrimeFaces Admin";
  private boolean alertsEnabled = true;
}
